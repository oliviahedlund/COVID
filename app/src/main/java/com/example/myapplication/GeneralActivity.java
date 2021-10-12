package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
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
    //private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //gets userinfo
        Intent i = getIntent();
        user = (UserResponse) i.getSerializableExtra("userInfo");
        //token = (String) i.getStringExtra("token");

        if(savedInstanceState == null) {
            dashFragment = new Covid_Tracking_dashboardFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.frame, dashFragment).commit();
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
   /* private String setUserToken(String response){
        return this.token = response;
    }

    public String getUserToken(){
        return token;
    }*/

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

        userName = header.findViewById(R.id.fullName);
        userEmail = header.findViewById(R.id.textViewEmail);
        userName.setText(user.getFirstName() + " " + user.getLastName());
        userEmail.setText(user.getEmail());

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
                Fragment newFragment;

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    case R.id.nav_dashboard:
                        newFragment = new Covid_Tracking_dashboardFragment();
                        break;
                    case R.id.booking:
                        newFragment = new Appointment_fragment();
                        break;
                    case R.id.nav_covidpassport:
                        newFragment = new covidPassportFragment();
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
}