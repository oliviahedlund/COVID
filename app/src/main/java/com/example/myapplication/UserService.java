package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

        @POST("/api/Authenticate/login/")
        Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

        @POST("/api/Authenticate/register/")
        Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);
}
