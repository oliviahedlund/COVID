package com.example.myapplication.Admin;

public class PostRangeRequest {
    private String startDateTime;
    private String endDateTime;
    private int allowedDaysOfWeek;
    private int allowedAgeGroups;
    private int timePerAppointmentMinutes;
    private int center;

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setAllowedDaysOfWeek(int allowedDaysOfWeek) {
        this.allowedDaysOfWeek = allowedDaysOfWeek;
    }

    public void setAllowedAgeGroups(int allowedAgeGroups) {
        this.allowedAgeGroups = allowedAgeGroups;
    }

    public void setTimePerAppointmentMinutes(int timePerAppointmentMinutes) {
        this.timePerAppointmentMinutes = timePerAppointmentMinutes;
    }

    public void setCenter(int center) {
        this.center = center;
    }
}
