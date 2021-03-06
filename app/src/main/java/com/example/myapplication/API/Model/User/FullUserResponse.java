package com.example.myapplication.API.Model.User;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.API.Model.Appointment_user.AppointmentRespFull;
import com.example.myapplication.API.Model.Appointment_user.QuestionnaireRequest;


public class FullUserResponse implements Parcelable {

    private boolean isAdmin;
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String birthDate;
    private String address;
    private String city;
    private String disctrict;
    private String postalCode;
    private String firstDoseDate;
    private String secondDoseDate;
    private boolean canBook;
    private boolean gotFirstDose;
    private boolean gotSecondDose;

    private QuestionnaireRequest questionare;
    private AppointmentRespFull appointment;


    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDisctrict() {
        return disctrict;
    }

    public void setDisctrict(String disctrict) {
        this.disctrict = disctrict;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getFirstDoseDate() {
        return firstDoseDate;
    }

    public void setFirstDoseDate(String firstDoseDate) {
        this.firstDoseDate = firstDoseDate;
    }

    public String getSecondDoseDate() {
        return secondDoseDate;
    }

    public void setSecondDoseDate(String secondDoseDate) {
        this.secondDoseDate = secondDoseDate;
    }

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

    public QuestionnaireRequest getQuestionare() {
        return questionare;
    }

    public void setQuestionare(QuestionnaireRequest questionare) {
        this.questionare = questionare;
    }

    public AppointmentRespFull getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentRespFull appointment) {
        this.appointment = appointment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (isAdmin ? 1 : 0));
        parcel.writeString(id);
        parcel.writeString(email);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(phoneNumber);
        parcel.writeString(birthDate);
        parcel.writeString(address);
        parcel.writeString(city);
        parcel.writeString(disctrict);
        parcel.writeString(postalCode);
        parcel.writeString(firstDoseDate);
        parcel.writeString(secondDoseDate);
        parcel.writeByte((byte) (canBook ? 1 : 0));
        parcel.writeByte((byte) (gotFirstDose ? 1 : 0));
        parcel.writeByte((byte) (gotSecondDose ? 1 : 0));
    }
}
