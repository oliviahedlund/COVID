package com.example.myapplication.Helpers;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.API.Model.Covid_tracking.Cases;
import com.example.myapplication.API.Model.Covid_tracking.CountyVaxStat;
import com.example.myapplication.API.Model.Covid_tracking.UptakeStat;
import com.example.myapplication.API.Model.Covid_tracking.Vaccination;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.ApiClient;
import com.example.myapplication.R;
import com.example.myapplication.UI.AlertWindow;
import com.example.myapplication.UI.LoadingAnimation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CovidTrackVaccinationHelper {
    private Fragment fragment;
    private Vaccination vaccinationStats;
    private List<CountyVaxStat> countyVaxStats;
    private List<UptakeStat> uptakeStats;
    private DecimalFormat df = new DecimalFormat("###.####");

    public CovidTrackVaccinationHelper(Fragment fragment) {
        this.fragment = fragment;
    }

    public void API_getVaccinationStats(Activity activity, UserResponse user, Runnable runnable){
        Call<Vaccination> call = ApiClient.getUserService().getVaccinationStats(user.getToken());
        call.enqueue(new Callback<Vaccination>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Vaccination> call, Response<Vaccination> response) {
                if (response.isSuccessful()) {
                    vaccinationStats = response.body();
                    countyVaxStats = vaccinationStats.getCountyStats();
                    uptakeStats = vaccinationStats.getCumulativeUptakeStats();

                    new Handler().postDelayed(
                            runnable,600);
                }else{
                    LoadingAnimation.dismissLoadingAnimation();
                    new AlertWindow(fragment).createAlertWindow(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Vaccination> call, Throwable t) {
                Toast.makeText(activity,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }

    public String [] getFilteredDataSet(String [] dataSet){
        if(dataSet.length == 2){
            for(int i = 0; i < countyVaxStats.size(); i++){
                if(countyVaxStats.get(i).getCountyName().equals(dataSet[0]) && String.valueOf(countyVaxStats.get(i).getAgeGroup()).equals(dataSet[1])){
                    return new String[]{String.valueOf(countyVaxStats.get(i).getPopulation())
                            , String.valueOf(countyVaxStats.get(i).getMinFirstDoseAmount())
                            , String.valueOf(countyVaxStats.get(i).getFullVaccinationAmount())
                            , String.valueOf(df.format(countyVaxStats.get(i).getMinFirstDoseRation()))
                            , String.valueOf(df.format(countyVaxStats.get(i).getFullVaccinationRatio()))};
                }
            }
        }
        else {

            for(int i = 0; i < uptakeStats.size(); i++){
                if(String.valueOf(uptakeStats.get(i).getYear()).equals(dataSet[0].split(fragment.getResources().getString(R.string.week))[0].trim())
                && String.valueOf(uptakeStats.get(i).getWeek()).equals(dataSet[0].split(fragment.getResources().getString(R.string.week))[1].trim())
                && uptakeStats.get(i).getCountyName().equals(dataSet[1])
                && String.valueOf(uptakeStats.get(i).getDoseType()).equals(dataSet[2])){
                    return new String[]{String.valueOf(uptakeStats.get(i).getVaccinatedAmount())
                            , String.valueOf(df.format(uptakeStats.get(i).getVaccinatedRatio()))};
                }
            }
        }
        return null;
    }

    public String [] getCountyNames_countyStats(){
        List<String> countyNames = new ArrayList<String>();
        countyNames.add(countyVaxStats.get(0).getCountyName());
        for(int i = 0 ,j = 0; i < countyVaxStats.size(); i++){
            if(!countyVaxStats.get(i).getCountyName().equals(countyNames.get(j))){
                countyNames.add(countyVaxStats.get(i).getCountyName());
                j++;
            }
        }

        String [] buffer = new String[countyNames.size()];
        return countyNames.toArray(buffer);

    }

    public String [] getAgeGroup(){
        List<String> list = new ArrayList<String>();
        list.add(String.valueOf(countyVaxStats.get(0).getAgeGroup()));
        for(int i = 0; i < countyVaxStats.size(); i++){
            if(!list.contains(String.valueOf(countyVaxStats.get(i).getAgeGroup()))){
                list.add(String.valueOf(countyVaxStats.get(i).getAgeGroup()));
            }
        }

        String [] buffer = new String[list.size()];
        return list.toArray(buffer);

    }

    public String [] getCountyNames_uptakeStats(){
        List<String> countyNames = new ArrayList<String>();
        countyNames.add(uptakeStats.get(0).getCountyName());
        for(int i = 0; i < uptakeStats.size(); i++){
            if(!countyNames.contains(uptakeStats.get(i).getCountyName())){
                countyNames.add(uptakeStats.get(i).getCountyName());
            }
        }

        String [] buffer = new String[countyNames.size()];
        return countyNames.toArray(buffer);

    }


    public String [] getYearWeek(){
        List<String> list = new ArrayList<String>();
        list.add(String.valueOf(uptakeStats.get(0).getYear()) + " " + fragment.getResources().getString(R.string.week) + " " + String.valueOf(uptakeStats.get(0).getWeek()));
        for(int i = 0; i < uptakeStats.size(); i++){
            if(!list.contains(String.valueOf(uptakeStats.get(i).getYear()) + " " + fragment.getResources().getString(R.string.week) + " " + String.valueOf(uptakeStats.get(i).getWeek()))){
                list.add(String.valueOf(uptakeStats.get(i).getYear()) + " " + fragment.getResources().getString(R.string.week) + " " + String.valueOf(uptakeStats.get(i).getWeek()));
            }
        }

        String [] buffer = new String[list.size()];
        return list.toArray(buffer);

    }

    public List<CountyVaxStat> getCountyVaxStats() {
        return countyVaxStats;
    }

    public void setCountyVaxStats(List<CountyVaxStat> countyVaxStats) {
        this.countyVaxStats = countyVaxStats;
    }

    public List<UptakeStat> getUptakeStats() {
        return uptakeStats;
    }

    public void setUptakeStats(List<UptakeStat> uptakeStats) {
        this.uptakeStats = uptakeStats;
    }
}
