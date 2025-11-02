package datastructure.hash;

import models.Order;

class OrderNode {
    String key;
    Order value;
    OrderNode next;

    public OrderNode(String key, Order value) {
        this.key = key;
        this.value = value;
    }
}
