package com.example.myapplication;

public class Covid_Tracking_ListViewCell {
    private String id;
    private String label;
    private int image;
    private String data;

    public Covid_Tracking_ListViewCell(String id, String label, int image) {
        this.id = id;
        this.label = label;
        this.image = image;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
