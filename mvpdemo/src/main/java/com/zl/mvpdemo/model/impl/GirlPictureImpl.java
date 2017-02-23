package com.zl.mvpdemo.model.impl;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zl.mvpdemo.MyApplication;
import com.zl.mvpdemo.model.model.IGirlPictureModel;
import com.zl.mvpdemo.presenter.listener.OnGirlPictureListener;

/**
 * Created by ZL on 2017/2/17.
 */

public class GirlPictureImpl implements IGirlPictureModel{

    @Override
    public void getGirlPicture(final OnGirlPictureListener listener, String url) {
        Glide.with(MyApplication.getAppContext())
                .load(url)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        listener.onPictrueSuccess(resource.getIntrinsicHeight());
                    }
                });
    }
}
