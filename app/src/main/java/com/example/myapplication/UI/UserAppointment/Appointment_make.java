package com.example.myapplication.UI.UserAppointment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Helpers.DateTimeHelper;
import com.example.myapplication.API.Model.Appointment_user.Date_Time;
import com.example.myapplication.API.Model.Appointment_user.Center;
import com.example.myapplication.Helpers.CenterVaccineHelper;
import com.example.myapplication.UI.AlertWindow;
import com.example.myapplication.ApiClient;
import com.example.myapplication.UI.LoadingAnimation;
import com.example.myapplication.R;
import com.example.myapplication.UI.Adapter.Simple_DropdownAdapter;
import com.example.myapplication.API.Model.User.UserResponse;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Appointment_make extends Fragment implements DatePickerDialog.OnDateSetListener {
    public static final int NOT_DEFINED = -1;

    private UserResponse user;
    private List<Date_Time> dateTimes;
    private List<Center> centers;

    private AutoCompleteTextView centerDropdown;
    private String centerStrings[];
    private AutoCompleteTextView vaccineDropdown;
    private String vaccineStrings[];
    private AutoCompleteTextView timeDropdown;
    private String timeStrings[];

    private Button dateButton;
    private Button confirmButton;
    private Button cancelButton;

    private int center = NOT_DEFINED;
    private int vaccine = NOT_DEFINED;
    private int time = NOT_DEFINED;
    private int day = NOT_DEFINED;
    private int month = NOT_DEFINED;
    private int year = NOT_DEFINED;

    private View view;
    private int [] questionnaireAnswers;
    private CenterVaccineHelper centerVaccineHelper;
    private DateTimeHelper dateTimeHelper;
    private String selectedCenter;
    private String selectedVaccine;
    private Calendar [] allowedDays;

    public Appointment_make(int [] questionnaireAnswers){
        this.questionnaireAnswers = questionnaireAnswers;
    }

    public Fragment getFragment(){
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.appointment_center_date, container, false);
        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");

        API_getCenters(getActivity(), user);
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
            @Override
            public void onClick(View view) {
                if(center == NOT_DEFINED || month == NOT_DEFINED || year == NOT_DEFINED){
                    Toast.makeText(getContext(), "Please fill out the form completely", Toast.LENGTH_LONG).show();
                } else {
                    // api post to the server
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void API_getCenters(Activity activity, UserResponse user){
        Call<List<Center>> call = ApiClient.getUserService().getCenters(user.getToken());
        call.enqueue(new Callback<List<Center>>() {
            @Override
            public void onResponse(Call<List<Center>> call, Response<List<Center>> response) {
                if (response.isSuccessful()) {
                    centers = response.body();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            centerVaccineHelper = new CenterVaccineHelper(centers);
                            centerStrings = centerVaccineHelper.getCenters();
                            setupCenters();
                            LoadingAnimation.dismissLoadingAnimation();
                        }
                    },600);

                }else{
                    Toast.makeText(activity,"Appointments/Booking failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Center>> call, Throwable t) {
                Toast.makeText(activity,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(getFragment()).createAlertWindow(getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }

    public void setupCenters() {

        centerDropdown = (AutoCompleteTextView) view.findViewById(R.id.chooseCenter);
        Simple_DropdownAdapter centerAdapter = new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, centerStrings);

        centerDropdown.setAdapter(centerAdapter);
        centerDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                center = position;
                selectedCenter = centerVaccineHelper.getSelectedCenter(center);
                vaccineStrings = centerVaccineHelper.getVaccines(center);
                setupVaccines();
            }
        });
    }

    public void setupVaccines(){
        vaccineDropdown = (AutoCompleteTextView) view.findViewById(R.id.chooseVaccine);
        Simple_DropdownAdapter vaccineAdapter = new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, vaccineStrings);

        vaccineDropdown.setAdapter(vaccineAdapter);
        vaccineDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                vaccine = position;
                selectedVaccine = centerVaccineHelper.getSelectedVaccine(vaccine);
                dateButton.setEnabled(true);
                API_getDateTime(getActivity(), user);
                LoadingAnimation.startLoadingAnimation(getFragment().getActivity());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void API_getDateTime(Activity activity, UserResponse user){
        Call<List<Date_Time>> bookingResponseCall = ApiClient.getUserService().getDateTimes(user.getToken(), selectedCenter);
        bookingResponseCall.enqueue(new Callback<List<Date_Time>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<Date_Time>> call, Response<List<Date_Time>> response) {
                if (response.isSuccessful()) {
                    dateTimes = response.body();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dateTimeHelper = new DateTimeHelper(dateTimes);
                            allowedDays = dateTimeHelper.getDates();
                            LoadingAnimation.dismissLoadingAnimation();
                        }
                    },600);

                }else{
                    Toast.makeText(activity,"Appointments/Booking failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Date_Time>> call, Throwable t) {
                Toast.makeText(activity,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(getFragment()).createAlertWindow(getResources().getString(R.string.connectionFailureAlert));
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

        timeStrings = dateTimeHelper.getTimes(buffer);

        timeDropdown = (AutoCompleteTextView) view.findViewById(R.id.chooseTime);
        Simple_DropdownAdapter timeAdapter = new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, timeStrings);

        timeDropdown.setAdapter(timeAdapter);
        timeDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });
    }

}
