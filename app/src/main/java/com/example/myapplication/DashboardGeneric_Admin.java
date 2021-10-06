package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;



import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;



import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.res.Resources;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;






public class DashboardGeneric_Admin extends Fragment {
    private String[] locations;
    private String[] ageGroup;
    private String[] product;
    private String[] CountyItems = new String[]{"County","värmland","bohuslän","malmö"};
    private String[] CenterItems = new String[]{"Center"};
    private String[] DateItems = new String[]{"Date","okt","nov","dec"};


    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_admin_dashboard_generic, container, false);

        setupDropdownMenus();


        // Inflate the layout for this fragment
        return view;
    }

    private void setupDropdownMenus() {
        //Setup spinners
        Spinner County_dropdown = (Spinner) view.findViewById(R.id.spinner1);
        Spinner Center_dropdown = (Spinner) view.findViewById(R.id.spinner2);
        Spinner Date_dropdown = (Spinner) view.findViewById(R.id.spinner3);
        //show string array on spinners
        ArrayAdapter<String> adapterCounty = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, CountyItems);
        ArrayAdapter<String> adapterCenter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, CenterItems);
        ArrayAdapter<String> adapterDate = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, DateItems);
        //set arrayadapters on spinner
        County_dropdown.setAdapter(adapterCounty);
        Center_dropdown.setAdapter(adapterCenter);
        Date_dropdown.setAdapter(adapterDate);

    }


}


