package system;

import datastructure.graph.ShippingGraph;
import datastructure.hash.OrderHashTable;
import datastructure.tree.ProductBST;
import models.Order;
import models.Product;
import utils.CSVLoader;

import java.text.NumberFormat;
import java.util.*;

public class ECommerceSystem {
    private ProductBST productTree;
    private OrderHashTable orderTable;
    private ShippingGraph shippingGraph;
    private int orderCounter;

    public ECommerceSystem() {
        this.productTree = new ProductBST();
        this.orderTable = new OrderHashTable();
        this.shippingGraph = new ShippingGraph();
        this.orderCounter = 1000;
    }

    /**
     * Add a new product to the system.
     * 
     * @param id
     * @param name
     * @param category
     * @param price
     * @param stock
     */
    public void addProduct(String id, String name, String category, double price, int stock) {
        if (productTree.search(id) != null) {
            System.out.println("Produk dengan ID " + id + " sudah ada!");
            return;
        }

        Product product = new Product(id, name, category, price, stock);

        /**
         * Insert product into BST
         */
        productTree.insert(product);
        System.out.println("\nProduk berhasil ditambahkan!");
        product.displayInfo();
    }

    public void searchProduct(String id) {
        long startTime = System.nanoTime();
        /**
         * Search product in BST by ID
         * Search is done depth-first
         */
        Product product = productTree.search(id);
        long endTime = System.nanoTime();

        if (product != null) {
            System.out.println("\nProduk ditemukan!");
            product.displayInfo();
            System.out.println("\n⏱ Waktu pencarian: " + (endTime - startTime) + " ns");
        } else {
            System.out.println("\nProduk tidak ditemukan!");
        }
    }

    /**
     * Search products within a price range.
     * 
     * @param minPrice
     * @param maxPrice
     */
    public void searchProductsInRange(double minPrice, double maxPrice) {
        /**
         * Search products in BST within price range
         * Search is done depth-first
         */
        List<Product> products = productTree.searchInRange(minPrice, maxPrice);

        if (products.isEmpty()) {
            System.out.println("\nTidak ada produk dalam range harga tersebut.");
            return;
        }

        /**
         * Display formatting
         */
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        System.out.println("\n" + "═".repeat(80));
        System.out.println("PRODUK DALAM RANGE: " + currencyFormat.format(minPrice) +
                " - " + currencyFormat.format(maxPrice));
        System.out.println("═".repeat(80));
        System.out.printf("%-8s %-30s %-20s %15s %8s%n", "ID", "NAMA", "KATEGORI", "HARGA", "STOK");
        System.out.println("─".repeat(80));

        for (Product p : products) {
            System.out.printf("%-8s %-30s %-20s %15s %8d%n",
                    p.getId(), p.getName(), p.getCategory(),
                    currencyFormat.format(p.getPrice()), p.getStock());
        }
        System.out.println("═".repeat(80));
        System.out.println("Total: " + products.size() + " produk");
    }

    /**
     * Update product stock by ID.
     * 
     * @param id
     * @param newStock
     */
    public void updateProductStock(String id, int newStock) {
        /**
         * Search product in BST by ID
         * Search is done depth-first
         */
        Product product = productTree.search(id);
        if (product != null) {
            product.setStock(newStock);
            System.out.println("Stok produk berhasil diupdate!");
            product.displayInfo();
        } else {
            System.out.println("Produk tidak ditemukan!");
        }
    }

    /**
     * Delete product by ID.
     * 
     * @param id
     */
    public void deleteProduct(String id) {
        if (productTree.delete(id)) {
            System.out.println("Produk berhasil dihapus!");
        } else {
            System.out.println("Produk tidak ditemukan!");
        }
    }

    public void displayAllProducts() {
        List<Product> products = new ArrayList<>();
        /**
         * In-order traversal to get products sorted by price
         * Search is done depth-first.
         * Assign to products variable as list
         */
        productTree.inOrderTraversal(products);

        if (products.isEmpty()) {
            System.out.println("\nBelum ada produk.");
            return;
        }

        /**
         * Display formatting
         */
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        System.out.println("\n" + "═".repeat(80));
        System.out.println("KATALOG PRODUK (TERURUT BY ID)");
        System.out.println("═".repeat(80));
        System.out.printf("%-8s %-30s %-20s %15s %8s%n", "ID", "NAMA", "KATEGORI", "HARGA", "STOK");
        System.out.println("─".repeat(80));

        for (Product p : products) {
            System.out.printf("%-8s %-30s %-20s %15s %8d%n",
                    p.getId(), p.getName(), p.getCategory(),
                    currencyFormat.format(p.getPrice()), p.getStock());
        }
        System.out.println("═".repeat(80));
        System.out.println("Total: " + products.size() + " produk");
    }

