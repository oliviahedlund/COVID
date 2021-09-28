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

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Appointment_Day_Time_fragment extends Fragment implements DatePickerDialog.OnDateSetListener{
    private Button dayButton;
    private Button confirmButton;
    private Button cancelButton;
    private int month;
    private int year;
    DateTimeHelper dt;
    View view;
    Calendar [] allowedDays;
    String [] allowedTimes;
    AutoCompleteTextView dropDown;
    int selectedDay;
    String selectedTime;


    public Appointment_Day_Time_fragment(int month, int year){
        this.month = month;
        this.year = year;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.appointment_day_time, container, false);

        setupAllowedDays();
        setupWidget();


        // Inflate the layout for this fragment
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupAllowedDays() {
        ArrayList<String> array = new ArrayList<String>();
        array.add("2021-09-28T11:00:00");
        array.add("2021-09-30T11:00:00");
        array.add("2021-09-30T11:40:00");

        dt = new DateTimeHelper(array);
        ArrayList<Calendar> daysBuffer = dt.getDates();

        allowedDays = new Calendar[daysBuffer.size()];
        allowedDays = daysBuffer.toArray(allowedDays);

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
        dpd.setMinDate(now);
        dpd.setMaxDate(last);
        dpd.setSelectableDays(allowedDays);

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

        ArrayList<String> timesBuffer = dt.getTimes(dayOfMonth);

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
