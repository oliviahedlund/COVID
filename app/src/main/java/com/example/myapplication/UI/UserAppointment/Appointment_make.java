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

import com.example.myapplication.API.Model.Appointment_user.BookingResponse;
import com.example.myapplication.UI.AlertWindow;
import com.example.myapplication.ApiClient;
import com.example.myapplication.Booking.CenterVaccineHelper;
import com.example.myapplication.UI.LoadingAnimation;
import com.example.myapplication.R;
import com.example.myapplication.UI.Adapter.Simple_DropdownAdapter;
import com.example.myapplication.API.Model.User.UserResponse;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Appointment_make extends Fragment implements DatePickerDialog.OnDateSetListener {
    public static final int NOT_DEFINED = -1;

    private ArrayList<LocalDateTime> localDateTimeArrayList;
    private ArrayList<Calendar> days;
    private ArrayList<String> times;

    private Calendar [] allowedDays;

    private List<BookingResponse> bookingResponses;

    private AutoCompleteTextView centerDropdown;
    private String centers[];
    private AutoCompleteTextView vaccineDropdown;
    private String vaccines[];
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

    private LoadingAnimation loadingAnimation;
    private int [] questionnaireAnswers;

    private View view;
    private UserResponse user;
    private CenterVaccineHelper centerVaccineHelper;
    private String selectedCenter;
    private String selectedVaccine;

    public Appointment_make(int [] questionnaireAnswers){
        this.questionnaireAnswers = questionnaireAnswers;
    }

    public Fragment getFragment(){
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.appointment_center_date, container, false);
        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");
        localDateTimeArrayList = new ArrayList<LocalDateTime>();

        centerVaccineHelper = new CenterVaccineHelper();
        centerVaccineHelper.apiGetCenterList();
        centers = centerVaccineHelper.getCenters();

        loadingAnimation = new LoadingAnimation(this.getActivity());

        setupCenters();
        setupButtons();

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void CallBookingAPI(Activity activity, UserResponse user, int month, int year, int center){
        Call<List<BookingResponse>> bookingResponseCall = ApiClient.getUserService().booking(user.getToken(), month,year,center);
        bookingResponseCall.enqueue(new Callback<List<BookingResponse>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<BookingResponse>> call, Response<List<BookingResponse>> response) {
                if (response.isSuccessful()) {
                    bookingResponses = response.body();

                    for (int i = 0; i < bookingResponses.size(); i++) {
                        localDateTimeArrayList.add(bookingResponses.get(i).getTime());
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getDates();
                            loadingAnimation.dismissLoadingAnimation();
                        }
                    },600);

                }else{
                    Toast.makeText(activity,"Appointments/Booking failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<BookingResponse>> call, Throwable t) {
                Toast.makeText(activity,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                loadingAnimation.dismissLoadingAnimation();
                new AlertWindow(getFragment()).createAlertWindow();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String[] getTimes(Calendar day) {
        int hour;
        int minute;

        times = new ArrayList<String>();

        for (int i = 0; i < localDateTimeArrayList.size(); i++) {
            if (localDateTimeArrayList.get(i).getYear() == day.get(Calendar.YEAR)
                    && localDateTimeArrayList.get(i).getMonthValue() == (day.get(Calendar.MONTH) + 1)
                    && localDateTimeArrayList.get(i).getDayOfMonth() == day.get(Calendar.DAY_OF_MONTH)) {

                hour = localDateTimeArrayList.get(i).getHour();
                minute = localDateTimeArrayList.get(i).getMinute();

                if(minute < 10){
                    times.add(hour + " : 0" + minute);
                } else
                    times.add(hour + " : " + minute);
            }
        }

        String [] allowedTimes = new String[times.size()];

        return times.toArray(allowedTimes);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getDates() {
        days = new ArrayList<Calendar>();

        for (int i = 0; i < localDateTimeArrayList.size(); i++) {
            Calendar buffer = Calendar.getInstance();
            buffer.set(Calendar.YEAR, localDateTimeArrayList.get(i).getYear());
            buffer.set(Calendar.MONTH, localDateTimeArrayList.get(i).getMonthValue() - 1);
            buffer.set(Calendar.DAY_OF_MONTH, localDateTimeArrayList.get(i).getDayOfMonth());
            days.add(buffer);
        }

        allowedDays = new Calendar[days.size()];
        allowedDays = days.toArray(allowedDays);
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
                CallBookingAPI(getActivity(), user, 10, 2021, 0);
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

        dpd.setSelectableDays(getAllowedDays());

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

        timeStrings = getTimes(buffer);

        timeDropdown = (AutoCompleteTextView) view.findViewById(R.id.chooseTime);
        Simple_DropdownAdapter timeAdapter = new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, timeStrings);

        timeDropdown.setAdapter(timeAdapter);
        timeDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });
    }

    public Calendar[] getAllowedDays() {
        return allowedDays;
    }
}
