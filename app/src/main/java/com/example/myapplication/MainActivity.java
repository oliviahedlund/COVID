package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private TextView registerButton;
    private EditText userEmail;
    private EditText userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Edit Text for user email and password
        userEmail = findViewById(R.id.editTextTextEmailAddress3);
        userPassword = findViewById(R.id.editTextTextPassword2);

        setupButtons();

    }

    private void setupButtons(){
        loginButton = findViewById(R.id.button1);
        registerButton = findViewById(R.id.textView5);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(userEmail.getText().toString(), userPassword.getText().toString());
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(RegisterActivity.class);
            }
        });
    }

    public void validate(String userEmail, String userPassword){
        if((userEmail.equals("test@gmail.com")) && (userPassword.equals("123"))){
            openActivity(GeneralActivity.class);
            finish(); //clears login activity from history
        }
        else{
            TextView incorrectLogin = findViewById(R.id.errorText1);
            incorrectLogin.setText(R.string.IncorrectEmailPassw);
        }

    }

    public void openActivity(Class _act){
        Intent intent = new Intent(this, _act);
        startActivity(intent);
    }


}