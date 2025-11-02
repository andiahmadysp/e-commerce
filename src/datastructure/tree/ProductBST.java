package datastructure.tree;

import models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductBST {
    private ProductNode root;
    private int size;

    public void insert(Product product) {
        root = insertRec(root, product);
        size++;
    }

    private ProductNode insertRec(ProductNode node, Product product) {
        if (node == null) {
            return new ProductNode(product);
        }

        if (product.getId().compareTo(node.product.getId()) < 0) {
            node.left = insertRec(node.left, product);
        } else if (product.getId().compareTo(node.product.getId()) > 0) {
            node.right = insertRec(node.right, product);
        }

        return node;
    }

    public Product search(String id) {
        return searchRec(root, id);
    }

    private Product searchRec(ProductNode node, String id) {
        if (node == null) return null;

        if (id.equals(node.product.getId())) {
            return node.product;
        }

        if (id.compareTo(node.product.getId()) < 0) {
            return searchRec(node.left, id);
        }
        return searchRec(node.right, id);
    }

    public List<Product> searchInRange(double minPrice, double maxPrice) {
        List<Product> result = new ArrayList<>();
        searchInRangeRec(root, minPrice, maxPrice, result);
        return result;
    }

    private void searchInRangeRec(ProductNode node, double minPrice, double maxPrice, List<Product> result) {
        if (node == null) return;

        searchInRangeRec(node.left, minPrice, maxPrice, result);

        if (node.product.getPrice() >= minPrice && node.product.getPrice() <= maxPrice) {
            result.add(node.product);
        }

        searchInRangeRec(node.right, minPrice, maxPrice, result);
    }

    public void inOrderTraversal(List<Product> products) {
        inOrderRec(root, products);
    }

    private void inOrderRec(ProductNode node, List<Product> products) {
        if (node != null) {
            inOrderRec(node.left, products);
            products.add(node.product);
            inOrderRec(node.right, products);
        }
    }

    public Product findMin() {
        return findMinRec(root);
    }

    private Product findMinRec(ProductNode node) {
        if (node == null) return null;
        if (node.left == null) return node.product;
        return findMinRec(node.left);
    }

    public Product findMax() {
        return findMaxRec(root);
    }

    private Product findMaxRec(ProductNode node) {
        if (node == null) return null;
        if (node.right == null) return node.product;
        return findMaxRec(node.right);
    }

    public boolean delete(String id) {
        int initialSize = size;
        root = deleteRec(root, id);
        return size < initialSize;
    }

    private ProductNode deleteRec(ProductNode node, String id) {
        if (node == null) return null;

        if (id.compareTo(node.product.getId()) < 0) {
            node.left = deleteRec(node.left, id);
        } else if (id.compareTo(node.product.getId()) > 0) {
            node.right = deleteRec(node.right, id);
        } else {
            size--;

            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            Product minRight = findMinRec(node.right);
            node.product = minRight;
            node.right = deleteRec(node.right, minRight.getId());
            size++;
        }

        return node;
    }

    public int getSize() {
        return size;
    }
}
