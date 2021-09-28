package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private TextView registerButton;
    private EditText userEmail;
    private EditText userPassword;
    private String loginToken;
    private ImageButton languageButton1;
    private TextView languageButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupButtons();

    }

    private void setupButtons(){
        //Find buttons
        loginButton = findViewById(R.id.button1);
        registerButton = findViewById(R.id.textView5);
        languageButton1 = findViewById(R.id.imageButton);
        languageButton2 = findViewById(R.id.textView16);

        //Find Edit Text for user email and password
        userEmail = findViewById(R.id.editTextTextEmailAddress3);
        userPassword = findViewById(R.id.editTextTextPassword2);

        //Setup OnClickListeners
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
        languageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchLanguage();
            }
        });
        languageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchLanguage();
            }
        });
    }

    //Switches language depending on current language
    private void switchLanguage(){
        if("(EN)".equals(languageButton2.getText().toString())){
            LanguageHelper.setLocale(this,"en");
        }
        else{
            LanguageHelper.setLocale(this,"sv");
        }
        //updates view
        this.recreate();
    }

    public void login(){

        Intent i = new Intent(MainActivity.this, GeneralActivity.class);
        startActivity(i);

//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUserEmail(userEmail.getText().toString());
//        loginRequest.setPassword(userPassword.getText().toString());
//
//        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
//        loginResponseCall.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                //om response ej är 200 ha felhantering, isBusy
//                if(response.isSuccessful()){
//                    Toast.makeText(MainActivity.this,"Login Successful", Toast.LENGTH_LONG).show();
//                    LoginResponse loginResponse = response.body();
//                    loginToken = loginResponse.getToken();
//
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Intent i = new Intent(MainActivity.this, GeneralActivity.class);
//                            i.putExtra("tok", loginToken);
//                            startActivity(i);
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


    }

    public void openActivity(Class _act){
        Intent intent = new Intent(this, _act);
        startActivity(intent);
    }


}