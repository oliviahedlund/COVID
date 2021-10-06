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
    //private String[] locations;
    //private String[] ageGroup;
    //private String[] product;
    private String[] CountyItems = new String[]{"Choose county","Blekinge", "Dalarna", "Gotland", "Gävleborg", "Halland",
            "Jämtland", "Jönköping", "Kalmar", "Kronoberg", "Norrbotten", "Skåne", "Stockholm", "Södermanland", "Uppsala",
            "Värmland", "Västerbotten", "Västernorrland", "Västmanland", "Västra Götaland", "Örebro", "Östergötland"};
    private String[] CenterItems = new String[]{"Choose center", "Center 1", "Center 2"};
    private String[] MonthItems = new String[]{"Choose month","Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Okt","Nov","Dec"};

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

        //Show string array on spinners
        ArrayAdapter<String> adapterCounty = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, CountyItems);
        ArrayAdapter<String> adapterCenter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, /*getCenters(County_dropdown.getSelectedItem().toString())*/ CenterItems);
        ArrayAdapter<String> adapterDate = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, MonthItems);

        //Set array adapter on spinners
        County_dropdown.setAdapter(adapterCounty);
        Center_dropdown.setAdapter(adapterCenter);
        Date_dropdown.setAdapter(adapterDate);
    }

    private String[] getCenters(String county){
        switch(county){
            case "Blekinge":
            case "Dalarna":
            case "Gotland":
            case "Gävleborg":
            case "Halland":
            case "Jämtland":
            case "Jönköping":
            case "Kalmar":
            case "Kronoberg":
            case "Norrbotten":
            case "Skåne":
            case "Stockholm":
            case "Södermansland":
            case "Uppsala":
            case "Värmland":
            case "Västerbotten":
            case "Västernorrland":
            case "Västmanland":
            case "Västra Götaland":
            case "Örebro":
            case "Östergötland":
                return new String[]{"Center", "Center 1", "Center 2"};
            default:
                return new String[]{"Center"};
        }
    }

}


