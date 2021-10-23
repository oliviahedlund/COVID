package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.UI.Covid_Passport.CovidPassportFragment;
import com.example.myapplication.UI.Covid_tracking.Covid_Tracking_dashboardFragment;
import com.example.myapplication.UI.Covid_tracking.Covid_Tracking_tabs_fragment;
import com.example.myapplication.UI.FAQ;
import com.example.myapplication.UI.SettingsFragment;
import com.example.myapplication.UI.UserAppointment.Appointment_Info;
import com.example.myapplication.UI.User_profile.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

public class GeneralActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private View header;
    private Button logoutButton;
    private TextView userName;
    private TextView userEmail;
    private UserResponse user;

    private Covid_Tracking_tabs_fragment dashFragment;
    //private String token;

    private long backPressedTime;

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 1){
            if(backPressedTime + 2000 > System.currentTimeMillis()){
                startActivity(new Intent(GeneralActivity.this, MainActivity.class));
                finish();
            } else{
                Toast.makeText(getBaseContext(), "Press back again to exit to login screen", Toast.LENGTH_SHORT).show();
            }
            backPressedTime = System.currentTimeMillis();
        } else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //gets userinfo
        Intent i = getIntent();
        user = (UserResponse) i.getSerializableExtra("userInfo");
        //token = (String) i.getStringExtra("token");

        if(savedInstanceState == null) {
            dashFragment = new Covid_Tracking_tabs_fragment();
            getSupportFragmentManager().beginTransaction().add(R.id.frame, dashFragment).addToBackStack(null).commit();
        }

        //Setup Menu Bar
        setupMenuBar();

        //Setup Navigation Drawer
        setUpNavigationView();


    }
    private UserResponse setUserData(UserResponse response){
        return this.user = response;
    }

    public UserResponse getUserData(){
        return user;
    }


    //opens drawer menu when icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            //hide keyboard if it is visible when menu is clicked
            try  {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {

            }
            return super.onOptionsItemSelected(item);
        }
        return true;
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

        userName = header.findViewById(R.id.fullName);
        userEmail = header.findViewById(R.id.textViewEmail);
        userName.setText(user.getFirstName() + " " + user.getLastName());
        userEmail.setText(user.getEmail());

        //Logout Button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GeneralActivity.this, MainActivity.class));
                finish();
            }
        });

        //Menu Options
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // When Menu Item Is Clicked
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Fragment newFragment;

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    case R.id.nav_dashboard:
                        newFragment = new Covid_Tracking_tabs_fragment();
                        break;
                    case R.id.booking:
                        newFragment = new Appointment_Info(); break;

                    case R.id.nav_covidpassport:
                        newFragment = new CovidPassportFragment();
                        break;
                    case R.id.nav_settingsFragment:
                        newFragment = new SettingsFragment();
                        break;
                    case R.id.nav_profile:
                        //getUserData(); om man anropar denna funktion kan man skicka med det som returneras in i nästa vy med hjälp av intent.putextra så som i main activity
                        newFragment = new ProfileFragment();
                        break;
                    case R.id.nav_FAQ:
                        newFragment = new FAQ();
                        break;
                    default:
                        newFragment = getSupportFragmentManager().findFragmentById(R.id.frame);
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frame, newFragment).commit();
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public Activity getGeneralActivity(){
        return this;
    }
}