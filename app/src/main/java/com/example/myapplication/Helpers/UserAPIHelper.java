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
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAPIHelper {
    private Fragment fragment;
    private UserResponse userResponse;
    private UserResponse[] userResponseList;
    private int counter;

    private Boolean FullyDosed;

    public UserAPIHelper(Fragment fragment){
        this.fragment = fragment;
        counter = 0;
        //userResponseList = new ArrayList<UserResponse>();
    }

    public void initUserListSize(int size){
        userResponseList = new UserResponse[size];
    }

    public UserResponse[] getUserResponseList() {
        return userResponseList;
    }

    public UserResponse getUserAtPosition(int index) {
        return userResponseList[index];
    }


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
                        new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.unknownError));
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

    public void API_manageUser(UserResponse user, String userId, boolean canBook){
        Call<Void> call = ApiClient.getUserService().manageUser(user.getToken(), userId, canBook);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                }
                else{
                    LoadingAnimation.dismissLoadingAnimation();
                    try {
                        new AlertWindow(fragment).createAlertWindow(response.errorBody().string());
                    } catch (IOException e) {
                        new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.unknownError));
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }


    //gets a user from ID and puts it in a list at a given index position
    //must first init list size: initUserListSize
    public void API_getUsersById(UserResponse user, int index, String userId, Runnable runnable){
        Call<UserResponse> call = ApiClient.getUserService().getUserById(user.getToken(), userId);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()){
                    counter = counter+1;
                    userResponseList[index] = response.body();

                    //when all users have been retrieved, to do next:
                    if(counter == userResponseList.length) {
                        new Handler().postDelayed(runnable, 600);
                    }
                }
                else{
                    LoadingAnimation.dismissLoadingAnimation();
                    try {
                        new AlertWindow(fragment).createAlertWindow(response.errorBody().string());
                    } catch (IOException e) {
                        new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.unknownError));
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
