package com.daniel.final_project.objects;

public class Shop {
    private String sid = "";
    private String name = "";
    private String type = "";
    private String address = "";
    private String description = "";
    private String imageUrl = "";
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

    public String getImageUrl() {
        return imageUrl;
    }

    public Shop setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
