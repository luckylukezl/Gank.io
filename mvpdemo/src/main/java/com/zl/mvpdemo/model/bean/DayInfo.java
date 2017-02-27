package com.zl.mvpdemo.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ZL on 2017/2/27.
 */

public class DayInfo extends GankInfo<DayData> {

    @SerializedName("category") private List<String> category ;

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }
}
