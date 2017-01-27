package com.firebasepractice.pravin103082.contentproviderpractice.calender;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebasepractice.pravin103082.contentproviderpractice.DataBaseHandler;
import com.firebasepractice.pravin103082.contentproviderpractice.R;
import com.firebasepractice.pravin103082.contentproviderpractice.alram_manager.AlarmBroadcastReceiver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.Manifest.permission.READ_CALENDAR;
import static android.Manifest.permission.WRITE_CALENDAR;

public class CalenderActivity extends AppCompatActivity {
    private static final String TAG = CalenderActivity.class.getSimpleName();
    EditText startYear, startMonth, startHourOfDate, startMinute, startDate;
    EditText endYear, endMonth, endtHourOfDate, endMinute, endDate;
    EditText deleteEventId, edtEventTitle;
    Button btnAddEvent, btnDeleteEvent, btnShowEvent, btnShowAllEvents;
    int eventIdBefore, eventIdAfter;
    CalenderModel calenderModel;
    DataBaseHandler db;
    Calendar beginCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        db = new DataBaseHandler(this);
        calenderModel = new CalenderModel();
        startYear = (EditText) findViewById(R.id.startYear);
        startMonth = (EditText) findViewById(R.id.startMonth);
        startHourOfDate = (EditText) findViewById(R.id.startHourOfDate);
        startDate = (EditText) findViewById(R.id.startDate);
        startMinute = (EditText) findViewById(R.id.startMinute);

        endYear = (EditText) findViewById(R.id.endYear);
        endMonth = (EditText) findViewById(R.id.endMonth);
        endtHourOfDate = (EditText) findViewById(R.id.endHourOfDate);
        endMinute = (EditText) findViewById(R.id.endMinute);
        endDate = (EditText) findViewById(R.id.endDate);

        edtEventTitle = (EditText) findViewById(R.id.edtEventTitle);
        deleteEventId = (EditText) findViewById(R.id.deleteEventId);

        btnAddEvent = (Button) findViewById(R.id.btnAddEvent);
        btnDeleteEvent = (Button) findViewById(R.id.btnDeleteEvent);
        btnShowEvent = (Button) findViewById(R.id.btnShowEvent);

        btnShowAllEvents = (Button) findViewById(R.id.btnShowAllEvents);

        btnShowAllEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CalenderModel> calenderModels = db.sortByTime();

