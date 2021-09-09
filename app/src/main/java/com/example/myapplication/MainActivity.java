package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Edit Text for user email and password
        EditText userEmail = findViewById(R.id.editTextTextEmailAddress3);
        EditText userPassword = findViewById(R.id.editTextTextPassword2);

        //Buttons for login and register
        Button loginbutton = findViewById(R.id.button1);
        TextView registerbutton = findViewById(R.id.textView5);


        //open new activity when clicked
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(userEmail.getText().toString(), userPassword.getText().toString());
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
            openActivity(GeneralActivity.class);


        }
        else{
            TextView incorrectLogin = findViewById(R.id.errorText1);
            incorrectLogin.setText(R.string.text17);
        }

    }

    public void openActivity(Class _act){
        Intent intent = new Intent(this, _act);
        startActivity(intent);
    }


}