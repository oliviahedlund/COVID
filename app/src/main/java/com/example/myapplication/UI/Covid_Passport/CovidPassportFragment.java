package com.example.myapplication.UI.Covid_Passport;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.GeneralActivity;
import com.example.myapplication.Helpers.StringFormatHelper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.example.myapplication.R;

import java.time.ZonedDateTime;
import java.util.Calendar;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link covidPassportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CovidPassportFragment extends Fragment {
    private String id;
    private String fullName;
    private ZonedDateTime birthDate;
    private TextView userName;
    private TextView userBirthDate;
    private Button scanner;
    private ImageView qr;
    private LinearLayout message;

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

        setupWidgets();
        makeTextInfo();
        ifFullyVaccinated();
        setupScanner();

        return view;
    }

    private void setupWidgets(){
        qr = (ImageView) view.findViewById(R.id.qr_output);
        message = (LinearLayout) view.findViewById(R.id.qr_output_none);
        userName = (TextView) view.findViewById(R.id.name);
        userBirthDate = (TextView) view.findViewById(R.id.birth_date);
    }

    private void makeTextInfo(){
        birthDate = user.getBirthDate();
        fullName = user.getFirstName() +" "+ user.getLastName();
        userName.setText(fullName);
        userBirthDate.setText(StringFormatHelper.yearMonthDay(birthDate));
    }

    private void ifFullyVaccinated() {
        Calendar now = Calendar.getInstance();
        ZonedDateTime secondDate = user.getSecondDoseDate();

        if(secondDate.getYear() == now.get(Calendar.YEAR)){
            if(now.get(Calendar.DAY_OF_YEAR) >= (secondDate.getDayOfYear() + 14)){
                makeQRCode();
            } else{
                message.setVisibility(View.VISIBLE);
            }
        } else if(secondDate.getYear() < now.get(Calendar.YEAR) && secondDate.getYear() != 1){
            makeQRCode();
        } else{
            message.setVisibility(View.VISIBLE);
        }
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

        if(!id.isEmpty()){
            MultiFormatWriter writer = new MultiFormatWriter();
            try {
                BitMatrix matrix = writer.encode(id, BarcodeFormat.QR_CODE, 350, 350);
                BarcodeEncoder encoder = new BarcodeEncoder();
                Bitmap bitmap = encoder.createBitmap(matrix);
                qr.setVisibility(View.VISIBLE);
                qr.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }
}