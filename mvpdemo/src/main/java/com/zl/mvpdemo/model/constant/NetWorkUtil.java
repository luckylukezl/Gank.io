package com.zl.mvpdemo.model.constant;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ZL on 2017/2/25.
 */

public class NetWorkUtil {

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable();
    }
}
