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
        return findMinByPrice(root);
    }

    private Product findMinByPrice(ProductNode node) {
        if (node == null) return null;

        Product minProduct = node.product;

        Product leftMin = findMinByPrice(node.left);
        if (leftMin != null && leftMin.getPrice() < minProduct.getPrice()) {
            minProduct = leftMin;
        }

        Product rightMin = findMinByPrice(node.right);
        if (rightMin != null && rightMin.getPrice() < minProduct.getPrice()) {
            minProduct = rightMin;
        }

        return minProduct;
    }

    public Product findMax() {
        return findMaxByPrice(root);
    }

    private Product findMaxByPrice(ProductNode node) {
        if (node == null) return null;

        Product maxProduct = node.product;

        Product leftMax = findMaxByPrice(node.left);
        if (leftMax != null && leftMax.getPrice() > maxProduct.getPrice()) {
            maxProduct = leftMax;
        }

        Product rightMax = findMaxByPrice(node.right);
        if (rightMax != null && rightMax.getPrice() > maxProduct.getPrice()) {
            maxProduct = rightMax;
        }

        return maxProduct;
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

            Product minRight = findMinById(node.right);
            node.product = minRight;
            node.right = deleteRec(node.right, minRight.getId());
            size++;
        }

        return node;
    }

    private Product findMinById(ProductNode node) {
        if (node == null) return null;
        if (node.left == null) return node.product;
        return findMinById(node.left);
    }

    public int getSize() {
        return size;
    }
}