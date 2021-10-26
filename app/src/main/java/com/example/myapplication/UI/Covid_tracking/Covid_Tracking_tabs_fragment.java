package com.example.myapplication.UI.Covid_tracking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback;

import com.example.myapplication.R;
import com.example.myapplication.UI.Adapter.Covid_tracking_fragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class Covid_Tracking_tabs_fragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private Covid_tracking_fragmentAdapter fragmentAdapter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.covid_tracking_tabs_fragment, container, false);

        tabLayout = view.findViewById(R.id.covidTrackingTabs);
        viewPager2 = view.findViewById(R.id.viewPager2_covid_tracking);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        fragmentAdapter = new Covid_tracking_fragmentAdapter(fm, getLifecycle());
        viewPager2.setOffscreenPageLimit(1);
        viewPager2.setAdapter(fragmentAdapter);

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.cases)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.Stock)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.Stats1)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.Stats2)));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
            public void onPageSelected(int position){
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        return view;
    }
}
