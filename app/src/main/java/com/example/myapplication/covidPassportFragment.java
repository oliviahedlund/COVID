package com.example.myapplication;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link covidPassportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class covidPassportFragment extends Fragment {
    private String id;
    private String fullname;
    private String birthDate;
    private TextView userName;
    private TextView userBirthDate;
    //private ImageView qr;

    public covidPassportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GeneralActivity activity = (GeneralActivity) getActivity();
        UserResponse user = activity.getUserData();
        View view = inflater.inflate(R.layout.fragment_covid_passport, container, false);
        //set string to user id,birth date and full name
        id = user.getId();
        birthDate = user.getBirthDate();
        fullname = user.getFirstName() +" "+ user.getLastName();
        userName = (TextView) view.findViewById(R.id.name);
        userName.setText(fullname);
        userBirthDate = (TextView) view.findViewById(R.id.birth_date);
        userBirthDate.setText(birthDate.substring(0,10));

        if(!id.isEmpty()){
            //initialize multiformatwriter
            MultiFormatWriter writer = new MultiFormatWriter();
            try {
                BitMatrix matrix = writer.encode(id, BarcodeFormat.QR_CODE, 350, 350);
                //initialize barcode encoder
                BarcodeEncoder encoder = new BarcodeEncoder();
                //initialize bitmap
                Bitmap bitmap = encoder.createBitmap(matrix);
                //set bitmap on imageView
                ImageView qr = view.findViewById(R.id.qr_output);
                qr.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }

        // Inflate the layout for this fragment
        return view;
    }


}