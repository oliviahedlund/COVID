package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;

public class LoginActivity extends MenuActivity {

    //toolbar menu
    Toolbar myToolbar;
    MainActivity mainact = new MainActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myToolbar = findViewById(R.id.myToolbar); //toolbar menu
        setSupportActionBar(myToolbar); //toolbar menu

        //Buttons for login and register
        Button loginbutton = findViewById(R.id.button1);
        TextView registerbutton = findViewById(R.id.textView5);

        //open new activity when clicked
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(MainActivity.class);
            }
        });
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(RegisterActivity.class);
            }
        });
    }

    public void openActivity(Class _act){
        Intent intent = new Intent(this, _act);
        startActivity(intent);
    }

}