package com.daniel.final_project.objects;

public class Shop {
    private String sid = "";
    private String name = "";
    private String type = "";
    private String address = "";
    private String description = "";
    private String imageUrlSquare = "";
    private String imageUrlWide = "";
    private String uid = "";

    public Shop() {
    }

    public String getSid() {
        return sid;
    }

    public Shop setSid(String sid) {
        this.sid = sid;
        return this;
    }

    public String getName() {
        return name;
    }

    public Shop setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public Shop setType(String type) {
        this.type = type;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Shop setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Shop setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrlSquare() {
        return imageUrlSquare;
    }

    public Shop setImageUrlSquare(String imageUrlSquare) {
        this.imageUrlSquare = imageUrlSquare;
        return this;
    }

    public String getImageUrlWide() {
        return imageUrlWide;
    }

    public Shop setImageUrlWide(String imageUrlWide) {
        this.imageUrlWide = imageUrlWide;
        return this;
    }


    public String getUid() {
        return uid;
    }

    public Shop setUid(String uid) {
        this.uid = uid;
        return this;
    }

}
