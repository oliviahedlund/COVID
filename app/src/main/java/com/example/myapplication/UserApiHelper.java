package com.example.myapplication;

import android.app.Activity;
import android.os.Handler;
import android.widget.Toast;

import com.example.myapplication.API.Model.Login.LoginRequest;
import com.example.myapplication.API.Model.Login.LoginResponse;
import com.example.myapplication.API.Model.User.UserRequest;
import com.example.myapplication.API.Model.User.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserApiHelper {
    private UserResponse userResponse;
    private Activity activity;
    private boolean successful;
    private String loginToken;

    public UserApiHelper(Activity activity){
        this.activity = activity;
    }
    public UserResponse getUserResponse(){
        return userResponse;
    }
    public boolean callSuccessful(){
        return successful;
    }

    public void callUserApi(Runnable runnable){
        userResponse = new UserResponse();
        successful=false;
        UserRequest userRequest = new UserRequest();
        String bearerToken = "Bearer " + loginToken;


        Call<UserResponse> userResponseCall = ApiClient.getUserService().getUser(bearerToken);
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if (response.isSuccessful()) {
                    successful=true;
                    //Toast.makeText(MainActivity.this, "ok, got user", Toast.LENGTH_LONG).show();
                    userResponse = response.body(); //i userResponse ligger all information om användaren
                    userResponse.setToken(bearerToken);
                    new Handler().postDelayed(runnable,600);

                }else{
                    Toast.makeText(activity,"user Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(activity,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void CallLoginApi(Activity activity, LoginRequest loginRequest, Runnable runnable) {
        successful=false;

        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                //om response ej är 200 ha felhantering, isBusy
                if (response.isSuccessful()) {
                    Toast.makeText(activity, "Login Successful", Toast.LENGTH_LONG).show();
                    LoginResponse loginResponse = response.body();
                    loginToken = loginResponse.getToken();
                    callUserApi(runnable);

                } else {
                    Toast.makeText(activity, "Login Failed", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(activity, "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
