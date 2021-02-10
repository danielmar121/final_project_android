package com.daniel.final_project.objects;

import com.daniel.final_project.utils.OrderStatuses;

public class Order {
    private String oid = "";
    private String uid = "";
    private String sid = "";
    private String shopName = "";
    private OrderStatuses orderStatus = OrderStatuses.OPEN;

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

    public String getOrderStatus() {
        // Convert enum to string
        if (orderStatus == null) {
            return null;
        } else {
            return orderStatus.name();
        }
    }

    public Order setOrderStatus(String orderStatus) {
        // Get enum from string
        if (orderStatus == null) {
            this.orderStatus = null;
        } else {
            this.orderStatus = OrderStatuses.valueOf(orderStatus);
        }
        return this;
    }

    public String getShopName() {
        return shopName;
    }

    public Order setShopName(String shopName) {
        this.shopName = shopName;
        return this;
    }
}
