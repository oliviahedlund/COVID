package com.example.myapplication.Helpers;

import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.API.Model.Appointment_user.AppointmentRequest;
import com.example.myapplication.API.Model.Appointment_user.AppointmentResponse;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.ApiClient;
import com.example.myapplication.R;
import com.example.myapplication.UI.AlertWindow;
import com.example.myapplication.UI.LoadingAnimation;
import com.example.myapplication.UI.UserAppointment.Appointment_Info;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentHelper {
    private Fragment fragment;

    public AppointmentHelper(Fragment fragment){
        this.fragment = fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void API_sendNewAppointment(UserResponse user, Runnable runnable, AppointmentRequest appointment){
        Call<Void> call = ApiClient.getUserService().postNewAppointments(user.getToken(), appointment);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){

                    new Handler().postDelayed(runnable, 600);
                }
                else{
                    LoadingAnimation.dismissLoadingAnimation();
                    try {
                        new AlertWindow(fragment, new Appointment_Info()).createAlertWindow(response.errorBody().string());
                    } catch (IOException e) {
                        new AlertWindow(fragment, new Appointment_Info()).createAlertWindow("Unknown error");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment, new Appointment_Info()).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void API_updateAppointment(UserResponse user, Runnable runnable, AppointmentRequest appointment){
        Call<Void> call = ApiClient.getUserService().updateAppointments(user.getToken(), appointment);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){

                    new Handler().postDelayed(runnable, 600);
                }
                else{
                    LoadingAnimation.dismissLoadingAnimation();
                    try {
                        new AlertWindow(fragment, new Appointment_Info()).createAlertWindow(response.errorBody().string());
                    } catch (IOException e) {
                        new AlertWindow(fragment, new Appointment_Info()).createAlertWindow("Unknown error");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment, new Appointment_Info()).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void API_deleteAppointment_user(UserResponse user, Runnable runnable){
        Call<Void> call = ApiClient.getUserService().deleteAppointment_user(user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){

                    new Handler().postDelayed(runnable, 600);
                }
                else{
                    LoadingAnimation.dismissLoadingAnimation();
                    try {
                        new AlertWindow(fragment, new Appointment_Info()).createAlertWindow(response.errorBody().string());
                    } catch (IOException e) {
                        new AlertWindow(fragment, new Appointment_Info()).createAlertWindow("Unknown error");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment, new Appointment_Info()).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }
}
