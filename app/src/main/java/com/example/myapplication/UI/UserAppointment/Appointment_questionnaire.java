package com.example.myapplication.UI.UserAppointment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.Model.Appointment_user.AppointmentResponse;
import com.example.myapplication.API.Model.Appointment_user.Center;
import com.example.myapplication.API.Model.Appointment_user.QuestionnaireRequest;
import com.example.myapplication.API.Model.Appointment_user.Vaccine;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.ApiClient;
import com.example.myapplication.UI.Adapter.QuestionnaireAdapter_user;
import com.example.myapplication.R;
import com.example.myapplication.UI.AlertWindow;
import com.example.myapplication.UI.LoadingAnimation;

import java.io.IOException;
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

    private UserResponse user;
    private int [] answers;
    private QuestionnaireRequest questionnaireRequest;

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
        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");

        setupQuestionnaire();
        setupButtons();

        return view;
    }

    private void setupQuestionnaire() {
        recyclerView = view.findViewById(R.id.recyclerViewQuestionnaire);
        array = getResources().getStringArray(R.array.questions);

        answers = new int[array.length];
        for(int i = 0; i < array.length; i++){
            answers[i] = NOT_DEFINED;
        }

        QuestionnaireAdapter_user adapter = new QuestionnaireAdapter_user(getContext(), array, answers);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setupButtons(){
        confirmButton = view.findViewById(R.id.confirmQuestionnaire);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(isAllAnswered()){
                    if(matchRejectCondition()){
                        new AlertWindow(getFragment()).createAlertWindow("Sorry, you are not allowed to book an appointment!");
                    } else {
                        fillQuestionnaireRequest();
                        API_sendNewQuestionnaire();
                        LoadingAnimation.startLoadingAnimation(getActivity());
                    }

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
    }

    public boolean isAllAnswered(){
        for(int i = 0; i < array.length; i++){
            if(answers[i] == NOT_DEFINED) {
                return false;
            }
        }
        return true;
    }

    public boolean matchRejectCondition(){
        return isAllYes();
    }

    public boolean isAllYes(){
        for(int i = 0; i < array.length; i++){
            if(answers[i] == 1) return false;
        }
        return true;
    }

    public void fillQuestionnaireRequest(){
        questionnaireRequest = new QuestionnaireRequest();
        questionnaireRequest.setNeededHelpDuetoVax((answers[0] == 0));
        questionnaireRequest.setTraveledInLast14Days((answers[1] == 0));
        questionnaireRequest.setAllergicToVax((answers[2] == 0));
        questionnaireRequest.setHasBloodProblems((answers[3] == 0));
        questionnaireRequest.setPregnant((answers[4] == 0));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void API_sendNewQuestionnaire(){
        Call<QuestionnaireRequest> call = ApiClient.getUserService().postNewQuestionnaire(user.getToken(), questionnaireRequest);
        call.enqueue(new Callback<QuestionnaireRequest>() {
            @Override
            public void onResponse(Call<QuestionnaireRequest> call, Response<QuestionnaireRequest> response) {
                if(response.isSuccessful()){

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LoadingAnimation.dismissLoadingAnimation();
                            Appointment_make appointment_CenterDate_fragment = new Appointment_make(answers);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, appointment_CenterDate_fragment)
                                    .commit();
                        }
                    }, 600);
                }
                else{
                    LoadingAnimation.dismissLoadingAnimation();
                    try {
                        new AlertWindow(getFragment()).createAlertWindow(response.errorBody().string());
                    } catch (IOException e) {
                        new AlertWindow(getFragment()).createAlertWindow("Unknown error");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<QuestionnaireRequest> call, Throwable t) {
                LoadingAnimation.dismissLoadingAnimation();
                new AlertWindow(getFragment()).createAlertWindow(getFragment().getResources().getString(R.string.connectionFailureAlert));
            }
        });
    }

    public Fragment getFragment(){return this;}
}
