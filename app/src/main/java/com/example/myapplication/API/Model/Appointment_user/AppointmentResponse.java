package com.example.myapplication.API.Model.Appointment_user;

import java.io.Serializable;

public class AppointmentResponse implements Serializable {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
