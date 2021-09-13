package com.example.myapplication;

import androidx.appcompat.widget.Toolbar;

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

public class RegisterActivity extends MenuActivity {

    //toolbar menu
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myToolbar = findViewById(R.id.myToolbar); //toolbar menu
        setSupportActionBar(myToolbar); //toolbar menu

        Button registerbutton = findViewById(R.id.button2);
        EditText username = findViewById(R.id.editTextTextPersonName3);
        EditText email = findViewById(R.id.editTextTextEmailAddress3);
        EditText password = findViewById(R.id.editTextTextPassword2);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterRequest registerRequest = new RegisterRequest();
                registerRequest.setUsername(username.getText().toString());
                registerRequest.setEmail(email.getText().toString());
                registerRequest.setPassword(password.getText().toString());
                registerRequest.setAddress("adress1test");
                registerRequest.setAddress2("adress2test");
                registerRequest.setBirthDate("1998-08-25");
                registerRequest.setCity("kdtest");
                registerRequest.setDistrict("kdtestdistrict1");
                registerRequest.setFirstName("namn1");
                registerRequest.setLastName("namn2");
                registerRequest.setPhoneNumber("07255555");
                registerRequest.setPostalCode("12345");
                registerUser(registerRequest);
            }
        });

    }

    public void validate_registration(String userEmail){
        TextView email = findViewById(R.id.textView3);

        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(userEmail);
        if(mat.matches()){
            System.out.println("Valid email address");
            openActivity(MainActivity.class);
        }else{
            System.out.println("Not a valid email address");
            email.setTextColor(Color.parseColor("#ff0000"));
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