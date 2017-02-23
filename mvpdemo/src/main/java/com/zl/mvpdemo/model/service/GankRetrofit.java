package com.zl.mvpdemo.model.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ZL on 2017/2/19.
 */

public class GankRetrofit {

    private String mUrl = "http://gank.io/api/data/";

    private static final int DEFAULT_TIMEOUT = 10;

    private IGankioService mService;

    private GankRetrofit(){
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .client(client.build())
                .baseUrl(mUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mService = retrofit.create(IGankioService.class);
    }

    private static class SingletonHolder{
        private static final GankRetrofit gankService = new GankRetrofit();
    }

    public static GankRetrofit getGankRetrofit(){
        return SingletonHolder.gankService;
    }

    public IGankioService getService(){
        return mService;

    }

}
