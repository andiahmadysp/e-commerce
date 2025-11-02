import system.ECommerceSystem;

import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ECommerceSystem system = new ECommerceSystem();

    public static void main(String[] args) {
        system.loadSampleData();

        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║           SISTEM MANAJEMEN E-COMMERCE          ║");
        System.out.println("║   Tree • Hash Table • Graph - Data Structures  ║");
        System.out.println("╚════════════════════════════════════════════════╝");

        while (true) {
            displayMainMenu();
            int choice = getIntInput("\nPilih menu: ");
            System.out.println();

            switch (choice) {
                case 1:
                    productMenu();
                    break;
                case 2:
                    orderMenu();
                    break;
                case 3:
                    shippingMenu();
                    break;
                case 4:
                    analyticsMenu();
                    break;
                case 5:
                    displaySystemArchitecture();
                    break;
                case 0:
                    System.out.println("Terima kasih telah menggunakan sistem!");
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n" + "═".repeat(55));
        System.out.println("MENU UTAMA E-COMMERCE");
        System.out.println("═".repeat(55));
        System.out.println("1. Manajemen Produk (BST - Binary Search Tree)");
        System.out.println("2. Manajemen Pesanan (Hash Table)");
        System.out.println("3. Optimasi Pengiriman (Graph - Dijkstra)");
        System.out.println("4. Analytics & Dashboard");
        System.out.println("5. Arsitektur & Kompleksitas Sistem");
        System.out.println("0. Keluar");
        System.out.println("═".repeat(55));
    }

    private static void productMenu() {
        while (true) {
            System.out.println("\n" + "─".repeat(55));
            System.out.println("MANAJEMEN PRODUK (BST)");
            System.out.println("─".repeat(55));
            System.out.println("1. Tambah Produk Baru");
            System.out.println("2. Cari Produk (by ID)");
            System.out.println("3. Cari Produk dalam Range Harga");
            System.out.println("4. Update Stok Produk");
            System.out.println("5. Hapus Produk");
            System.out.println("6. Tampilkan Semua Produk (Terurut by ID)");
            System.out.println("7. Tampilkan Produk Termahal/Termurah");
            System.out.println("0. Kembali");

            int choice = getIntInput("\nPilih: ");
            System.out.println();

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    searchProduct();
                    break;
                case 3:
                    searchProductInRange();
                    break;
                case 4:
                    updateStock();
                    break;
                case 5:
                    deleteProduct();
                    break;
                case 6:
                    system.displayAllProducts();
                    break;
                case 7:
                    system.displayPriceExtremes();
                    break;
                case 0:
                    return;
            }
        }
    }

    private static void orderMenu() {
        while (true) {
            System.out.println("\n" + "─".repeat(55));
            System.out.println("MANAJEMEN PESANAN (HASH TABLE)");
            System.out.println("─".repeat(55));
            System.out.println("1. Buat Pesanan Baru");
            System.out.println("2. Cari Pesanan (by models.Order ID)");
            System.out.println("3. Update Status Pesanan");
            System.out.println("4. Tampilkan Semua Pesanan");
            System.out.println("5. Tampilkan Pesanan by Status");
            System.out.println("6. Batalkan Pesanan");
            System.out.println("0. Kembali");

            int choice = getIntInput("\nPilih: ");
            System.out.println();

            switch (choice) {
                case 1:
                    createOrder();
                    break;
                case 2:
                    searchOrder();
                    break;
                case 3:
                    updateOrderStatus();
                    break;
                case 4:
                    system.displayAllOrders();
                    break;
                case 5:
                    displayOrdersByStatus();
                    break;
                case 6:
                    cancelOrder();
                    break;
                case 0:
                    return;
            }
        }
    }

    private static void shippingMenu() {
        while (true) {
            System.out.println("\n" + "─".repeat(55));
            System.out.println("OPTIMASI PENGIRIMAN (GRAPH - DIJKSTRA)");
            System.out.println("─".repeat(55));
            System.out.println("1. Tambah Kota/Hub");
            System.out.println("2. Tambah Rute Pengiriman");
            System.out.println("3. Cari Jalur Terdekat");
            System.out.println("4. Tampilkan Semua Rute");
            System.out.println("5. Simulasi Pengiriman Multiple Orders");
            System.out.println("0. Kembali");

            int choice = getIntInput("\nPilih: ");
            System.out.println();

            switch (choice) {
                case 1:
                    addCity();
                    break;
                case 2:
                    addRoute();
                    break;
                case 3:
                    findShortestPath();
                    break;
                case 4:
                    system.displayShippingNetwork();
                    break;
                case 5:
                    simulateMultipleShipments();
                    break;
                case 0:
                    return;
            }
        }
    }

    private static void analyticsMenu() {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("DASHBOARD ANALYTICS");
        System.out.println("═".repeat(70));

        system.displayDashboard();
    }

    private static void displaySystemArchitecture() {
        System.out.println("\n" + "═".repeat(75));
        System.out.println("ARSITEKTUR SISTEM & ANALISIS KOMPLEKSITAS");
        System.out.println("═".repeat(75));

        System.out.println("\n┌─ 1. BINARY SEARCH TREE (BST) - models.Product Management");
        System.out.println("│  Use Case: Manajemen katalog produk dengan pencarian efisien");
        System.out.println("│");
        System.out.println("│  ├─ Insert models.Product: O(log n) average, O(n) worst");
        System.out.println("│  ├─ Search by ID: O(log n) average, O(n) worst");
        System.out.println("│  ├─ Range Search: O(log n + k) - k = hasil dalam range");
        System.out.println("│  ├─ Delete models.Product: O(log n) average");
        System.out.println("│  ├─ In-models.Order Traversal: O(n)");
        System.out.println("│  └─ Space: O(n)");
        System.out.println("│");
        System.out.println("│  Keuntungan: Data otomatis terurut, range query efisien");
        System.out.println("│  Real Application: Filter produk by harga, kategori");

        System.out.println("\n├─ 2. HASH TABLE - models.Order Management");
        System.out.println("│  Use Case: Tracking pesanan real-time dengan lookup cepat");
        System.out.println("│");
        System.out.println("│  ├─ Create models.Order: O(1) average, O(n) worst");
        System.out.println("│  ├─ Search models.Order: O(1) average, O(n) worst");
        System.out.println("│  ├─ Update Status: O(1) average");
        System.out.println("│  ├─ Cancel models.Order: O(1) average");
        System.out.println("│  └─ Space: O(n)");
        System.out.println("│");
        System.out.println("│  Implementation: Separate Chaining, Dynamic Resizing");
        System.out.println("│  Load Factor: 0.75 (trigger resize)");
        System.out.println("│  Real Application: models.Order tracking, payment processing");

        System.out.println("\n└─ 3. GRAPH + DIJKSTRA - Shipping Optimization");
        System.out.println("   Use Case: Mencari rute pengiriman terdekat/termurah");
        System.out.println("");
        System.out.println("   ├─ Add City/Hub: O(1)");
        System.out.println("   ├─ Add Route: O(1)");
        System.out.println("   ├─ Dijkstra's Algorithm: O((V + E) log V)");
        System.out.println("   │  • V = jumlah kota/hub");
        System.out.println("   │  • E = jumlah rute");
        System.out.println("   └─ Space: O(V + E)");
        System.out.println("");
        System.out.println("   Data Structure: Adjacency List");
        System.out.println("   Optimization: Priority Queue (Min Heap)");
        System.out.println("   Real Application: Logistics, delivery optimization");

        System.out.println("\n" + "═".repeat(75));
        System.out.println("INTEGRATED SYSTEM BENEFITS");
        System.out.println("═".repeat(75));
        System.out.println("• BST: Katalog produk terorganisir & pencarian range efisien");
        System.out.println("• Hash Table: Akses pesanan O(1) untuk customer tracking");
        System.out.println("• Graph: Optimasi biaya & waktu pengiriman real-time");
        System.out.println("• Total: Complete E-Commerce solution dengan performance optimal");
        System.out.println("═".repeat(75));
    }

    private static void addProduct() {
        System.out.println("─".repeat(50));
        System.out.println("TAMBAH PRODUK BARU");
        System.out.println("─".repeat(50));

        String id = getStringInput("ID Produk (P001): ");
        String name = getStringInput("Nama Produk: ");
        String category = getStringInput("Kategori: ");
        double price = getDoubleInput("Harga: Rp ");
        int stock = getIntInput("Stok: ");

        system.addProduct(id, name, category, price, stock);
    }

    private static void searchProduct() {
        String id = getStringInput("Masukkan ID Produk: ");
        system.searchProduct(id);
    }

    private static void searchProductInRange() {
        double minPrice = getDoubleInput("Harga Minimum: Rp ");
        double maxPrice = getDoubleInput("Harga Maksimum: Rp ");
        system.searchProductsInRange(minPrice, maxPrice);
    }

    private static void updateStock() {
        String id = getStringInput("ID Produk: ");
        int newStock = getIntInput("Stok Baru: ");
        system.updateProductStock(id, newStock);
    }

    private static void deleteProduct() {
        String id = getStringInput("ID Produk yang akan dihapus: ");
        system.deleteProduct(id);
    }

    private static void createOrder() {
        System.out.println("─".repeat(50));
        System.out.println("BUAT PESANAN BARU");
        System.out.println("─".repeat(50));

        String customerName = getStringInput("Nama Customer: ");
        String productId = getStringInput("ID Produk: ");
        int quantity = getIntInput("Jumlah: ");
        String shippingCity = getStringInput("Kota Tujuan: ");

        system.createOrder(customerName, productId, quantity, shippingCity);
    }

    private static void searchOrder() {
        String orderId = getStringInput("Masukkan models.Order ID: ");
        system.searchOrder(orderId);
    }

    private static void updateOrderStatus() {
        String orderId = getStringInput("models.Order ID: ");
        System.out.println("\nStatus:");
        System.out.println("1. PENDING");
        System.out.println("2. PROCESSING");
        System.out.println("3. SHIPPED");
        System.out.println("4. DELIVERED");
        int choice = getIntInput("Pilih status: ");

        String status = "";
        switch (choice) {
            case 1: status = "PENDING"; break;
            case 2: status = "PROCESSING"; break;
            case 3: status = "SHIPPED"; break;
            case 4: status = "DELIVERED"; break;
            default:
                System.out.println("Status tidak valid!");
                return;
        }

        system.updateOrderStatus(orderId, status);
    }

    private static void displayOrdersByStatus() {
        System.out.println("\nFilter by Status:");
        System.out.println("1. PENDING");
        System.out.println("2. PROCESSING");
        System.out.println("3. SHIPPED");
        System.out.println("4. DELIVERED");
        int choice = getIntInput("Pilih: ");

        String status = "";
        switch (choice) {
            case 1: status = "PENDING"; break;
            case 2: status = "PROCESSING"; break;
            case 3: status = "SHIPPED"; break;
            case 4: status = "DELIVERED"; break;
        }

        system.displayOrdersByStatus(status);
    }

    private static void cancelOrder() {
        String orderId = getStringInput("models.Order ID yang akan dibatalkan: ");
        system.cancelOrder(orderId);
    }

    private static void addCity() {
        String cityName = getStringInput("Nama Kota/Hub: ");
        system.addCity(cityName);
    }

    private static void addRoute() {
        String from = getStringInput("Dari Kota: ");
        String to = getStringInput("Ke Kota: ");
        int distance = getIntInput("Jarak (km): ");
        system.addRoute(from, to, distance);
    }

    private static void findShortestPath() {
        String from = getStringInput("Dari Kota: ");
        String to = getStringInput("Ke Kota: ");
        system.findShortestDeliveryPath(from, to);
    }

    private static void simulateMultipleShipments() {
        String origin = getStringInput("Hub Asal: ");
        system.simulateMultipleShipments(origin);
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka!");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka!");
            }
        }
    }
}

