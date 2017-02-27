package com.zl.mvpdemo.model.service;

import com.zl.mvpdemo.model.bean.DayData;
import com.zl.mvpdemo.model.bean.DayInfo;
import com.zl.mvpdemo.model.bean.GankData;
import com.zl.mvpdemo.model.bean.GankInfo;
import com.zl.mvpdemo.model.bean.GirlData;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by ZL on 2017/2/19.
 */

public interface IGankioService {
    @GET("data/福利/{count}/{page}")
    Observable<GankInfo<List<GirlData>>> getGirls(
        @Path("count") int count
        , @Path("page") int page);

    @GET("data/{type}/{count}/{page}")
    Observable<GankInfo<List<GankData>>> getGankData(
            @Path("type") String type,
            @Path("count") int count,
            @Path("page") int page
    );

    @GET("day/{date}")
    Observable<DayInfo> getDayData(
            @Path("date") String date
    );
}
