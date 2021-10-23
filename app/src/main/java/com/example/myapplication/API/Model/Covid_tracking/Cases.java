package com.example.myapplication.API.Model.Covid_tracking;

import java.time.ZonedDateTime;
import java.util.List;

public class Cases {
    private ZonedDateTime lastRetrived;
    private List<CaseStat> caseStats;

    public ZonedDateTime getLastRetrived() {
        return lastRetrived;
    }

    public void setLastRetrived(ZonedDateTime lastRetrived) {
        this.lastRetrived = lastRetrived;
    }

    public List<CaseStat> getCaseStats() {
        return caseStats;
    }

    public void setCaseStats(List<CaseStat> caseStats) {
        this.caseStats = caseStats;
    }
}
