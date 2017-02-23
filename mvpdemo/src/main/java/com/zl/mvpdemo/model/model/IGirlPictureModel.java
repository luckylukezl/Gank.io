package com.zl.mvpdemo.model.model;

import com.zl.mvpdemo.presenter.listener.OnGirlPictureListener;

/**
 * Created by ZL on 2017/2/17.
 */

public interface IGirlPictureModel {
    void getGirlPicture(OnGirlPictureListener listener,String url);
}
