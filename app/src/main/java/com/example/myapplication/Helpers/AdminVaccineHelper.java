package com.example.myapplication.Helpers;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.UI.AlertWindow;
import com.example.myapplication.ApiClient;
import com.example.myapplication.API.Model.Appointment_user.Vaccine;
import com.example.myapplication.LoadingAnimation;
import com.example.myapplication.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminVaccineHelper {
    private List<Vaccine> vaccineResponse;
    String responseID;
    private Fragment fragment;

    public AdminVaccineHelper(Fragment fragment){
        this.fragment = fragment;
    }

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void API_getVaccine(Activity activity, UserResponse user, Runnable runnable){
        Call<List<Vaccine>> vaccineResponseCall = ApiClient.getUserService().getVaccines(user.getToken());
        vaccineResponseCall.enqueue(new Callback<List<Vaccine>>() {

            @Override
            public void onResponse(Call<List<Vaccine>> call, Response<List<Vaccine>> response) {
                if (response.isSuccessful()) {
                    vaccineResponse = response.body(); //i userResponse ligger all information om användaren
                    new Handler().postDelayed(runnable,600);

                }else{
                    LoadingAnimation.dismissLoadingAnimation();
                    new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
                }

            }

            @Override
            public void onFailure(Call<List<Vaccine>> call, Throwable t) {
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }

    public void API_postVaccine(UserResponse user, String vaccineName, Runnable runnable){
        System.out.println("in AdminVaccineHelper");
        Call<String> call = ApiClient.getUserService().postVaccine(user.getToken(), vaccineName);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    responseID = response.body().toString();
                    new Handler().postDelayed(runnable,600);


                }else{
                    LoadingAnimation.dismissLoadingAnimation();
                    new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }
}
