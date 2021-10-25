package com.example.myapplication.UI.AdminBooking;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.API.Model.Appointment_admin.PostRangeRequest;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.AdminActivity;
import com.example.myapplication.Helpers.StringFormatHelper;
import com.example.myapplication.UI.AlertWindow;
import com.example.myapplication.Helpers.CenterVaccineHelper;
import com.example.myapplication.Helpers.AdminBookingHelper;
import com.example.myapplication.R;
import com.example.myapplication.UI.Adapter.Simple_DropdownAdapter;
import com.example.myapplication.UI.LoadingAnimation;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


public class AdminBookingRangeFragment extends Fragment {

    private View view;
    private String[] centers;
    private String[] age;
    private AutoCompleteTextView centerFilter;
    private AutoCompleteTextView ageFilter;
    private List<CheckBox> cbWeekdays;
    private CenterVaccineHelper centerVaccineHelper;
    private UserResponse user;
    private int centerPosition;
    private int agePosition;
    private PostRangeRequest postRangeRequest;
    List<EditText> editTextsDate;
    AdminBookingHelper adminBookingAPI;
    AdminActivity adminActivity;


    public AdminBookingRangeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.admin_booking_range, container, false);
        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");
        adminActivity = (AdminActivity) getActivity();
        centerVaccineHelper = adminActivity.getCenterVaccineHelper();

        agePosition=-1;
        centerPosition=-1;

        setupDropdown();
        setupCheckboxes();
        setupTimeTexts();
        setupButton();
        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void postAppointmentRange(){
        adminBookingAPI = new AdminBookingHelper(this);

        if(!validInput()){
            return;
        }
        TextView errorMsg= view.findViewById(R.id.errorMsg);
        errorMsg.setText("");

        LoadingAnimation.startLoadingAnimation(getActivity());

        setupRangeRequest();

        Runnable onPostResponse = new Runnable() {
            @Override
            public void run() {
                LoadingAnimation.dismissLoadingAnimation();
                Fragment newFragment = new AdminBookingRangeFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, newFragment).commit();
                new AlertWindow(AdminBookingRangeFragment.this).createAlertWindow(getActivity().getResources().getString(R.string.rangeAdd));
            }
        };
        adminBookingAPI.PostBookingRange(getActivity(), user, postRangeRequest, onPostResponse);


    }

    private void setupRangeRequest() {
        EditText etMinutes = view.findViewById(R.id.appMinutes);
        int minutes = Integer.parseInt(etMinutes.getText().toString());

        postRangeRequest = new PostRangeRequest();
        postRangeRequest.setCenter(centerVaccineHelper.getSelectedCenter(centerPosition));
        postRangeRequest.setAllowedAgeGroups(getAgeCoding());
        postRangeRequest.setAllowedDaysOfWeek(getCheckedWeekdays());
        postRangeRequest.setTimePerAppointmentMinutes(minutes);
        postRangeRequest.setStartDateTime(convertDateInput(0));
        postRangeRequest.setEndDateTime(convertDateInput(5));
    }

    private boolean validInput() {
        TextView errorMsg = view.findViewById(R.id.errorMsg);
        if(centerPosition==-1){
            errorMsg.setText(R.string.noCenter);
            return false;
        }
        if(agePosition==-1){
            errorMsg.setText(R.string.noAges);
            return false;
        }

        if(getCheckedWeekdays()==-1){
            errorMsg.setText(R.string.noDays);
            return false;
        }
        EditText etMinutes = view.findViewById(R.id.appMinutes);
        try{
            int min = Integer.parseInt(etMinutes.getText().toString());
            if(min<5){
                errorMsg.setText(R.string.appShort);
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            errorMsg.setText(R.string.notInt);
            return false;
        }
        ZonedDateTime sd;
        ZonedDateTime ed;
        try{
            sd = convertDateInput(0);
            ed = convertDateInput(5);
        } catch (Exception e) {
            e.printStackTrace();
            errorMsg.setText(R.string.startEndDate);
            return false;
        }
        if(ed.compareTo(sd)<=0){
            errorMsg.setText(R.string.endDateAfter);
            return false;
        }
        if(sd.compareTo(ZonedDateTime.now())<=0){
            errorMsg.setText(R.string.startFuture);
            return false;
        }
        return true;
    }

    private ZonedDateTime convertDateInput(int startIndex){
        int[] dateList = new int[5];
        for (int i = 0; i < 5; i++) {
            dateList[i] = Integer.parseInt(editTextsDate.get(startIndex+i).getText().toString());
        }
        return StringFormatHelper.convertTimeToZoneDateTime(dateList);

    }

    private int getAgeCoding(){
        int[] ageConverter = {-1,1,2,4,8,16};
        return ageConverter[agePosition];
    }

    //Called when publish is clicked
    private int getCheckedWeekdays(){
        int[] binaryRepresentation = {1,2,4,8,16,32,64};

        int chosenWeekdays = 0;
        for (int i = 0; i < cbWeekdays.size(); i++) {
            if(cbWeekdays.get(i).isChecked()){
                chosenWeekdays+=binaryRepresentation[i];
            }
        }
        if(chosenWeekdays==0){
            chosenWeekdays=-1;
        }
        else if(chosenWeekdays==127){
            chosenWeekdays=0;
        }

        return chosenWeekdays;
    }

    private void setupDropdown(){
        centers = centerVaccineHelper.getCenters();
        age = getResources().getStringArray(R.array.Age);

        centerFilter = (AutoCompleteTextView) view.findViewById(R.id.generateCenter);
        ageFilter = (AutoCompleteTextView) view.findViewById(R.id.generateAge);

        Simple_DropdownAdapter centerAdapter = new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, centers);
        centerFilter.setAdapter(centerAdapter);
        centerFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                centerPosition = position;
            }
        });

        Simple_DropdownAdapter ageAdapter = new Simple_DropdownAdapter(this.getActivity().getApplicationContext(), R.layout.simple_dropdown_item, age);
        ageFilter.setAdapter(ageAdapter);
        ageFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                agePosition = position;
            }
        });

        LoadingAnimation.dismissLoadingAnimation();
    }

    private void setupCheckboxes(){
        cbWeekdays = new ArrayList<CheckBox>();
        cbWeekdays.add(view.findViewById(R.id.cbSunday));
        cbWeekdays.add(view.findViewById(R.id.cbMonday));
        cbWeekdays.add(view.findViewById(R.id.cbTuesday));
        cbWeekdays.add(view.findViewById(R.id.cbWednesday));
        cbWeekdays.add(view.findViewById(R.id.cbThursday));
        cbWeekdays.add(view.findViewById(R.id.cbFriday));
        cbWeekdays.add(view.findViewById(R.id.cbSaturday));

        for (int i = 0; i < cbWeekdays.size(); i++) {
            cbWeekdays.get(i).setText(getResources().getStringArray(R.array.weekdaysShort)[i]);
        }
    }

    //Switches focus to next editText when max characters have been typed
    private void setupTimeTexts(){
        editTextsDate = new ArrayList<EditText>();
        editTextsDate.add(view.findViewById(R.id.startYear));
        editTextsDate.add(view.findViewById(R.id.startMonth));
        editTextsDate.add(view.findViewById(R.id.startDay));
        editTextsDate.add(view.findViewById(R.id.startHour));
        editTextsDate.add(view.findViewById(R.id.startMinute));
        editTextsDate.add(view.findViewById(R.id.endYear));
        editTextsDate.add(view.findViewById(R.id.endMonth));
        editTextsDate.add(view.findViewById(R.id.endDay));
        editTextsDate.add(view.findViewById(R.id.endHour));
        editTextsDate.add(view.findViewById(R.id.endMinute));

        int indexStartYear = 0;
        int indexEndYear = 5;
        int yearLength = 4;
        int generalLength = 2;

        for (EditText currTextView : editTextsDate) {
            currTextView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                @Override
                public void afterTextChanged(Editable s) {
                    if(currTextView == editTextsDate.get(indexStartYear)) {
                        if (s.length() == yearLength) {
                            nextTextView().requestFocus();
                        }
                    }
                    else if(currTextView == editTextsDate.get(indexEndYear)){
                        if(s.length() == yearLength){
                            nextTextView().requestFocus();
                        }
                    }
                    else{
                        if(s.length() == generalLength){
                            nextTextView().requestFocus();
                        }
                    }
                }

                public EditText nextTextView() {
                    int i;
                    for (i = 0; i < editTextsDate.size() - 1; i++) {
                        if (editTextsDate.get(i) == currTextView) {
                            return editTextsDate.get(i + 1);
                        }
                    }
                    return editTextsDate.get(i);
                }
            });
        }
    }

    private void setupButton(){
        Button publishButton = view.findViewById(R.id.publishTimesBtn);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                postAppointmentRange();
            }
        });
    }

}