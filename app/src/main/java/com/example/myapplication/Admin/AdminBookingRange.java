package com.example.myapplication.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.example.myapplication.R;
import com.example.myapplication.Simple_DropdownAdapter;


public class AdminBookingRange extends Fragment {

    private AutoCompleteTextView center;
    private View view;
    private String[] centers;
    private String[] age;
    private AutoCompleteTextView centerFilter;
    private AutoCompleteTextView ageFilter;

    public AdminBookingRange() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_booking_range, container, false);
        setupDropdown();
        return view;
    }

    private void setupDropdown(){

        centers = getResources().getStringArray(R.array.Centers);
        age = getResources().getStringArray(R.array.Age);

        centerFilter = (AutoCompleteTextView) view.findViewById(R.id.generateCenter);
        ageFilter = (AutoCompleteTextView) view.findViewById(R.id.generateAge);

        Simple_DropdownAdapter centerAdapter = new Simple_DropdownAdapter(this.getContext(), R.layout.simple_dropdown_item, centers);
        centerFilter.setAdapter(centerAdapter);
        Simple_DropdownAdapter ageAdapter = new Simple_DropdownAdapter(this.getActivity().getApplicationContext(), R.layout.simple_dropdown_item, age);
        ageFilter.setAdapter(ageAdapter);



    }
}