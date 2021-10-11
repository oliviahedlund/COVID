package com.example.myapplication.Admin;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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


public class AdminAddVaccineFragment extends Fragment {

    public AdminAddVaccineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_add_vaccine, container, false);

        return view;
    }
}