    public void displayPriceExtremes() {
        /**
         * Find products with minimum and maximum price
         */
        Product cheapest = productTree.findMin();
        Product expensive = productTree.findMax();

        /**
         * Display formatting
         */
        System.out.println("\n" + "═".repeat(60));
        System.out.println("PRODUK TERMURAH & TERMAHAL");
        System.out.println("═".repeat(60));

        if (cheapest != null) {
            System.out.println("\nTERMURAH:");
            cheapest.displayInfo();
        }

        if (expensive != null) {
            System.out.println("\nTERMAHAL:");
            expensive.displayInfo();
        }
        System.out.println("═".repeat(60));
    }

    /**
     * Create a new order.
     * 
     * @param customerName
     * @param productId
     * @param quantity
     * @param shippingCity
     */
    public void createOrder(String customerName, String productId, int quantity, String shippingCity) {
        /**
         * Search product in BST by ID
         * Search is done depth-first
         */
        Product product = productTree.search(productId);

        if (product == null) {
            System.out.println("Produk tidak ditemukan!");
            return;
        }

        if (product.getStock() < quantity) {
            System.out.println("Stok tidak mencukupi! Stok tersedia: " + product.getStock());
            return;
        }

        /**
         * Generate unique order ID
         */
        String orderId = "ORD" + (orderCounter++);
        double totalPrice = product.getPrice() * quantity;

        /**
         * Create order and insert into hash table
         */
        Order order = new Order(orderId, customerName, productId, quantity, totalPrice, shippingCity);
        orderTable.put(orderId, order);

        /**
         * Update product stock
         */
        product.setStock(product.getStock() - quantity);

        System.out.println("\nPesanan berhasil dibuat!");
        order.displayInfo();
    }

    /**
     * Search for an order by ID.
     * 
     * @param orderId
     */
    public void searchOrder(String orderId) {
        long startTime = System.nanoTime();
        /**
         * Search order in hash table with orderId as key
         */
        Order order = orderTable.get(orderId);
        long endTime = System.nanoTime();

        if (order != null) {
            System.out.println("\nPesanan ditemukan!");
            order.displayInfo();
            System.out.println("\n⏱ Waktu pencarian: " + (endTime - startTime) + " ns");
        } else {
            System.out.println("\nPesanan tidak ditemukan!");
        }
    }

    /**
     * Update order status by order ID.
     * 
     * @param orderId
     * @param status
     */
    public void updateOrderStatus(String orderId, String status) {
        /**
         * Search order in hash table with orderId as key
         */
        Order order = orderTable.get(orderId);
        if (order != null) {
            /**
             * Update order status
             */
            order.setStatus(status);
            System.out.println("Status pesanan berhasil diupdate!");
            order.displayInfo();
        } else {
            System.out.println("Pesanan tidak ditemukan!");
        }
    }

    /**
     * Cancel an order by ID.
     * 
     * @param orderId
     */
    public void cancelOrder(String orderId) {
        /**
         * Search order in hash table with orderId as key
         */
        Order order = orderTable.get(orderId);
        if (order != null) {
            /**
             * Restore product stock
             */
            Product product = productTree.search(order.getProductId());
            if (product != null) {
                product.setStock(product.getStock() + order.getQuantity());
            }

            /**
             * Remove order from hash table
             */
            orderTable.remove(orderId);
            System.out.println("Pesanan berhasil dibatalkan dan stok dikembalikan!");
        } else {
            System.out.println("Pesanan tidak ditemukan!");
        }
    }

