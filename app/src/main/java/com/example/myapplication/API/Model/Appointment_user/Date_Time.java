package com.example.myapplication.API.Model.Appointment_user;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Date_Time {
    private ZonedDateTime time;
    private int length;

    public int getLength() {
        return length;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public void setLength(int length) {
        this.length = length;
    }

}



