package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Helpers.AdminVaccineHelper;
import com.example.myapplication.Helpers.CenterVaccineHelper;
import com.example.myapplication.UI.AdminBooking.AdminAddCenterFragment;
import com.example.myapplication.UI.AdminBooking.AdminAddVaccineFragment;
import com.example.myapplication.UI.AdminBooking.AdminBookingRangeFragment;
import com.example.myapplication.UI.AdminBooking.AdminQuestionnaireFragment;
import com.example.myapplication.UI.AdminBooking.DashboardGeneric_Admin;
import com.example.myapplication.UI.Covid_tracking.Covid_Tracking_tabs_fragment;
import com.example.myapplication.UI.LoadingAnimation;
import com.example.myapplication.UI.Settings.SettingsFragment;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.UI.AdminBooking.ValidateAppointmentsFragment;
import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private View header;
    private Button logoutButton;
    private TextView userName;
    private TextView userEmail;
    private DashboardGeneric_Admin dashFragment;
    private UserResponse user;
    private CenterVaccineHelper centerVaccineHelper;
    private AdminVaccineHelper vaccineHelper;
    private long backPressedTime;

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 1){
            if(backPressedTime + 2000 > System.currentTimeMillis()){
                startActivity(new Intent(AdminActivity.this, MainActivity.class));
                finish();
            } else{
                Toast.makeText(getBaseContext(), "Press back again to exit to login screen", Toast.LENGTH_SHORT).show();
            }
            backPressedTime = System.currentTimeMillis();
        } else{
            super.onBackPressed();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //gets userinfo
        Intent i = getIntent();
        user = (UserResponse) i.getSerializableExtra("userInfo");

        if(savedInstanceState == null) {
            dashFragment = new DashboardGeneric_Admin();
            getSupportFragmentManager().beginTransaction().add(R.id.frameAdmin, dashFragment).commit();
        }

        getCentersAndVaccine();

        //Setup Menu Bar
        setupMenuBar();

        //Setup Navigation Drawer
        setUpNavigationView();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getCentersAndVaccine() {
        Runnable next2 = new Runnable() {
            @Override
            public void run() {
                LoadingAnimation.dismissLoadingAnimation();
            }
        };
        Runnable next1 = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                callVaccine(next2);
            }
        };

        LoadingAnimation.startLoadingAnimation(this);
        callCenter(next1);
    }

    public CenterVaccineHelper getCenterVaccineHelper() {
        return centerVaccineHelper;
    }

    public AdminVaccineHelper getVaccineHelper() {
        return vaccineHelper;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void callCenter(Runnable runnable){
        centerVaccineHelper = new CenterVaccineHelper(getSupportFragmentManager().findFragmentById(R.id.frameAdmin));
        centerVaccineHelper.API_getCenters(this, user, runnable);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void callVaccine(Runnable runnable){
        vaccineHelper = new AdminVaccineHelper(getSupportFragmentManager().findFragmentById(R.id.frameAdmin));
        vaccineHelper.API_getVaccine(this, user, runnable);
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
                startActivity(new Intent(AdminActivity.this, MainActivity.class));

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
                        newFragment = new DashboardGeneric_Admin();
                        break;
                    case R.id.nav_validate:
                        newFragment = new ValidateAppointmentsFragment();
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