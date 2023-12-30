package com.susano.furniturestore.Models;

public class Product {
    static int id;
    public int product_id;
    public String name, description, category, imageKey;
    public double price;

    public Product(String name, String description, String imageKey, String category, double price){
        this.name = name;
        this.description = description;
        this.imageKey = imageKey;
        this.category = category;
        this.price = price;
        this.id = Product.id++;
    }


}
