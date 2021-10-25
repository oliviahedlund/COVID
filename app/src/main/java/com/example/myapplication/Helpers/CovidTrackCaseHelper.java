package com.example.myapplication.Helpers;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.API.Model.Covid_tracking.CaseStat;
import com.example.myapplication.API.Model.Covid_tracking.Cases;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.ApiClient;
import com.example.myapplication.R;
import com.example.myapplication.UI.AlertWindow;
import com.example.myapplication.UI.LoadingAnimation;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CovidTrackCaseHelper {
    private Cases cases;
    private List<CaseStat> caseStats;
    private Fragment fragment;

    public CovidTrackCaseHelper(Fragment fragment) {
        this.fragment = fragment;
    }

    public void API_getCases(Activity activity, UserResponse user, Runnable runnable){
        Call<Cases> call = ApiClient.getUserService().getCases(user.getToken());
        call.enqueue(new Callback<Cases>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Cases> call, Response<Cases> response) {
                if (response.isSuccessful()) {
                    cases = response.body();
                    caseStats = cases.getCaseStats();

                    caseStats.add(CaseStatForSweden());

                    new Handler().postDelayed(
                            runnable,600);
                }else{
                    LoadingAnimation.dismissLoadingAnimation();
                    new AlertWindow(fragment).createAlertWindow(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Cases> call, Throwable t) {
                Toast.makeText(activity,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }

    private CaseStat CaseStatForSweden(){
        int totalCaseCount = 0;
        int totalIntensiveCareCount = 0;
        int totalDeathCount = 0;

        for(int i = 0; i < caseStats.size(); i++){
            totalCaseCount = totalCaseCount + caseStats.get(i).getTotalCaseCount();
            totalIntensiveCareCount = totalIntensiveCareCount + caseStats.get(i).getTotalIntensiveCareCount();
            totalDeathCount = totalDeathCount + caseStats.get(i).getTotalDeathCount();
        }

        CaseStat buffer = new CaseStat();
        buffer.setCountyName(fragment.getResources().getString(R.string.sweden));
        buffer.setTotalCaseCount(totalCaseCount);
        buffer.setTotalIntensiveCareCount(totalIntensiveCareCount);
        buffer.setTotalDeathCount(totalDeathCount);
        return buffer;
    }

    public String [] getCountyNames(){
        String [] buffer = new String[caseStats.size()];
        for(int i = 0; i < caseStats.size(); i++){
            buffer[i] = caseStats.get(i).getCountyName();
        }
        return buffer;
    }

    public int [] getFilteredDataSet(int selectedCounty){
        CaseStat buffer = caseStats.get(selectedCounty);
        return new int[]{buffer.getTotalCaseCount(), buffer.getTotalIntensiveCareCount(), buffer.getTotalDeathCount()};
    }

    public Cases getCases() {
        return cases;
    }

    public void setCases(Cases cases) {
        this.cases = cases;
    }

    public List<CaseStat> getCaseStats() {
        return caseStats;
    }

    public void setCaseStats(List<CaseStat> caseStats) {
        this.caseStats = caseStats;
    }
}
