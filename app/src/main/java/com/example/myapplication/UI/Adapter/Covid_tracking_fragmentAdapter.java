package com.example.myapplication.UI.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.myapplication.UI.Covid_tracking.Covid_Tracking_dashboardFragment;
import com.example.myapplication.UI.UserAppointment.Appointment_makeCancel;

public class Covid_tracking_fragmentAdapter extends FragmentStateAdapter {
    public Covid_tracking_fragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new Covid_Tracking_dashboardFragment();
            case 2:
                return new Appointment_makeCancel();
        }
        return new Covid_Tracking_dashboardFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
