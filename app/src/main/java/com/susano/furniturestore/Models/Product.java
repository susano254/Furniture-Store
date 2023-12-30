package com.susano.furniturestore.Models;

public class Product {
    static int id;
    private int product_id;
    private String name, description, category, imageKey;
    private double price;

    public Product(String name, String description, String imageKey, String category, double price){
        this.name = name;
        this.description = description;
        this.imageKey = imageKey;
        this.category = category;
        this.price = price;
        this.id = Product.id++;
    }


    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
