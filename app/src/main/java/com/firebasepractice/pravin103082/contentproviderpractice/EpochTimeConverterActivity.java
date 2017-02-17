package com.firebasepractice.pravin103082.contentproviderpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebasepractice.pravin103082.contentproviderpractice.calender.CalenderModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EpochTimeConverterActivity extends AppCompatActivity {

    EditText time;
    TextView txtCnverted;
    Button btnConvert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epoch_time_converter);
        time=(EditText)findViewById(R.id.time);
        txtCnverted=(TextView)findViewById(R.id.txtCnverted);
        btnConvert=(Button)findViewById(R.id.btnConvert);
        time.setText("1486541400");

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=time.getText().toString();
                txtCnverted.setText(getDate(Long.parseLong(s),"HH:mm"));
            }
        });
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.US);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
