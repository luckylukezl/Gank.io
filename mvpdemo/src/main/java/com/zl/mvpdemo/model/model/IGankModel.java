package com.zl.mvpdemo.model.model;

import com.zl.mvpdemo.model.bean.DayInfo;
import com.zl.mvpdemo.model.bean.GankData;
import com.zl.mvpdemo.presenter.listener.OnDataBaseListener;
import com.zl.mvpdemo.presenter.listener.OnGankDataListener;

import java.util.List;

/**
 * Created by ZL on 2017/2/22.
 */

public interface IGankModel {
    void getGankData(OnGankDataListener listener , String type ,int count, int page);
    void getGankData(OnGankDataListener listener , String type , int page);
    void getDayData(OnGankDataListener listener, String date);
    void onDestory();
}
