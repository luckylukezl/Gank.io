package com.zl.mvpdemo.model.model;

import com.zl.mvpdemo.presenter.listener.OnGankDataListener;

/**
 * Created by ZL on 2017/2/22.
 */

public interface IGankModel {
    void getGankData(OnGankDataListener listener , String type , int page);
}
