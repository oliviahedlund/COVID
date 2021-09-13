package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends MenuActivity {

    //toolbar menu
    Toolbar myToolbar;
    MainActivity mainact = new MainActivity();
    //Edit Text for user email and password
    EditText userEmail;
    EditText userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = findViewById(R.id.editTextTextEmailAddress3);
        userPassword = findViewById(R.id.editTextTextPassword2);

        myToolbar = findViewById(R.id.myToolbar); //toolbar menu
        setSupportActionBar(myToolbar); //toolbar menu


        //Buttons for login and register
        Button loginbutton = findViewById(R.id.button1);
        TextView registerbutton = findViewById(R.id.textView5);


        //open new activity when clicked
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // validate(userEmail.getText().toString(), userPassword.getText().toString());
                if(userEmail.getText().toString().isEmpty() || userPassword.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Username / Password Required", Toast.LENGTH_LONG).show();
                }
                else{    login(); }
            }
        });
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(RegisterActivity.class);
            }
        });
    }

    public void validate(String userEmail, String userPassword){
        if((userEmail.equals("test@gmail.com")) && (userPassword.equals("123"))){
            openActivity(DashActivity.class);
        }
        else{
            TextView incorrectLogin = findViewById(R.id.textView7);
            incorrectLogin.setText("Incorrect email or password");
        }

    }

    public void openActivity(Class _act){
        Intent intent = new Intent(this, _act);
        startActivity(intent);
    }

    public void login(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(userEmail.getText().toString());
        loginRequest.setPassword(userPassword.getText().toString());

        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if(response.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Login Successful", Toast.LENGTH_LONG).show();
                    LoginResponse loginResponse = response.body();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            startActivity(new Intent(LoginActivity.this,DashActivity.class).putExtra("data",loginResponse.getUsername()));
                        }
                    },700);

                }else{
                    Toast.makeText(LoginActivity.this,"Login Failed", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }

}