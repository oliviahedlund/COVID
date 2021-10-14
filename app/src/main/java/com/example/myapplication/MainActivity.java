package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;  //will be excluded
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.API.Model.Login.LoginRequest;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.Helpers.UserLoginHelper;
import com.example.myapplication.Helpers.LanguageHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private TextView registerButton;
    private EditText userEmail;
    private EditText userPassword;
    private String loginToken;
    private ImageButton languageButton1;
    private TextView languageButton2;
    private UserLoginHelper userLoginHelper;
    private TextView errorText;

    private UserResponse userResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check default language before creating view
        checkDefaultLanguage();
        setContentView(R.layout.activity_main);
        userLoginHelper = new UserLoginHelper(this);

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
                System.out.println("USER ID: "+ userResponse.getId());
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

    public void login(){
        LoadingAnimation.startLoadingAnimation(MainActivity.this);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail(userEmail.getText().toString());
        loginRequest.setPassword(userPassword.getText().toString());

        Runnable next = new Runnable() {
            @Override
            public void run() {
                if(userLoginHelper.callSuccessful()){
                    startUserActivity();
                }
                else{
                    errorText.setText("wrong email or parrsord");
                    System.out.println("Hantera fel");
                }
                LoadingAnimation.dismissLoadingAnimation();
            }
        };

        userLoginHelper.CallLoginApi(this, loginRequest, next);
    }

    public void startUserActivity(){
        userResponse = userLoginHelper.getUserResponse();
        Intent i;

        if (userResponse.getAdmin()) {
            i = new Intent(MainActivity.this, AdminActivity.class);
        } else {
            i = new Intent(MainActivity.this, GeneralActivity.class);
        }

        i.putExtra("userInfo", userResponse);
        startActivity(i);
        finish(); //clears page from history
    }

    private void setupButtons(){
        //Find buttons
        loginButton = findViewById(R.id.button1);
        registerButton = findViewById(R.id.textView5);
        languageButton1 = findViewById(R.id.imageButton);
        languageButton2 = findViewById(R.id.textView16);
        errorText = findViewById(R.id.errorText1);

        //Find Edit Text for user email and password
        userEmail = findViewById(R.id.editTextTextEmailAddress3);
        userPassword = findViewById(R.id.editTextTextPassword2);

        //Setup OnClickListeners
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initialize patterns for email and password
                Pattern emailPattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
                Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$"); //one digit+lower+upper
                //initialize matchers for the patterns for email and password
                Matcher mat_email = emailPattern.matcher(userEmail.getText().toString());
                Matcher mat_passw = passwordPattern.matcher(userPassword.getText().toString());
                //check if email and password matches required patterns
                if(mat_email.matches()) {
                    //Toast.makeText(MainActivity.this, "Username / Password Required", Toast.LENGTH_LONG).show();
                    if(mat_passw.matches()){
                        login();
                    }
                    else{ errorText.setText("Invalid password"); }
                }
                else{ errorText.setText("Invalid E-mail address"); }
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

}