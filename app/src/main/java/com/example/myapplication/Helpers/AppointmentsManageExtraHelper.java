package com.example.myapplication.Helpers;

import android.os.Handler;

import androidx.fragment.app.Fragment;

import com.example.myapplication.API.Model.Appointment_user.AppointmentRequest;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.ApiClient;
import com.example.myapplication.R;
import com.example.myapplication.UI.AlertWindow;
import com.example.myapplication.UI.LoadingAnimation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentsManageExtraHelper {
    private Fragment fragment;
    private List<AppointmentRequest> responseList;

    public AppointmentsManageExtraHelper(Fragment fragment){
        this.fragment = fragment;
    }

    public List<AppointmentRequest> getResponseList() {
        return responseList;
    }

    public void API_getAppointments(UserResponse user, Runnable runnable, boolean getPrior){
        Call<List<AppointmentRequest>> call = ApiClient.getUserService().getAppointments(user.getToken(), getPrior);
        call.enqueue(new Callback<List<AppointmentRequest>>() {
            @Override
            public void onResponse(Call<List<AppointmentRequest>> call, Response<List<AppointmentRequest>> response) {
                if(response.isSuccessful()){
                    responseList = response.body();
                    new Handler().postDelayed(runnable, 600);
                }
                else{
                    LoadingAnimation.dismissLoadingAnimation();
                    new AlertWindow(fragment).createAlertWindow("Get Appointments Failed");

                }
            }

            @Override
            public void onFailure(Call<List<AppointmentRequest>> call, Throwable t) {
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }
}
