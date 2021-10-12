package com.example.myapplication.Admin;

import android.app.Activity;
import android.os.Handler;
import android.widget.Toast;

import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.ApiClient;
import com.example.myapplication.API.Model.Appointment_user.Vaccine;
import com.example.myapplication.API.Model.User.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiVaccine {
    private List<Vaccine> vaccineResponse;

    public String[] getVaccines(){
        String[] stringVaccine = new String[vaccineResponse.size()];
        for (int i = 0; i < vaccineResponse.size(); i++) {
            stringVaccine[i] = vaccineResponse.get(i).getVaccineName();
        }
        return stringVaccine;
    }
    public String getVaccinID(int index){
        return vaccineResponse.get(index).getVaccineId();
    }

    public void CallVaccineAPI(Activity activity, UserResponse user, Runnable runnable){
        Call<List<Vaccine>> vaccineResponseCall = ApiClient.getUserService().getVaccines(user.getToken());
        vaccineResponseCall.enqueue(new Callback<List<Vaccine>>() {

            @Override
            public void onResponse(Call<List<Vaccine>> call, Response<List<Vaccine>> response) {
                //TODO: errorhandling
                if (response.isSuccessful()) {
                    vaccineResponse = response.body(); //i userResponse ligger all information om användaren

                    new Handler().postDelayed(runnable,600);

                }else{
                    Toast.makeText(activity,"Vaccin get failed", Toast.LENGTH_LONG).show();
                    System.out.println("else");
                }
            }

            @Override
            public void onFailure(Call<List<Vaccine>> call, Throwable t) {
                Toast.makeText(activity,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                System.out.println("fail");
            }
        });
    }
}
