package com.daniel.final_project.objects;

public class Product {
    private String pid = "";
    private String name = "";
    private int quantity = 0;
    private String description = "";
    private double price = 0.0;
    private String sid = "";
    private String imageUrl = "";


    public Product() {
    }

    public String getPid() {
        return pid;
    }

    public Product setPid(String pid) {
        this.pid = pid;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Product setPrice(double price) {
        this.price = price;
        return this;
    }

    public String getSid() {
        return sid;
    }

    public Product setSid(String sid) {
        this.sid = sid;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Product setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
