package com.example.myapplication;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class Appointment_Day_Time_fragment extends Fragment implements DatePickerDialog.OnDateSetListener{
    private Button dayButton;
    private Button confirmButton;
    private Button cancelButton;
    private int month;
    private int year;
    View view;

    public Appointment_Day_Time_fragment(int month, int year){
        this.month = month;
        this.year = year;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.appointment_day_time, container, false);

        setupButtons();

        // Inflate the layout for this fragment
        return view;
    }

    public void setupButtons() {
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
                Appointment_fragment appointmentFragment = new Appointment_fragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, appointmentFragment).commit();
            }
        });


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
//        dpd.setSelectableDays();

        dpd.show(getActivity().getSupportFragmentManager(), "Daypickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }
}
