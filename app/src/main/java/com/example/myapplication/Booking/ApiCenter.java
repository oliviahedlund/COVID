package com.example.myapplication.Booking;

import com.example.myapplication.API.Model.Appointment_user.Center;
import com.example.myapplication.ApiClient;
import com.example.myapplication.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCenter {
    private String responseID;

    public String getResponseID(){
        return responseID;
    }

    public void API_postCenters(UserResponse user, Center bodycenter){
        Call<String> call = ApiClient.getUserService().postCenter(user.getToken(), bodycenter);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    responseID = response.body().toString();

                }else{
                    System.out.println("could not add center");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println(t.getLocalizedMessage());
            }
        });
    }
}
