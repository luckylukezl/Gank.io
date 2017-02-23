package com.zl.mvpdemo.view.view;

import com.zl.mvpdemo.model.bean.GirlData;

import java.util.List;

/**
 * 与界面交互接口
 */

public interface IGirlView {
    void setGirlInfo(List<GirlData> girlInfo);
    void setGirlData(GirlData girlData);
    void showLoading();
    void getDataCompleted();
    void showError();
}
