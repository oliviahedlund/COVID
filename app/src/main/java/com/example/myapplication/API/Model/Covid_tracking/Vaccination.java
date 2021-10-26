package com.example.myapplication.API.Model.Covid_tracking;

import java.time.ZonedDateTime;
import java.util.List;

public class Vaccination {
    private ZonedDateTime lastRetrived;
    private List<CountyVaxStat> countyStats;
    private List<UptakeStat> cumulativeUptakeStats;

    public ZonedDateTime getLastRetrived() {
        return lastRetrived;
    }

    public void setLastRetrived(ZonedDateTime lastRetrived) {
        this.lastRetrived = lastRetrived;
    }

    public List<CountyVaxStat> getCountyStats() {
        return countyStats;
    }

    public void setCountyStats(List<CountyVaxStat> countyStats) {
        this.countyStats = countyStats;
    }

    public List<UptakeStat> getCumulativeUptakeStats() {
        return cumulativeUptakeStats;
    }

    public void setCumulativeUptakeStats(List<UptakeStat> cumulativeUptakeStats) {
        this.cumulativeUptakeStats = cumulativeUptakeStats;
    }
}
