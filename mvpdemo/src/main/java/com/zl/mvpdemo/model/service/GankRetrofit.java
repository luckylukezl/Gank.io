package com.zl.mvpdemo.model.service;


import com.zl.mvpdemo.MyApplication;
import com.zl.mvpdemo.model.impl.CacheInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ZL on 2017/2/19.
 */

public class GankRetrofit {

    private String mUrl = "http://gank.io/api/data/";

    private static final int DEFAULT_TIMEOUT = 5;

    private IGankioService mService;

    //缓存路径
    private File mCacheFile;
    private Cache mCache ;


    private GankRetrofit(){
        mCacheFile = new File(MyApplication.getAppContext().getCacheDir().getAbsolutePath(), "HttpCache");
        mCache = new Cache(mCacheFile, 1024 * 1024 * 10);//缓存文件为10MB

        OkHttpClient.Builder client =
                new OkHttpClient.Builder()
                .addInterceptor(new CacheInterceptor())
                .cache(mCache)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

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
