package com.example.myapplication.API.Model.Covid_tracking;

import java.time.ZonedDateTime;
import java.util.List;

public class VaxStock {
    private ZonedDateTime lastRetrived;
    private List<StockStat> stockStats;

    public ZonedDateTime getLastRetrived() {
        return lastRetrived;
    }

    public void setLastRetrived(ZonedDateTime lastRetrived) {
        this.lastRetrived = lastRetrived;
    }

    public List<StockStat> getStockStats() {
        return stockStats;
    }

    public void setStockStats(List<StockStat> stockStats) {
        this.stockStats = stockStats;
    }
}
