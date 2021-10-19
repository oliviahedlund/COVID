package com.example.myapplication.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.myapplication.API.Model.Appointment_user.AppointmentRequest;
import com.example.myapplication.API.Model.Appointment_user.Center;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.AdminActivity;
import com.example.myapplication.Helpers.AdminBookingHelper;
import com.example.myapplication.Helpers.AppointmentsManageExtraHelper;
import com.example.myapplication.Helpers.CenterVaccineHelper;
import com.example.myapplication.Helpers.UserAPIHelper;
import com.example.myapplication.R;
import com.example.myapplication.UI.Adapter.Simple_DropdownAdapter;
import com.example.myapplication.UI.Adapter.ValidateAppointmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class ValidateAppointmentsFragment extends Fragment {

    public ValidateAppointmentsFragment() {
        // Required empty public constructor
    }

    private View view;
    private RecyclerView recyclerView;
    private List<AppointmentRequest> appointments;
    private List<AppointmentRequest> filteredAppointments;
    private ValidateAppointmentAdapter adapter;
    private UserResponse user;
    private AdminActivity adminActivity;
    private CenterVaccineHelper centerVaccineHelper;
    private UserAPIHelper userAPIHelper;
    private AppointmentsManageExtraHelper manageExtraHelper;
    private int counter;
    private CheckBox checkBoxAll;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_validate_appointments, container, false);
        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");

        adminActivity = (AdminActivity) getActivity();
        centerVaccineHelper = adminActivity.getCenterVaccineHelper();

        userAPIHelper = new UserAPIHelper(this);

        setupCheckbox();
        setupButtons();
        setupCenterDropdown();
        setupAppointments();

        return view;
    }

    private void setupCenterDropdown() {
        String[] centers = new String[centerVaccineHelper.getCenters().length+1];
        centers[0] = "All centers";
        String[] centerTemp = centerVaccineHelper.getCenters();
        for (int i = 0; i < centerTemp.length; i++) {
            centers[i+1] = centerTemp[i];
        }
        AutoCompleteTextView centerFilter = (AutoCompleteTextView) view.findViewById(R.id.generateCenter);

        Simple_DropdownAdapter centerAdapter = new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, centers);
        centerFilter.setAdapter(centerAdapter);
        centerFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                filterCenters(position);
            }
        });
    }

    private void filterCenters(int position){
        int realIndex = position-1;
        if(realIndex==-1){
            filteredAppointments = appointments;
        }
        else {
            filteredAppointments = new ArrayList<AppointmentRequest>();
            for (AppointmentRequest a : appointments) {
                if (a.getCenterId().equals(centerVaccineHelper.getSelectedCenter(realIndex))) {
                    filteredAppointments.add(a);
                }
            }
        }
        setupAdapterView();
        checkBoxAll.setChecked(false);

    }

    private void setupAdapterView(){
        recyclerView = view.findViewById(R.id.recViewValidate);
        adapter = new ValidateAppointmentAdapter(getContext(), filteredAppointments);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LoadingAnimation.dismissLoadingAnimation();
    }

    private void setupNames(int i){
        UserResponse temp = userAPIHelper.getUserAtPosition(i);
        appointments.get(i).setUserOfAppointment(temp);

        String centerID = appointments.get(i).getCenterId();
        appointments.get(i).setCenterName(centerVaccineHelper.getCenterNameFromId(centerID));
    }

    private void getUserNames(){
        userAPIHelper.initUserListSize(appointments.size());

        for (int i = 0; i < appointments.size(); i++) {
            Runnable onAllUsersReceived = new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < appointments.size(); i++) {
                        setupNames(i);
                    }
                    setupAdapterView();
                }
            };

            userAPIHelper.API_getUsersById(user, i, appointments.get(i).getUserId(), onAllUsersReceived);
        }
    }

    private void setupAppointments() {
        LoadingAnimation.startLoadingAnimation(getActivity());
        manageExtraHelper = new AppointmentsManageExtraHelper(ValidateAppointmentsFragment.this);

        Runnable onAppointmentsReceived = new Runnable() {
            @Override
            public void run() {
                appointments = manageExtraHelper.getResponseList();
                filteredAppointments = appointments;
                if(appointments.isEmpty()){
                    setupEmptyView();
                }
                else{
                    getUserNames();
                }
            }
        };

        manageExtraHelper.API_getAppointments(user, onAppointmentsReceived, true);
    }
    private void setupEmptyView(){
        TextView noAppointments = view.findViewById(R.id.noAppointments);
        noAppointments.setVisibility(View.VISIBLE);
        Button validateButton = view.findViewById(R.id.validateButton);
        validateButton.setClickable(false);
        CheckBox checkAll = view.findViewById(R.id.checkBoxAll);
        checkAll.setClickable(false);

        LoadingAnimation.dismissLoadingAnimation();

    }

    private void setupButtons() {
        Button validateButton = view.findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validationProcess();

            }
        });

        Button invalButton = view.findViewById(R.id.invalidateButton);
        invalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invalidationProcess();
            }
        });
    }

    private void reloadFragment(String msg){
        LoadingAnimation.dismissLoadingAnimation();
        Fragment newFragment = new ValidateAppointmentsFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, newFragment).commit();
        new AlertWindow(ValidateAppointmentsFragment.this).createAlertWindow(msg);

    }

    private List<String> getCheckedList(){
        boolean[] valList = adapter.getCheckedList();
        List<String> checkedAppointments = new ArrayList<String>();
        for (int i = 0; i < valList.length; i++) {
            if(valList[i]){
                checkedAppointments.add(adapter.getAppointment(i).getUserId());
            }
        }
        return checkedAppointments;
    }

    private void postChecked(boolean isValid, String id, int size){
        String msg;
        if(isValid){
            msg = "Appointments Validated";
        }
        else {
            msg = "Appointments Invalidated";
        }

        Runnable onResponse = new Runnable() {
            @Override
            public void run() {
                counter = counter+1;
                if(counter==size){
                    reloadFragment(msg);
                }
            }
        };

        if(isValid){
            manageExtraHelper.API_postValidation(user, onResponse, id);
        }
        else{
            AdminBookingHelper adminBookingHelper = new AdminBookingHelper(this);
            adminBookingHelper.API_CancelAppointment(user, id, onResponse);
        }

    }

    private void invalidationProcess() {
        LoadingAnimation.startLoadingAnimation(getActivity());
        //create list with appointment-id's to invalidate
        List<String> invalidAppointments = getCheckedList();

        counter=0;
        for (int i = 0; i < invalidAppointments.size(); i++) {
            postChecked(false, invalidAppointments.get(i), invalidAppointments.size());
        }
    }
