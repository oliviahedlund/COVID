package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends MenuActivity {

    //toolbar menu
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myToolbar = findViewById(R.id.myToolbar); //toolbar menu
        setSupportActionBar(myToolbar); //toolbar menu



        EditText userEmail = findViewById(R.id.editTextTextEmailAddress3);

        Button registerbutton = findViewById(R.id.button2);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate_registration(userEmail.getText().toString());
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
}