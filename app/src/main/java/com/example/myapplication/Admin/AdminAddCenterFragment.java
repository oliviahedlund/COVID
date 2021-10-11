package com.example.myapplication.Admin;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.ApiClient;
import com.example.myapplication.Booking.Center;
import com.example.myapplication.Booking.Vaccine;
import com.example.myapplication.R;
import com.example.myapplication.UserResponse;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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


    public AdminAddCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_add_center, container, false);
        setup(view);
        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");

        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                center = centerName.getEditableText().toString();
                centerAdd = centerAddress.getEditableText().toString();
                vaccineName = vaccine.getEditableText().toString();
                number = amount.getEditableText().toString();
                value=Integer.parseInt(number);
                System.out.println(center + centerAdd + vaccineName + value);
                Center bodyCenter = new Center();
                bodyCenter.setAddress(centerAdd);
                bodyCenter.setName(center);
                Vaccine vac = new Vaccine();
                List<Vaccine> vaccines = new ArrayList<>();
                vac.setVaccineId("5b2382b1-1b6b-49a2-a3fd-ae438fcdf336");
                vac.setAmount(value);
                vaccines.add(vac);
                bodyCenter.setVaccines(vaccines);
                API_postCenters(user, bodyCenter);
            }
        });
        return view;
    }

    public void API_postCenters(UserResponse user, Center bodycenter){
        Call<String> call = ApiClient.getUserService().postCenters(user.getToken(), bodycenter);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    centers = response.body().toString();

                }else{
                    System.out.println("could not add center");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println(t.getLocalizedMessage());
            }
        });
    }



    private void setup(View view){
        centerName = view.findViewById(R.id.centerName);
        centerAddress = view.findViewById(R.id.centerAddress);
        vaccine = view.findViewById(R.id.vaccineName);
        amount = view.findViewById(R.id.vaccineAmount);
        btn = view.findViewById(R.id.button7);

    }
}