                for (int i = 0; i < calenderModels.size(); i++) {
                    Log.d("EVENT_ID", calenderModels.get(i).getEventID() + " " +
                            "" + calenderModels.get(i).getEventTitle() + " " +
                            "" + calenderModels.get(i).getDiff() + " " +
                            "Y " + calenderModels.get(i).getYear() + " " +
                            "M " + calenderModels.get(i).getMonth() + " " +
                            "D " + calenderModels.get(i).getDay_of_month() + " " +
                            "H " + calenderModels.get(i).getHour_of_day() + " " +
                            "M " + calenderModels.get(i).getMinute() + " " +
                            "S " + calenderModels.get(i).getSecond());
                }
                // db.sortByTime();

            }
        });

        btnShowEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPermissions()) {
                    Log.e("SIZE >>", readCalendarEvent(CalenderActivity.this).size() + "");
                    showCalenderEvents();
                } else {
                    requestPermission();
                }
            }

        });
        btnDeleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions()) {
                    if (!TextUtils.isEmpty(edtEventTitle.getText().toString())) {
                        //  int eventID=ListSelectedCalendars(CalenderActivity.this, edtEventTitle.getText().toString());
                        int eventID = Integer.parseInt(deleteEventId.getText().toString());
                        Log.e("EventID", "" + eventID);

                        if (eventID != 0) {
                            ContentResolver cr = getContentResolver();
                            ContentValues values = new ContentValues();
                            Uri deleteUri = null;
                            deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
                            int rows = getContentResolver().delete(deleteUri, null, null);
                            Log.i("DELETE >", "Rows deleted: " + rows);
                            if (rows > 0) {
                                Toast.makeText(CalenderActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                db.deleteEvent(eventID);
                            } else {
                                Toast.makeText(CalenderActivity.this, "No event with specified eventId", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CalenderActivity.this, "No event with specified Event Title", Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        edtEventTitle.setError("Please provide event id");
                        edtEventTitle.requestFocus();
                        return;
                    }
                } else {
                    requestPermission();
                }

            }
        });

        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkPermissions()) {
                    requestPermission();
                }

                calenderModel.setEventTitle(edtEventTitle.getText().toString());
                Calendar currnetTime = Calendar.getInstance();
                Calendar beginTime = Calendar.getInstance();
                Log.e("Strat Calender", beginTime.toString());
                beginTime.set(Integer.parseInt(startYear.getText().toString())
                        , Integer.parseInt(startMonth.getText().toString())
                        , Integer.parseInt(startDate.getText().toString())
                        , Integer.parseInt(startHourOfDate.getText().toString())
                        , Integer.parseInt(startMinute.getText().toString()));

                Calendar endTime = Calendar.getInstance();
                endTime.set(Integer.parseInt(endYear.getText().toString())
                        , Integer.parseInt(endMonth.getText().toString())
                        , Integer.parseInt(endDate.getText().toString())
                        , Integer.parseInt(endtHourOfDate.getText().toString())
                        , Integer.parseInt(endMinute.getText().toString()));

                beginCalendar = Calendar.getInstance();
                beginCalendar.set(Calendar.YEAR, Integer.parseInt(startYear.getText().toString()));
                beginCalendar.set(Calendar.MONTH, Integer.parseInt(startMonth.getText().toString()));
                beginCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(startDate.getText().toString()));
                beginCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startHourOfDate.getText().toString()));
                beginCalendar.set(Calendar.MINUTE, Integer.parseInt(startMinute.getText().toString()));
                beginCalendar.set(Calendar.SECOND, 0);

                calenderModel.setYear(Integer.parseInt(startYear.getText().toString()));
                calenderModel.setMonth(Integer.parseInt(startMonth.getText().toString()));
                calenderModel.setDay_of_month(Integer.parseInt(startDate.getText().toString()));
                calenderModel.setHour_of_day(Integer.parseInt(startHourOfDate.getText().toString()));
                calenderModel.setMinute(Integer.parseInt(startMinute.getText().toString()));
                calenderModel.setSecond(0);

                long mili = daysBetween(currnetTime, beginTime);
                Log.e("Diff", "" + mili);
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(mili);
                Log.e(TAG, "onClick: Days " + (mili / (60 * 60 * 24 * 1000)));
                calenderModel.setDiff("" + mili);
                Log.e("Count", CalendarContract.Events._COUNT);
                eventIdBefore = (int) getNewEventId(getContentResolver(), null);
                Log.e("eventId before", "" + eventIdBefore);
                //Store this Event id somewhere respective to its eventid

                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                        .putExtra(CalendarContract.Events.TITLE, edtEventTitle.getText().toString())
                        .putExtra(CalendarContract.Events.DESCRIPTION, "EventText")
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, "The gym")
                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                startActivityForResult(intent, 10);
            }
        });

        //setAlarmManager();
    }

    public static long daysBetween(Calendar startDate, Calendar endDate) {
        long end = endDate.getTimeInMillis();
        long start = startDate.getTimeInMillis();
        return TimeUnit.MILLISECONDS.toMillis(Math.abs(end - start));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("CHECK", "CHECK");
        Log.e("requestCode", requestCode + "");
        Log.e("result code", resultCode + "");

        eventIdAfter = (int) getNewEventId(getContentResolver(), null);
        Log.e("eventId", eventIdAfter + "");
        if (eventIdAfter == (eventIdBefore + 1)) {
            Log.e(TAG, "onActivityResult: Add to database");
            calenderModel.setEventID(eventIdAfter);
            db.addEvent(calenderModel);
            setAlarmManager();

        }
    }

    public ArrayList<String> nameOfEvent = new ArrayList<String>();
    public ArrayList<String> eventID = new ArrayList<String>();


    public ArrayList<String> readCalendarEvent(Context context) {
        Cursor cursor = context.getContentResolver()
                .query(
                        Uri.parse("content://com.android.calendar/events"),
                        new String[]{CalendarContract.Calendars._ID, "title", "description",
                                "dtstart", "dtend", "eventLocation", CalendarContract.Calendars.DELETED, CalendarContract.Events._ID}, CalendarContract.Calendars.DELETED + "=0",
                        null, null);
        cursor.moveToFirst();
        // fetching calendars name
        String CNames[] = new String[cursor.getCount()];

        // fetching calendars id
        eventID.clear();
        nameOfEvent.clear();
        for (int i = 0; i < CNames.length; i++) {
            eventID.add(cursor.getString(0));
            nameOfEvent.add(cursor.getString(1));
            CNames[i] = cursor.getString(1);
            // Log.e("ID > " + cursor.getString(0), "Des >" + cursor.getString(2) + " name >" + cursor.getString(1) + "DELETED >" + cursor.getString(7));
            cursor.moveToNext();

        }
        return nameOfEvent;
    }

    public static String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }


    public void showCalenderEvents() {
        Dialog dialog = new Dialog(CalenderActivity.this);
        dialog.setContentView(R.layout.content_provider_list);
        ListView listView = (ListView) dialog.findViewById(R.id.lstCalender);
        listView.setAdapter(new CalenderShowAdapter(CalenderActivity.this, nameOfEvent, eventID));
        dialog.show();

    }

    class CalenderShowAdapter extends ArrayAdapter<CalenderModel> {
        Context mContext;
        ArrayList<String> titles, ids;

        public CalenderShowAdapter(Context context, ArrayList<String> titles, ArrayList<String> ids) {
            super(context, R.layout.single_row_calender);
            mContext = context;
            this.titles = titles;
            this.ids = ids;
        }


        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.single_row_calender, parent, false);
            }
            TextView txtEventId = (TextView) convertView.findViewById(R.id.txtID);
            TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            txtEventId.setText(ids.get(position));
            txtTitle.setText(titles.get(position));
            return convertView;
        }

        @Override
        public int getCount() {
            return ids.size();
        }
    }

    public boolean checkPermissions() {
        int readCalender = ActivityCompat.checkSelfPermission(this, READ_CALENDAR);
        int writeCalender = ActivityCompat.checkSelfPermission(this, WRITE_CALENDAR);
        if (readCalender == PackageManager.PERMISSION_GRANTED && writeCalender == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private static final int PERMISSION_REQUEST_CODE = 1;

    public void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_CALENDAR, WRITE_CALENDAR}, PERMISSION_REQUEST_CODE);
    }


    private int ListSelectedCalendars(Context context, String eventtitle) {

        int eventId = 0;
        Cursor cursor = context.getContentResolver()
                .query(
                        Uri.parse("content://com.android.calendar/events"),
                        new String[]{CalendarContract.Calendars._ID, "title", "description",
                                "dtstart", "dtend", "eventLocation", CalendarContract.Calendars.DELETED}, CalendarContract.Calendars.DELETED + "=0",
                        null, null);
        cursor.moveToFirst();
        // fetching calendars name
        String CNames[] = new String[cursor.getCount()];

        // fetching calendars id
        eventID.clear();
        nameOfEvent.clear();
        for (int i = 0; i < CNames.length; i++) {
            eventID.add(cursor.getString(0));
            nameOfEvent.add(cursor.getString(1));
            CNames[i] = cursor.getString(1);
            if (eventtitle.equals(cursor.getString(1))) {
                Log.e("ID > " + cursor.getString(0), "Des >" + cursor.getString(2) + " name >" + cursor.getString(1) + "DELETED >" + cursor.getString(6));
                eventId = Integer.parseInt(cursor.getString(0));
                return eventId;
            }
            cursor.moveToNext();
        }
        return eventId;
    }


    public static long getNewEventId(ContentResolver cr, Uri cal_uri) {
        Uri local_uri = cal_uri;
        if (cal_uri == null) {
            local_uri = Uri.parse("content://com.android.calendar/events");
        }
        Cursor cursor = cr.query(local_uri, new String[]{"MAX(_id) as max_id"}, null, null, "_id");
        cursor.moveToFirst();
        long max_val = cursor.getLong(cursor.getColumnIndex("max_id"));
        return max_val;
    }

    public static final long SECOND_IN_MILLIS = 1000;
    public static final long MINUTE_IN_MILLIS = SECOND_IN_MILLIS * 60;
    public static final long HOUR_IN_MILLIS = MINUTE_IN_MILLIS * 60;
    public static final long DAY_IN_MILLIS = HOUR_IN_MILLIS * 24;
    public static final long WEEK_IN_MILLIS = DAY_IN_MILLIS * 7;


    public void setAlarmManager() {
        CalenderModel sortedCalenderModel = db.sortByTime().get(0);
        Log.e(TAG, "setAlarmManager: " + calenderModel.getEventTitle() + " " + calenderModel.getEventID() + " " + calenderModel.getDiff());

        Intent intent = new Intent(CalenderActivity.this, AlarmBroadcastReceiver.class);

        intent.putExtra("id", "" + calenderModel.getEventID());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                CalenderActivity.this.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
     /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,(System.currentTimeMillis() + Long.parseLong(sortedCalenderModel.getDiff()))-60000, pendingIntent);
        }*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, beginCalendar.getTimeInMillis(), pendingIntent);
        }

        long difffs = Long.parseLong(sortedCalenderModel.getDiff());
    }

}
