package com.example.myapplication.Booking;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingResponse {
    private LocalDateTime time;
    private int length;
    private int center;

    public int getCenter() {
        return center;
    }

    public int getLength() {
        return length;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDateTime getTime() {
//        return LocalDateTime.parse(time);
        return time;
    }

    public void setCenter(int center) {
        this.center = center;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
    //private List<SingleBookingResponse> bookingResponseList;
/*
    public BookingResponse(){
        bookingResponseList = new ArrayList<SingleBookingResponse>();
    }*/
    /*
    public ArrayList<String> getTimes(){
        ArrayList<String> times = new ArrayList<String>();
        for (int i = 0; i < bookingResponseList.size(); i++) {
            times.add(bookingResponseList.get(i).getTime());
        }
        return times;
    }*/
/*
    public List<SingleBookingResponse> getSponsors() {
        return bookingResponseList;
    }*/
    //public class SingleBookingResponse {


