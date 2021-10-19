package com.example.myapplication.API.Model.Admin;

public class UserNumberResponse {
    private int number;
    private String type;
    private String title;
    private int status;
    private String detail;
    private String instance;

    //Getters
    public int getNumber() {
        return number;
    }
    public String getType() {
        return type;
    }
    public String getTitle() {
        return title;
    }
    public int getStatus() {
        return status;
    }
    public String getDetail() {
        return detail;
    }
    public String getInstance() {
        return instance;
    }

    //Setters
    public void setNumber(int number) {
        this.number = number;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public void setInstance(String instance) {
        this.instance = instance;
    }
}
