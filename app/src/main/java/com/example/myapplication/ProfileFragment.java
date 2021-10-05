package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment
        GeneralActivity activity = (GeneralActivity) getActivity();
        UserResponse user = activity.getUserData();
        setupData(user, view);
        return view;
    }

    private void setupData(UserResponse response, View view){
       TextView firstName = (TextView) view.findViewById(R.id.profileName);
       firstName.setText(": " + response.getFirstName());

       TextView lastName = (TextView) view.findViewById(R.id.profileLastName);
       lastName.setText(": " + response.getLastName());

       TextView pEmail = (TextView) view.findViewById(R.id.profileEmail);
       pEmail.setText(": " + response.getEmail());

       TextView pPhone = (TextView) view.findViewById(R.id.profilePhone);
       pPhone.setText(": " + response.getPhoneNumber());

       TextView pBirth = (TextView) view.findViewById(R.id.profileBirth);
       pBirth.setText(": " + response.getBirthDate());

       TextView pAdress = (TextView) view.findViewById(R.id.profileAdress);
       pAdress.setText(": " + response.getAddress());

       TextView pCity = (TextView) view.findViewById(R.id.profileCity);
       pCity.setText(": " + response.getCity());

       TextView pZip = (TextView) view.findViewById(R.id.profileZip);
       pZip.setText(": " + response.getPostalCode());

       TextView pCounty = (TextView) view.findViewById(R.id.profileCounty);
       pCounty.setText(": " + response.getDistrict());


    }





}
