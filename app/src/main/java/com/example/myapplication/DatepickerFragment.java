package com.example.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;

import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatepickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    private AlertDialog.Builder timeCenterDialogBuilder;
    private AlertDialog.Builder questionnaireDialogBuilder;
    private AlertDialog timeCenterDialog;
    private AlertDialog questionnaireDialog;
    private Button confirmCenterDate;
    private Button cancelTimeCenter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        timeCenterPopup();

//        Toast.makeText(getContext(), "Wow, popup action button", Toast.LENGTH_SHORT).show();

    }

    public void timeCenterPopup(){
        timeCenterDialogBuilder = new AlertDialog.Builder(getContext());
        final View popupView = getLayoutInflater().inflate(R.layout.time_center_popup, null);

        confirmCenterDate = popupView.findViewById(R.id.confirmCenterDate);
        confirmCenterDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                questionnairePopup();
            }
        });

        timeCenterDialogBuilder.setView(popupView);
        timeCenterDialog = timeCenterDialogBuilder.create();
//        timeCenterDialogBuilder.setCancelable(false);
        timeCenterDialog.show();


    }

    public void questionnairePopup(){

        questionnaireDialogBuilder = new AlertDialog.Builder(getContext());

        final View popupView = getLayoutInflater().inflate(R.layout.questionnaire_popup, null);

        timeCenterDialogBuilder.setView(popupView);
        questionnaireDialog = timeCenterDialogBuilder.create();
        questionnaireDialog.show();
    }
}
