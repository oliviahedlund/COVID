package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private TextView registerButton;
    private EditText userEmail;
    private EditText userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setupButtons();

    }

    private void setupButtons(){
        loginButton = findViewById(R.id.button1);
        registerButton = findViewById(R.id.textView5);

        //Edit Text for user email and password
        userEmail = findViewById(R.id.editTextTextEmailAddress3);
        userPassword = findViewById(R.id.editTextTextPassword2);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userEmail.getText().toString().isEmpty() || userPassword.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Username / Password Required", Toast.LENGTH_LONG).show();
                }
                else{    login(); }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(RegisterActivity.class);
            }
        });
    }

    /*public void validate(String userEmail, String userPassword){
        if(userEmail.getText().toString().isEmpty() || userPassword.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Username / Password Required", Toast.LENGTH_LONG).show();
        }
        else{    login(); }

        }

    }*/

    public void login(){
        openActivity(GeneralActivity.class);
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUserEmail(userEmail.getText().toString());
//        loginRequest.setPassword(userPassword.getText().toString());
//
//        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
//        loginResponseCall.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//
//                if(response.isSuccessful()){
//                    Toast.makeText(MainActivity.this,"Login Successful", Toast.LENGTH_LONG).show();
//                    LoginResponse loginResponse = response.body();
//
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            openActivity(GeneralActivity.class);
//
//                        }
//                    },700);
//
//                }else{
//                    Toast.makeText(MainActivity.this,"Login Failed", Toast.LENGTH_LONG).show();
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                Toast.makeText(MainActivity.this,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//
//            }
//        });
//

    }

    public void openActivity(Class _act){
        Intent intent = new Intent(this, _act);
        startActivity(intent);
    }


}