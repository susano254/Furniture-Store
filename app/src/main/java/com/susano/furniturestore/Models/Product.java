package com.susano.furniturestore.Models;

import java.util.UUID;

public class Product {
    private String product_id,  name, description, category, imageKey;
    private double price;

    public Product(String product_id, String name, String description, String imageKey, String category, double price){
        this.name = name;
        this.description = description;
        this.imageKey = imageKey;
        this.category = category;
        this.price = price;
        this.product_id = product_id;
    }
    public Product(String name, String description, String imageKey, String category, double price){
        this.name = name;
        this.description = description;
        this.imageKey = imageKey;
        this.category = category;
        this.price = price;
        this.product_id = UUID.randomUUID().toString();
    }


    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
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
