package com.example.istore.Model;

public class Vendor {
    private int id;
    private String name;
    private String imgUrl;
    private String contact;

    public Vendor() {
    }

    public Vendor(int id, String name, String imgUrl,String contact) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
