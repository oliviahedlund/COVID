package com.example.myapplication.Helpers;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.API.Model.Appointment_admin.PostRangeRequest;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.UI.AlertWindow;
import com.example.myapplication.ApiClient;
import com.example.myapplication.R;
import com.example.myapplication.UI.LoadingAnimation;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminBookingHelper {
    private Fragment fragment;

    public AdminBookingHelper(Fragment fragment){
        this.fragment = fragment;
    }

    public ZonedDateTime convertTimeToZoneDateTime(int[] date){
        ZonedDateTime appointmentDateTime = LocalDateTime.of(date[0], date[1], date[2], date[3], date[4], 0).atZone(ZoneId.of("Europe/Stockholm")).withZoneSameInstant(ZoneId.of("UTC"));

        return appointmentDateTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void PostBookingRange(Activity activity, UserResponse user, PostRangeRequest postRangeRequest, Runnable runnable) {
        Call<String> postBookingRangeCall = ApiClient.getUserService().setRange(user.getToken(), postRangeRequest);
        postBookingRangeCall.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    new Handler().postDelayed(runnable,600);
                }else{
                    LoadingAnimation.dismissLoadingAnimation();
                    new AlertWindow(fragment).createAlertWindow(activity.getResources().getString(R.string.conflictRange));
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });

    }
}