package datastructure.tree;

import models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductBST {
    private ProductNode root;
    private int size;

    /**
     * Insert a product into the BST.
     * 
     * @param product
     */
    public void insert(Product product) {
        root = insertRec(root, product);
        size++;
    }

    /**
     * Recursive helper to insert a product into the BST.
     * 
     * @param node
     * @param product
     * @return Updated node
     */
    private ProductNode insertRec(ProductNode node, Product product) {
        /**
         * If node is null, create new node
         */
        if (node == null) {
            return new ProductNode(product);
        }

        /**
         * Traverse left or right based on product ID
         */
        if (product.getId().compareTo(node.product.getId()) < 0) {
            node.left = insertRec(node.left, product);
        } else if (product.getId().compareTo(node.product.getId()) > 0) {
            node.right = insertRec(node.right, product);
        }

        return node;
    }

    /**
     * Search for a product by ID.
     * Search is done depth-first.
     * 
     * @param id
     * @return Product object or null if not found
     */
    public Product search(String id) {
        return searchRec(root, id);
    }

    /**
     * Recursive helper to search for a product by ID.
     * Search is done depth-first.
     * 
     * @param node
     * @param id
     * @return Product object or null if not found
     */
    private Product searchRec(ProductNode node, String id) {
        /**
         * Base case: node is null
         */
        if (node == null)
            return null;

        /**
         * Check if current node matches ID
         */
        if (id.equals(node.product.getId())) {
            return node.product;
        }

        /**
         * Traverse left or right based on ID comparison
         */
        if (id.compareTo(node.product.getId()) < 0) {
            return searchRec(node.left, id);
        }
        return searchRec(node.right, id);
    }

    /**
     * Search for products within a price range.
     * Search is done depth-first.
     * 
     * @param minPrice
     * @param maxPrice
     * @return List of products within the price range
     */
    public List<Product> searchInRange(double minPrice, double maxPrice) {
        List<Product> result = new ArrayList<>();
        searchInRangeRec(root, minPrice, maxPrice, result);
        return result;
    }

    /**
     * Recursive helper to search for products within a price range.
     * Search is done depth-first.
     * 
     * @param node
     * @param minPrice
     * @param maxPrice
     * @param result
     */
    private void searchInRangeRec(ProductNode node, double minPrice, double maxPrice, List<Product> result) {
        /**
         * Base case: node is null
         */
        if (node == null)
            return;

        /**
         * Traverse left subtree
         */
        searchInRangeRec(node.left, minPrice, maxPrice, result);

        /**
         * Check if current node's product price is within range
         */
        if (node.product.getPrice() >= minPrice && node.product.getPrice() <= maxPrice) {
            /**
             * Add product to result list
             */
            result.add(node.product);
        }

        /**
         * Traverse right subtree
         */
        searchInRangeRec(node.right, minPrice, maxPrice, result);
    }

    /**
     * In-order traversal to get products in sorted order.
     * 
     * @param products
     */
    public void inOrderTraversal(List<Product> products) {
        inOrderRec(root, products);
    }

    /**
     * Recursive helper for in-order traversal.
     * 
     * @param node
     * @param products
     */
    private void inOrderRec(ProductNode node, List<Product> products) {
        if (node != null) {
            /**
             * Traverse left, visit node, traverse right
             */
            inOrderRec(node.left, products);
            products.add(node.product);
            inOrderRec(node.right, products);
        }
    }

    /**
     * Find product with minimum ID.
     * 
     * @return Product with minimum ID
     */
    public Product findMin() {
        return findMinRec(root);
    }

    /**
     * Recursive helper to find product with minimum ID.
     * 
     * @param node
     * @return Product with minimum ID
     */
    private Product findMinRec(ProductNode node) {
        if (node == null)
            return null;
        /**
         * Traverse left until null
         */
        if (node.left == null)
            return node.product;
        return findMinRec(node.left);
    }

    /**
     * Find product with maximum ID.
     * 
     * @return Product with maximum ID
     */
    public Product findMax() {
        return findMaxRec(root);
    }

    /**
     * Recursive helper to find product with maximum ID.
     * 
     * @param node
     * @return Product with maximum ID
     */
    private Product findMaxRec(ProductNode node) {
        if (node == null)
            return null;
        /**
         * Traverse right until null
         */
        if (node.right == null)
            return node.product;
        return findMaxRec(node.right);
    }

    /**
     * Delete a product by ID.
     * Will look for the product depth-first. 
     *
     * @param id
     * @return true if deleted, false if not found
     */
    public boolean delete(String id) {
        int initialSize = size;
        root = deleteRec(root, id);
        return size < initialSize;
    }

    private ProductNode deleteRec(ProductNode node, String id) {
        if (node == null)
            return null;

        /**
         * Traverse left or right based on ID comparison
         */
        if (id.compareTo(node.product.getId()) < 0) {
            node.left = deleteRec(node.left, id);
        } else if (id.compareTo(node.product.getId()) > 0) {
            node.right = deleteRec(node.right, id);
        } else {
            /**
             * Node is found - perform deletion
             */
            size--;

            /**
             * If node has no left child, return right child
             */
            if (node.left == null)
                return node.right;

            /**
             * If node has no right child, return left child
             */
            if (node.right == null)
                return node.left;

            /**
             * Two children - find inorder successor (min in right subtree)
             */
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
