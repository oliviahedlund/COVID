package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class Appointment_fragment extends Fragment{

    private CardView makeAppointmentButton;
    private CardView cancelAppointmentButton;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.appointment, container, false);

        setupButtons();

        // Inflate the layout for this fragment
        return view;
    }

    public void setupButtons(){
        makeAppointmentButton = (CardView) view.findViewById(R.id.makeAppointment);
        cancelAppointmentButton = (CardView) view.findViewById(R.id.cancelAppointment);

        makeAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appointment_Center_Date_fragment appointment_CenterDate_fragment = new Appointment_Center_Date_fragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, appointment_CenterDate_fragment)
                        .addToBackStack(null).commit();

//                DialogFragment datePicker = new DatepickerFragment();
//                datePicker.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });
    }

}
