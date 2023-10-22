package com.example.myadminapp;


public class CartItem {
    long id;
    String name;
    String price;
    String status;

    public CartItem() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CartItem(long id, String name, String price,String status) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.price = price;
    }

    public long getIId() {
        return id;
    }

    public void setIId(long id) {
        this.id = id;
    }

    public String getItemName() {
        return name;
    }

    public void setItemName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}

