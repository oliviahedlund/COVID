package com.example.myapplication.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.example.myapplication.R;
import com.example.myapplication.Simple_DropdownAdapter;

import java.util.List;


public class AdminAddVaccineFragment extends Fragment {
    private AutoCompleteTextView centerFilter;
    private String[] centers;

    public AdminAddVaccineFragment() {
        // Required empty public constructor
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_add_vaccine, container, false);

        setupCenters();
        setupVaccines();
        setupDropdown();

        return view;
    }

    private void setupCenters(){
        //TODO: anropa centers
    }
    private void setupVaccines(){
        //TODO: anropa vaccin
    }

    private void setupDropdown(){
        ////
        centers = getResources().getStringArray(R.array.Centers);

        centerFilter = (AutoCompleteTextView) view.findViewById(R.id.generateCenter);

        Simple_DropdownAdapter centerAdapter = new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, centers);
        centerFilter.setAdapter(centerAdapter);

    }
}