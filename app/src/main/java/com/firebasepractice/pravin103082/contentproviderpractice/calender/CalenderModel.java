package com.firebasepractice.pravin103082.contentproviderpractice.calender;

/**
 * Created by pravin103082 on 12-12-2016.
 */

public class CalenderModel {
    private int eventID;
    private String eventTitle;
    private String diff;
    private int year;
    private int month;
    private int day_of_month;
    private int minute;
    private int second;
    private int hour_of_day;

    public int getHour_of_day() {
        return hour_of_day;
    }

    public void setHour_of_day(int hour_of_day) {
        this.hour_of_day = hour_of_day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay_of_month() {
        return day_of_month;
    }

    public void setDay_of_month(int day_of_month) {
        this.day_of_month = day_of_month;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }
}
