package com.example.myapplication.UI;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.Helpers.LanguageHelper;
import com.example.myapplication.R;


public class SettingsFragment extends Fragment{
    Fragment thisFragment = this;
    View view;
    UserResponse user;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.settings, container, false);

        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");

        setUpButtons();


        return view;
    }

    private void setUpButtons(){
        Button enBut = (Button) view.findViewById(R.id.button);
        Button svBut = (Button) view.findViewById(R.id.button3);

        enBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage("en");
            }
        });
        svBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage("sv");
            }
        });
    }

    private void setLanguage(String language){
        Context context = getActivity().getBaseContext();
        //update only if language is not already set
        if(! language.equals(LanguageHelper.getLanguage(context))) {
            //set language
            LanguageHelper.setLocale(context, language);
            //update view
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            getActivity().recreate();
        }
    }

}