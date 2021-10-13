package com.example.myapplication;
import android.app.Activity;
import android.os.Handler;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.API.Model.Appointment_user.Center;
import com.example.myapplication.API.Model.Appointment_user.Vaccine;
//import com.example.myapplication.AlertWindow;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CenterVaccineHelper {
    private List<Center> centers;
    private int selectedCenter;
    private Fragment fragment;
    private String responseID;

    public CenterVaccineHelper(Fragment fragment){
        this.fragment = fragment;
    }

    public void API_postCenterVaccine(UserResponse user, String centerID, Vaccine vaccine, Runnable runnable){
        List<Vaccine> vaccines = new ArrayList<Vaccine>();
        vaccines.add(vaccine);
        Call<String> call = ApiClient.getUserService().postCenterVaccine(user.getToken(), centerID, vaccines);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    responseID = response.body().toString();
                    new Handler().postDelayed(runnable,600);

                }else{
                    System.out.println("could not add center");
                    LoadingAnimation.dismissLoadingAnimation();
                    new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println(t.getLocalizedMessage());
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }

    public void API_postCenter(UserResponse user, Center bodyCenter, Runnable runnable){
        Call<String> call = ApiClient.getUserService().postCenter(user.getToken(), bodyCenter);
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

    public String getResponseID(){
        return responseID;
    }

    public void API_getCenters(Activity activity, UserResponse user, Runnable runnable){
        Call<List<Center>> call = ApiClient.getUserService().getCenters(user.getToken());
        call.enqueue(new Callback<List<Center>>() {
            @Override
            public void onResponse(Call<List<Center>> call, Response<List<Center>> response) {
                if (response.isSuccessful()) {
                    centers = response.body();
                    new Handler().postDelayed(runnable,600);

                }else{
                    LoadingAnimation.dismissLoadingAnimation();
                    new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
                }
            }

            @Override
            public void onFailure(Call<List<Center>> call, Throwable t) {
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }

    public String [] getCenters(){
        String [] centerArray = new String[centers.size()];
        for (int i = 0; i < centers.size(); i++) {
            centerArray[i] = centers.get(i).getCenterName();
        }
        return centerArray;
    }

    public String [] getVaccines(int center){
        ArrayList<String> vaccineBuffer = new ArrayList<String>();
        for(Vaccine vaccine: centers.get(center).getVaccines()){
            if(vaccine != null) vaccineBuffer.add(vaccine.getVaccineName());
        }

        String [] vaccineArray = new String[vaccineBuffer.size()];
        vaccineArray = vaccineBuffer.toArray(vaccineArray);

        return vaccineArray;
    }

    public String getSelectedCenter(int center){
        selectedCenter = center;
        return centers.get(center).getCenterId();
    }

    public String getSelectedVaccine(int vaccine){
        return centers.get(selectedCenter).getVaccines().get(vaccine).getVaccineId();
    }
}
