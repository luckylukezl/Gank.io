package com.zl.mvpdemo.model.impl;

import com.orhanobut.logger.Logger;
import com.zl.mvpdemo.MyApplication;
import com.zl.mvpdemo.model.constant.NetWorkUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ZL on 2017/2/25.
 */

public class CacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        int maxAge = 60*60; // 有网络时 设置缓存超时时间1个小时
        int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
        Request request = chain.request();
        if (NetWorkUtil.isNetworkAvailable(MyApplication.getAppContext())) {
            request= request.newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK)//有网络时只从网络获取
                    .build();
        }else {
            request= request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)//无网络时只从缓存中读取
                    .build();
        }

        Response response = chain.proceed(request);
        if (NetWorkUtil.isNetworkAvailable(MyApplication.getAppContext())) {
            response= response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            response= response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        //Logger.i(response.headers().toString());
        return response;
    }
}
