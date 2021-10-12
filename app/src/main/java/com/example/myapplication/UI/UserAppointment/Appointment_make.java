package com.example.myapplication.UI.UserAppointment;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.example.myapplication.API.Model.Appointment_user.AppointmentResponse;
import com.example.myapplication.API.Model.Appointment_user.QuestionnaireRequest;
import com.example.myapplication.API.Model.Register.RegisterResponse;
import com.example.myapplication.Helpers.DateTimeHelper;
import com.example.myapplication.API.Model.Appointment_user.Center;
import com.example.myapplication.Helpers.CenterVaccineHelper;
import com.example.myapplication.Helpers.NewAppointmentHelper;
import com.example.myapplication.Helpers.NewQuestionnaireHelper;
import com.example.myapplication.MainActivity;
import com.example.myapplication.RegisterActivity;
import com.example.myapplication.UI.AlertWindow;
import com.example.myapplication.ApiClient;
import com.example.myapplication.UI.LoadingAnimation;
import com.example.myapplication.R;
import com.example.myapplication.UI.Adapter.Simple_DropdownAdapter;
import com.example.myapplication.API.Model.User.UserResponse;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    private NewAppointmentHelper newAppointmentHelper;
    private NewQuestionnaireHelper newQuestionnaireHelper;
    private String selectedCenter;
    private String selectedVaccine;
    private Calendar [] allowedDays;

    private int [] questionnaireAnswers;
    private QuestionnaireRequest questionnaireRequest;

    public Appointment_make(int [] questionnaireAnswers){
        this.questionnaireAnswers = questionnaireAnswers;
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
                if(center == NOT_DEFINED || month == NOT_DEFINED || year == NOT_DEFINED){
                    Toast.makeText(getContext(), "Please fill out the form completely", Toast.LENGTH_LONG).show();
                } else {
                    fillQuestionnaireRequest();
                    newQuestionnaireHelper = new NewQuestionnaireHelper(getFragment(), questionnaireRequest);
                    newQuestionnaireHelper.API_sendNewQuestionnaire(user, new Runnable() {
                        @Override
                        public void run() {
                            newAppointmentHelper = new NewAppointmentHelper(getFragment(), appointment);
                            newAppointmentHelper.API_sendNewAppointment(user, new Runnable() {
                                @Override
                                public void run() {
                                    LoadingAnimation.dismissLoadingAnimation();
                                }
                            });
                            LoadingAnimation.dismissLoadingAnimation();
                        }
                    });
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

        dateButton.setText(year + "-" + month + "-" + day);

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

    public void fillQuestionnaireRequest() {
        questionnaireRequest = new QuestionnaireRequest();
        questionnaireRequest.setNeededHelpDuetoVax((questionnaireAnswers[0] == 0));
        questionnaireRequest.setTraveledInLast14Days((questionnaireAnswers[1] == 0));
        questionnaireRequest.setAllergicToVax((questionnaireAnswers[2] == 0));
        questionnaireRequest.setHasBloodProblems((questionnaireAnswers[3] == 0));
        questionnaireRequest.setPregnant((questionnaireAnswers[4] == 0));
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
