package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.API.Model.Register.RegisterRequest;
import com.example.myapplication.API.Model.Register.RegisterResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    final int AGE_REQUIREMENT = 18000;

    EditText userFirstName;
    EditText userLastName;
    EditText userEmail;
    EditText userPassword;
    EditText userRepeatPassword;
    EditText userPhone;
    EditText userDate;
    EditText userStreet;
    EditText userZip;
    EditText userCity;
    EditText userCounty;

    private long backPressedTime;

    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            finish();
            return;
        } else{
            Toast.makeText(getBaseContext(), "Press back again to exit to login screen", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userFirstName = findViewById(R.id.editTextTextPersonName3);
        userLastName = findViewById(R.id.editTextTextPersonName4);
        userEmail = findViewById(R.id.editTextTextEmailAddress3);
        userPassword = findViewById(R.id.editTextTextPassword);
        userRepeatPassword = findViewById(R.id.editTextTextPassword2);
        userPhone = findViewById(R.id.editTextPhone);
        userDate = findViewById(R.id.editTextDate);
        userStreet = findViewById(R.id.editTextTextPostalAddress);
        userZip = findViewById(R.id.editTextNumber);
        userCity = findViewById(R.id.editTextTextPersonName);
        userCounty = findViewById(R.id.editTextTextPersonName2);


        Button registerbutton = findViewById(R.id.button2);

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate_registration()) {
                    RegisterRequest registerRequest = new RegisterRequest();
                    registerRequest.setEmail(userEmail.getText().toString());
                    registerRequest.setPassword(userPassword.getText().toString());
                    registerRequest.setAddress(userStreet.getText().toString());
                    //registerRequest.setAddress2("adress2test");
                    registerRequest.setBirthDate(changeDate(userDate.getText().toString()));
                    registerRequest.setCity(userCity.getText().toString());
                    registerRequest.setDistrict(userCounty.getText().toString());
                    registerRequest.setFirstName(userFirstName.getText().toString());
                    registerRequest.setLastName(userLastName.getText().toString());
                    registerRequest.setPhoneNumber(userPhone.getText().toString());
                    registerRequest.setPostalCode(userZip.getText().toString());
                    registerUser(registerRequest);

                }
               
                else{
                    System.out.println("onClick error");
                }

            }
        });
    }

    //changes date-string from YYYYMMDD to YYYY-MM-DD
    private String changeDate(String _date){
        String newString = new String();
        for(int i=0; i<8; i++){
            if(i==4||i==6){
                newString += "-";
            }
            newString += _date.charAt(i);
        }
        return newString;
    }

    private boolean validate(TextView text, EditText input, Pattern pattern, String id){
        Matcher mat = pattern.matcher(input.getText().toString());
        System.out.println("Input: " + input);
        if(mat.matches()){
            text.setTextColor(getResources().getColor(R.color.black));
            return true;
        }
        else{
            if(id.equals("email")) System.out.println("Not a valid email address");
            else if (id.equals("userFirstName")) System.out.println("Not a valid first name");
            else if (id.equals("userLastName")) System.out.println("Not a valid last name");
            else if (id.equals("userPassword")) System.out.println("Not a valid password");
            else if (id.equals("userPhone")) System.out.println("Not a valid phone number");
            else if (id.equals("userStreet")) System.out.println("Not a valid street address");
            else if (id.equals("userZip")) System.out.println("Not a valid zip code");
            else if (id.equals("userCity")) System.out.println("Not a valid city");
            else if (id.equals("userCounty")) System.out.println("Not a valid county");
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

    public boolean validate_date(TextView text, EditText input, Pattern pattern){
        int birthDate;
        try {
            birthDate = Integer.parseInt(input.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Not valid integer date-input");
            text.setTextColor(getResources().getColor(R.color.red));
            return false;
        }

        Matcher mat = pattern.matcher(input.getText().toString());
        Date todaysDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
        int intTodaysDate = Integer.parseInt(String.valueOf(sdf.format(todaysDate)));
        if(mat.matches() && (intTodaysDate-birthDate)>AGE_REQUIREMENT){
            text.setTextColor(getResources().getColor(R.color.black));
            return true;
        }
        else if(!mat.matches()){
            System.out.println("Not a valid date");
            text.setTextColor(getResources().getColor(R.color.red));
            return false;
        }
        else{
            System.out.println("User must be 18+ years old");
            text.setTextColor(getResources().getColor(R.color.red));
            return false;
        }

    }

    public boolean validate_registration(){
        TextView firstName = findViewById(R.id.textView2);
        TextView lastName = findViewById(R.id.textView13);
        TextView email = findViewById(R.id.textView3);
        TextView password = findViewById(R.id.textView4);
        TextView repeatPassword = findViewById(R.id.textView6);
        TextView phone = findViewById(R.id.textView8);
        TextView date = findViewById(R.id.textView9);
        TextView street = findViewById(R.id.textView10);
        TextView zip = findViewById(R.id.textView7);
        TextView city = findViewById(R.id.textView11);
        TextView county = findViewById(R.id.textView12);

        Pattern namePattern = Pattern.compile("[A-Za-z_ ]{1,30}");
        Pattern emailPattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$"); //one digit+lower+upper
        //Pattern passwordPattern = Pattern.compile("[\\x00-\\x7F]{8,20}"); //all ascii - 8-20 symbols - stor bokstav & siffra
        Pattern phonePattern = Pattern.compile("[0-9]{10,11}");
        Pattern datePattern = Pattern.compile("[0-9]{8}");
        Pattern streetPattern = Pattern.compile("[A-Za-z0-9_ ]{4,30}");
        Pattern zipPattern = Pattern.compile("[0-9]{5}");
        Pattern cityCountyPattern = Pattern.compile("[A-Za-z]{2,15}");

        if(
                validate(firstName, userFirstName, namePattern, "userFirstName") &&
                validate(lastName, userLastName, namePattern, "userLastName") &&
                validate(email, userEmail, emailPattern, "email") &&
                validate(password, userPassword, passwordPattern, "userPassword") &&
                repeat_password(userRepeatPassword.getText().toString(), userPassword.getText().toString(), repeatPassword) &&
                validate(phone, userPhone, phonePattern, "userPhone") &&
                validate_date(date, userDate, datePattern) &&
                validate(street, userStreet, streetPattern, "userStreet") &&
                validate(zip, userZip, zipPattern, "userZip") &&
                validate(city, userCity, cityCountyPattern, "userCity") &&
                validate(county, userCounty, cityCountyPattern, "userCounty")

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