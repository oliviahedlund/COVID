package com.example.myapplication.UI.AdminBooking;

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

import com.example.myapplication.AdminActivity;
import com.example.myapplication.UI.AlertWindow;
import com.example.myapplication.API.Model.Appointment_user.Center;
import com.example.myapplication.API.Model.Appointment_user.Vaccine;
import com.example.myapplication.Helpers.CenterVaccineHelper;
import com.example.myapplication.Helpers.AdminVaccineHelper;
import com.example.myapplication.R;
import com.example.myapplication.UI.Adapter.Simple_DropdownAdapter;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.UI.LoadingAnimation;

import java.util.ArrayList;
import java.util.List;


public class AdminAddCenterFragment extends Fragment {
    private EditText centerName;
    private EditText centerAddress;
    private EditText amount;
    private String center;
    private String centerAdd;
    private String number;
    private int value;
    private UserResponse user;
    private Button btn;
    private View view;
    private AdminVaccineHelper apiVaccin;
    private CenterVaccineHelper centerVaccineHelper;
    private String[] vaccines;
    int vaccinePosition;
    private AutoCompleteTextView vaccineFilter;
    private AdminActivity adminActivity;


    public AdminAddCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_add_center, container, false);

        adminActivity = (AdminActivity) getActivity();
        centerVaccineHelper = adminActivity.getCenterVaccineHelper();
        apiVaccin = adminActivity.getVaccineHelper();

        vaccinePosition=-1; //inizialize if not chosen
        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");

        setupDropdown();
        setup();

        return view;
    }


    private void setupDropdown(){
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

    private void postAPI(){
        Center bodyCenter = setupBodyCenter();
        Runnable next = new Runnable() {
            @Override
            public void run() {
                adminActivity.callCenter(new Runnable() {
                    @Override
                    public void run() {
                        LoadingAnimation.dismissLoadingAnimation();
                        Fragment newFragment = new AdminAddCenterFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, newFragment).commit();
                        new AlertWindow(AdminAddCenterFragment.this).createAlertWindow("Center added");
                    }
                });

            }
        };

        if(bodyCenter!=null) {
            LoadingAnimation.startLoadingAnimation(getActivity());
            centerVaccineHelper.API_postCenter(user, bodyCenter, next);
        }
        else{
            new AlertWindow(AdminAddCenterFragment.this).createAlertWindow("Not enough information provided");
        }
    }

    private void setup(){
        centerName = view.findViewById(R.id.centerName);
        centerAddress = view.findViewById(R.id.centerAddress);
        amount = view.findViewById(R.id.vaccineAmount);

        btn = view.findViewById(R.id.button7);
        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                postAPI();
            }
        });

    }

    private Center setupBodyCenter(){
        center = centerName.getEditableText().toString();
        centerAdd = centerAddress.getEditableText().toString();
        if(center.equals("") || centerAdd.equals("")){
            return null;
        }
        Center bodyCenter = new Center();
        bodyCenter.setCenterName(center);
        bodyCenter.setCenterCounty(centerAdd);

        List<Vaccine> vaccineList = new ArrayList<Vaccine>();
        vaccineList = setupBodyVaccine();
        if(vaccineList == null){
            return null;
        }

        bodyCenter.setVaccines(vaccineList);
        return bodyCenter;

    }

    private List<Vaccine> setupBodyVaccine(){
        number = amount.getEditableText().toString();
        try {
            value=Integer.parseInt(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Wrong input in vaccin amount");
            return null;
        }
        if(value==0){
            System.out.println("No vaccin amount");
            return null;
        }
        List<Vaccine> vaccineList = new ArrayList<Vaccine>();
        Vaccine vac = new Vaccine();
        if(vaccinePosition!=-1) {
            vac.setVaccineId(apiVaccin.getVaccineID(vaccinePosition));
        }
        else{
            System.out.println("Error: no vaccin chosen");
            return null;
        }
        vac.setAmount(value);
        vaccineList.add(vac);
        return vaccineList;
    }
}