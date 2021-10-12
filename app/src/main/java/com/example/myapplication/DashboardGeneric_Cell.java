package com.example.myapplication;

public class DashboardGeneric_Cell {
    private String name;
    private String AppTime;
    private String Birthday;

    public DashboardGeneric_Cell( String appTime) {
        AppTime = appTime;
    }


    public String getAppTime() {
        return AppTime;
    }

    public void setAppTime(String appTime) {
        AppTime = appTime;
    }


}
