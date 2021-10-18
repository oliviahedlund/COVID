package com.example.myapplication.UI.AdminBooking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.UI.Adapter.QuestionnaireAdapter;
import com.example.myapplication.R;

public class Admin_see_user_quest extends Fragment {


    public Admin_see_user_quest() {
        // Required empty public constructor
    }
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.admin_quest_user, container, false);


        return view;
    }
}