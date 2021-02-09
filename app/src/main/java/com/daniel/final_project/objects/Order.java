package com.daniel.final_project.objects;

import com.google.firebase.database.PropertyName;

public class Order {

    private String oid = "";
    private String uid = "";
    private String sid = "";
    private Boolean isOpen = true;

    public Order() {
    }

    public String getOid() {
        return oid;
    }

    public Order setOid(String oid) {
        this.oid = oid;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public Order setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getSid() {
        return sid;
    }

    public Order setSid(String sid) {
        this.sid = sid;
        return this;
    }

    @PropertyName("isOpen")
    public Boolean isOpen() {
        return isOpen;
    }

    @PropertyName("isOpen")
    public Order setOpen(Boolean open) {
        this.isOpen = open;
        return this;
    }
}
