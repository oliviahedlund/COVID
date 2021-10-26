package com.example.myapplication.Helpers;

import android.os.Build;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.API.Model.Admin.UserInfo;
import com.example.myapplication.API.Model.Appointment_user.AppointmentRequest;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.AdminActivity;
import com.example.myapplication.ApiClient;
import com.example.myapplication.UI.LoadingAnimation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardGenericHelper {
    private UserInfo userInfo;
    private AdminActivity activity;

    public DashboardGenericHelper(Fragment fragment){}


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getUserInfoApi(UserResponse user,String id,Runnable runnable){
        Call<UserInfo> UserInfoCall = ApiClient.getUserService().getUserInfoAll(user.getToken(), id);
        UserInfoCall.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if(response.isSuccessful()) {
                    userInfo = response.body();

                    new Handler().postDelayed(runnable, 600);

                    System.out.println("userInfo.getId() = " + userInfo.getId());
                    if (!userInfo.getId().equals("")) System.out.println("Veri good work yes");
                    else System.out.println("Empty userInfo");
                }else{//Unsuccessful response
                    LoadingAnimation.dismissLoadingAnimation();
                    Toast.makeText(activity,"UserInfo error", Toast.LENGTH_LONG).show();
                    System.out.println("Fail - else");
                }
            }
            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                LoadingAnimation.dismissLoadingAnimation();
                Toast.makeText(activity,"Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                System.out.println("Fail - onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    public UserInfo getUserInfo(){ return userInfo; }
}
