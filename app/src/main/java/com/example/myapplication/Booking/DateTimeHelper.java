package com.example.myapplication.Booking;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.AlertWindow;
import com.example.myapplication.ApiClient;
import com.example.myapplication.LoadingAnimation;
import com.example.myapplication.UserResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DateTimeHelper {
    //{"2021-09-26T11:00:00", "2021-09-27T11:00:00", "2021-09-27T11:40:00"}

    private ArrayList<LocalDateTime> array;
    private ArrayList<Calendar> days;
    private ArrayList<String> times;

    private Calendar [] allowedDays;
    public Calendar[] getAllowedDays() {
        return allowedDays;
    }

    private List<BookingResponse> bookingResponses; ////

    //Example: ArrayList<String>  arrray = {"2021-09-26T11:00:00", "2021-09-27T11:00:00", "2021-09-27T11:40:00"};

    public DateTimeHelper() {
        array = new ArrayList<LocalDateTime>();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void CallBookingAPI(Activity activity, UserResponse user, int month, int year, int center, LoadingAnimation loader, Fragment fragment){
        BookingRequest bookingRequest = new BookingRequest();

        Call<List<BookingResponse>> bookingResponseCall = ApiClient.getUserService().booking(user.getToken(), month,year,center);

        bookingResponseCall.enqueue(new Callback<List<BookingResponse>>() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<BookingResponse>> call, Response<List<BookingResponse>> response) {

                if (response.isSuccessful()) {

                    bookingResponses = response.body();

                    for (int i = 0; i < bookingResponses.size(); i++) {
                        array.add(bookingResponses.get(i).getTime());
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getDates();
                            loader.dismissLoadingAnimation();
                        }
                    },600);

                }else{
                    Toast.makeText(activity,"Appointments/Booking failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<BookingResponse>> call, Throwable t) {
                Toast.makeText(activity,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                loader.dismissLoadingAnimation();
                new AlertWindow(fragment).createAlertWindow();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String[] getTimes(Calendar day) {
        int hour;
        int minute;

        times = new ArrayList<String>();

        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getYear() == day.get(Calendar.YEAR)
                    && array.get(i).getMonthValue() == (day.get(Calendar.MONTH) + 1)
                    && array.get(i).getDayOfMonth() == day.get(Calendar.DAY_OF_MONTH)) {

                hour = array.get(i).getHour();
                minute = array.get(i).getMinute();

                if(minute < 10){
                    times.add(hour + " : 0" + minute);
                } else
                    times.add(hour + " : " + minute);
            }
        }

        String [] allowedTimes = new String[times.size()];

        return times.toArray(allowedTimes);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getDates() {
        days = new ArrayList<Calendar>();

        for (int i = 0; i < array.size(); i++) {
            Calendar buffer = Calendar.getInstance();
            buffer.set(Calendar.YEAR, array.get(i).getYear());
            buffer.set(Calendar.MONTH, array.get(i).getMonthValue() - 1);
            buffer.set(Calendar.DAY_OF_MONTH, array.get(i).getDayOfMonth());
            days.add(buffer);
        }

        allowedDays = new Calendar[days.size()];
        allowedDays = days.toArray(allowedDays);
    }

    //Returns position in list based on chosen day and time
    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getPosition(Integer day, LocalTime time){
        int hour = time.getHour();
        int minute = time.getMinute();

        for (int i = 0; i < array.size(); i++) {
            if(array.get(i).getDayOfMonth() == day){
                if(array.get(i).getHour() == hour) {
                    if (array.get(i).getMinute() == minute) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }


}




