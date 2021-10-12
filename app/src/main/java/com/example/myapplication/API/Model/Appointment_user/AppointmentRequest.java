package com.example.myapplication.API.Model.Appointment_user;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class AppointmentRequest implements Serializable {

    private ZonedDateTime time;
    private int length;
    private String centerId;
    private String vaccineId;

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public String getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(String vaccineId) {
        this.vaccineId = vaccineId;
    }

}
