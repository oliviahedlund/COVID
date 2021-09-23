package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AdminQuestionnaireFragment extends Fragment {


    public AdminQuestionnaireFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_questionnaire, container, false);

        AdminNewQuestionnaires newFragment = new AdminNewQuestionnaires();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.frameAdminQ, newFragment).commit();

        setupButtons();
        return view;
    }

    private void setupButtons(){
        Button newQ = view.findViewById(R.id.button4);
        Button answQ = view.findViewById(R.id.button5);

        newQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminNewQuestionnaires newFragment = new AdminNewQuestionnaires();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdminQ, newFragment).commit();
            }
        });
        answQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminQuestMessages newFragment = new AdminQuestMessages();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdminQ, newFragment).commit();
            }
        });


    }
}