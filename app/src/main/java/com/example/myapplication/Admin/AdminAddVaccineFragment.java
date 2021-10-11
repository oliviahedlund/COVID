package com.example.myapplication.Admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.example.myapplication.CenterVaccineHelper;
import com.example.myapplication.LoadingAnimation;
import com.example.myapplication.R;
import com.example.myapplication.Simple_DropdownAdapter;
import com.example.myapplication.UserResponse;


public class AdminAddVaccineFragment extends Fragment {
    private AutoCompleteTextView centerFilter;
    private AutoCompleteTextView vaccineFilter;
    private String[] centers;
    private String[] vaccines;
    private UserResponse user;
    ApiVaccine apiVaccin;
    CenterVaccineHelper centerVaccineHelper;

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

        return view;
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
        apiVaccin = new ApiVaccine();

        Runnable vaccineRunnable = new Runnable() {
            @Override
            public void run() {
                setupDropdown();
            }
        };
        apiVaccin.CallVaccineAPI(getActivity(), user, vaccineRunnable);
    }



    private void setupDropdown(){
        //centers = getResources().getStringArray(R.array.Centers);
        centers = centerVaccineHelper.getCenters();
        vaccines = apiVaccin.getVaccines();

        centerFilter = (AutoCompleteTextView) view.findViewById(R.id.generateCenter);
        vaccineFilter = (AutoCompleteTextView) view.findViewById(R.id.generateVaccin);

        Simple_DropdownAdapter centerAdapter = new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, centers);
        centerFilter.setAdapter(centerAdapter);

        Simple_DropdownAdapter vaccineAdapter = new Simple_DropdownAdapter(getContext(), R.layout.simple_dropdown_item, vaccines);
        vaccineFilter.setAdapter(vaccineAdapter);
        LoadingAnimation.dismissLoadingAnimation();
    }
}