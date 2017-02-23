package com.zl.mvpdemo.model.model;

import com.zl.mvpdemo.presenter.listener.OnGirlListener;

/**
 * 获取图片接口
 */

public interface IGirlModel {
    void getGirls(OnGirlListener listener, int page);
}
