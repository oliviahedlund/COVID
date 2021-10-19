package com.example.myapplication.Helpers;

import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.ApiClient;
import com.example.myapplication.GeneralActivity;
import com.example.myapplication.R;
import com.example.myapplication.UI.AlertWindow;
import com.example.myapplication.UI.LoadingAnimation;
import com.example.myapplication.UI.UserAppointment.Appointment_Info;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAPIHelper {
    private Fragment fragment;
    private UserResponse userResponse;

    private Boolean FullyDosed;

    public UserAPIHelper(Fragment fragment){
        this.fragment = fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void API_getUser(UserResponse user, Runnable runnable){
        Call<UserResponse> call = ApiClient.getUserService().getUser(user.getToken());
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()){
                    userResponse = response.body();
                    userResponse.setToken(user.getToken());
                    fragment.getActivity().getIntent().putExtra("userInfo", userResponse);
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
            public void onFailure(Call<UserResponse> call, Throwable t) {
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void API_isFullyDosed(UserResponse user, Runnable runnable){
        Call<Boolean> call = ApiClient.getUserService().isFullyDosed(user.getToken(), user.getId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    FullyDosed = response.body();

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
            public void onFailure(Call<Boolean> call, Throwable t) {
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }

    public Boolean isFullyDosed() {
        return FullyDosed;
    }
}
