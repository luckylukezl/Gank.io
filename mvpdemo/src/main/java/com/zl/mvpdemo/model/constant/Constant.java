package com.zl.mvpdemo.model.constant;

import android.os.Environment;

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


}
