package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class GeneralActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dashboardFragment dashFragment = new dashboardFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame, dashFragment).commit();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        setUpNavigationView();


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return super.onOptionsItemSelected(item);
        }
        return true;
    }


    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_dashboard:
                        dashboardFragment dashFragment = new dashboardFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, dashFragment).commit();
                        mDrawerLayout.closeDrawers();
                        //navItemIndex = 0;
                        //CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_covidpassport:
                        covidPassportFragment passFragment = new covidPassportFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, passFragment).commit();
                        mDrawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        });
    }

}