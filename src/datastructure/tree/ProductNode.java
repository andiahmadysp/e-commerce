package datastructure.tree;

import models.Product;

class ProductNode {
    Product product;
    ProductNode left, right;

    public ProductNode(Product product) {
        this.product = product;
    }
}
