package com.example.myapplication.API.Model.Admin;

public class UserInfo {
    private boolean isAdmin;
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String birthDate;
    private String address;
    private String address2;
    private String city;
    private String district;
    private String postalCode;
    private boolean canBook;
    private boolean gotFirstDose;
    private boolean gotSecondDose;
    private String firstDoseDate;
    private String secondDoseDate;

    //Get functions
    public boolean isAdmin() {
        return isAdmin;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public String getAddress2() {
        return address2;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public boolean isCanBook() {
        return canBook;
    }

    public boolean isGotFirstDose() {
        return gotFirstDose;
    }

    public boolean isGotSecondDose() {
        return gotSecondDose;
    }

    public String getFirstDoseDate() {
        return firstDoseDate;
    }

    public String getSecondDoseDate() {
        return secondDoseDate;
    }

    //Set functions
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCanBook(boolean canBook) {
        this.canBook = canBook;
    }

    public void setGotFirstDose(boolean gotFirstDose) {
        this.gotFirstDose = gotFirstDose;
    }

    public void setGotSecondDose(boolean gotSecondDose) {
        this.gotSecondDose = gotSecondDose;
    }

    public void setFirstDoseDate(String firstDoseDate) {
        this.firstDoseDate = firstDoseDate;
    }

    public void setSecondDoseDate(String secondDoseDate) {
        this.secondDoseDate = secondDoseDate;
    }
}