package com.zl.mvpdemo.view.view;

/**
 * Created by ZL on 2017/2/20.
 */

public interface IView<T> {
    void setData(T t);
    void showLoading();
    void getDataCompleted();
    void showError();
}
