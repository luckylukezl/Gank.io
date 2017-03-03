package com.zl.mvpdemo;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;


/**
 * Created by ZL on 2017/2/9.
 */

public class MyApplication extends Application{

    private static MyApplication sApplication;

    public static Context getAppContext(){

        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
        sApplication = this;

    }
}