    /**
     * Display all orders in the system.
     */
    public void displayAllOrders() {
        /**
         * Get all orders from hash table as list
         */
        List<Order> orders = orderTable.getAllOrders();

        if (orders.isEmpty()) {
            System.out.println("\nBelum ada pesanan.");
            return;
        }

        /**
         * Display formatting
         */
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        System.out.println("\n" + "═".repeat(90));
        System.out.println("DAFTAR SEMUA PESANAN");
        System.out.println("═".repeat(90));
        System.out.printf("%-10s %-20s %-10s %-8s %15s %-12s%n",
                "ORDER ID", "CUSTOMER", "PRODUK", "QTY", "TOTAL", "STATUS");
        System.out.println("─".repeat(90));

        for (Order o : orders) {
            System.out.printf("%-10s %-20s %-10s %-8d %15s %-12s%n",
                    o.getOrderId(), o.getCustomerName(), o.getProductId(),
                    o.getQuantity(), currencyFormat.format(o.getTotalPrice()), o.getStatus());
        }
        System.out.println("═".repeat(90));
        System.out.println("Total: " + orders.size() + " pesanan");
    }

    /**
     * Display orders filtered by status.
     * 
     * @param status
     */
    public void displayOrdersByStatus(String status) {
        List<Order> allOrders = orderTable.getAllOrders();
        List<Order> filteredOrders = new ArrayList<>();

        /**
         * Filter orders by status
         */
        for (Order order : allOrders) {
            if (order.getStatus().equals(status)) {
                filteredOrders.add(order);
            }
        }

        if (filteredOrders.isEmpty()) {
            System.out.println("\nTidak ada pesanan dengan status " + status);
            return;
        }

        /**
         * Display formatting
         */
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        System.out.println("\n" + "═".repeat(90));
        System.out.println("PESANAN STATUS: " + status);
        System.out.println("═".repeat(90));
        System.out.printf("%-10s %-20s %-10s %-8s %15s %-15s%n",
                "ORDER ID", "CUSTOMER", "PRODUK", "QTY", "TOTAL", "TUJUAN");
        System.out.println("─".repeat(90));

        for (Order o : filteredOrders) {
            System.out.printf("%-10s %-20s %-10s %-8d %15s %-15s%n",
                    o.getOrderId(), o.getCustomerName(), o.getProductId(),
                    o.getQuantity(), currencyFormat.format(o.getTotalPrice()), o.getShippingCity());
        }
        System.out.println("═".repeat(90));
        System.out.println("Total: " + filteredOrders.size() + " pesanan");
    }

    /**
     * Add a new city/hub to the shipping graph.
     * 
     * @param city
     */
    public void addCity(String city) {
        shippingGraph.addCity(city);
        System.out.println("Kota/Hub " + city + " berhasil ditambahkan!");
    }

    /**
     * Add a new route between two cities/hubs.
     * 
     * @param from
     * @param to
     * @param distance
     */
    public void addRoute(String from, String to, int distance) {
        shippingGraph.addRoute(from, to, distance);
        System.out.println("Rute " + from + " ↔ " + to + " (" + distance + " km) berhasil ditambahkan!");
    }

    /**
     * Find the shortest delivery path between two cities/hubs.
     * 
     * @param from
     * @param to
     */
    public void findShortestDeliveryPath(String from, String to) {
        /**
         * Construct shortest path using Dijkstra's algorithm
         */
        List<String> path = shippingGraph.getPath(from, to);

        if (path.isEmpty()) {
            System.out.println("Tidak ada rute dari " + from + " ke " + to);
            return;
        }

        /**
         * Calculate total distance
         */
        Map<String, Integer> distances = shippingGraph.dijkstra(from);
        int totalDistance = distances.get(to);

        System.out.println("\n" + "═".repeat(60));
        System.out.println("RUTE PENGIRIMAN TERDEKAT");
        System.out.println("═".repeat(60));
        System.out.println("Dari     : " + from);
        System.out.println("Ke       : " + to);
        System.out.println("Jarak    : " + totalDistance + " km");
        System.out.println("\nRute     : " + String.join(" → ", path));
        System.out.println("═".repeat(60));

        double estimatedCost = totalDistance * 2000; // Rp 2000/km
        double estimatedHours = totalDistance / 60.0; // 60 km/jam

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        System.out.println("\n💡 Estimasi:");
        System.out.println("├─ Biaya Pengiriman : " + currencyFormat.format(estimatedCost));
        System.out.println("└─ Waktu Tempuh     : " + String.format("%.1f", estimatedHours) + " jam");
    }

