package com.example.myapplication.UI.UserAppointment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.Model.Appointment_user.Center;
import com.example.myapplication.API.Model.Appointment_user.Vaccine;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.ApiClient;
import com.example.myapplication.UI.Adapter.QuestionnaireAdapter_user;
import com.example.myapplication.R;
import com.example.myapplication.UI.AlertWindow;
import com.example.myapplication.UI.LoadingAnimation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.example.myapplication.UI.Adapter.QuestionnaireAdapter;


public class Appointment_questionnaire extends Fragment {
    public static final int NOT_DEFINED = -1;
    String array[];
    View view;
    RecyclerView recyclerView;
    private int [] answers;
    private Button confirmButton;
    private Button cancelButton;

    public Appointment_questionnaire() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.questionnaire_user_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewQuestionnaire);
        array = getResources().getStringArray(R.array.questions);

        answers = new int[array.length];
        for(int i = 0; i < array.length; i++){
            answers[i] = NOT_DEFINED;
        }

        QuestionnaireAdapter_user adapter = new QuestionnaireAdapter_user(getContext(), array, answers);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        confirmButton = view.findViewById(R.id.confirmQuestionnaire);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(isAllAnswered()){
                    // send the answers array to the next fragment
                    Appointment_make appointment_CenterDate_fragment = new Appointment_make(answers);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, appointment_CenterDate_fragment)
                            .commit();
                } else{
                    Toast.makeText(getActivity(),"Please answer all the questions", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton = view.findViewById(R.id.cancelQuestionnaire);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appointment_makeCancel appointmentFragment = new Appointment_makeCancel();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, appointmentFragment).commit();
            }
        });

        return view;
    }

    public boolean isAllAnswered(){
        for(int i = 0; i < array.length; i++){
            if(answers[i] == NOT_DEFINED) {
                return false;
            }
        }
        return true;
    }
}
