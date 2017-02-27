package com.zl.mvpdemo.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ZL on 2017/2/27.
 */

public class DayData {

    @SerializedName("Android") private List<GankData> androidList;
    @SerializedName("休息视频") private List<GankData> movieList;
    @SerializedName("iOS") private List<GankData> iOSList;
    @SerializedName("福利") private List<GankData> GilrList;
    @SerializedName("拓展资源") private List<GankData> resList;
    @SerializedName("瞎推荐") private List<GankData> recommendList;
    @SerializedName("App") private List<GankData> appList;
    @SerializedName("前端") private List<GankData> webList;

    public List<GankData> getWebList() {
        return webList;
    }

    public void setWebList(List<GankData> webList) {
        this.webList = webList;
    }

    public List<GankData> getAndroidList() {
        return androidList;
    }

    public void setAndroidList(List<GankData> androidList) {
        this.androidList = androidList;
    }

    public List<GankData> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<GankData> movieList) {
        this.movieList = movieList;
    }

    public List<GankData> getiOSList() {
        return iOSList;
    }

    public void setiOSList(List<GankData> iOSList) {
        this.iOSList = iOSList;
    }

    public List<GankData> getGilrList() {
        return GilrList;
    }

    public void setGilrList(List<GankData> gilrList) {
        GilrList = gilrList;
    }

    public List<GankData> getResList() {
        return resList;
    }

    public void setResList(List<GankData> resList) {
        this.resList = resList;
    }

    public List<GankData> getRecommendList() {
        return recommendList;
    }

    public void setRecommendList(List<GankData> recommendList) {
        this.recommendList = recommendList;
    }

    public List<GankData> getAppList() {
        return appList;
    }

    public void setAppList(List<GankData> appList) {
        this.appList = appList;
    }
}
