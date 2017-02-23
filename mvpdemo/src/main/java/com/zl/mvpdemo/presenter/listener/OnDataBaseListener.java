package com.zl.mvpdemo.presenter.listener;



/**
 * Created by ZL on 2017/2/20.
 */

public interface OnDataBaseListener<T> {
    void onSuccess(T t);
    void onFailed();
    void onStart();
    void onCompleted();
}
