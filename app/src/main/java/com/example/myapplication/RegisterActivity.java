package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.*;
import java.util.*;

public class RegisterActivity extends AppCompatActivity {

    final int AGE_REQUIRMENT = 180000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Button registerbutton = findViewById(R.id.button2);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate_registration()){
                    openActivity(MainActivity.class);
                }
                else{
                    System.out.println("onClick error");
                }
            }
        });
    }

    private boolean validate(TextView text, EditText input, Pattern pattern, String id){
        Matcher mat = pattern.matcher(input.getText().toString());
        if(mat.matches()){
            text.setTextColor(getResources().getColor(R.color.black));
            return true;
        }
        else{
            if(id == "email") System.out.println("Not a valid email address");
            else if (id == "userName") System.out.println("Not a valid name");
            else if (id == "userPassword") System.out.println("Not a valid password");
            else if (id == "userPhone") System.out.println("Not a valid phone number");
            else if (id == "userDate") System.out.println("Not a valid date");
            else if (id == "userStreet") System.out.println("Not a valid street address");
            else if (id == "userZip") System.out.println("Not a valid zip code");
            else if (id == "userCity") System.out.println("Not a valid city");
            else if (id == "userCounty") System.out.println("Not a valid county");
            text.setTextColor(getResources().getColor(R.color.red));
            return false;
        }
    }

    public boolean validate_repeatpassword(TextView text, EditText password, EditText repeatPassword){

        if(password.getText().toString().equals(repeatPassword.getText().toString())){
            text.setTextColor(getResources().getColor(R.color.black));
            return true;
        }
        System.out.println("password did not match");
        text.setTextColor(getResources().getColor(R.color.red));
        return false;
    }


    public boolean validate_age(TextView text, EditText date, Pattern pattern){
        int birthDate = Integer.parseInt(String.valueOf(date.getText()));
        Matcher mat = pattern.matcher(date.getText().toString());
        Date todaysDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
        int intTodaysDate = Integer.parseInt(String.valueOf(sdf.format(todaysDate)));
        if(mat.matches() && (intTodaysDate-birthDate)>=AGE_REQUIRMENT){

            text.setTextColor(getResources().getColor(R.color.black));
            return true;
        }
        else{
            System.out.println("not old enough");
            text.setTextColor(getResources().getColor(R.color.red));
            return false;
        }

    }

    public boolean validate_registration(){
        TextView name = findViewById(R.id.textView2);
        TextView email = findViewById(R.id.textView3);
        TextView password = findViewById(R.id.textView4);
        TextView repeatPassword = findViewById(R.id.textView6);
        TextView phone = findViewById(R.id.textView8);
        TextView date = findViewById(R.id.textView9);
        TextView street = findViewById(R.id.textView10);
        TextView zip = findViewById(R.id.textView7);
        TextView city = findViewById(R.id.textView11);
        TextView county = findViewById(R.id.textView12);

        EditText userName = findViewById(R.id.editTextTextPersonName3);
        EditText userEmail = findViewById(R.id.editTextTextEmailAddress3);
        EditText userPassword = findViewById(R.id.editTextTextPassword);
        EditText userRepeatPassword = findViewById(R.id.editTextTextPassword2);
        EditText userPhone = findViewById(R.id.editTextPhone);
        EditText userDate = findViewById(R.id.editTextDate);
        EditText userStreet = findViewById(R.id.editTextTextPostalAddress);
        EditText userZip = findViewById(R.id.editTextNumber);
        EditText userCity = findViewById(R.id.editTextTextPersonName);
        EditText userCounty = findViewById(R.id.editTextTextPersonName2);

        Pattern namePattern = Pattern.compile("[A-Za-z_ ]{1,40}");
        Pattern emailPattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Pattern passwordPattern = Pattern.compile("[\\x00-\\x7F]{5,20}"); //all US-ascii 5-20 characters
        Pattern phonePattern = Pattern.compile("[0-9]{8,12}");
        Pattern datePattern = Pattern.compile("[0-9]{8}");
        Pattern streetPattern = Pattern.compile("[A-Za-z0-9_ ]{5,40}");
        Pattern zipPattern = Pattern.compile("[0-9]{5}");
        Pattern cityPattern = Pattern.compile("[A-Za-z]{5,40}");
        Pattern countyPattern = Pattern.compile("[A-Za-z_ ]{1,30}");


        if(validate(name, userName, namePattern, "userName") &&
                validate(email, userEmail, emailPattern, "email") &&
                validate(password, userPassword, passwordPattern, "UserPassword") &&
                validate_repeatpassword(repeatPassword,userPassword,userRepeatPassword) &&
                validate(phone, userPhone, phonePattern, "userPhone") &&
                validate_age(date, userDate, datePattern) &&
                validate(street, userStreet, streetPattern, "userStreet") &&
                validate(zip, userZip, zipPattern, "userZip") &&
                validate(city, userCity, cityPattern, "userCity") &&
                validate(county, userCounty, countyPattern, "userCounty")

        ){
            openActivity(MainActivity.class);
            return true;
        }
        else{
            System.out.println("validate error");
            return false;
        }
    }

    public void openActivity(Class _act){
        Intent intent = new Intent(this, _act);
        startActivity(intent);
    }
}