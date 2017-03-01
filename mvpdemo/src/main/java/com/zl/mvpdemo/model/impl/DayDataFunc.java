package com.zl.mvpdemo.model.impl;

import com.orhanobut.logger.Logger;
import com.zl.mvpdemo.model.bean.DayInfo;
import com.zl.mvpdemo.model.bean.GankData;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by ZL on 2017/2/27.
 */

public class DayDataFunc implements Func1<DayInfo,List<GankData>> {
    @Override
    public List<GankData> call(DayInfo dayInfo) {
        if(dayInfo.getError()){
            throw new GankException();
        }



        List<String> category = dayInfo.getCategory();
        List<GankData> gankDatas = new ArrayList<>();
        //Logger.d(category);

        if(category.contains("福利")){
            gankDatas.addAll(dayInfo.getResults().getGilrList());
        }
        if(category.contains("Android"))gankDatas.addAll(dayInfo.getResults().getAndroidList());
        if(category.contains("iOS"))gankDatas.addAll(dayInfo.getResults().getiOSList());
        if(category.contains("前端"))gankDatas.addAll(dayInfo.getResults().getWebList());
        if(category.contains("App"))gankDatas.addAll(dayInfo.getResults().getAppList());
        if(category.contains("拓展资源"))gankDatas.addAll(dayInfo.getResults().getResList());
        if(category.contains("瞎推荐"))gankDatas.addAll(dayInfo.getResults().getRecommendList());
        if(category.contains("休息视频"))gankDatas.addAll(dayInfo.getResults().getMovieList());


        return gankDatas;
    }
}
