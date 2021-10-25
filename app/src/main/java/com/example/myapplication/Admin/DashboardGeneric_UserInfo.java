package com.example.myapplication.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.R;
import com.example.myapplication.API.Model.Admin.UserInfo;
import com.example.myapplication.Admin.DashboardGeneric_Admin;


public class DashboardGeneric_UserInfo extends Fragment {
    private UserInfo userInfo;
    private DashboardGeneric_Admin helper;
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.display_userinfo_admin_dashboardgeneric, container, false);
        //userInfo = helper.getUserInfo();
       // System.out.println("penis: " + userInfo.getFirstName());

        return view;
    }


}
