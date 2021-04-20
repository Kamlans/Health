package com.example.aihealth.Models;

public class Hospital {

    private String name , phNum , location , imgUrl;

    public Hospital() {
    }

    public Hospital(String name, String phNum, String location ,String imgUrl) {
        this.name = name;
        this.phNum = phNum;
        this.location = location;
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
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

    @Override
    public String toString() {
        return "Hospital{" +
                "name='" + name + '\'' +
                ", phNum='" + phNum + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
