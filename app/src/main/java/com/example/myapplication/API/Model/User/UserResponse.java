package com.example.myapplication.API.Model.User;

import com.example.myapplication.API.Model.Appointment_user.AppointmentRequest;
import com.example.myapplication.API.Model.Appointment_user.QuestionnaireRequest;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class UserResponse implements Serializable {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private ZonedDateTime birthDate;
    private String address;
    private String address2;
    private String city;
    private String district;
    private String postalCode;
    private String id;
    private Boolean isAdmin;
    private String token;
    private boolean canBook;
    private boolean gotFirstDose;
    private boolean gotSecondDose;
    private ZonedDateTime firstDoseDate;
    private ZonedDateTime secondDoseDate;
    private AppointmentRequest appointment;
    private QuestionnaireRequest questionare;

    public boolean isCanBook() {
        return canBook;
    }

    public void setCanBook(boolean canBook) {
        this.canBook = canBook;
    }

    public boolean isGotFirstDose() {
        return gotFirstDose;
    }

    public void setGotFirstDose(boolean gotFirstDose) {
        this.gotFirstDose = gotFirstDose;
    }

    public boolean isGotSecondDose() {
        return gotSecondDose;
    }

    public void setGotSecondDose(boolean gotSecondDose) {
        this.gotSecondDose = gotSecondDose;
    }

    public ZonedDateTime getFirstDoseDate() {
        return firstDoseDate;
    }

    public void setFirstDoseDate(ZonedDateTime firstDoseDate) {
        this.firstDoseDate = firstDoseDate;
    }

    public ZonedDateTime getSecondDoseDate() {
        return secondDoseDate;
    }

    public void setSecondDoseDate(ZonedDateTime secondDoseDate) {
        this.secondDoseDate = secondDoseDate;
    }

    public AppointmentRequest getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentRequest appointment) {
        this.appointment = appointment;
    }

    public QuestionnaireRequest getQuestionare() {
        return questionare;
    }

    public void setQuestionare(QuestionnaireRequest questionare) {
        this.questionare = questionare;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public ZonedDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(ZonedDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}


