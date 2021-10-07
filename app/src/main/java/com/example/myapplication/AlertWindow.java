package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class AlertWindow {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private Button okButton;

    private Fragment fragment;

    public AlertWindow(Fragment fragment){
        this.fragment = fragment;
    }

    public void createAlertWindow(){
        dialogBuilder = new AlertDialog.Builder(fragment.getContext());
        final View popupView = fragment.getLayoutInflater().inflate(R.layout.popupwindow, null);
        okButton = (Button) popupView.findViewById(R.id.alertConfirmButton);

        dialogBuilder.setView(popupView);
        dialog = dialogBuilder.create();
        dialog.show();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Appointment_fragment appointmentFragment = new Appointment_fragment();
                fragment.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, appointmentFragment).commit();
            }
        });
    }
}
