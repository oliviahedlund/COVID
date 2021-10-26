package com.example.myapplication.API.Model.Appointment_user;

import com.example.myapplication.API.Model.User.UserResponse;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class AppointmentRequest implements Serializable {

    private String id;
    private String userId;
    private UserResponse userOfAppointment;
    private ZonedDateTime time;
    private int length;
    private String centerId;
    private String centerName;
    private String vaccineId;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserOfAppointment(UserResponse userOfAppointment) {
        this.userOfAppointment = userOfAppointment;
    }

    public UserResponse getUserOfAppointment() {
        return userOfAppointment;
    }
/*
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
*/
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

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(String vaccineId) {
        this.vaccineId = vaccineId;
    }

}
