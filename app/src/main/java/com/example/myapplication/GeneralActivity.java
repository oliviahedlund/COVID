package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class GeneralActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private View header;
    private Button logoutButton;

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

    }

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
                    case R.id.logoutButton:
                        openActivity(MainActivity.class);
                        finish();
                        break;
                }
                return true;
            }
        });
    }
}