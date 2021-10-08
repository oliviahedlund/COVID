package com.example.myapplication;

public class Covid_Tracking_ListViewCell {
    private String id;
    private String label;
    private String data;


    public Covid_Tracking_ListViewCell(String id, String label) {
        this.id = id;
        this.label = label;
        this.data = null;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
