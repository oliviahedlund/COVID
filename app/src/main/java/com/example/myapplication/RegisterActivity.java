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

public class RegisterActivity extends AppCompatActivity {

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
        Matcher mat = pattern.matcher(input.toString());
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

    public boolean validate_registration(){
        TextView name = findViewById(R.id.textView2);
        TextView email = findViewById(R.id.textView3);
        TextView password = findViewById(R.id.textView4);
        TextView phone = findViewById(R.id.textView8);
        TextView date = findViewById(R.id.textView9);
        TextView street = findViewById(R.id.textView10);
        TextView zip = findViewById(R.id.textView7);
        TextView city = findViewById(R.id.textView11);
        TextView county = findViewById(R.id.textView12);

        EditText userName = findViewById(R.id.editTextTextPersonName3);
        EditText userEmail = findViewById(R.id.editTextTextEmailAddress3);
        EditText userPassword = findViewById(R.id.editTextTextPassword);
        //Repeat password?
        EditText userPhone = findViewById(R.id.editTextPhone);
        EditText userDate = findViewById(R.id.editTextDate);
        EditText userStreet = findViewById(R.id.editTextTextPostalAddress);
        EditText userZip = findViewById(R.id.editTextNumber);
        EditText userCity = findViewById(R.id.editTextTextPersonName);
        EditText userCounty = findViewById(R.id.editTextTextPersonName2);

        Pattern namePattern = Pattern.compile/*("[A-Za-z_]*");*/("Jesper");
        Pattern emailPattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Pattern passwordPattern = Pattern.compile("[\\x00-\\x7F]{5,20}"); //all ascii - 5-20 symbols
        Pattern phonePattern = Pattern.compile("[+]{0,1}+[-][0,1]+[0-9]{9,12}");
        Pattern datePattern = Pattern.compile("[0-9]{6,8}");
        Pattern streetPattern = Pattern.compile /*("\s*[\p{L}][\p{L}_0-9]*\s");*/ ("[A-Za-z0-9_]{4,30}"); //åäö?
        Pattern zipPattern = Pattern.compile("[0-9]{5}");
        Pattern cityPattern = Pattern.compile("[A-Za-z]{2,15}");
        Pattern countyPattern = Pattern.compile("[A-Za-z]*"); //åäö?


        if(validate(name, userName, namePattern, "userName") &&
                validate(email, userEmail, emailPattern, "email") &&
                validate(password, userPassword, passwordPattern, "UserPassword") &&
                validate(phone, userPhone, phonePattern, "userPhone") &&
                validate(date, userDate, datePattern, "userDate") &&
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