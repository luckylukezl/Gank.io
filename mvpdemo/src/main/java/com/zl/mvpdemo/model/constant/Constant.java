package com.zl.mvpdemo.model.constant;

import android.os.Environment;

import java.util.Date;

/**
 * Created by ZL on 2017/2/16.
 */

public class Constant {
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static float SCREEN_DENSITY;

    public static String PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/Gank";
    public static String SHARE_PATH = PATH + "/share";
    public static String SAVE_PATH = PATH + "/save";

    public static String IMAGE_URL_END = "?imageView2/0/w/";

    public static Date DATE_TODAY = new Date();

    public static String NEW_PREFERENCE = "new_preference";
    public static String FIRST_CHECK_PREFERENCE = "first check preference";

}
