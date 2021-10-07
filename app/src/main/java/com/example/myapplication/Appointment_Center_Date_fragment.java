package com.example.myapplication;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Booking.CenterVaccineHelper;
import com.example.myapplication.Booking.DateTimeHelper;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.DateFormatSymbols;
import java.util.Calendar;


public class Appointment_Center_Date_fragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    public static final int NOT_DEFINED = -1;
    public static final int MAX_YEAR = 2030;

    private AutoCompleteTextView centerDropdown;
    private String centers[];
    private AutoCompleteTextView vaccineDropdown;
    private String vaccines[];
    private AutoCompleteTextView timeDropdown;
    private String times[];


    private Button dateButton;
    private Button confirmButton;
    private Button cancelButton;
    private int center = NOT_DEFINED;
    private int vaccine = NOT_DEFINED;
    private int time = NOT_DEFINED;

    private int day = NOT_DEFINED;
    private int month = NOT_DEFINED;
    private int year = NOT_DEFINED;

    private LoadingAnimation loadingAnimation;

    View view;
    UserResponse user;
    DateTimeHelper dateTimeHelper;
    private CenterVaccineHelper centerVaccineHelper;
    private String selectedCenter;
    private String selectedVaccine;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.appointment_center_date, container, false);

        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");

        centerVaccineHelper = new CenterVaccineHelper();
        centerVaccineHelper.apiGetCenterList();
        centers = centerVaccineHelper.getCenters();

        loadingAnimation = new LoadingAnimation(this.getActivity());
        dateTimeHelper = new DateTimeHelper();

        setupCenters();
        setupButtons();


        // Inflate the layout for this fragment
        return view;
    }

    public Fragment getFragment(){
        return this;
    }

    public void setupCenters() {

        centerDropdown = (AutoCompleteTextView) view.findViewById(R.id.chooseCenter);
        Simple_DropdownAdapter centerAdapter = new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, centers);

        centerDropdown.setAdapter(centerAdapter);
        centerDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                center = position;
                selectedCenter = centerVaccineHelper.getSelectedCenter(center);
                vaccines = centerVaccineHelper.getVaccines(center);
                setupVaccines();
            }
        });
    }

    public void setupVaccines(){
        vaccineDropdown = (AutoCompleteTextView) view.findViewById(R.id.chooseVaccine);
        Simple_DropdownAdapter vaccineAdapter = new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, vaccines);

        vaccineDropdown.setAdapter(vaccineAdapter);
        vaccineDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                vaccine = position;
                selectedVaccine = centerVaccineHelper.getSelectedVaccine(vaccine);
                dateButton.setEnabled(true);
                dateTimeHelper.CallBookingAPI(getActivity(), user, 10, 2021, 0, loadingAnimation, getFragment());
                loadingAnimation.startLoadingAnimation();
            }
        });
    }

    private void setupButtons() {
        dateButton = (Button) view.findViewById(R.id.chooseDate);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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
//                    Appointment_Day_Time_fragment appointment_day_time_fragment = new Appointment_Day_Time_fragment(month, year, center);
//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, appointment_day_time_fragment)
//                            .commit();
                }

//                Toast.makeText(getContext(), "Center: " + String.valueOf(center) + " " + "Month: " + String.valueOf(month) + " " + "Year: " + String.valueOf(year), Toast.LENGTH_LONG).show();
            }
        });
        cancelButton = (Button) view.findViewById(R.id.cancelCenterDate);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().getSupportFragmentManager().popBackStackImmediate();

                Appointment_fragment appointmentFragment = new Appointment_fragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, appointmentFragment).commit();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showDatePicker(){
        Calendar minDate = Calendar.getInstance();

        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                minDate.get(Calendar.YEAR), // Initial year selection
                minDate.get(Calendar.MONTH), // Initial month selection
                minDate.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );

        dpd.setMinDate(minDate);

        Calendar maxDate = Calendar.getInstance();
        maxDate.set(Calendar.YEAR, minDate.get(Calendar.YEAR)+1);
        dpd.setMaxDate(maxDate);

        for (Calendar loopdate = minDate; minDate.before(maxDate); minDate.add(Calendar.DATE, 1), loopdate = minDate) {
            int dayOfWeek = loopdate.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
                Calendar[] disabledDays =  new Calendar[1];
                disabledDays[0] = loopdate;
                dpd.setDisabledDays(disabledDays);
            }
        }

        dpd.setSelectableDays(dateTimeHelper.getAllowedDays());

        dpd.show(getActivity().getSupportFragmentManager(), "Datepickerdialog");
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSet(DatePickerDialog view, int yearValue, int monthOfYear, int dayOfMonth) {
        year = yearValue;
        month = monthOfYear;
        day = dayOfMonth;

        dateButton.setText(year + "-" + month + "-" + day);

        setupTimes(year, month, day);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setupTimes(int year, int month, int day){

        Calendar buffer = Calendar.getInstance();
        buffer.set(Calendar.YEAR, year);
        buffer.set(Calendar.MONTH, month);
        buffer.set(Calendar.DAY_OF_MONTH, day);

        times = dateTimeHelper.getTimes(buffer);

        timeDropdown = (AutoCompleteTextView) view.findViewById(R.id.chooseTime);
        Simple_DropdownAdapter timeAdapter = new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, times);

        timeDropdown.setAdapter(timeAdapter);
        timeDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

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
