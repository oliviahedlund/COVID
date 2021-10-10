package com.example.myapplication.API.Model.Appointment_user;

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



