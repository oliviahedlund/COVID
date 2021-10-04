package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Admin.AdminActivity;
//import com.example.myapplication.Booking.BookingRequest;
//import com.example.myapplication.Booking.BookingResponse;
import com.example.myapplication.Booking.DateTimeHelper;

import java.time.LocalTime;
import java.util.ArrayList;

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

    private UserResponse userResponse;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check default language before creating view
        checkDefaultLanguage();
        setContentView(R.layout.activity_main);

        setupButtons();

////////////// Dummy Login /////////////////////
        Button testB = findViewById(R.id.dummyLoginButton);
        CheckBox isAdmin = findViewById(R.id.checkBox);
        testB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                if(isAdmin.isChecked()){
                    i = new Intent(MainActivity.this, AdminActivity.class);
                }
                else{i = new Intent(MainActivity.this, GeneralActivity.class);}
                UserResponse dummyUserResponse = new UserResponse();
                dummyUserResponse.setEmail("dummy@test.com");
                dummyUserResponse.setFirstName("Dummy");
                dummyUserResponse.setLastName("Dumdum");
                dummyUserResponse.setPhoneNumber("0701234567");
                dummyUserResponse.setBirthDate("19990412");
                dummyUserResponse.setAddress("Dummystreet 12");
                dummyUserResponse.setCity("Dumtown");
                dummyUserResponse.setDistrict("Dummiton");
                dummyUserResponse.setPostalCode("77777");
                dummyUserResponse.setId("0");
                dummyUserResponse.setAdmin(false);
                String dummyToken = " ";
                i.putExtra("userInfo", dummyUserResponse);
                i.putExtra("token", loginToken);
                startActivity(i);
                finish(); //clears page from history
            }
        });
////////////// Dummy Login /////////////////////
////////////// Set Text Button /////////////////////
        Button setLoginText = findViewById(R.id.setLoginButton);
        setLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail = findViewById(R.id.editTextTextEmailAddress3);
                userPassword = findViewById(R.id.editTextTextPassword2);
                userEmail.setText("olivia@gmail.com");
                userPassword.setText("Citron123");
            }
        });
        Button setLoginAdmin = findViewById(R.id.setLoginButtonAdmin);
        setLoginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail = findViewById(R.id.editTextTextEmailAddress3);
                userPassword = findViewById(R.id.editTextTextPassword2);
                userEmail.setText("olivia@admin.com");
                userPassword.setText("Citron123");
            }
        });
////////////// Set Text Button /////////////////////
    }

    private void checkDefaultLanguage(){
        if("sv".equals(LanguageHelper.getLanguage(this.getBaseContext()))) {
            LanguageHelper.setLocale(this.getBaseContext(), "sv");
        }
        else{
            LanguageHelper.setLocale(this.getBaseContext(), "en");
        }
    }


    private void callUserApi(){
        UserRequest userRequest = new UserRequest();
        loginToken = "Bearer " + loginToken;
        userRequest.setToken(loginToken);

        Call<UserResponse> userResponseCall = ApiClient.getUserService().getUser(loginToken);
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                //errorhandling
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "ok, got user", Toast.LENGTH_LONG).show();
                    userResponse = response.body(); //i userResponse ligger all information om användaren
                    Intent i;
                    // replace if-statement with: userResponse.getAdmin()
                    if(userResponse.getAdmin()){
                        i = new Intent(MainActivity.this, AdminActivity.class);
                    }
                    else {
                        i = new Intent(MainActivity.this, GeneralActivity.class);
                    }
                    userResponse.setToken(loginToken); //////////////
                    i.putExtra("userInfo", userResponse);
                    //i.putExtra("token", loginToken);
                    startActivity(i);
                    finish(); //clears page from history

                }else{
                    Toast.makeText(MainActivity.this,"user Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });

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
                //openActivity(RegisterActivity.class);
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
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
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail(userEmail.getText().toString());
        loginRequest.setPassword(userPassword.getText().toString());

        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                //om response ej är 200 ha felhantering, isBusy
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Login Successful", Toast.LENGTH_LONG).show();
                    LoginResponse loginResponse = response.body();
                    loginToken = loginResponse.getToken();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            callUserApi();
                        }
                    },700);

                }else{
                    Toast.makeText(MainActivity.this,"Login Failed", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }
/*
    public void openActivity(Class _act){
        Intent intent = new Intent(this, _act);
        startActivity(intent);
    }
*/

}