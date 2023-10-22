package com.example.aceuserapplication;

public class EventsData {

    String title,image,key;
    String type;

    public EventsData(){

    }
    public EventsData(String title,String image,String key,String type){

        this.title=title;
        this.image=image;
        this.key=key;
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

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setType(String type) {
        this.type = type;
    }
}
