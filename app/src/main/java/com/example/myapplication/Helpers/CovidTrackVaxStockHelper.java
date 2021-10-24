package com.example.myapplication.Helpers;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.API.Model.Covid_tracking.StockStat;
import com.example.myapplication.API.Model.Covid_tracking.VaxStock;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.ApiClient;
import com.example.myapplication.R;
import com.example.myapplication.UI.AlertWindow;
import com.example.myapplication.UI.LoadingAnimation;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CovidTrackVaxStockHelper {
    private VaxStock vaxStock;
    private List<StockStat> stockStats;
    private Fragment fragment;

    public CovidTrackVaxStockHelper(Fragment fragment) {
        this.fragment = fragment;
    }

    public void API_getVaxStock(Activity activity, UserResponse user, Runnable runnable){
        Call<VaxStock> call = ApiClient.getUserService().getVaxStock(user.getToken());
        call.enqueue(new Callback<VaxStock>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<VaxStock> call, Response<VaxStock> response) {
                if (response.isSuccessful()) {
                    vaxStock = response.body();
                    stockStats = vaxStock.getStockStats();

                    new Handler().postDelayed(
                            runnable,600);
                }else{
                    LoadingAnimation.dismissLoadingAnimation();
                    new AlertWindow(fragment).createAlertWindow(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<VaxStock> call, Throwable t) {
                Toast.makeText(activity,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }

    public String [] getCountyNames(){
        String [] buffer = new String[stockStats.size()];
        for(int i = 0; i < stockStats.size(); i++){
            buffer[i] = stockStats.get(i).getCountyName();
        }
        return buffer;
    }

    public int [] getFilteredDataSet(int selectedCounty){
        StockStat buffer = stockStats.get(selectedCounty);
        return new int[]{buffer.getAmount()};
    }

    public VaxStock getVaxStock() {
        return vaxStock;
    }

    public void setVaxStock(VaxStock vaxStock) {
        this.vaxStock = vaxStock;
    }

    public List<StockStat> getStockStats() {
        return stockStats;
    }

    public void setStockStats(List<StockStat> stockStats) {
        this.stockStats = stockStats;
    }
}
