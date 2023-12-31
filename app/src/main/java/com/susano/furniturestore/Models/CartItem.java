package com.susano.furniturestore.Models;

public class CartItem extends Product{
    private String date, time;
    private int quantity;


    public CartItem(Product product, String date, String time, int quantity) {
        super(product.getProduct_id(), product.getName(), product.getDescription(), product.getImageKey(), product.getCategory(), product.getPrice());
        this.date = date;
        this.time = time;
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