    /**
     * Display the entire shipping network.
     */
    public void displayShippingNetwork() {
        /**
         * Get adjacency list of the shipping graph
         */
        Map<String, Map<String, Integer>> network = shippingGraph.getAdjacencyList();

        if (network.isEmpty()) {
            System.out.println("\nBelum ada data jaringan pengiriman.");
            return;
        }

        /**
         * Display formatting
         */
        System.out.println("\n" + "═".repeat(60));
        System.out.println("JARINGAN PENGIRIMAN");
        System.out.println("═".repeat(60));

        for (Map.Entry<String, Map<String, Integer>> entry : network.entrySet()) {
            /**
             * For every city, display its routes and distances
             */
            String city = entry.getKey();
            Map<String, Integer> routes = entry.getValue();

            System.out.println("\n📍 " + city + ":");
            if (routes.isEmpty()) {
                System.out.println("   (Tidak ada rute)");
            } else {
                for (Map.Entry<String, Integer> route : routes.entrySet()) {
                    System.out.println("   → " + route.getKey() + " (" + route.getValue() + " km)");
                }
            }
        }
        System.out.println("\n═".repeat(60));
        System.out.println("Total Kota/Hub: " + network.size());
    }

    /**
     * Simulate multiple shipments from a given origin city/hub.
     * 
     * @param origin
     */
    public void simulateMultipleShipments(String origin) {
        List<Order> pendingOrders = new ArrayList<>();
        /**
         * Collect all orders with status "PROCESSING" or "PENDING"
         */
        for (Order order : orderTable.getAllOrders()) {
            if (order.getStatus().equals("PROCESSING") || order.getStatus().equals("PENDING")) {
                pendingOrders.add(order);
            }
        }

        if (pendingOrders.isEmpty()) {
            System.out.println("Tidak ada pesanan yang perlu dikirim.");
            return;
        }

        /**
         * Calculate shortest distances from origin to all destinations
         */
        Map<String, Integer> distances = shippingGraph.dijkstra(origin);

        System.out.println("\n" + "═".repeat(70));
        System.out.println("SIMULASI PENGIRIMAN DARI: " + origin);
        System.out.println("═".repeat(70));
        System.out.printf("%-10s %-15s %-15s %-10s %-15s%n",
                "ORDER ID", "CUSTOMER", "TUJUAN", "JARAK", "EST. BIAYA");
        System.out.println("─".repeat(70));

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        int totalDistance = 0;
        double totalCost = 0;

        for (Order order : pendingOrders) {
            /**
             * For each order, get distance to shipping city
             */
            String destination = order.getShippingCity();
            Integer distance = distances.get(destination);

            if (distance != null && distance != Integer.MAX_VALUE) {
                double cost = distance * 2000;
                totalDistance += distance;
                totalCost += cost;

                System.out.printf("%-10s %-15s %-15s %-10d %-15s%n",
                        order.getOrderId(),
                        order.getCustomerName(),
                        destination,
                        distance,
                        currencyFormat.format(cost));
            }
        }

        System.out.println("═".repeat(70));
        System.out.println("Total Pesanan     : " + pendingOrders.size());
        System.out.println("Total Jarak       : " + totalDistance + " km");
        System.out.println("Total Biaya Kirim : " + currencyFormat.format(totalCost));
        System.out.println("═".repeat(70));
    }

