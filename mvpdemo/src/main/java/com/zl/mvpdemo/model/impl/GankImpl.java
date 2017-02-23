package com.zl.mvpdemo.model.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.zl.mvpdemo.MyApplication;
import com.zl.mvpdemo.model.bean.GankData;
import com.zl.mvpdemo.model.bean.GankInfo;
import com.zl.mvpdemo.model.bean.GirlData;
import com.zl.mvpdemo.model.model.IGankModel;
import com.zl.mvpdemo.model.service.GankRetrofit;
import com.zl.mvpdemo.model.service.IGankioService;
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

    @Override
    public void getGankData(final OnGankDataListener listener , String type, int page) {
        IGankioService service = GankRetrofit.getGankRetrofit().getService();
        Observable<GankInfo<List<GankData>>> observable = service.getGankData(type , 10 , page);

        observable.map(new GirlDataFunc<List<GankData>>())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        listener.onStart();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(Schedulers.io())
//                .map(new Func1<List<GankData>, List<GankData>>() {
//                    @Override
//                    public List<GankData> call(List<GankData> datas) {
//                        for(GankData data:datas){
//                            List<String> list = data.getImages();
//                            if(list == null)continue;
//                            for(String s:list){
//                                FutureTarget future = Glide.with(MyApplication.getAppContext())
//                                        .load(s)
//                                        .downloadOnly(500, 500);
//                                try {
//                                    File file = (File) future.get();
//                                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//                                    Log.i("zlTag" , s + ":"  + bitmap.getWidth() + "h" + bitmap.getHeight());
//
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                } catch (ExecutionException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                        }
//
//                        return datas;
//                    }
//                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<GankData>>() {
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
                });

    }
}
