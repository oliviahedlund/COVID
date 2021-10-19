package com.example.myapplication.Helpers;

import android.os.Handler;

import androidx.fragment.app.Fragment;

import com.example.myapplication.API.Model.Appointment_user.AppointmentRequest;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.ApiClient;
import com.example.myapplication.R;
import com.example.myapplication.UI.AlertWindow;
import com.example.myapplication.UI.LoadingAnimation;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentsManageExtraHelper {
    private Fragment fragment;
    private List<AppointmentRequest> responseList;

    public AppointmentsManageExtraHelper(Fragment fragment){
        this.fragment = fragment;
    }

    public List<AppointmentRequest> getResponseList() {
        return responseList;
    }


    int partition(List<AppointmentRequest> arr, int low, int high)
    {
        AppointmentRequest pivot = arr.get(high);
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++)
        {
            // If current element is smaller than or
            // equal to pivot
            if (arr.get(j).getTime().compareTo(pivot.getTime()) <=0)
            {
                i++;

                // swap arr[i] and arr[j]
                AppointmentRequest temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        AppointmentRequest temp = arr.get(i+1);
        arr.set(i+1, arr.get(high));
        arr.set(high, temp);

        return i+1;
    }


    void quickSort(List<AppointmentRequest> arr, int low, int high)
    {
        if (low < high)
        {
			/* pi is partitioning index, arr[pi] is
			now at right place */
            int pi = partition(arr, low, high);

            // Recursively sort elements before
            // partition and after partition
            quickSort(arr, low, pi-1);
            quickSort(arr, pi+1, high);
        }
    }

    public List<AppointmentRequest> sortResponse(List<AppointmentRequest> list){
        quickSort(list, 0, list.size()-1);
        return list;

    }

    public void API_getAppointments(UserResponse user, Runnable runnable, boolean getPrior){
        Call<List<AppointmentRequest>> call = ApiClient.getUserService().getAppointments(user.getToken(), getPrior);
        call.enqueue(new Callback<List<AppointmentRequest>>() {
            @Override
            public void onResponse(Call<List<AppointmentRequest>> call, Response<List<AppointmentRequest>> response) {
                if(response.isSuccessful()){
                    responseList = sortResponse(response.body());
                    new Handler().postDelayed(runnable, 600);
                }
                else{
                    LoadingAnimation.dismissLoadingAnimation();
                    new AlertWindow(fragment).createAlertWindow("Get Appointments Failed");

                }
            }

            @Override
            public void onFailure(Call<List<AppointmentRequest>> call, Throwable t) {
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }

    public void API_postValidation(UserResponse user, Runnable runnable, String appointmentId){
        Call<Void> call = ApiClient.getUserService().postValidation(user.getToken(), appointmentId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    new Handler().postDelayed(runnable, 600);
                }
                else{
                    LoadingAnimation.dismissLoadingAnimation();
                    new AlertWindow(fragment).createAlertWindow("Set Validation Failed");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });

    }
}
