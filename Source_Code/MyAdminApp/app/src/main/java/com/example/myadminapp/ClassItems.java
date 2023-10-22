package com.example.myadminapp;

public class ClassItems {
    String name;
    String statusClass;
    long id;

    public ClassItems(String id , String name ,String status) {
        this.name = name;
        this.id = Long.parseLong(id);
        this.statusClass = status;
    }

    public ClassItems() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatusClass() {
        return statusClass;
    }

    public void setStatusClass(String statusClass) {
        this.statusClass = statusClass;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
