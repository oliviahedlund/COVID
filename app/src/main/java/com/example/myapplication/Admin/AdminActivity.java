package com.example.myapplication.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Covid_Tracking_dashboardFragment;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.SettingsFragment;
import com.example.myapplication.UserResponse;
import com.example.myapplication.covidPassportFragment;
import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private View header;
    private Button logoutButton;
    private TextView userName;
    private TextView userEmail;
    private Covid_Tracking_dashboardFragment dashFragment;
    private UserResponse user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //gets userinfo
        Intent i = getIntent();
        user = (UserResponse) i.getSerializableExtra("userInfo");

        if(savedInstanceState == null) {
            dashFragment = new Covid_Tracking_dashboardFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.frameAdmin, dashFragment).commit();
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
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutAdmin);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUpNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_viewAdmin);
        header = navigationView.getHeaderView(0);
        logoutButton = header.findViewById(R.id.logoutButton);

        //clear username
        userName = header.findViewById(R.id.fullName);
        userEmail = header.findViewById(R.id.textViewEmail);
        userName.setText("");
        userEmail.setText(user.getEmail());

        //Logout Button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(intent);
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
                        newFragment = new Covid_Tracking_dashboardFragment();
                        break;
                        /*
                    case R.id.nav_quest:
                        newFragment = new AdminQuestionnaireFragment();
                        break;*/
                    case R.id.nav_settingsFragment:
                        newFragment = new SettingsFragment();
                        break;
                    case R.id.nav_sched:
                        newFragment = new AdminBookingRangeFragment();
                        break;
                    case R.id.nav_addCenter:
                        newFragment = new AdminAddCenterFragment();
                        break;
                    case R.id.nav_addVaccin:
                        newFragment = new AdminAddVaccineFragment();
                        break;
                    default:
                        newFragment = getSupportFragmentManager().findFragmentById(R.id.frameAdmin);
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, newFragment).commit();
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }
}