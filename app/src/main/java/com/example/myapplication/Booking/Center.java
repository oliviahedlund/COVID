package com.example.myapplication.Booking;

import java.util.List;

public class Center {
    private String id;
    private String name;
    private String address;
    private List<Vaccine> vaccines;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<Vaccine> getVaccines() {
        return vaccines;
    }
}

