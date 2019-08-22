package com.example.trackmyhours;

public class Data {
    private String workingHours;
    private String lunchHours;
    private String breakHours;
    private String date;


    public Data(String workingHours, String lunchHours, String breakHours, String date) {
        this.workingHours = workingHours;
        this.lunchHours = lunchHours;
        this.breakHours = breakHours;
        this.date = date;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public String getLunchHours() {
        return lunchHours;
    }

    public void setLunchHours(String lunchHours) {
        this.lunchHours = lunchHours;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBreakHours() {
        return breakHours;
    }

    public void setBreakHours(String breakHours) {
        this.breakHours = breakHours;
    }
}
