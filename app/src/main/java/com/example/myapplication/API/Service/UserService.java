package com.example.myapplication.API.Service;

import com.example.myapplication.API.Model.Appointment_user.AppointmentRequest;
import com.example.myapplication.API.Model.Appointment_user.AppointmentResponse;
import com.example.myapplication.API.Model.Appointment_user.Date_Time;
import com.example.myapplication.API.Model.Appointment_user.Center;
import com.example.myapplication.API.Model.Appointment_user.QuestionnaireRequest;
import com.example.myapplication.API.Model.Appointment_user.Vaccine;
import com.example.myapplication.API.Model.Login.LoginRequest;
import com.example.myapplication.API.Model.Login.LoginResponse;
import com.example.myapplication.API.Model.Register.RegisterRequest;
import com.example.myapplication.API.Model.Register.RegisterResponse;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.Admin.PostRangeRequest;

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

        @POST("/api/Appointments/Booking/")
        Call<AppointmentResponse> postNewAppointments(@Header("Authorization") String authHeader, @Body AppointmentRequest appointmentRequest);

        @GET("/api/Center")
        Call<List<Center>> getCenters(@Header("Authorization") String authHeader);

        @POST("/api/Questionare")
        Call<Void> postNewQuestionnaire(@Header("Authorization") String authHeader, @Body QuestionnaireRequest questionnaireRequest);

        @POST("/api/Center")
        Call<String> postCenters(@Header("Authorization") String authHeader, @Body Center center);

        @POST("/api/Appointments/range/")
        Call<String> setRange(@Header("Authorization") String authHeader, @Body PostRangeRequest postRangeRequest);

        @GET("/api/Vaccine")
        Call<List<Vaccine>> getVaccines(@Header("Authorization") String authHeader);

}
