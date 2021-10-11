package com.example.myapplication.Booking;

import java.util.List;

public class Center {
    private String id;
    private String name;
    private String address;
    private List<Vaccine> vaccineStocks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Vaccine> getVaccines() {
        return vaccineStocks;
    }

    public void setVaccines(List<Vaccine> vaccines) {
        this.vaccineStocks = vaccines;
    }
}