    /**
     * Display system dashboard with key metrics.
     */
    public void displayDashboard() {
        List<Product> products = new ArrayList<>();
        /**
         * In-order traversal to get all products
         * Search is done depth-first.
         * Assign to products variable as list
         */
        productTree.inOrderTraversal(products);
        List<Order> orders = orderTable.getAllOrders();

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        double totalRevenue = 0;
        int totalItemsSold = 0;
        Map<String, Integer> statusCount = new HashMap<>();

        for (Order order : orders) {
            totalRevenue += order.getTotalPrice();
            totalItemsSold += order.getQuantity();
            statusCount.put(order.getStatus(),
                    statusCount.getOrDefault(order.getStatus(), 0) + 1);
        }

        int totalStock = 0;
        double totalInventoryValue = 0;
        for (Product product : products) {
            totalStock += product.getStock();
            totalInventoryValue += product.getPrice() * product.getStock();
        }

        System.out.println("\n┌─ PRODUK");
        System.out.println("├─ Total Produk       : " + products.size());
        System.out.println("├─ Total Stok         : " + totalStock + " unit");
        System.out.println("└─ Nilai Inventori    : " + currencyFormat.format(totalInventoryValue));

        System.out.println("\n┌─ PESANAN");
        System.out.println("├─ Total Pesanan      : " + orders.size());
        System.out.println("├─ Total Item Terjual : " + totalItemsSold);
        System.out.println("├─ Total Revenue      : " + currencyFormat.format(totalRevenue));
        System.out.println("└─ Status:");
        for (Map.Entry<String, Integer> entry : statusCount.entrySet()) {
            System.out.println("   ├─ " + entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\n┌─ PENGIRIMAN");
        System.out.println("├─ Total Kota/Hub     : " + shippingGraph.getAllCities().size());
        System.out.println("└─ Status             : " +
                (shippingGraph.getAllCities().isEmpty() ? "Belum dikonfigurasi" : "Aktif"));

        System.out.println("\n" + "═".repeat(70));
    }

    /**
     * Load sample data from CSV files
     */
    public void loadSampleData() {
        System.out.println("\n Memuat data dari CSV...\n");

        List<String[]> productData = CSVLoader.loadCSV("src/data/products.csv");
        for (String[] row : productData) {
            /**
             * Parse product data per row and insert into BST
             */
            try {
                /**
                 * CSV Shape:
                 * id,name,category,price,stock
                 */
                final int id_idx = 0;
                final int name_idx = 1;
                final int category_idx = 2;
                final int price_idx = 3;
                final int stock_idx = 4;

                String id = row[id_idx];
                String name = row[name_idx];
                String category = row[category_idx];
                double price = Double.parseDouble(row[price_idx]);
                int stock = Integer.parseInt(row[stock_idx]);

                /**
                 * Create product and insert into BST
                 */
                Product product = new Product(id, name, category, price, stock);
                productTree.insert(product);
            } catch (Exception e) {
                System.out.println("Error loading product: " + Arrays.toString(row));
            }
        }

        List<String[]> routeData = CSVLoader.loadCSV("src/data/routes.csv");
        Set<String> cities = new HashSet<>();

        for (String[] row : routeData) {
            /**
             * Parse route data per row and add to shipping graph
             */
            try {
                /**
                 * CSV Shape:
                 * from,to,distance
                 */
                final int from_idx = 0;
                final int to_idx = 1;
                final int distance_idx = 2;

                String from = row[from_idx];
                String to = row[to_idx];
                int distance = Integer.parseInt(row[distance_idx]);

                /**
                 * Add cities to set
                 */
                cities.add(from);
                cities.add(to);

                /**
                 * Add route to shipping graph
                 */
                shippingGraph.addRoute(from, to, distance);
            } catch (Exception e) {
                System.out.println("Error loading route: " + Arrays.toString(row));
            }
        }

        for (String city : cities) {
            /**
             * Add all unique cities from set to shipping graph as nodes
             */
            shippingGraph.addCity(city);
        }

        List<String[]> orderData = CSVLoader.loadCSV("src/data/orders.csv");
        for (String[] row : orderData) {
            /**
             * Parse order data per row and insert into hash table
             */
            try {
                /**
                 * CSV Shape:
                 * orderId,customerName,productId,quantity,shippingCity,status
                 */
                final int orderId_idx = 0;
                final int customerName_idx = 1;
                final int productId_idx = 2;
                final int quantity_idx = 3;
                final int shippingCity_idx = 4;
                final int status_idx = 5;

                String orderId = row[orderId_idx];
                String customerName = row[customerName_idx];
                String productId = row[productId_idx];
                int quantity = Integer.parseInt(row[quantity_idx]);
                String shippingCity = row[shippingCity_idx];
                String status = row[status_idx];

                /**
                 * Search product in BST by ID
                 * Search is done depth-first
                 */
                Product product = productTree.search(productId);
                /**
                 * Create order and insert into hash table if product exists and stock is sufficient
                 */
                if (product != null && product.getStock() >= quantity) {
                    double totalPrice = product.getPrice() * quantity;
                    Order order = new Order(orderId, customerName, productId,
                            quantity, totalPrice, shippingCity);
                    order.setStatus(status);
                    orderTable.put(orderId, order);

                    product.setStock(product.getStock() - quantity);
                }
            } catch (Exception e) {
                System.out.println("Error loading order: " + Arrays.toString(row));
            }
        }

        System.out.println("\nSemua data berhasil dimuat!");
        System.out.println("  - " + productTree.getSize() + " produk");
        System.out.println("  - " + orderTable.getSize() + " pesanan");
        System.out.println("  - " + shippingGraph.getAllCities().size() + " kota/hub");
    }
}
