package com.example.myapplication.UI.UserAppointment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class Appointment_makeCancel extends Fragment{

    private CardView makeAppointmentButton;
    private CardView cancelAppointmentButton;

    View view;

    public Appointment_makeCancel(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.appointment, container, false);
        setupButtons();
        return view;
    }

    public void setupButtons(){
        makeAppointmentButton = (CardView) view.findViewById(R.id.makeAppointment);
        cancelAppointmentButton = (CardView) view.findViewById(R.id.cancelAppointment);

        makeAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appointment_questionnaire appointmentQuestionnaire = new Appointment_questionnaire();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, appointmentQuestionnaire).commit();
            }
        });
    }

}
