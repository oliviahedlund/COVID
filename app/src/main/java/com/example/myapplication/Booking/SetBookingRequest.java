package com.example.myapplication.Booking;

import retrofit2.http.Query;

public class SetBookingRequest {
    private int center;
    private int length;
    private String time;


    public void setTime(String time) {
        this.time = time;
    }

    public void setCenter(int center) {
        this.center = center;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
