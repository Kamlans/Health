package com.example.aihealth.Models;

public class AppointmentModel {

    String appointment , symptoms ;

    public AppointmentModel(String appointment , String symptoms) {
        this.appointment = appointment;
        this.symptoms = symptoms;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
}
