package com.example.myadminapp;
public class EventData {
    private String title;
    private String image;
    private String key;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public EventData() {
        // Default constructor required for Firebase
    }

    public EventData(String title, String image, String key,String type) {
        this.title = title;
        this.image = image;
        this.key = key;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

