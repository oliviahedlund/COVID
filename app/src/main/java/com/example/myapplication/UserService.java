package com.example.myapplication;

import com.example.myapplication.Admin.PostRangeRequest;
import com.example.myapplication.Booking.BookingResponse;
import com.example.myapplication.API.Model.Appointment_user.Center;
import com.example.myapplication.Booking.SetBookingRequest;
import com.example.myapplication.API.Model.Appointment_user.Vaccine;

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

        @GET("/api/Appointments/booking/")
        Call<List<BookingResponse>> booking(@Header("Authorization") String authHeader, @Query("month") int month, @Query("year") int year, @Query("center") int center);

        @POST("/api/Appointments/booking/")
        Call<String> setBooking(@Header("Authorization") String authHeader, @Body SetBookingRequest setBookingRequest);

        @POST("/api/Appointments/range/")
        Call<String> setRange(@Header("Authorization") String authHeader, @Body PostRangeRequest postRangeRequest);

        @POST("/api/Center")
        Call<String> postCenter(@Header("Authorization") String authHeader, @Body Center center);

        @GET("/api/Center")
        Call<List<Center>> getCenters(@Header("Authorization") String authHeader);

        @GET("/api/Vaccine")
        Call<List<Vaccine>> getVaccines(@Header("Authorization") String authHeader);

        @POST("/api/Center/Vaccines")
        Call<String> postCenterVaccine(@Header("Authorization") String authHeader, @Query("center") String centerID, @Body List<Vaccine> vaccines);

        @POST("/api/Vaccine")
        Call<String> postVaccine(@Header("Authorization") String authHeader, @Query("vaccineName") String vaccineName);


}
