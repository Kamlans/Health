package com.example.aihealth.Models;

public class Pharmacist {

    private String name , phNum , locatin , imgUrl;

    public Pharmacist(){

    }

    public Pharmacist(String name, String phNum, String locatin, String imgUrl) {
        this.name = name;
        this.phNum = phNum;
        this.locatin = locatin;
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

    public String getLocatin() {
        return locatin;
    }

    public void setLocatin(String locatin) {
        this.locatin = locatin;
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
                ", locatin='" + locatin + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
