package com.example.myapplication.Booking;

import android.app.Activity;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.myapplication.ApiClient;
import com.example.myapplication.UserResponse;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DateTimeHelper {
    //{"2021-09-26T11:00:00", "2021-09-27T11:00:00", "2021-09-27T11:40:00"}

    private ArrayList<String> array;
    private ArrayList<LocalDateTime> dtArray;
    private ArrayList<Integer> days;
    private ArrayList<LocalTime> times;
    private List<BookingResponse> bookingResponse; ////


    //Example: ArrayList<String>  arrray = {"2021-09-26T11:00:00", "2021-09-27T11:00:00", "2021-09-27T11:40:00"};
    //         DateTimeHelperTest dt = new DateTimeHelperTest(array);
    public DateTimeHelper() {
        array = new ArrayList<String>();
        dtArray = new ArrayList<LocalDateTime>();
        days = new ArrayList<Integer>();
        /*for (int i = 0; i < _array.size(); i++) {
            array.add(_array.get(i).getTime());
        }*/
    }

    public void CallBookingAPI(Activity activity, UserResponse user, int month, int year, int center){
        BookingRequest bookingRequest = new BookingRequest();
        //System.out.println(user.getToken());
        Call<List<BookingResponse>> bookingResponseCall = ApiClient.getUserService().booking(user.getToken(), month,year,center);
        bookingResponseCall.enqueue(new Callback<List<BookingResponse>>() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<BookingResponse>> call, Response<List<BookingResponse>> response) {
                //errorhandling
                if (response.isSuccessful()) {
                    //Toast.makeText(MainActivity.this, "ok, got user", Toast.LENGTH_LONG).show();
                    bookingResponse = response.body(); //i userResponse ligger all information om användaren
                    //System.out.println(bookingResponse);

                    //initialize array with strings
                    for (int i = 0; i < bookingResponse.size(); i++) {
                        array.add(bookingResponse.get(i).getTime());
                    }
                    System.out.println("här");

                }else{
                    Toast.makeText(activity,"Appointments/Booking failed", Toast.LENGTH_LONG).show();
                    System.out.println("else");

                }
            }

            @Override
            public void onFailure(Call<List<BookingResponse>> call, Throwable t) {
                Toast.makeText(activity,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                System.out.println("fail");
            }
        });

    }

    //gets times from initialization-array + specified day and returns a list of available times
    @RequiresApi(api = Build.VERSION_CODES.O)  //method invoking this must add this line
    public ArrayList<LocalTime> getTimes(int _day) {
        int hour;
        int minute;
        times = new ArrayList<LocalTime>();
        int j = 0;

        for (int i = 0; i < array.size(); i++) {
            dtArray.add(LocalDateTime.parse(array.get(i)));
            System.out.println("Available appointment: " + dtArray.get(i)); ////
            if (dtArray.get(i).getDayOfMonth() == _day) {
                hour = dtArray.get(i).getHour();
                minute = dtArray.get(i).getMinute();
                times.add(LocalTime.of(hour, minute));
                System.out.println("Added time: " + times.get(times.size() - 1)); ////
            }


        }
        System.out.println("Returned from getTimes"); ////
        return times;
    }

    //gets dates from initialization-array and returns a list of available days
    @RequiresApi(api = Build.VERSION_CODES.O) //method invoking this must add this line
    public ArrayList<Integer> getDates() {
        int j = 0;
        for (int i = 0; i < array.size(); i++) {
            dtArray.add(LocalDateTime.parse(array.get(i)));
            System.out.println("Available appointment: " + dtArray.get(i)); ////
            if (!days.contains(dtArray.get(i).getDayOfMonth())) {
                days.add(dtArray.get(i).getDayOfMonth());
                System.out.println("New day: " + days.get(days.size() - 1)); ////
                j++;
            }
        }
        System.out.println("Returned from getDates");
        return days;
    }

    //Returns position in list based on chosen day and time
    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getPosition(Integer day, LocalTime time){
        int hour = time.getHour();
        int minute = time.getMinute();

        for (int i = 0; i < dtArray.size(); i++) {
            if(dtArray.get(i).getDayOfMonth() == day){
                if(dtArray.get(i).getHour() == hour) {
                    if (dtArray.get(i).getMinute() == minute) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
}




