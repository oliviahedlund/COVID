package com.example.myapplication.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.Helpers.CenterVaccineHelper;
import com.example.myapplication.R;

public class GenericMessageFragment extends Fragment {
    private View view;
    private String message;

    public GenericMessageFragment(String message){
        this.message = message;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.generic_message, container, false);
        TextView textView = view.findViewById(R.id.genericMessage);
        textView.setText(message);

        return view;
    }
}
