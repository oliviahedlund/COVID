package com.example.myapplication.UI.ViewCells;

public class Covid_Tracking_ListViewCell {
    private String label;
    private String data;

    public Covid_Tracking_ListViewCell(String label, String data) {
        this.label = label;
        this.data = data;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}