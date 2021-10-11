package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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


public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private TextView registerButton;
    private EditText userEmail;
    private EditText userPassword;
    private String loginToken;
    private ImageButton languageButton1;
    private TextView languageButton2;
    private UserApiHelper userApiHelper;

    private UserResponse userResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check default language before creating view
        checkDefaultLanguage();
        setContentView(R.layout.activity_main);
        userApiHelper = new UserApiHelper(this);

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
                if(userApiHelper.callSuccessful()){
                    startUserActivity();
                }
                else{
                    System.out.println("Hantera fel");
                }
                LoadingAnimation.dismissLoadingAnimation();
            }
        };

        userApiHelper.CallLoginApi(this, loginRequest, next);
    }

    public void startUserActivity(){
        userResponse = userApiHelper.getUserResponse();
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
                else{
                    login();
                }
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