package com.example.myapplication.Admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.API.Model.Appointment_user.Vaccine;
import com.example.myapplication.AlertWindow;
import com.example.myapplication.CenterVaccineHelper;
import com.example.myapplication.LoadingAnimation;
import com.example.myapplication.R;
import com.example.myapplication.Simple_DropdownAdapter;
import com.example.myapplication.UserResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AdminAddVaccineFragment extends Fragment {
    private AutoCompleteTextView centerFilter;
    private AutoCompleteTextView vaccineFilter;
    private String[] centers;
    private String[] vaccines;
    private UserResponse user;
    ApiVaccine apiVaccin;
    CenterVaccineHelper centerVaccineHelper;
    private int centerPosition;
    private int vaccinePosition;

    public AdminAddVaccineFragment() {
        // Required empty public constructor
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_add_vaccine, container, false);
        Intent i = getActivity().getIntent();
        user = (UserResponse) i.getSerializableExtra("userInfo");

        apiCallCenter();
        setupButtons();

        return view;
    }

    private void postVaccineAPI(){
        LoadingAnimation.startLoadingAnimation(getActivity());
        EditText etVaccineName = view.findViewById(R.id.vaccineName);
        String vaccineName = etVaccineName.getText().toString();
        ApiVaccine apiVaccine = new ApiVaccine(this);
        Runnable next = new Runnable() {
            @Override
            public void run() {
                LoadingAnimation.dismissLoadingAnimation();
                Fragment newFragment = new AdminAddVaccineFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, newFragment).commit();
                new AlertWindow(AdminAddVaccineFragment.this).createAlertWindow("Vaccine added");

            }
        };
        apiVaccine.API_postVaccine(user ,vaccineName, next);
    }

    private void postCenterVaccineAPI(){
        LoadingAnimation.startLoadingAnimation(getActivity());
        String centerID = centerVaccineHelper.getSelectedCenter(centerPosition);
        Vaccine vaccine = apiVaccin.getVaccine(vaccinePosition);

        EditText etAmount = view.findViewById(R.id.vaccineAmount);
        Pattern amountPattern = Pattern.compile("[0-9]*");
        Matcher mat = amountPattern.matcher(etAmount.getText().toString());
        if(!mat.matches()){
            return;
        }
        vaccine.setAmount(Integer.parseInt(etAmount.getText().toString()));

        Runnable next = new Runnable() {
            @Override
            public void run() {
                LoadingAnimation.dismissLoadingAnimation();
                Fragment newFragment = new AdminAddVaccineFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, newFragment).commit();
                new AlertWindow(AdminAddVaccineFragment.this).createAlertWindow("Vaccine added to center");

            }
        };

        centerVaccineHelper.API_postCenterVaccine(user, centerID, vaccine, next);
    }

    private void setupButtons(){
        Button addVaccine = view.findViewById(R.id.btnCreateVaccine);
        Button addVacToCenter = view.findViewById(R.id.btnAddVacToCenter);

        addVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postVaccineAPI();
            }
        });

        addVacToCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postCenterVaccineAPI();
            }
        });


    }
    private void apiCallCenter(){
        LoadingAnimation.startLoadingAnimation(getActivity());
        centerVaccineHelper = new CenterVaccineHelper(this);

        Runnable centerRunnable = new Runnable() {
            @Override
            public void run() {
                apiCallVaccine();
            }
        };
        centerVaccineHelper.API_getCenters(getActivity(), user, centerRunnable);

    }


    private void apiCallVaccine(){
        apiVaccin = new ApiVaccine(this);

        Runnable vaccineRunnable = new Runnable() {
            @Override
            public void run() {
                setupDropdown();
            }
        };
        apiVaccin.API_getVaccine(getActivity(), user, vaccineRunnable);
    }



    private void setupDropdown(){
        //centers = getResources().getStringArray(R.array.Centers);
        centers = centerVaccineHelper.getCenters();
        vaccines = apiVaccin.getVaccines();

        centerFilter = (AutoCompleteTextView) view.findViewById(R.id.generateCenter);
        vaccineFilter = (AutoCompleteTextView) view.findViewById(R.id.generateVaccin);

        Simple_DropdownAdapter centerAdapter = new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, centers);
        centerFilter.setAdapter(centerAdapter);
        centerFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                centerPosition = position;
            }
        });

        Simple_DropdownAdapter vaccineAdapter = new Simple_DropdownAdapter(getContext(), R.layout.simple_dropdown_item, vaccines);
        vaccineFilter.setAdapter(vaccineAdapter);
        vaccineFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                vaccinePosition = position;
            }
        });

        LoadingAnimation.dismissLoadingAnimation();
    }
}