package com.zl.mvpdemo.view.listener;

import android.content.res.Resources;
import android.view.View;

import com.zl.mvpdemo.model.bean.GirlData;

/**
 * Created by ZL on 2017/2/24.
 */

public interface OnGirlTouchListener {
    void onTouch(GirlData girlData, View view , int position);
}
