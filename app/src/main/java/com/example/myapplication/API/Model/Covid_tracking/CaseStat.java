package com.example.myapplication.API.Model.Covid_tracking;

public class CaseStat {
    private String countyName;
    private int totalCaseCount;
    private int totalIntensiveCareCount;
    private int totalDeathCount;

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public int getTotalCaseCount() {
        return totalCaseCount;
    }

    public void setTotalCaseCount(int totalCaseCount) {
        this.totalCaseCount = totalCaseCount;
    }

    public int getTotalIntensiveCareCount() {
        return totalIntensiveCareCount;
    }

    public void setTotalIntensiveCareCount(int totalIntensiveCareCount) {
        this.totalIntensiveCareCount = totalIntensiveCareCount;
    }

    public int getTotalDeathCount() {
        return totalDeathCount;
    }

    public void setTotalDeathCount(int totalDeathCount) {
        this.totalDeathCount = totalDeathCount;
    }
}
