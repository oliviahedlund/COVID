package com.example.myapplication.API.Model.Covid_tracking;

public class CountyVaxStat{
    private int countyId;
    private String countyName;
    private int ageGroup;
    private int population;
    private int minFirstDoseAmount;
    private int fullVaccinationAmount;
    private double minFirstDoseRation;
    private double fullVaccinationRatio;

    public int getCountyId() {
        return countyId;
    }

    public void setCountyId(int countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public int getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(int ageGroup) {
        this.ageGroup = ageGroup;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getMinFirstDoseAmount() {
        return minFirstDoseAmount;
    }

    public void setMinFirstDoseAmount(int minFirstDoseAmount) {
        this.minFirstDoseAmount = minFirstDoseAmount;
    }

    public int getFullVaccinationAmount() {
        return fullVaccinationAmount;
    }

    public void setFullVaccinationAmount(int fullVaccinationAmount) {
        this.fullVaccinationAmount = fullVaccinationAmount;
    }

    public double getMinFirstDoseRation() {
        return minFirstDoseRation;
    }

    public void setMinFirstDoseRation(double minFirstDoseRation) {
        this.minFirstDoseRation = minFirstDoseRation;
    }

    public double getFullVaccinationRatio() {
        return fullVaccinationRatio;
    }

    public void setFullVaccinationRatio(double fullVaccinationRatio) {
        this.fullVaccinationRatio = fullVaccinationRatio;
    }
}
