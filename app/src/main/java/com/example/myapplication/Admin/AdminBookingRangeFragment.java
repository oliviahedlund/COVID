package com.example.myapplication.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.Helpers.CenterVaccineHelper;
import com.example.myapplication.LoadingAnimation;
import com.example.myapplication.R;
import com.example.myapplication.UI.Adapter.Simple_DropdownAdapter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kotlin.collections.ArrayDeque;


public class AdminBookingRangeFragment extends Fragment {

    private AutoCompleteTextView center;
    private View view;
    private String[] centers;
    private String[] age;
    private AutoCompleteTextView centerFilter;
    private AutoCompleteTextView ageFilter;
    List<CheckBox> cbWeekdays;
    CenterVaccineHelper centerVaccineHelper;
    private UserResponse user;


    public AdminBookingRangeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_booking_range, container, false);
        //TODO: get centers
        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");
        apiCallCenter();

        //setupDropdown();
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


    private void setupDropdown(){

        //centers = getResources().getStringArray(R.array.Centers);
        centers = centerVaccineHelper.getCenters();
        age = getResources().getStringArray(R.array.Age);

        centerFilter = (AutoCompleteTextView) view.findViewById(R.id.generateCenter);
        ageFilter = (AutoCompleteTextView) view.findViewById(R.id.generateAge);

        Simple_DropdownAdapter centerAdapter = new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, centers);
        centerFilter.setAdapter(centerAdapter);
        Simple_DropdownAdapter ageAdapter = new Simple_DropdownAdapter(this.getActivity().getApplicationContext(), R.layout.simple_dropdown_item, age);
        ageFilter.setAdapter(ageAdapter);

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
        List<EditText> editTexts = new ArrayList<EditText>();
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
                AdminBookingAPI adminBookingAPI = new AdminBookingAPI();

                //TODO: get/check center
                //TODO: get/check age
                //TODO: field for vaccin-product?

                int chosenWeekdays = getCheckedWeekdays();
                if(chosenWeekdays==-1){
                    //TODO: error message
                    System.out.println("No days selected");
                    return;
                }
                System.out.println(chosenWeekdays);

                //TODO: get and check date-time-input
                //temporary
                int year = 2021;
                int month = 11;
                int date = 12;
                int hour = 8;
                int minute = 20;
                String test = adminBookingAPI.convertTimeToAPIString(year, month, date, hour, minute);
                System.out.println(test);


            }
        });
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

}