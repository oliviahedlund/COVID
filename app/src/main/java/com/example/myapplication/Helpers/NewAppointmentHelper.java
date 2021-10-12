package com.example.myapplication.Helpers;

import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.API.Model.Appointment_user.AppointmentRequest;
import com.example.myapplication.API.Model.Appointment_user.AppointmentResponse;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.ApiClient;
import com.example.myapplication.R;
import com.example.myapplication.UI.AlertWindow;
import com.example.myapplication.UI.LoadingAnimation;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewAppointmentHelper {
    private Fragment fragment;
    private AppointmentRequest appointment;

    public NewAppointmentHelper(Fragment fragment, AppointmentRequest appointment){
        this.appointment = appointment;
        this.fragment = fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void API_sendNewAppointment(UserResponse user, Runnable runnable){
        Call<AppointmentResponse> call1 = ApiClient.getUserService().postNewAppointments(user.getToken(), appointment);
        call1.enqueue(new Callback<AppointmentResponse>() {
            @Override
            public void onResponse(Call<AppointmentResponse> call, Response<AppointmentResponse> response) {
                if(response.isSuccessful()){

                    new Handler().postDelayed(runnable, 600);
                }
                else{
                    LoadingAnimation.dismissLoadingAnimation();
                    try {
                        new AlertWindow(fragment).createAlertWindow(response.errorBody().string());
                    } catch (IOException e) {
                        new AlertWindow(fragment).createAlertWindow("Unknown error");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppointmentResponse> call, Throwable t) {
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }
}
