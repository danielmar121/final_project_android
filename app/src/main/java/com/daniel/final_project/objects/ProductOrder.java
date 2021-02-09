package com.daniel.final_project.objects;

public class ProductOrder {

    private String productOrderId = "";
    private String oid = "";
    private String pid = "";
    private int quantity = 0;

    public ProductOrder() {
    }

    public String getProductOrderId() {
        return productOrderId;
    }

    public ProductOrder setProductOrderId(String productOrderId) {
        this.productOrderId = productOrderId;
        return this;
    }

    public String getOid() {
        return oid;
    }

    public ProductOrder setOid(String oid) {
        this.oid = oid;
        return this;
    }

    public String getPid() {
        return pid;
    }

    public ProductOrder setPid(String pid) {
        this.pid = pid;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public ProductOrder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }
}
