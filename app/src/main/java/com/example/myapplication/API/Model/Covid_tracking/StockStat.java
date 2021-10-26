package com.example.myapplication.API.Model.Covid_tracking;

public class StockStat {
    private String countyName;
    private int amount;

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
