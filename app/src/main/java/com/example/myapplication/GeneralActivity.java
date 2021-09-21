package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeneralActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private View header;
    private Button logoutButton;
    private TextView userName;
    private TextView userEmail;
    private UserResponse user;
    private Covid_Tracking_dashboardFragment dashFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if(savedInstanceState == null) {
            dashFragment = new Covid_Tracking_dashboardFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.frame, dashFragment).commit();
        }

        //Setup Menu Bar
        setupMenuBar();

        //Setup Navigation Drawer
        setUpNavigationView();

        //anropar detta för att få fram information om användaren
        callUserApi();

    }

    public void callUserApi(){
        UserRequest userRequest = new UserRequest();
        Intent i = getIntent();
        String token = i.getStringExtra("tok");
        token = "Bearer " + token;
        userRequest.setToken(token);

        Call<UserResponse> userResponseCall = ApiClient.getUserService().getUser(token);
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                //todo - errorhandling
                if (response.isSuccessful()) {
                    Toast.makeText(GeneralActivity.this, "ok, got user", Toast.LENGTH_LONG).show();
                    UserResponse userResponse = response.body(); //i userResponse ligger all information om användaren
                    System.out.println(userResponse);
                    setUserData(userResponse);
                    System.out.println(userResponse.getEmail());
                    userName = findViewById(R.id.fullName);
                    userEmail = findViewById(R.id.textViewEmail);
                    userName.setText(userResponse.getFirstName() + " " + userResponse.getLastName());
                    userEmail.setText(userResponse.getEmail());

                }else{
                    Toast.makeText(GeneralActivity.this,"user Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(GeneralActivity.this,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    private UserResponse setUserData(UserResponse response){
        return this.user = response;
    }

    private UserResponse getUserData(){
        return user;
    }

    //opens drawer menu when icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void openActivity(Class _act){
        Intent intent = new Intent(this, _act);
        startActivity(intent);
    }

    private void setupMenuBar(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void setUpNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        header = navigationView.getHeaderView(0);
        logoutButton = header.findViewById(R.id.logoutButton);

        //Logout Button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(MainActivity.class);
                finish();
            }
        });

        //Menu Options
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // When Menu Item Is Clicked
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    case R.id.nav_dashboard:
                        Covid_Tracking_dashboardFragment dashFragment = new Covid_Tracking_dashboardFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, dashFragment).commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_covidpassport:
                        covidPassportFragment passFragment = new covidPassportFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, passFragment).commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_settingsFragment:
                        SettingsFragment settFragment = new SettingsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, settFragment).commit();
                        mDrawerLayout.closeDrawers();
                        break;

                    case R.id.nav_profile:
                        //getUserData(); om man anropar denna funktion kan man skicka med det som returneras in i nästa vy med hjälp av intent.putextra så som i main activity
                        ProfileFragment profileFragment = new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, profileFragment).commit();
                        mDrawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        });
    }
}