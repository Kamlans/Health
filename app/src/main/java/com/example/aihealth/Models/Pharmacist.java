package com.example.aihealth.Models;

public class Pharmacist {

    private String name , phNum , location, imgUrl;

    public Pharmacist(){

    }

    public Pharmacist(String name, String phNum, String location, String imgUrl) {
        this.name = name;
        this.phNum = phNum;
        this.location = location;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhNum() {
        return phNum;
    }

    public void setPhNum(String phNum) {
        this.phNum = phNum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "Pharmacist{" +
                "name='" + name + '\'' +
                ", phNum='" + phNum + '\'' +
                ", locatin='" + location + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
