package com.example.myapplication.Admin;

import android.os.Bundle;

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

import com.example.myapplication.AlertWindow;
import com.example.myapplication.CenterVaccineHelper;
import com.example.myapplication.LoadingAnimation;
import com.example.myapplication.R;
import com.example.myapplication.Simple_DropdownAdapter;
import com.example.myapplication.UserResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kotlin.collections.ArrayDeque;


public class AdminBookingRangeFragment extends Fragment {

    private AutoCompleteTextView center;
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
    List<EditText> editTexts;
    AdminBookingAPI adminBookingAPI;


    public AdminBookingRangeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_booking_range, container, false);
        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");

        apiCallCenter();
        setupCheckboxes();
        setupTimeTexts();
        setupButton();
        return view;
    }

    private void apiCallCenter(){
        LoadingAnimation.startLoadingAnimation(getActivity());
        centerVaccineHelper = new CenterVaccineHelper(this);

        Runnable next = new Runnable() {
            @Override
            public void run() {
                setupDropdown();
            }
        };
        centerVaccineHelper.API_getCenters(getActivity(), user, next);

    }

    private String convertDateInputToString(int startIndex){
        int[] dateList = new int[5];
        for (int i = 0; i < 5; i++) {
            dateList[i] = Integer.parseInt(editTexts.get(startIndex+i).getText().toString());
        }
        return adminBookingAPI.convertTimeToAPIString(dateList);

    }
    private void postAppointmentRange(){
        LoadingAnimation.startLoadingAnimation(getActivity());
        adminBookingAPI = new AdminBookingAPI(this);

        EditText etMinutes = view.findViewById(R.id.appMinutes);
        Pattern minPattern = Pattern.compile("[0-9]*");
        Matcher mat = minPattern.matcher(etMinutes.getText().toString());
        if(!mat.matches()){
            System.out.println("Minutes not integer");
            return;
        }
        int minutes = Integer.parseInt(etMinutes.getText().toString());

        postRangeRequest = new PostRangeRequest();
        postRangeRequest.setCenter(centerVaccineHelper.getSelectedCenter(centerPosition));
        postRangeRequest.setAllowedAgeGroups(getAgeCoding());
        postRangeRequest.setAllowedDaysOfWeek(getCheckedWeekdays());
        postRangeRequest.setTimePerAppointmentMinutes(minutes);
        postRangeRequest.setStartDateTime(convertDateInputToString(0));
        postRangeRequest.setEndDateTime(convertDateInputToString(5));

        Runnable next = new Runnable() {
            @Override
            public void run() {
                LoadingAnimation.dismissLoadingAnimation();
                Fragment newFragment = new AdminBookingRangeFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, newFragment).commit();
                new AlertWindow(AdminBookingRangeFragment.this).createAlertWindow("Range added");
            }
        };

        adminBookingAPI.PostBookingRange(getActivity(), user, postRangeRequest, next);
    }

    private int getAgeCoding(){
        int[] ageConverter = {-1,1,2,4,8,16,0};
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
        agePosition = 6;

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
        editTexts = new ArrayList<EditText>();
        editTexts.add(view.findViewById(R.id.startYear));
        editTexts.add(view.findViewById(R.id.startMonth));
        editTexts.add(view.findViewById(R.id.startDay));
        editTexts.add(view.findViewById(R.id.startHour));
        editTexts.add(view.findViewById(R.id.startMinute));
        editTexts.add(view.findViewById(R.id.endYear));
        editTexts.add(view.findViewById(R.id.endMonth));
        editTexts.add(view.findViewById(R.id.endDay));
        editTexts.add(view.findViewById(R.id.endHour));
        editTexts.add(view.findViewById(R.id.endMinute));

        int indexStartYear = 0;
        int indexEndYear = 5;
        int yearLength = 4;
        int generalLength = 2;

        for (EditText currTextView : editTexts) {
            currTextView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                @Override
                public void afterTextChanged(Editable s) {
                    if(currTextView == editTexts.get(indexStartYear)) {
                        if (s.length() == yearLength) {
                            nextTextView().requestFocus();
                        }
                    }
                    else if(currTextView == editTexts.get(indexEndYear)){
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
                    for (i = 0; i < editTexts.size() - 1; i++) {
                        if (editTexts.get(i) == currTextView) {
                            return editTexts.get(i + 1);
                        }
                    }
                    return editTexts.get(i);
                }
            });
        }
    }

    private void setupButton(){
        Button publishButton = view.findViewById(R.id.publishTimesBtn);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postAppointmentRange();
            }
        });
    }

}