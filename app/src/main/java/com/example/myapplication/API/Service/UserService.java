package com.example.myapplication.API.Service;

import com.example.myapplication.API.Model.Appointment_user.Date_Time;
import com.example.myapplication.API.Model.Appointment_user.Center;
import com.example.myapplication.API.Model.Login.LoginRequest;
import com.example.myapplication.API.Model.Login.LoginResponse;
import com.example.myapplication.API.Model.Register.RegisterRequest;
import com.example.myapplication.API.Model.Register.RegisterResponse;
import com.example.myapplication.API.Model.User.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

        @POST("/api/Authenticate/login/")
        Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

        @POST("/api/Authenticate/register/")
        Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

        @GET("/api/User/")
        Call<UserResponse> getUser(@Header("Authorization") String authHeader);

        @GET("/api/Appointments/Booking/")
        Call<List<Date_Time>> getDateTimes(@Header("Authorization") String authHeader, @Query("center") String center);

        @GET("/api/Center")
        Call<List<Center>> getCenters(@Header("Authorization") String authHeader);
}
