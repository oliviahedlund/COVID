package com.example.myapplication;


import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Booking.DateTimeHelper;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class Appointment_Day_Time_fragment extends Fragment implements DatePickerDialog.OnDateSetListener{
    private Button dayButton;
    private Button confirmButton;
    private Button cancelButton;
    private int month;
    private int year;
    private int center;

    private DateTimeHelper dateTimeHelper;
    private View view;
//    private Calendar [] allowedDays;
    private String [] allowedTimes;
    private AutoCompleteTextView dropDown;
    private int selectedDay;
    private String selectedTime;

    private LoadingAnimation loadingAnimation;

    UserResponse user;


    public Appointment_Day_Time_fragment(int month, int year, int center){
        this.month = month;
        this.year = year;
        this.center = center;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.appointment_day_time, container, false);

        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");

        loadingAnimation = new LoadingAnimation(this.getActivity());
        loadingAnimation.startLoadingAnimation();
        dateTimeHelper = new DateTimeHelper();

        // month + 1 : because Calendar.January in java returns 0 and API in C# returns 1
        dateTimeHelper.CallBookingAPI(getActivity(), user, month + 1, year, center, loadingAnimation);

        setupWidget();

        // Inflate the layout for this fragment
        return view;
    }

    public void setupWidget() {

        dayButton = (Button) view.findViewById(R.id.chooseDay);
        dayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDayPicker();
            }
        });

        confirmButton = (Button) view.findViewById(R.id.confirmDayTime);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // här anropa api för att skicka user selected day, month, year, center and user token
            }
        });

        cancelButton = (Button) view.findViewById(R.id.cancelDayTime);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().getSupportFragmentManager().popBackStack();
                Appointment_fragment appointmentFragment = new Appointment_fragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, appointmentFragment).commit();
            }
        });

        dropDown = (AutoCompleteTextView) view.findViewById(R.id.chooseTime);
    }

    public void showDayPicker(){

        Calendar now = Calendar.getInstance();
        int today = now.get(Calendar.DAY_OF_MONTH);
        now.set(year, month, today);

        Calendar last = Calendar.getInstance();
        int lastDayOfMonth = now.getActualMaximum(Calendar.DATE);
        last.set(year, month, lastDayOfMonth);

        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                year, // Initial year selection
                month, // Initial month selection
                today // Inital day selection
        );

        // disable the year and month selection and past days selection
//        dpd.setMinDate(now);
//        dpd.setMaxDate(last);
        dpd.setSelectableDays(dateTimeHelper.getAllowedDays());

        dpd.show(getActivity().getSupportFragmentManager(), "Daypickerdialog");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        selectedDay = dayOfMonth;
        setupAllowedTimes(dayOfMonth);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupAllowedTimes(int dayOfMonth) {

        ArrayList<String> timesBuffer = dateTimeHelper.getTimes(dayOfMonth);

        allowedTimes = new String[timesBuffer.size()];
        allowedTimes = timesBuffer.toArray(allowedTimes);


        Simple_DropdownAdapter adapter = new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, allowedTimes);

        dropDown.setAdapter(adapter);
        dropDown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                System.out.println(allowedTimes[position]);
            }
        });
    }
}
