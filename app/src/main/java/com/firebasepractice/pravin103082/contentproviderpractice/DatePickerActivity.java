package com.firebasepractice.pravin103082.contentproviderpractice;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class DatePickerActivity extends AppCompatActivity {

    private static final String TAG =DatePickerActivity.class.getSimpleName() ;
    Button btnOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        btnOpen = (Button) findViewById(R.id.btnOpen);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TimePickerDialog timePickerDialog=new TimePickerDialog(DatePickerActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Log.e(TAG, "onDateSet: HOURS  "+hourOfDay+" MINUTE "+minute );

                    }
                },8,0,true);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        DatePickerActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Log.e(TAG, "onDateSet:  "+year+" "+month+" "+dayOfMonth );
                        timePickerDialog.show();
                    }
                }, 2016, 11, 20);

                datePickerDialog.setTitle("My Time");
             datePickerDialog.show();
            }
        });
    }
}
