package com.example.myapplication;


import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {

    //EditText userName = findViewById(R.id.editTextTextPersonName3);
    EditText userEmail = findViewById(R.id.editTextTextEmailAddress3);
    EditText userPassword = findViewById(R.id.editTextTextPassword);
    //Repeat password?
    EditText userPhone = findViewById(R.id.editTextPhone);
    EditText userDate = findViewById(R.id.editTextDate);
    EditText userStreet = findViewById(R.id.editTextTextPostalAddress);
    EditText userZip = findViewById(R.id.editTextNumber);
    EditText userCity = findViewById(R.id.editTextTextPersonName);
    EditText userCounty = findViewById(R.id.editTextTextPersonName2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText userEmail = findViewById(R.id.editTextTextEmailAddress3);

        Button registerbutton = findViewById(R.id.button2);

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate_registration()) {
                    RegisterRequest registerRequest = new RegisterRequest();
                    //registerRequest.setUsername(username.getText().toString());
                    registerRequest.setEmail(userEmail.getText().toString());
                    registerRequest.setPassword(userPassword.getText().toString());
                    registerRequest.setAddress(userStreet.getText().toString());
                    //registerRequest.setAddress2("adress2test");
                    registerRequest.setBirthDate(userDate.getText().toString());
                    registerRequest.setCity(userCity.getText().toString());
                    registerRequest.setDistrict(userCounty.getText().toString());
                    registerRequest.setFirstName("namn1");
                    registerRequest.setLastName("namn2");
                    registerRequest.setPhoneNumber(userPhone.getText().toString());
                    registerRequest.setPostalCode(userZip.getText().toString());
                    registerUser(registerRequest);

                }

            }
        });

    }

    /*public void validate_registration(String userEmail){
        TextView email = findViewById(R.id.textView3);

        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(userEmail);
        if(mat.matches()){
            System.out.println("Valid email address");
            openActivity(MainActivity.class);

        }else{
            System.out.println("Not a valid email address");
            email.setTextColor(Color.parseColor("#ff0000"));

            finish(); //clears register activity from history
        }

    }*/

    private boolean validate(TextView text, EditText input, Pattern pattern, String id){
        Matcher mat = pattern.matcher(input.getText().toString());
        if(mat.matches()){
            text.setTextColor(getResources().getColor(R.color.black));
            return true;
        }
        else{
            if(id.equals("email")) System.out.println("Not a valid email address");
            else if (id.equals("userName")) System.out.println("Not a valid name");
            else if (id.equals("userPassword")) System.out.println("Not a valid password");
            else if (id.equals("userPhone")) System.out.println("Not a valid phone number");
            else if (id.equals("userDate")) System.out.println("Not a valid date");
            else if (id.equals("userStreet")) System.out.println("Not a valid street address");
            else if (id.equals("userZip")) System.out.println("Not a valid zip code");
            else if (id.equals("userCity")) System.out.println("Not a valid city");
            else if (id.equals("userCounty")) System.out.println("Not a valid county");
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

        Pattern namePattern = Pattern.compile("[A-Za-z]{1,40}");
        Pattern emailPattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Pattern passwordPattern = Pattern.compile("[\\x00-\\x7F]{5,20}"); //all ascii
        Pattern phonePattern = Pattern.compile("[0-9]*");
        Pattern datePattern = Pattern.compile("[0-9]*");
        Pattern streetPattern = Pattern.compile("[A-Za-z0-9]*");
        Pattern zipPattern = Pattern.compile("[0-9]*");
        Pattern cityPattern = Pattern.compile("[A-Za-z]*");
        Pattern countyPattern = Pattern.compile("[A-Za-z]*");


        if(
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

    public void registerUser(RegisterRequest registerRequest){
        Call<RegisterResponse> registerResponseCall = ApiClient.getUserService().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful()){
                    String message = "succesful registration";
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                    openActivity(MainActivity.class);
                }
                else{
                    String message = "unable to register";
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}