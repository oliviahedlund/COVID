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
    private Center bodyCenter;
    private Vaccine vac;
    private List<Vaccine> vaccines;
    private List<Center> centers;
    private Button btn;


    public AdminAddCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_add_vaccine, container, false);
        setup(view);
        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");

        bodyCenter.setAddress(centerAdd);
        bodyCenter.setName(center);
        vac.setVaccineName(vaccineName);
        vac.setAmount(value);
        vaccines.add(vac);
        bodyCenter.setVaccines(vaccines);



        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void API_getCenters(UserResponse user, Center bodyCenter){
        Call<List<Center>> call = ApiClient.getUserService().getCenters(user.getToken(), bodyCenter);
        call.enqueue(new Callback<List<Center>>() {
            @Override
            public void onResponse(Call<List<Center>> call, Response<List<Center>> response) {
                if (response.isSuccessful()) {
                    centers = response.body();
                    System.out.println(centers);


                }else{
                    System.out.println("else");
                }
            }

            @Override
            public void onFailure(Call<List<Center>> call, Throwable t) {
                System.out.println("fail");
            }
        });
    }



    private void setup(View view){
        centerName = view.findViewById(R.id.centerName);
        centerAddress = view.findViewById(R.id.centerAddress);
        vaccine = view.findViewById(R.id.vaccineName);
        amount = view.findViewById(R.id.vaccineAmount);
        center = centerName.getText().toString();
        centerAdd = centerAddress.getText().toString();
        vaccineName = vaccine.getText().toString();
        number = amount.getText().toString();
        value=Integer.parseInt(number);
        btn = view.findViewById(R.id.button7);

    }
}