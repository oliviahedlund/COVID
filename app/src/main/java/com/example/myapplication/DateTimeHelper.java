package com.example.myapplication;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeHelper {
    static int maxAppointmentsMonth = 2500;
    static int maxDaysMonth = 31;
    static int maxAppointmentsDay = 80;
    //{"2021-09-26T11:00:00", "2021-09-27T11:00:00", "2021-09-27T11:40:00"}

    private String[] array;
    private LocalDateTime[] dtArray;
    private int[] days;
    private LocalTime[] times;


    //Example: String[] array = {"2021-09-26T11:00:00", "2021-09-27T11:00:00", "2021-09-27T11:40:00"};
    //         DateTimeHelper dt = new DateTimeHelperTest(array);
    public DateTimeHelper(String[] _array) {
        array = _array;
        dtArray = new LocalDateTime[maxAppointmentsMonth];
        days = new int[maxDaysMonth];
        times = new LocalTime[maxAppointmentsDay];
    }

    //gets times from initialization-array + specified day and returns a list of available times
    //list size is 80, where non-used space is null
    @RequiresApi(api = Build.VERSION_CODES.O)  //method invoking this must add this line
    public LocalTime[] getTimes(int _day) {
        int hour;
        int minute;

        int j = 0;
        for (int i = 0; i < array.length; i++) {
            dtArray[i] = LocalDateTime.parse(array[i]);
            System.out.println("Available appointment: "+dtArray[i]);
            if(dtArray[i].getDayOfMonth() == _day){
                hour = dtArray[i].getHour();
                minute = dtArray[i].getMinute();
                times[j] = LocalTime.of(hour, minute);
                System.out.println("Added time: "+times[j]);
                j++;
            }

        }
        System.out.println("Returned from getTimes");
        return times;
    }

    //gets dates from initialization-array and returns a list of available days
    //list size is 31, where non-used space is 0
    @RequiresApi(api = Build.VERSION_CODES.O) //method invoking this must add this line
    public int[] getDates() {

        int j = 0;
        for (int i = 0; i < array.length; i++) {
            dtArray[i] = LocalDateTime.parse(array[i]);
            System.out.println("Available appointment: "+ dtArray[i]);
            if(j==0 || dtArray[i].getDayOfMonth() != days[j-1]){
                days[j] = dtArray[i].getDayOfMonth();
                System.out.println("New day: "+days[j]);
                j++;
            }
        }
        System.out.println("Returned from getDates");
        return days;
    }


}




