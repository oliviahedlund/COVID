package com.example.myapplication;

public class AppointmentResponse {
    private String id;
    private String userId;
    private String centerId;
    private String vaccineId;
    private String time;
    private int length;

    //Get functions
    public String getId() {
        return id;
    }
    public String getUserId() {
        return userId;
    }
    public String getCenterId() {
        return centerId;
    }
    public String getVaccineId() {
        return vaccineId;
    }
    public String getTime() {
        return time;
    }
    public int getLength() {
        return length;
    }

    //Set functions
    public void setId(String token) {
        this.id = id;
    }
    public void setUserId(String token) {
        this.userId = userId;
    }
    public void setCenterId(String token) {
        this.centerId = centerId;
    }
    public void setVaccineId(String token) {
        this.vaccineId = vaccineId;
    }
    public void setTime(String token) {
        this.time = time;
    }
    public void setLength(String token) {
        this.length = length;
    }

}
