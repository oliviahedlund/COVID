package com.example.myapplication.API.Model.Appointment_user;

public class Vaccine {

    private String vaccineId;
    private String vaccineName;
    private int amount;

    public String getVaccineId() {
        return vaccineId;
    }

    public void setId(String id) {
        this.vaccineId = id;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setName(String name) {
        this.vaccineName = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

