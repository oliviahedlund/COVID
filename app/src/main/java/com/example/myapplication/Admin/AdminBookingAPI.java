package com.example.myapplication.Admin;

import android.app.Activity;
import android.widget.Toast;

import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.ApiClient;
import com.example.myapplication.Booking.SetBookingRequest;
import com.example.myapplication.API.Model.User.UserResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminBookingAPI {


    public String convertTimeToAPIString(int year, int month, int date, int hour, int minute){
        month = month-1; //because januari = 0

        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,date,hour,minute,00);
        SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return apiFormat.format(calendar.getTime());
    }

    public void PostBookingRange(Activity activity, UserResponse user, PostRangeRequest postRangeRequest) {
        postRangeRequest.setStartDateTime("2021-12-04T08:00:00" + "Z");
        postRangeRequest.setEndDateTime("2021-12-06T16:00:00.456Z");
        postRangeRequest.setAllowedDaysOfWeek(0);
        postRangeRequest.setAllowedAgeGroups(-1);
        postRangeRequest.setTimePerAppointmentMinutes(20);
        postRangeRequest.setCenter("b56e2ae8-49c2-4f5b-94c3-e1bcff4a8768");


        Call<String> postBookingRangeCall = ApiClient.getUserService().setRange(user.getToken(), postRangeRequest);

        postBookingRangeCall.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //TODO: errorhandling
                if (response.isSuccessful()) {
                    System.out.println(response);


                }else{
                    System.out.println("Post range failed");
                    System.out.println("else");
                    System.out.println(response);
                    System.out.println(response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(activity,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                System.out.println("fail");
            }
        });

    }
}
