package com.example.aihealth.Models;

public class Doctor {

    private String nameOfDoctor ,specializationOfDoctor  , phoneNumOfDoctor , qualificationOfDoctor ,imageOfDoctorURI;

    public Doctor(String nameOfDoctor, String specializationOfDoctor, String phoneNumOfDoctor, String qualificationOfDoctor, String imageOfDoctorURI) {
        this.nameOfDoctor = nameOfDoctor;
        this.specializationOfDoctor = specializationOfDoctor;
        this.phoneNumOfDoctor = phoneNumOfDoctor;
        this.qualificationOfDoctor = qualificationOfDoctor;
        this.imageOfDoctorURI = imageOfDoctorURI;
    }

    public Doctor() {
    }

    public String getImageOfDoctorURI() {
        return imageOfDoctorURI;
    }

    public void setImageOfDoctorURI(String imageOfDoctorURI) {
        this.imageOfDoctorURI = imageOfDoctorURI;
    }

    public String getNameOfDoctor() {
        return nameOfDoctor;
    }

    public void setNameOfDoctor(String nameOfDoctor) {
        this.nameOfDoctor = nameOfDoctor;
    }

    public String getQualificationOfDoctor() {
        return qualificationOfDoctor;
    }

    public void setQualificationOfDoctor(String qualificationOfDoctor) {
        this.qualificationOfDoctor = qualificationOfDoctor;
    }

    public String getPhoneNumOfDoctor() {
        return phoneNumOfDoctor;
    }

    public void setPhoneNumOfDoctor(String phoneNumOfDoctor) {
        this.phoneNumOfDoctor = phoneNumOfDoctor;
    }

    public String getSpecializationOfDoctor() {
        return specializationOfDoctor;
    }

    public void setSpecializationOfDoctor(String specializationOfDoctor) {
        this.specializationOfDoctor = specializationOfDoctor;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "nameOfDoctor='" + nameOfDoctor + '\'' +
                ", specializationOfDoctor='" + specializationOfDoctor + '\'' +
                ", phoneNumOfDoctor='" + phoneNumOfDoctor + '\'' +
                ", qualificationOfDoctor='" + qualificationOfDoctor + '\'' +
                ", imageOfDoctorURI='" + imageOfDoctorURI + '\'' +
                '}';
    }
}
