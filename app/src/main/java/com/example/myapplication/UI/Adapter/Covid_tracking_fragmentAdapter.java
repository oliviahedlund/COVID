package com.example.myapplication.UI.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.myapplication.UI.Covid_tracking.Covid_Tracking_tab_cases;
import com.example.myapplication.UI.Covid_tracking.Covid_Tracking_tab_vaccinationStats;
import com.example.myapplication.UI.Covid_tracking.Covid_Tracking_tab_vaccinationStats_cumulative;
import com.example.myapplication.UI.Covid_tracking.Covid_Tracking_tab_vaxStock;
import com.example.myapplication.UI.UserAppointment.Appointment_makeCancel;

public class Covid_tracking_fragmentAdapter extends FragmentStateAdapter {

    public Covid_tracking_fragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position){
            case 1:
                fragment = new Covid_Tracking_tab_vaxStock();
                break;
            case 2:
                fragment = new Covid_Tracking_tab_vaccinationStats();
                break;
            case 3:
                fragment = new Covid_Tracking_tab_vaccinationStats_cumulative();
                break;
            default: fragment = new Covid_Tracking_tab_cases();
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
