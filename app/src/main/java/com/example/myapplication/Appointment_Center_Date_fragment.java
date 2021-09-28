package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.DateFormatSymbols;
import java.util.Calendar;


public class Appointment_Center_Date_fragment extends Fragment {
    public static final int NOT_DEFINED = -1;
    public static final int MAX_YEAR = 2030;

    private AutoCompleteTextView centerDropdown;
    private String centers[] = {"CCC", "Arena"};

    private Button dateButton;
    private Button confirmButton;
    private Button cancelButton;
    private int center = NOT_DEFINED;
    private int month = NOT_DEFINED;
    private int year = NOT_DEFINED;
    private int minMonth = month;

    MonthPickerDialog picker;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.appointment_center_date, container, false);

        setupDropdownMenu();
        setupButtons();
        // Inflate the layout for this fragment
        return view;
    }

    private void setupButtons() {
        dateButton = (Button) view.findViewById(R.id.chooseDate);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        confirmButton = (Button) view.findViewById(R.id.confirmCenterDate);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(center == NOT_DEFINED || month == NOT_DEFINED || year == NOT_DEFINED){
                    Toast.makeText(getContext(), "Please fill out the form completely", Toast.LENGTH_LONG).show();
                } else {
                    Appointment_Day_Time_fragment appointment_day_time_fragment = new Appointment_Day_Time_fragment(month, year);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, appointment_day_time_fragment)
                            .addToBackStack(null).commit();
                }

//                Toast.makeText(getContext(), "Center: " + String.valueOf(center) + " " + "Month: " + String.valueOf(month) + " " + "Year: " + String.valueOf(year), Toast.LENGTH_LONG).show();
            }
        });
        cancelButton = (Button) view.findViewById(R.id.cancelCenterDate);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appointment_fragment appointmentFragment = new Appointment_fragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, appointmentFragment).commit();
            }
        });
    }

    private void showDatePicker() {
        Calendar today = Calendar.getInstance();
        int monthNow = today.get(Calendar.MONTH);
        int yearNow = today.get(Calendar.YEAR);
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(this.getContext(),
                new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {

                        if(month != NOT_DEFINED && year != NOT_DEFINED){
                            dateButton.setText("Date: " + getMonthForInt(month) + " " + String.valueOf(year));
                        }

                    }
                    }, yearNow, monthNow);
        showPicker(monthNow, yearNow, builder);
    }

    private void showPicker(int monthNow, int yearNow, MonthPickerDialog.Builder builder) {
        int activeYear = year == NOT_DEFINED ? yearNow : year;
        int minMonth = activeYear == yearNow ? monthNow : Calendar.JANUARY;
        picker = builder
                .setActivatedYear(activeYear)
                .setActivatedMonth(monthNow)
                .setMinYear(yearNow)
                .setMinMonth(minMonth)

                .setMaxYear(MAX_YEAR)
                .setTitle("Select Date")
//                .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                    @Override
                    public void onMonthChanged(int selectedMonth) { month = selectedMonth; } })
                .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                    @Override
                    public void onYearChanged(int selectedYear) { picker.dismiss(); year = selectedYear; showPicker(monthNow,yearNow,builder); } })
                .build();

        picker.show();
    }

    public void setupDropdownMenu(){

        centerDropdown = (AutoCompleteTextView) view.findViewById(R.id.chooseCenter);
        Simple_DropdownAdapter adapter = new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, centers);

        centerDropdown.setAdapter(adapter);
        centerDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                center = position;
            }
        });
    }

    private String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

}
