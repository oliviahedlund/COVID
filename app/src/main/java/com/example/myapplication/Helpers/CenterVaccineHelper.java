package com.example.myapplication.Helpers;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.API.Model.Appointment_user.Center;
import com.example.myapplication.API.Model.Appointment_user.Vaccine;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.ApiClient;
import com.example.myapplication.R;
import com.example.myapplication.UI.AlertWindow;
import com.example.myapplication.UI.LoadingAnimation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class CenterVaccineHelper {
    private List<Center> centers;
    private int selectedCenter;
    private Fragment fragment;

    public CenterVaccineHelper(Fragment fragment){
        this.fragment = fragment;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
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
                    new AlertWindow(fragment).createAlertWindow(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Center>> call, Throwable t) {
                Toast.makeText(activity,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }
    public String [] getCenters(){
        ArrayList<String> centerBuffer = new ArrayList<String>();
        for(Center center: centers){
            if(center != null) centerBuffer.add(center.getName());
        }

        String [] centerArray = new String[centerBuffer.size()];
        centerArray = centerBuffer.toArray(centerArray);

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
        return centers.get(center).getId();
    }

    public String getSelectedVaccine(int vaccine){
        return centers.get(selectedCenter).getVaccines().get(vaccine).getVaccineId();
    }
}