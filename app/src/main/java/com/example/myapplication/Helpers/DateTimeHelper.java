package com.example.myapplication.Helpers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myapplication.API.Model.Appointment_user.Date_Time;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DateTimeHelper {
    private ArrayList<LocalDateTime> localDateTimeArray;
    private ArrayList<String> times;
    private ArrayList<Calendar> days;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public DateTimeHelper(List<Date_Time> dateTimes){

        localDateTimeArray = new ArrayList<LocalDateTime>();

        for (int i = 0; i < dateTimes.size(); i++) {
            localDateTimeArray.add(dateTimes.get(i).getTime());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String[] getTimes(Calendar day) {
        int hour;
        int minute;

        times = new ArrayList<String>();

        for (int i = 0; i < localDateTimeArray.size(); i++) {
            if (localDateTimeArray.get(i).getYear() == day.get(Calendar.YEAR)
                    && localDateTimeArray.get(i).getMonthValue() == (day.get(Calendar.MONTH) + 1)
                    && localDateTimeArray.get(i).getDayOfMonth() == day.get(Calendar.DAY_OF_MONTH)) {

                hour = localDateTimeArray.get(i).getHour();
                minute = localDateTimeArray.get(i).getMinute();

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
        days = new ArrayList<Calendar>();

        for (int i = 0; i < localDateTimeArray.size(); i++) {
            Calendar buffer = Calendar.getInstance();
            buffer.set(Calendar.YEAR, localDateTimeArray.get(i).getYear());
            buffer.set(Calendar.MONTH, localDateTimeArray.get(i).getMonthValue() - 1);
            buffer.set(Calendar.DAY_OF_MONTH, localDateTimeArray.get(i).getDayOfMonth());
            days.add(buffer);
        }

        return days.toArray(new Calendar[days.size()]);
    }
}
