package com.firebasepractice.pravin103082.contentproviderpractice;

import android.app.Application;

import com.mastek.appengage.ActivityTracker;
import com.mastek.appengage.MA;
import com.mastek.appengage.exchandler.ExceptionHandler;

/**
 * Created by Pravin103082 on 28-12-2016.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        registerActivityLifecycleCallbacks(new ActivityTracker());
        MA.init(this,"http://52.87.24.173/api/","4170b44d6459bba992acaa857ac5b25d7fac6cc1");

    }
}
