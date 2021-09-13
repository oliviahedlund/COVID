package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    final int AGE_REQUIREMENT = 18000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Button registerbutton = findViewById(R.id.button2);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate_registration()) openActivity(MainActivity.class);
            }
        });
    }

    private boolean validate(TextView text, EditText input, Pattern pattern, String id){
        Matcher mat = pattern.matcher(input.getText().toString());
        System.out.println("Input: " + input);
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

    private boolean repeat_password(String repeatPassword, String password, TextView text){
        if(repeatPassword.equals(password)){
            text.setTextColor(getResources().getColor(R.color.black));
            return true;
        }
        else{
            System.out.println("Passwords do not match");
            text.setTextColor(getResources().getColor(R.color.red));
            return false;
        }
    }

    private boolean validate_date(){
        if(true){
            System.out.println(System.currentTimeMillis());
            return true;
        }
        else{
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

        Pattern namePattern = Pattern.compile("[A-Za-z_ ]{1,30}");
        Pattern emailPattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Pattern passwordPattern = Pattern.compile("[\\x00-\\x7F]{8,20}"); //all ascii - 8-20 symbols - stor bokstav & siffra
        Pattern phonePattern = Pattern.compile("[0-9]{10,11}");
        Pattern datePattern = Pattern.compile("[0-9]{8}");
        Pattern streetPattern = Pattern.compile("[A-Za-z0-9_ ]{4,30}");
        Pattern zipPattern = Pattern.compile("[0-9]{5}");
        Pattern cityCountyPattern = Pattern.compile("[A-Za-z]{2,15}");

        if(validate(name, userName, namePattern, "userName") &&
                validate(email, userEmail, emailPattern, "email") &&
                validate(password, userPassword, passwordPattern, "userPassword") &&
                repeat_password(userRepeatPassword.getText().toString(), userPassword.getText().toString(), repeatPassword) &&
                validate(phone, userPhone, phonePattern, "userPhone") &&
                validate(date, userDate, datePattern, "userDate") &&
                validate(street, userStreet, streetPattern, "userStreet") &&
                validate(zip, userZip, zipPattern, "userZip") &&
                validate(city, userCity, cityCountyPattern, "userCity") &&
                validate(county, userCounty, cityCountyPattern, "userCounty")

        ){
            openActivity(MainActivity.class);
            return true;
        }
        return false;
    }

    public void openActivity(Class _act){
        Intent intent = new Intent(this, _act);
        startActivity(intent);
    }
}