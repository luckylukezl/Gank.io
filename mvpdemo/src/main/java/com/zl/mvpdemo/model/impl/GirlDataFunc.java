package com.zl.mvpdemo.model.impl;

import android.util.Log;

import com.zl.mvpdemo.model.bean.GankInfo;

import rx.functions.Func1;

/**
 * Created by ZL on 2017/2/19.
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 */

public class GirlDataFunc<T> implements Func1<GankInfo<T>, T> {


    @Override
    public T call(GankInfo<T> tGankInfo) {
        if(tGankInfo.getError()){
            throw new GankException();
        }
        return tGankInfo.getResults();
    }
}
