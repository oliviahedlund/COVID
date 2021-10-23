package com.example.myapplication.API.Model.Covid_tracking;

import java.time.ZonedDateTime;
import java.util.List;

public class Cases {
    private ZonedDateTime lastRetrived;
    private List<CaseStat> caseStats;

    public String [] getCountyNames(){
        String [] buffer = new String[caseStats.size()];
        for(int i = 0; i < caseStats.size(); i++){
            buffer[i] = caseStats.get(i).getCountyName();
        }
        return buffer;
    }
}
