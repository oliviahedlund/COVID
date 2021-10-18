package com.example.myapplication.Helpers;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.API.Model.Appointment_user.Date_Time;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.ApiClient;
import com.example.myapplication.R;
import com.example.myapplication.UI.AlertWindow;
import com.example.myapplication.UI.LoadingAnimation;
import com.example.myapplication.UI.UserAppointment.Appointment_Info;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DateTimeHelper {
    private ArrayList<ZonedDateTime> zonedDateTimeArrayList;
    private List<Date_Time> dateTimes;
    private Fragment fragment;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public DateTimeHelper(Fragment fragment){
        this.fragment = fragment;
        zonedDateTimeArrayList = new ArrayList<ZonedDateTime>();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void API_getDateTime(Activity activity, UserResponse user, Runnable runnable, String selectedCenter){
        Call<List<Date_Time>> call = ApiClient.getUserService().getDateTimes(user.getToken(), selectedCenter);
        call.enqueue(new Callback<List<Date_Time>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<Date_Time>> call, Response<List<Date_Time>> response) {
                if (response.isSuccessful()) {
                    dateTimes = response.body();
                    for (int i = 0; i < dateTimes.size(); i++) {
                        zonedDateTimeArrayList.add(dateTimes.get(i).getTime());
                    }
                    new Handler().postDelayed(
                        runnable,600);
                }else{
                    LoadingAnimation.dismissLoadingAnimation();
                    new AlertWindow(fragment).createAlertWindow(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Date_Time>> call, Throwable t) {
                Toast.makeText(activity,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(fragment).createAlertWindow(fragment.getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String[] getTimes(Calendar day) {
        int hour;
        int minute;

        ArrayList<String> times = new ArrayList<String>();

        for (int i = 0; i < zonedDateTimeArrayList.size(); i++) {
            if (zonedDateTimeArrayList.get(i).getYear() == day.get(Calendar.YEAR)
                    && zonedDateTimeArrayList.get(i).getMonthValue() == (day.get(Calendar.MONTH) + 1)
                    && zonedDateTimeArrayList.get(i).getDayOfMonth() == day.get(Calendar.DAY_OF_MONTH)) {

                hour = zonedDateTimeArrayList.get(i).getHour();
                minute = zonedDateTimeArrayList.get(i).getMinute();

                if(minute < 10){
                    times.add(hour + " : 0" + minute);
                } else
                    times.add(hour + " : " + minute);
            }
        }

        String [] allowedTimes = new String[times.size()];

        return times.toArray(allowedTimes);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Calendar [] getDates() {
        ArrayList<Calendar> days = new ArrayList<Calendar>();

        for (int i = 0; i < zonedDateTimeArrayList.size(); i++) {
            Calendar buffer = Calendar.getInstance();
            buffer.set(Calendar.YEAR, zonedDateTimeArrayList.get(i).getYear());
            buffer.set(Calendar.MONTH, zonedDateTimeArrayList.get(i).getMonthValue() - 1);
            buffer.set(Calendar.DAY_OF_MONTH, zonedDateTimeArrayList.get(i).getDayOfMonth());
            days.add(buffer);
        }

        return days.toArray(new Calendar[days.size()]);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalTime getSelectedTime(int time){
        return dateTimes.get(time).getTime().toLocalTime();
    }

    public int getLength(int time){
        return dateTimes.get(time).getLength();
    }
}
