package com.example.myapplication.Helpers;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeStringHelper {

    public static String yearMonthDayTime(ZonedDateTime zonedDateTime){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return zonedDateTime.format(format);
    }


}
