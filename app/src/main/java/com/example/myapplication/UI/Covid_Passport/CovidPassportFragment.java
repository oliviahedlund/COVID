package com.example.myapplication.UI.Covid_Passport;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.GeneralActivity;
import com.example.myapplication.UI.UserAppointment.Appointment_Info;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import com.example.myapplication.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link covidPassportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CovidPassportFragment extends Fragment {
    private String id;
    private String fullName;
    private String birthDate;
    private TextView userName;
    private TextView userBirthDate;
    private Button scanner;

    private UserResponse user;
    private View view;

    public CovidPassportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GeneralActivity activity = (GeneralActivity) getActivity();
        user = activity.getUserData();
        view = inflater.inflate(R.layout.fragment_covid_passport, container, false);

        setupScanner();
        makeQRCode();
        return view;
    }

    public void setupScanner(){
        scanner = (Button) view.findViewById(R.id.scanner);
        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QRScanner qrScanner = new QRScanner();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, qrScanner).commit();
            }
        });
    }

    public void makeQRCode(){
        id = user.getId();
        birthDate = user.getBirthDate();
        fullName = user.getFirstName() +" "+ user.getLastName();
        userName = (TextView) view.findViewById(R.id.name);
        userName.setText(fullName);
        userBirthDate = (TextView) view.findViewById(R.id.birth_date);
        userBirthDate.setText(birthDate.substring(0,10));

        if(!id.isEmpty()){
            MultiFormatWriter writer = new MultiFormatWriter();
            try {
                BitMatrix matrix = writer.encode(id, BarcodeFormat.QR_CODE, 350, 350);
                BarcodeEncoder encoder = new BarcodeEncoder();
                Bitmap bitmap = encoder.createBitmap(matrix);
                ImageView qr = view.findViewById(R.id.qr_output);
                qr.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }
}