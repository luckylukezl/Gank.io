package com.zl.mvpdemo.model.impl;

import com.zl.mvpdemo.model.model.IGirlPictureUrlModel;

/**
 * Created by ZL on 2017/2/19.
 */

public class GirlPictureUrlModel implements IGirlPictureUrlModel {

    String mUrl;

    public GirlPictureUrlModel(String url){
        mUrl = url;
    }

    @Override
    public String BuildUrlModel(int width) {
        return String.format("%s?imageView2/0/w/%s",mUrl , width);
    }
}
