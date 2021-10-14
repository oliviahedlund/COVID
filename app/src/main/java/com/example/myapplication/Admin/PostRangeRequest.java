package com.example.myapplication.Admin;

import java.time.ZonedDateTime;

public class PostRangeRequest {
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private int allowedDaysOfWeek;
    private int allowedAgeGroups;
    private int timePerAppointmentMinutes;
    private String centerId;

    public void setStartDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(ZonedDateTime endDateTime) {
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

    public void setCenter(String center) {
        this.centerId = center;
    }
}
