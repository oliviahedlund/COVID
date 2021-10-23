package com.example.myapplication.Helpers;

import com.example.myapplication.API.Model.Covid_tracking.CaseStat;
import com.example.myapplication.API.Model.Covid_tracking.Cases;

import java.util.List;

public class CovidTrackCaseHelper {
    private Cases cases;
    private List<CaseStat> caseStats;

    public String [] getCountyNames(){
        String [] buffer = new String[caseStats.size()];
        for(int i = 0; i < caseStats.size(); i++){
            buffer[i] = caseStats.get(i).getCountyName();
        }
        return buffer;
    }

    public int [] getFilteredDataSet(int selectedCounty){
        CaseStat buffer = caseStats.get(selectedCounty);
        return new int[]{buffer.getTotalCaseCount(), buffer.getTotalIntensiveCareCount(), buffer.getTotalDeathCount()};
    }

    public Cases getCases() {
        return cases;
    }

    public void setCases(Cases cases) {
        this.cases = cases;
    }

    public List<CaseStat> getCaseStats() {
        return caseStats;
    }

    public void setCaseStats(List<CaseStat> caseStats) {
        this.caseStats = caseStats;
    }
}
