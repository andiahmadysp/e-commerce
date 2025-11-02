package models;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Order {
    private String orderId;
    private String customerName;
    private String productId;
    private int quantity;
    private double totalPrice;
    private String status;
    private String shippingCity;
    private LocalDateTime orderTime;

    public Order(String orderId, String customerName, String productId,
                 int quantity, double totalPrice, String shippingCity) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = "PENDING";
        this.shippingCity = shippingCity;
        this.orderTime = LocalDateTime.now();
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void displayInfo() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        System.out.println("┌─ DETAIL PESANAN");
        System.out.println("├─ Order ID    : " + orderId);
        System.out.println("├─ Customer    : " + customerName);
        System.out.println("├─ Produk ID   : " + productId);
        System.out.println("├─ Jumlah      : " + quantity);
        System.out.println("├─ Total       : " + currencyFormat.format(totalPrice));
        System.out.println("├─ Status      : " + status);
        System.out.println("├─ Tujuan      : " + shippingCity);
        System.out.println("└─ Waktu Order : " + orderTime.format(formatter));
    }
}
