package com.example.myapplication.UI;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.UI.UserAppointment.Appointment_makeCancel;

public class AlertWindow {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private TextView alertTitle;
    private Button okButton;

    private Fragment fragment;

    public AlertWindow(Fragment fragment){
        this.fragment = fragment;
    }

    public void createAlertWindow(String message){
        dialogBuilder = new AlertDialog.Builder(fragment.getContext());
        final View popupView = fragment.getLayoutInflater().inflate(R.layout.alertwindow, null);
        okButton = (Button) popupView.findViewById(R.id.alertConfirmButton);
        alertTitle = (TextView) popupView.findViewById(R.id.alertTitle);
        alertTitle.setText(message);

        dialogBuilder.setView(popupView);
        dialog = dialogBuilder.create();
        dialog.show();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Appointment_makeCancel appointmentFragment = new Appointment_makeCancel();
                fragment.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, appointmentFragment).commit();
            }
        });
    }
}
