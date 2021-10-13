package com.example.myapplication.Admin;

import android.app.Activity;
import android.os.Handler;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.AlertWindow;
import com.example.myapplication.ApiClient;
import com.example.myapplication.Booking.SetBookingRequest;
import com.example.myapplication.LoadingAnimation;
import com.example.myapplication.R;
import com.example.myapplication.UserResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminBookingAPI {
    private Fragment fragment;

    public AdminBookingAPI(Fragment fragment){
        this.fragment = fragment;
    }

    public String convertTimeToAPIString(int[] date){
        date[1] = date[1]-1; //because januari = 0

        Calendar calendar = Calendar.getInstance();
        calendar.set(date[0],date[1],date[2],date[3],date[4],00);
        SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return apiFormat.format(calendar.getTime());
    }

    public void PostBookingRange(Activity activity, UserResponse user, PostRangeRequest postRangeRequest, Runnable runnable) {
        Call<String> postBookingRangeCall = ApiClient.getUserService().setRange(user.getToken(), postRangeRequest);
        postBookingRangeCall.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    new Handler().postDelayed(runnable,600);
                }else{
                    LoadingAnimation.dismissLoadingAnimation();
                    new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
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
