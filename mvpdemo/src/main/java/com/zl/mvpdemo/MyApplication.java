package com.zl.mvpdemo;

import android.app.Application;
import android.content.Context;


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
        sApplication = this;

    }
}
