package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
            email.setTextColor(getResources().getColor(R.color.red));
        }

    }
    public void openActivity(Class _act){
        Intent intent = new Intent(this, _act);
        startActivity(intent);
    }
}