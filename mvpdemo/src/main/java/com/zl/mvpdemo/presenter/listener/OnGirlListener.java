package com.zl.mvpdemo.presenter.listener;

import com.zl.mvpdemo.model.bean.GirlData;

import java.util.List;

/**
 * Created by Clytze on 2017/1/22.
 */

public interface OnGirlListener {
    void onSuccess(List<GirlData> info);
    void onSuccess(GirlData girlData);
    void onFailed();
    void onStart();
    void onCompleted();
}
