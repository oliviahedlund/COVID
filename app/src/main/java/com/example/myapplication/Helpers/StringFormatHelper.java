package com.example.myapplication.Helpers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class StringFormatHelper {

    public static String yearMonthDayTime(ZonedDateTime zonedDateTime){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return zonedDateTime.format(format);
    }

    public static String yearMonthDay(ZonedDateTime zonedDateTime){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return zonedDateTime.format(format);
    }

    public static ZonedDateTime convertTimeToZoneDateTime(int[] date){
        //date = list of: year, month, day, hour, minute
        ZonedDateTime appointmentDateTime = LocalDateTime.of(date[0], date[1], date[2], date[3], date[4], 0).atZone(ZoneId.of("Europe/Stockholm")).withZoneSameInstant(ZoneId.of("UTC"));
        return appointmentDateTime;
    }

    public static String phoneNumber(String phoneNumber){
        StringBuilder formattedNumber = new StringBuilder(phoneNumber);
        //070-111 22 33
        formattedNumber.insert(8, ' ');
        formattedNumber.insert(6, ' ');
        formattedNumber.insert(3, '-');
        return formattedNumber.toString();
    }

    public static String zipCode(String zip){
        StringBuilder formattedZip = new StringBuilder(zip);
        formattedZip.insert(3, ' ');
        return formattedZip.toString();
    }

}
