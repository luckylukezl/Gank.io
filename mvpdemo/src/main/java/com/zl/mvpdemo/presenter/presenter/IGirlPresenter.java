package com.zl.mvpdemo.presenter.presenter;

/**
 * presenter,获取图片接口
 */

public interface IGirlPresenter {
    void getGirls(int page);
    void getGirlsFromLoc(int page);
    void init();
    void onDestory();
}
