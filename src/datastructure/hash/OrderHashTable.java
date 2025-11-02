package datastructure.hash;

import models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderHashTable {
    private OrderNode[] table;
    private int size;
    private int capacity;
    private static final double LOAD_FACTOR = 0.75;

    public OrderHashTable() {
        this.capacity = 16; // initial capacity
        this.table = new OrderNode[capacity];
        this.size = 0;
    }

    /**
     * Hash function to compute the index for a given key.
     * It uses a simple polynomial rolling hash function.
     * 
     * @param key
     * @return index in the hash table
     */
    private int hash(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            /**
             * For each character, multiply current hash by 31 and add character code
             */
            hash = (hash * 31 + key.charAt(i)) % capacity;
        }
        return Math.abs(hash);
    }

    /**
     * Resize the hash table when load factor exceeds threshold
     */
    private void resize() {
        int oldCapacity = capacity;
        capacity *= 2;
        OrderNode[] oldTable = table;
        table = new OrderNode[capacity];
        size = 0;

        /**
         * Rehash all existing entries into new table
         */
        for (int i = 0; i < oldCapacity; i++) {
            OrderNode current = oldTable[i];
            while (current != null) {
                put(current.key, current.value);
                current = current.next;
            }
        }
    }

    /**
     * Insert or update an order in the hash table.
     * 
     * @param key
     * @param value
     */
    public void put(String key, Order value) {
        /**
         * Check load factor and resize if necessary
         */
        if ((double) size / capacity >= LOAD_FACTOR) {
            resize();
        }

        /**
         * Compute index and insert/update the order
         */
        int index = hash(key);
        OrderNode newNode = new OrderNode(key, value);

        if (table[index] == null) {
            table[index] = newNode;
            size++;
        } else {
            /**
             * Handle collision with separate chaining
             */
            OrderNode current = table[index];
            while (current != null) {
                /**
                 * Update existing key
                 */
                if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }
                /**
                 * Move to next node until end of chain
                 */
                if (current.next == null)
                    break;
                current = current.next;
            }
            /**
             * Append new node at the end of the chain
             */
            current.next = newNode;
            size++;
        }
    }

    /**
     * Retrieve an order by key.
     * 
     * @param key
     * @return Order object or null if not found
     */
    public Order get(String key) {
        int index = hash(key);
        OrderNode current = table[index];

        /**
         * Traverse the chain to find the key
         */
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Remove an order by key.
     * 
     * @param key
     * @return true if removed, false if not found
     */
    public boolean remove(String key) {
        int index = hash(key);
        OrderNode current = table[index];
        OrderNode prev = null;

        /**
         * Traverse the chain to find and remove the key
         */
        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        /**
         * Traverse entire table and collect all orders
         */
        for (int i = 0; i < capacity; i++) {
            OrderNode current = table[i];
            while (current != null) {
                orders.add(current.value);
                current = current.next;
            }
        }
        return orders;
    }

    public int getSize() {
        return size;
    }
}
