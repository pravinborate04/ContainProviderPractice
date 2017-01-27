package com.firebasepractice.pravin103082.contentproviderpractice.alram_manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.firebasepractice.pravin103082.contentproviderpractice.DataBaseHandler;
import com.firebasepractice.pravin103082.contentproviderpractice.MainActivity;

/**
 * Created by pravin103082 on 19-12-2016.
 */

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG =AlarmBroadcastReceiver.class.getSimpleName();
    DataBaseHandler dataBaseHandler;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: ");
        Toast.makeText(context, "onReceive", Toast.LENGTH_LONG).show();

        dataBaseHandler=new DataBaseHandler(context);

        Log.e(TAG, "onReceive: "+intent.getExtras().get("id"));

        dataBaseHandler.deleteEvent(Integer.parseInt(intent.getExtras().get("id").toString()));
        Intent intent1=new Intent(context, MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }

}
