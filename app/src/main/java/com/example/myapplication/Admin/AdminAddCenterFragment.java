package com.example.myapplication.Admin;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.Booking.ApiCenter;
import com.example.myapplication.API.Model.Appointment_user.Center;
import com.example.myapplication.API.Model.Appointment_user.Vaccine;
import com.example.myapplication.CenterVaccineHelper;
import com.example.myapplication.LoadingAnimation;
import com.example.myapplication.R;
import com.example.myapplication.Simple_DropdownAdapter;
import com.example.myapplication.UserResponse;

import java.util.ArrayList;
import java.util.List;


public class AdminAddCenterFragment extends Fragment {
    private Button importVaccine;
    private EditText centerName;
    private EditText centerAddress;
    private EditText vaccine;
    private EditText amount;
    private String center;
    private String centerAdd;
    private String vaccineName;
    private String number;
    private int value;
    private UserResponse user;
    private String centers;
    private Button btn;
    private View view;
    private ApiCenter apiCenter;
    private ApiVaccine apiVaccin;
    private CenterVaccineHelper centerVaccineHelper;
    private String[] vaccines;
    int vaccinePosition;
    private AutoCompleteTextView vaccineFilter;


    public AdminAddCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_add_center, container, false);
        //setup();
        //inizialize if not chosen
        vaccinePosition=-1;

        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");
        apiCenter = new ApiCenter();
        apiCallVaccine();


        return view;
    }

    private void apiCallVaccine(){
        apiVaccin = new ApiVaccine();

        Runnable next = new Runnable() {
            @Override
            public void run() {
                setup();
            }
        };
        apiVaccin.CallVaccineAPI(getActivity(), user, next);
    }



    private void setup(){
        centerName = view.findViewById(R.id.centerName);
        centerAddress = view.findViewById(R.id.centerAddress);
        //vaccine = view.findViewById(R.id.vaccineName);
        amount = view.findViewById(R.id.vaccineAmount);
        btn = view.findViewById(R.id.button7);
        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                center = centerName.getEditableText().toString();
                centerAdd = centerAddress.getEditableText().toString();
                number = amount.getEditableText().toString();

                try {
                    value=Integer.parseInt(number);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    System.out.println("Wrong input in vaccin amount");
                    return;
                }

                System.out.println(center + centerAdd + vaccineName + value);

                Center bodyCenter = new Center();
                bodyCenter.setName(center);
                bodyCenter.setAddress(centerAdd);

                Vaccine vac = new Vaccine();
                List<Vaccine> vaccines = new ArrayList<>();
                if(vaccinePosition!=-1) {
                    vac.setId(apiVaccin.getVaccinID(vaccinePosition));
                }
                else{
                    System.out.println("Error: no vaccin chosen");
                    return;
                }
                //vac.setId("5b2382b1-1b6b-49a2-a3fd-ae438fcdf336");
                vac.setAmount(value);
                //vaccines.add(vac);
                bodyCenter.setVaccines(vaccines);
                apiCenter.API_postCenters(user, bodyCenter);
                //API_postCenters(user, bodyCenter);
            }
        });

        vaccines = apiVaccin.getVaccines();

        vaccineFilter = (AutoCompleteTextView) view.findViewById(R.id.generateVaccin);

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