package com.example.myapplication.UI.UserAppointment;

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

import com.example.myapplication.API.Model.Appointment_user.AppointmentRequest;
import com.example.myapplication.Helpers.DateTimeHelper;
import com.example.myapplication.Helpers.CenterVaccineHelper;
import com.example.myapplication.Helpers.AppointmentHelper;
import com.example.myapplication.UI.LoadingAnimation;
import com.example.myapplication.R;
import com.example.myapplication.UI.Adapter.Simple_DropdownAdapter;
import com.example.myapplication.API.Model.User.UserResponse;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;


public class Appointment_make extends Fragment implements DatePickerDialog.OnDateSetListener {
    public static final int NOT_DEFINED = -1;

    private UserResponse user;
    private ZonedDateTime appointmentDateTime;
    private AppointmentRequest appointment;

    private AutoCompleteTextView centerDropdown;
    private AutoCompleteTextView vaccineDropdown;
    private AutoCompleteTextView timeDropdown;

    private Button dateButton;
    private Button confirmButton;
    private Button cancelButton;

    private int center = NOT_DEFINED;
    private int vaccine = NOT_DEFINED;
    private int selectedTime = NOT_DEFINED;
    private int day = NOT_DEFINED;
    private int month = NOT_DEFINED;
    private int year = NOT_DEFINED;

    private View view;

    private CenterVaccineHelper centerVaccineHelper;
    private DateTimeHelper dateTimeHelper;
    private AppointmentHelper appointmentHelper;

    private String selectedCenter;
    private String selectedVaccine;
    private Calendar [] allowedDays;


    public Appointment_make(){
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.appointment_center_date, container, false);
        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");

        centerVaccineHelper = new CenterVaccineHelper(this);
        centerVaccineHelper.API_getCenters(getActivity(), user, new Runnable(){
            @Override
            public void run() {
                setupCenters();
                LoadingAnimation.dismissLoadingAnimation();
            }
        });
        LoadingAnimation.startLoadingAnimation(this.getActivity());

        setupButtons();

        return view;
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(user.getAppointment() == null){
                    appointmentHelper = new AppointmentHelper(getFragment());
                    appointmentHelper.API_sendNewAppointment(user, new Runnable() {
                        @Override
                        public void run() {
                            LoadingAnimation.dismissLoadingAnimation();
                            Appointment_Info info = new Appointment_Info();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, info).commit();
                        }
                    }, appointment);

                    LoadingAnimation.startLoadingAnimation(getActivity());
                } else {
                    appointmentHelper = new AppointmentHelper(getFragment());
                    appointmentHelper.API_updateAppointment(user, new Runnable() {
                        @Override
                        public void run() {
                            LoadingAnimation.dismissLoadingAnimation();
                            Appointment_Info info = new Appointment_Info();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, info).commit();
                        }
                    }, appointment);

                    LoadingAnimation.startLoadingAnimation(getActivity());
                }
            }
        });
        cancelButton = (Button) view.findViewById(R.id.cancelCenterDate);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appointment_makeCancel appointmentFragment = new Appointment_makeCancel();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, appointmentFragment).commit();
            }
        });
        cancelButton.setVisibility(View.GONE);
    }

    public void setupCenters() {

        centerDropdown = (AutoCompleteTextView) view.findViewById(R.id.chooseCenter);
        Simple_DropdownAdapter centerAdapter = new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, centerVaccineHelper.getCenters());

        centerDropdown.setAdapter(centerAdapter);
        centerDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                center = position;
                selectedCenter = centerVaccineHelper.getSelectedCenter(center);
                setupVaccines();
            }
        });
    }

    public void setupVaccines(){
        vaccineDropdown = (AutoCompleteTextView) view.findViewById(R.id.chooseVaccine);
        Simple_DropdownAdapter vaccineAdapter = new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, centerVaccineHelper.getVaccines(center));

        vaccineDropdown.setAdapter(vaccineAdapter);
        vaccineDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                vaccine = position;
                selectedVaccine = centerVaccineHelper.getSelectedVaccine(vaccine);
                dateButton.setEnabled(true);
                dateTimeHelper = new DateTimeHelper(getFragment());

                dateTimeHelper.API_getDateTime(getActivity(), user, new Runnable() {
                    @Override
                    public void run() {
                        allowedDays = dateTimeHelper.getDates();
                        LoadingAnimation.dismissLoadingAnimation();
                    }
                }, selectedCenter);

                LoadingAnimation.startLoadingAnimation(getFragment().getActivity());
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

        dpd.setSelectableDays(allowedDays);

        dpd.show(getActivity().getSupportFragmentManager(), "Datepickerdialog");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSet(DatePickerDialog view, int yearValue, int monthOfYear, int dayOfMonth) {
        year = yearValue;
        month = monthOfYear;
        day = dayOfMonth;

        dateButton.setText(day + "-" + (month + 1) + "-" + year);

        setupTimes(year, month, day);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setupTimes(int year, int month, int day){

        Calendar buffer = Calendar.getInstance();
        buffer.set(Calendar.YEAR, year);
        buffer.set(Calendar.MONTH, month);
        buffer.set(Calendar.DAY_OF_MONTH, day);

        timeDropdown = (AutoCompleteTextView) view.findViewById(R.id.chooseTime);
        Simple_DropdownAdapter timeAdapter = new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, dateTimeHelper.getTimes(buffer));

        timeDropdown.setAdapter(timeAdapter);
        timeDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedTime = position;
                fillAppointment();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void fillAppointment(){
        appointment = new AppointmentRequest();
        appointmentDateTime = LocalDateTime.of(LocalDate.of(year, month, day), dateTimeHelper.getSelectedTime(selectedTime)).atZone(ZoneId.of("Europe/Stockholm")).withZoneSameInstant(ZoneId.of("UTC"));
        appointment.setTime(appointmentDateTime);
        appointment.setCenterId(selectedCenter);
        appointment.setVaccineId(selectedVaccine);
        appointment.setLength(dateTimeHelper.getLength(selectedTime));
    }

    public Fragment getFragment(){
        return this;
    }
}
