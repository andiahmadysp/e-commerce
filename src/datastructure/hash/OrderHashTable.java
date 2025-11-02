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
        this.capacity = 16;
        this.table = new OrderNode[capacity];
        this.size = 0;
    }

    private int hash(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash * 31 + key.charAt(i)) % capacity;
        }
        return Math.abs(hash);
    }

    private void resize() {
        int oldCapacity = capacity;
        capacity *= 2;
        OrderNode[] oldTable = table;
        table = new OrderNode[capacity];
        size = 0;

        for (int i = 0; i < oldCapacity; i++) {
            OrderNode current = oldTable[i];
            while (current != null) {
                put(current.key, current.value);
                current = current.next;
            }
        }
    }

    public void put(String key, Order value) {
        if ((double) size / capacity >= LOAD_FACTOR) {
            resize();
        }

        int index = hash(key);
        OrderNode newNode = new OrderNode(key, value);

        if (table[index] == null) {
            table[index] = newNode;
            size++;
        } else {
            OrderNode current = table[index];
            while (current != null) {
                if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }
                if (current.next == null) break;
                current = current.next;
            }
            current.next = newNode;
            size++;
        }
    }

    public Order get(String key) {
        int index = hash(key);
        OrderNode current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public boolean remove(String key) {
        int index = hash(key);
        OrderNode current = table[index];
        OrderNode prev = null;

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
