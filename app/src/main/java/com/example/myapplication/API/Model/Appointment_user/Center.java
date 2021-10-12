package com.example.myapplication.API.Model.Appointment_user;

import java.util.List;

public class Center {
    private String centerId;
    private String centerName;
    private String centerCounty;
    private List<Vaccine> vaccines;

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

    public String getCenterCounty() {
        return centerCounty;
    }

    public void setCenterCounty(String centerCounty) {
        this.centerCounty = centerCounty;
    }

    public List<Vaccine> getVaccines() {
        return vaccines;
    }

    public void setVaccines(List<Vaccine> vaccines) {
        this.vaccines = vaccines;
    }
}
