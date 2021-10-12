package com.example.myapplication.Admin;

import android.app.Activity;
import android.os.Handler;
import android.widget.Toast;

import com.example.myapplication.ApiClient;
import com.example.myapplication.API.Model.Appointment_user.Vaccine;
import com.example.myapplication.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiVaccine {
    private List<Vaccine> vaccineResponse;
    String responseID;

    public String[] getVaccines(){
        String[] stringVaccine = new String[vaccineResponse.size()];
        for (int i = 0; i < vaccineResponse.size(); i++) {
            stringVaccine[i] = vaccineResponse.get(i).getVaccineName();
        }
        return stringVaccine;
    }
    public String getVaccineID(int index){
        return vaccineResponse.get(index).getVaccineId();
    }
    public Vaccine getVaccine(int index){
        return vaccineResponse.get(index);
    }

    public void API_getVaccine(Activity activity, UserResponse user, Runnable runnable){
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

    public void API_postVaccine(UserResponse user, String vaccineName){
        System.out.println("in ApiVaccine");
        Call<String> call = ApiClient.getUserService().postVaccine(user.getToken(), vaccineName);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    System.out.println("response");
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