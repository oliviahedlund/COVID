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

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

        @GET("/api/Appointments/Booking/")
        Call<List<Date_Time>> getDateTimes(@Header("Authorization") String authHeader, @Query("center") String center);

        @POST("/api/Appointments/Booking/")
        Call<Void> postNewAppointments(@Header("Authorization") String authHeader, @Body AppointmentRequest appointmentRequest);

        @PUT("/api/Appointments/Booking/")
        Call<Void> updateAppointments(@Header("Authorization") String authHeader, @Body AppointmentRequest appointmentRequest);

        @DELETE("/api/Appointments/Booking/")
        Call<Void> deleteAppointment_user(@Header("Authorization") String authHeader);

        @GET("/api/Center")
        Call<List<Center>> getCenters(@Header("Authorization") String authHeader);

        @GET("/api/Center/{centerId}")
        Call<Center> getCenterName(@Header("Authorization") String authHeader, @Path("centerId") String centerId);

        @GET("/api/Vaccine/{vaccineId}")
        Call<Vaccine> getVaccineName(@Header("Authorization") String authHeader, @Path("vaccineId") String vaccineId);

        @POST("/api/Questionare")
        Call<Void> postNewQuestionnaire(@Header("Authorization") String authHeader, @Body QuestionnaireRequest questionnaireRequest);



}
