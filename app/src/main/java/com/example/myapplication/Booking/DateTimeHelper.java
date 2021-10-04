package com.example.myapplication.Booking;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.myapplication.ApiClient;
import com.example.myapplication.UserResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DateTimeHelper {
    private final static int STARTDATE = 8;
    private final static int ENDDATE = 10;
    private final static int STARTTIME = 11;
    private final static int ENDTIME = 16;

    private List<BookingResponse> bookingResponse;
    private ArrayList<String> apiRawAppointmentList;
    private ArrayList<LocalDateTime> dateTimeArray; /// OLD - Delete
    private ArrayList<Calendar> days; /// OLD - Delete
    private ArrayList<String> times; /// OLD - Delete
    private Calendar [] allowedDays;


    public DateTimeHelper() {
        apiRawAppointmentList = new ArrayList<String>();
        dateTimeArray = new ArrayList<LocalDateTime>(); /// OLD - Delete
        days = new ArrayList<Calendar>(); /// OLD - Delete
    }

    public Calendar[] getAllowedDays() {
        return allowedDays;
    }

    public ArrayList<String> getArray() {
        return apiRawAppointmentList;
    }

    public void setArray(ArrayList<String> array) {
        this.apiRawAppointmentList = array;
    }

    public void CallBookingAPI(Activity activity, UserResponse user, int month, int year, int center){
        // TODO: Ändra input till en bookingRequest istället för month, year, center
        // int month = bookingRequest.month
        // int year = bookingRequest.year
        // int center = bookingRequest.center
        Call<List<BookingResponse>> bookingResponseCall = ApiClient.getUserService().booking(user.getToken(), month,year,center);

        bookingResponseCall.enqueue(new Callback<List<BookingResponse>>() {

            @RequiresApi(api = Build.VERSION_CODES.O) /// OLD - Delete
            @Override
            public void onResponse(Call<List<BookingResponse>> call, Response<List<BookingResponse>> response) {
                //TODO: errorhandling
                if (response.isSuccessful()) {
                    bookingResponse = response.body(); //i userResponse ligger all information om användaren

                    for (int i = 0; i < bookingResponse.size(); i++) {
                        apiRawAppointmentList.add(bookingResponse.get(i).getTime());
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setupAllowedDays();
                        }
                    },600);

                }else{
                    Toast.makeText(activity,"Appointments/Booking failed", Toast.LENGTH_LONG).show();
                    System.out.println("else");
                }
            }

            @Override
            public void onFailure(Call<List<BookingResponse>> call, Throwable t) {
                Toast.makeText(activity,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                System.out.println("fail");
            }
        });
    }

    //// OLD - Delete , rename setupAllowedDaysNEW ////
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupAllowedDaysOLD() {
        ArrayList<Calendar> daysBuffer = this.getDates();

        allowedDays = new Calendar[daysBuffer.size()];
        allowedDays = daysBuffer.toArray(allowedDays);

    }


    //// OLD - Delete ////
    @RequiresApi(api = Build.VERSION_CODES.O)  //method invoking this must add this line
    public ArrayList<String> getTimes(int _day) {
        int hour;
        int minute;

        times = new ArrayList<String>();
        int j = 0;

        for (int i = 0; i < apiRawAppointmentList.size(); i++) {
            dateTimeArray.add(LocalDateTime.parse(apiRawAppointmentList.get(i)));
            System.out.println("Available appointment: " + dateTimeArray.get(i)); ////
            if (dateTimeArray.get(i).getDayOfMonth() == _day) {
                hour = dateTimeArray.get(i).getHour();
                minute = dateTimeArray.get(i).getMinute();
//                times.add(LocalTime.of(hour, minute));
                if(minute < 10){
                    times.add(hour + " : 0" + minute);
                } else
                    times.add(hour + " : " + minute);
                System.out.println("Added time: " + times.get(times.size() - 1)); ////
            }


        }
        System.out.println("Returned from getTimes"); ////
        return times;
    }

    //// OLD - Delete ////
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Calendar> getDates() {
//        System.out.println("Array size is: " + array.size());
        Log.d("hahadate", "" + apiRawAppointmentList.size());

        int j = 0;
        for (int i = 0; i < apiRawAppointmentList.size(); i++) {
            dateTimeArray.add(LocalDateTime.parse(apiRawAppointmentList.get(i)));
            System.out.println("Available appointment: " + dateTimeArray.get(i)); ////
            if (!days.contains(dateTimeArray.get(i).getDayOfMonth())) {
                Calendar buffer = Calendar.getInstance();
                buffer.set(Calendar.DAY_OF_MONTH, dateTimeArray.get(i).getDayOfMonth());
                days.add(buffer);
                System.out.println("New day: " + days.get(days.size() - 1)); ////
                j++;
            }
        }
        System.out.println("Returned from getDates");
        return days;
    }

    //////////// NEW and improved ////////////
    private void setupAllowedDays() {
        ArrayList<Integer> intDays = new ArrayList<Integer>();

        // Adds bookable days to an integer array
        for (int i = 0; i < apiRawAppointmentList.size(); i++) {
            if (!intDays.contains(extractDayApiResponse(i))) {
                intDays.add(extractDayApiResponse(i));
            }
        }
        // setup allowed days
        allowedDays = convertIntToCalendar(intDays);
    }

    //////////// NEW and improved ////////////
    private Calendar[] convertIntToCalendar(ArrayList<Integer> intList){
        Calendar[] calendarList = new Calendar[intList.size()];
        Calendar temp = Calendar.getInstance();

        for (int i = 0; i < intList.size(); i++) {
            temp.set(Calendar.DAY_OF_MONTH, intList.get(i));
            calendarList[i] = temp;
        }
        return calendarList;
    }

    //////////// NEW and improved ////////////
    public ArrayList<String> getTimesNEW(int _day) {
        ArrayList<String> times = new ArrayList<String>();

        for (int i = 0; i < apiRawAppointmentList.size(); i++) {
            if (extractDayApiResponse(i) == _day) {
                times.add(extractTimeApiResponse(i));
            }
        }
        return times;
    }

    //////////// NEW and improved ////////////
    private int extractDayApiResponse(int index){
        return Integer.parseInt(apiRawAppointmentList.get(index).substring(STARTDATE, ENDDATE));
    }

    //////////// NEW and improved ////////////
    private String extractTimeApiResponse(int index){
        return apiRawAppointmentList.get(index).substring(STARTTIME, ENDTIME);
    }


    private int getPosition(Calendar calendarDay, String time) {
        String day = calendarDay.toString();

        for (int i = 0; i < apiRawAppointmentList.size(); i++) {
            if (day.equals(extractDayApiResponse(i))) {
                if (time.equals(extractTimeApiResponse(i))) {
                    return i;
                }
            }
        }
        return -1;
    }


    public int SetBookingAPI(Activity activity, UserResponse user, Calendar calendarMonth, String appointmentTime) {
        int index = getPosition(calendarMonth, appointmentTime);
        if(index == -1){
            System.out.println("No appointment available at: "+ calendarMonth.toString()+" "+appointmentTime);
            return 0;
        }
        String time = bookingResponse.get(index).getTime();
        int center = bookingResponse.get(index).getCenter();
        int length = bookingResponse.get(index).getLength();

        /*Call<BookingResponse> bookingResponseCall = ApiClient.getUserService().booking(user.getToken(), time,center,length);

        bookingResponseCall.enqueue(new Callback<List<BookingResponse>>() {

            @RequiresApi(api = Build.VERSION_CODES.O) /// OLD - Delete
            @Override
            public void onResponse(Call<List<BookingResponse>> call, Response<List<BookingResponse>> response) {
                //TODO: errorhandling
                if (response.isSuccessful()) {
                    return 1;



                }else{
                    Toast.makeText(activity,"Appointments/Booking failed", Toast.LENGTH_LONG).show();
                    System.out.println("else");
                    return 0;
                }
            }

            @Override
            public void onFailure(Call<List<BookingResponse>> call, Throwable t) {
                Toast.makeText(activity,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                System.out.println("fail");
                return 0;
            }
        });
        */
        return 0;
    }

    /*
    //Returns position in list based on chosen day and time
    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getPosition(Integer day, LocalTime time){
        int hour = time.getHour();
        int minute = time.getMinute();

        for (int i = 0; i < dateTimeArray.size(); i++) {
            if(dateTimeArray.get(i).getDayOfMonth() == day){
                if(dateTimeArray.get(i).getHour() == hour) {
                    if (dateTimeArray.get(i).getMinute() == minute) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }*/


}




