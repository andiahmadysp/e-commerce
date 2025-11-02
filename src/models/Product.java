package models;

import java.text.NumberFormat;
import java.util.Locale;

public class Product {
    private String id;
    private String name;
    private String category;
    private double price;
    private int stock;

    public Product(String id, String name, String category, double price, int stock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void displayInfo() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        System.out.println("┌─ DETAIL PRODUK");
        System.out.println("├─ ID       : " + id);
        System.out.println("├─ Nama     : " + name);
        System.out.println("├─ Kategori : " + category);
        System.out.println("├─ Harga    : " + currencyFormat.format(price));
        System.out.println("└─ Stok     : " + stock + " unit");
    }
}
