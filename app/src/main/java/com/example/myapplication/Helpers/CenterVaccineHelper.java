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
import com.example.myapplication.UI.UserAppointment.Appointment_Info;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CenterVaccineHelper {
    private List<Center> centers;
    private Center center;
    private Vaccine vaccine;
    private int selectedCenter;
    private Fragment fragment;
    private String responseID;

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
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void API_getCenterName(Activity activity, UserResponse user, Runnable runnable){
        Call<Center> call = ApiClient.getUserService().getCenterName(user.getToken(), user.getAppointment().getCenterId());
        call.enqueue(new Callback<Center>() {
            @Override
            public void onResponse(Call<Center> call, Response<Center> response) {
                if (response.isSuccessful()) {
                    center = response.body();

                    new Handler().postDelayed(runnable,600);

                }else{
                    LoadingAnimation.dismissLoadingAnimation();
                    new AlertWindow(fragment).createAlertWindow(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Center> call, Throwable t) {
                Toast.makeText(activity,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void API_getVaccineName(Activity activity, UserResponse user, Runnable runnable){
        Call<Vaccine> call = ApiClient.getUserService().getVaccineName(user.getToken(), user.getAppointment().getVaccineId());
        call.enqueue(new Callback<Vaccine>() {
            @Override
            public void onResponse(Call<Vaccine> call, Response<Vaccine> response) {
                if (response.isSuccessful()) {
                    vaccine = response.body();

                    new Handler().postDelayed(runnable,600);

                }else{
                    LoadingAnimation.dismissLoadingAnimation();
                    new AlertWindow(fragment).createAlertWindow(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Vaccine> call, Throwable t) {
                Toast.makeText(activity,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
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

    public String [] getCenters(){
        String [] centerArray = new String[centers.size()];
        for (int i = 0; i < centers.size(); i++) {
            centerArray[i] = centers.get(i).getCenterName();
        }
        return centerArray;
    }

    public String [] getVaccines(int center){
        List<Vaccine> list = centers.get(center).getVaccines();
        String [] vaccineArray = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) != null) vaccineArray[i] = list.get(i).getVaccineName();
        }
        return vaccineArray;
    }

    public String getSelectedCenter(int center){
        selectedCenter = center;
        return centers.get(center).getCenterId();
    }

    public String getSelectedVaccine(int vaccine){
        return centers.get(selectedCenter).getVaccines().get(vaccine).getVaccineId();
    }

    public String getCenterNameFromId(String id){
        for (Center c: centers) {
            if(id.equals(c.getCenterId())){
                return c.getCenterName();
            }
        }
        return null;
    }

    public String getCenterName() {
        return center.getCenterName();
    }

    public String getVaccineName() {
        return vaccine.getVaccineName();
    }

    public String getResponseID(){
        return responseID;
    }
}
