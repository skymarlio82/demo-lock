package com.wiz.demo.lock.data.entity;

import java.util.Date;

public class Order {
    private int id = 0;
    private String productName = null;
    private int amount = 0;
    private Date orderDateTime = null;

    public Order() {

    }

    public Order(int orderId, String productName, int amount, Date orderDateTime) {
        this.id = orderId;
        this.productName = productName;
        this.amount = amount;
        this.orderDateTime = orderDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(Date orderDateTime) {
        this.orderDateTime = orderDateTime;
    }
}