/*
    private void postInvalidation(String id, int size){
        Runnable onResponse = new Runnable() {
            @Override
            public void run() {
                counter = counter+1;
                if(counter==size){
                    reloadFragment("Appointments invalidated");
                }

            }
        };
        AdminBookingHelper adminBookingHelper = new AdminBookingHelper(this);
        adminBookingHelper.API_CancelAppointment(user, id, onResponse);
    }
*/
    private void validationProcess(){
        LoadingAnimation.startLoadingAnimation(getActivity());

        //create list with appointment-id's to validate
        List<String> validAppointments = getCheckedList();

        //post validation and wait for counter to be == size to reload fragment
        counter=0;
        for (int i = 0; i < validAppointments.size(); i++) {
            postChecked(true, validAppointments.get(i), validAppointments.size());
        }
    }
/*
    private void postValidation(String id, int size){
        Runnable onResponse = new Runnable() {
            @Override
            public void run() {
                counter = counter+1;
                if(counter==size){
                    reloadFragment("Appointments validated");
                }

            }
        };
        manageExtraHelper.API_postValidation(user, onResponse, id);
    }
*/
    private void setupCheckbox() {
        checkBoxAll = view.findViewById(R.id.checkBoxAll);
        checkBoxAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                adapter.setAll(isChecked);
                if(isChecked){
                    checkBoxAll.setText("Uncheck all");
                }
                else{
                    checkBoxAll.setText("Check all");
                }
            }
        });
    }
}