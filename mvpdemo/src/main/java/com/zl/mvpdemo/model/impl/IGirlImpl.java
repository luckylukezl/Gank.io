package com.zl.mvpdemo.model.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.zl.mvpdemo.MyApplication;
import com.zl.mvpdemo.model.bean.GankInfo;
import com.zl.mvpdemo.model.bean.GirlData;
import com.zl.mvpdemo.model.constant.Constant;
import com.zl.mvpdemo.model.model.IGirlModel;
import com.zl.mvpdemo.model.service.GankRetrofit;
import com.zl.mvpdemo.model.service.IGankioService;
import com.zl.mvpdemo.presenter.listener.OnGirlListener;

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
 * 利用okhttpfinal获取图片
 */

public class IGirlImpl implements IGirlModel {

    private static int PIC_WIDTH = Constant.SCREEN_WIDTH / 2;

    @Override
    public void getGirls(final OnGirlListener listener , int page) {

        GankRetrofit retrofit = GankRetrofit.getGankRetrofit();

        IGankioService service = retrofit.getService();
        Observable<GankInfo<List<GirlData>>> observable = service.getGirls(10,page);

        observable.map(new GirlDataFunc<List<GirlData>>())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        listener.onStart();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .map(new Func1<List<GirlData>, List<GirlData>>() {
                    @Override
                    public List<GirlData> call(List<GirlData> datas) {
                        for(GirlData data:datas){
                            final String url = data.getUrl() + "?imageView2/0/w/" + PIC_WIDTH;
                            FutureTarget future = Glide.with(MyApplication.getAppContext())
                                    .load(url)
                                    .downloadOnly(500, 500);
                            try {
                                File file = (File) future.get();
                                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                data.setHeight(bitmap.getHeight());
                                data.setWidth(bitmap.getWidth());
                                //Log.i("zlTag" , data.getHeight() + "h" + data.getWidth());

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }

                        return datas;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<GirlData>>() {
                    @Override
                    public void onCompleted() {
                        listener.onCompleted();

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed();

                    }

                    @Override
                    public void onNext(List<GirlData> girlDatas) {
                        listener.onSuccess(girlDatas);
                        Log.i("zlTag","size" + girlDatas.size());
                    }

                });

    }



}
