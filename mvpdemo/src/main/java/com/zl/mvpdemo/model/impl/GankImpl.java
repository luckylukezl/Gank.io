package com.zl.mvpdemo.model.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.orhanobut.logger.Logger;
import com.zl.mvpdemo.MyApplication;
import com.zl.mvpdemo.model.bean.DayData;
import com.zl.mvpdemo.model.bean.DayInfo;
import com.zl.mvpdemo.model.bean.GankData;
import com.zl.mvpdemo.model.bean.GankInfo;
import com.zl.mvpdemo.model.bean.GirlData;
import com.zl.mvpdemo.model.model.IGankModel;
import com.zl.mvpdemo.model.service.GankRetrofit;
import com.zl.mvpdemo.model.service.IGankioService;
import com.zl.mvpdemo.presenter.listener.OnDataBaseListener;
import com.zl.mvpdemo.presenter.listener.OnGankDataListener;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ZL on 2017/2/22.
 */

public class GankImpl implements IGankModel {

    private Subscriber<List<GankData>> mSubscriber;

    @Override
    public void getGankData(final OnGankDataListener listener, String type, int count, int page) {
        IGankioService service = GankRetrofit.getGankRetrofit().getService();
        Observable<GankInfo<List<GankData>>> observable = service.getGankData(type , count , page);

        mSubscriber = new Subscriber<List<GankData>>() {
            @Override
            public void onCompleted() {
                listener.onCompleted();

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailed();
                Log.i("zlTag","error:" + e.toString());
            }

            @Override
            public void onNext(List<GankData> gankDatas) {
                Log.i("zlTag" , "size:" + gankDatas.size());
                listener.onSuccess(gankDatas);
            }
        };

        observable.map(new GirlDataFunc<List<GankData>>())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        listener.onStart();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
    }

    @Override
    public void getGankData(final OnGankDataListener listener , String type, int page) {
        getGankData(listener,type,10,page);
    }


    @Override
    public void getDayData(final OnGankDataListener listener, String date) {
        IGankioService service = GankRetrofit.getGankRetrofit().getService();
        Observable<DayInfo> observable = service.getDayData(date);

        mSubscriber = new Subscriber<List<GankData>>() {
            @Override
            public void onCompleted() {
                listener.onCompleted();

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailed();

            }

            @Override
            public void onNext(List<GankData> dayInfo) {
                listener.onSuccess(dayInfo);
                //Logger.d(dayInfo.size());
            }
        };

        observable.subscribeOn(Schedulers.io())
                .map(new DayDataFunc())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        listener.onStart();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
    }

    @Override
    public void onDestory() {
        if(mSubscriber != null){
            mSubscriber.unsubscribe();
        }
    }
}
