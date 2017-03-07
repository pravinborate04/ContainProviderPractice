package com.firebasepractice.pravin103082.contentproviderpractice.alram_manager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebasepractice.pravin103082.contentproviderpractice.R;

public class AlarmActivity extends AppCompatActivity {

    private static final String TAG = AlarmActivity.class.getSimpleName();
    Button btnSetAlarm;
    EditText setTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        btnSetAlarm=(Button)findViewById(R.id.btnSetAlarm);
        setTime=(EditText)findViewById(R.id.setTime);


        btnSetAlarm.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(setTime.getText().toString());
                Intent intent = new Intent(AlarmActivity.this, AlarmBroadcastReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        AlarmActivity.this.getApplicationContext(), 234324243, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                            + (i * 1000), pendingIntent);
                }
                Toast.makeText(AlarmActivity.this, "Alarm set in " + i + " seconds", Toast.LENGTH_LONG).show();

                long l=System.currentTimeMillis() + (i * 1000);

                Log.e(TAG, "onClick: "+l );
            }
        });
    }
}
