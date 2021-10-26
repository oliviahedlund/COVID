package com.example.myapplication.API.Model.Covid_tracking;

public class UptakeStat {
    private int week;
    private int year;
    private String countyName;
    private int vaccinatedAmount;
    private double vaccinatedRatio;
    private int doseType;



    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public int getVaccinatedAmount() {
        return vaccinatedAmount;
    }

    public void setVaccinatedAmount(int vaccinatedAmount) {
        this.vaccinatedAmount = vaccinatedAmount;
    }

    public double getVaccinatedRatio() {
        return vaccinatedRatio;
    }

    public void setVaccinatedRatio(double vaccinatedRatio) {
        this.vaccinatedRatio = vaccinatedRatio;
    }

    public int getDoseType() {
        return doseType;
    }

    public void setDoseType(int doseType) {
        this.doseType = doseType;
    }


}
