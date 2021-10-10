package com.example.myapplication.Booking;

public class BookingRequest {
    private int month;
    private int year;
    private int center;

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setCenter(int center) {
        this.center = center;
    }

    public int getCenter() {
        return center;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
