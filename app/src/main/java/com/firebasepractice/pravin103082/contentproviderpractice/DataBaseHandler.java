package com.firebasepractice.pravin103082.contentproviderpractice;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.firebasepractice.pravin103082.contentproviderpractice.calender.CalenderModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pravin103082 on 19-12-2016.
 */

public class DataBaseHandler extends SQLiteOpenHelper {


    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "eventsManager";

    // Contacts table name
    private static final String TABLE_EVENTS = "events";

    // Contacts Table Columns names
    private static final String eventID = "eventID";
    private static final String eventTitle = "eventTitle";
    private static final String diff = "diff";
    private static final String year="year";
    private static final String month="month";
    private static final String day_of_month="day_of_month";
    private static final String hour_of_day="hour_of_day";
    private static final String minute="minute";
    private static final String second="second";


    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);



    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
                + eventID + " INTEGER PRIMARY KEY,"
                + eventTitle + " TEXT,"
                + diff + " TEXT,"
                + year + " INTEGER,"
                + month + " INTEGER,"
                + day_of_month + " INTEGER,"
                + hour_of_day + " INTEGER,"
                + minute + " INTEGER,"
                + second + " INTEGER "+ ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    // Adding new event
    public void addEvent(CalenderModel calenderModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(eventID, calenderModel.getEventID());
        values.put(eventTitle, calenderModel.getEventTitle());
        values.put(diff,calenderModel.getDiff());
        values.put(year,calenderModel.getYear());
        values.put(month,calenderModel.getMonth());
        values.put(day_of_month,calenderModel.getDay_of_month());
        values.put(minute,calenderModel.getMinute());
        values.put(second,calenderModel.getSecond());
        values.put(hour_of_day,calenderModel.getHour_of_day());
        db.insert(TABLE_EVENTS, null, values);
        db.close();
    }


    // Getting All Contacts
    public List<CalenderModel> getAllEvents() {
        List<CalenderModel> calenderModels = new ArrayList<CalenderModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EVENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CalenderModel calenderModel = new CalenderModel();
                calenderModel.setEventID(Integer.parseInt(cursor.getString(0)));
                calenderModel.setEventTitle(cursor.getString(1));
                calenderModel.setDiff(cursor.getString(2));

                calenderModels.add(calenderModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return calenderModels;
    }

    public void deleteEvent(int eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, eventID + " = ?",
                new String[] { String.valueOf(eventId) });
        db.close();
    }

    public  List<CalenderModel> sortByTime(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_EVENTS, null, null, null, null, null, diff +" ASC");

        List<CalenderModel> calenderModels = new ArrayList<CalenderModel>();
        if (cursor.moveToFirst()) {
            do {
                CalenderModel calenderModel = new CalenderModel();
                calenderModel.setEventID(Integer.parseInt(cursor.getString(0)));
                calenderModel.setEventTitle(cursor.getString(1));
                calenderModel.setDiff(cursor.getString(2));
                calenderModel.setYear(Integer.parseInt(cursor.getString(3)));
                calenderModel.setMonth(Integer.parseInt(cursor.getString(4)));
                calenderModel.setDay_of_month(Integer.parseInt(cursor.getString(5)));
                calenderModel.setHour_of_day(Integer.parseInt(cursor.getString(6)));
                calenderModel.setMinute(Integer.parseInt(cursor.getString(7)));
                calenderModel.setSecond(Integer.parseInt(cursor.getString(8)));
                calenderModels.add(calenderModel);
            } while (cursor.moveToNext());
        }
        return calenderModels;
    }
}
