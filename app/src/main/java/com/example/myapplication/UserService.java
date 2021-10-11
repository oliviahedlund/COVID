package com.example.myapplication;

import androidx.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

        @POST("/api/Authenticate/login/")
        Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

        @POST("/api/Authenticate/register/")
        Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

        @GET("/api/User/")
        Call<UserResponse> getUser(@Header("Authorization") String authHeader);

        @GET("/api/Appointments/Booking/Manage/Extra/")
        Call<List<AppointmentResponse>> getAllAppointments(@Header("Authorization") String authHeader, @Query("getPrior") boolean getPrior);

        @GET("/api/User/Manage")
        Call<UserInfo> getUserInfoAll(@Header("Authorization") String authHeader, @Query("UserId") String UserId);
}
