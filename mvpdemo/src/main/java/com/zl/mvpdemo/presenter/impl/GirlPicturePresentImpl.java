package com.zl.mvpdemo.presenter.impl;

import com.zl.mvpdemo.model.impl.GirlPictureImpl;
import com.zl.mvpdemo.model.model.IGirlPictureModel;
import com.zl.mvpdemo.presenter.listener.OnGirlPictureListener;
import com.zl.mvpdemo.presenter.presenter.IGirlPicturePresenter;
import com.zl.mvpdemo.presenter.presenter.IGirlPresenter;

/**
 * Created by ZL on 2017/2/17.
 */

public class GirlPicturePresentImpl implements IGirlPicturePresenter,OnGirlPictureListener{

    private IGirlPictureModel mGirlPictureModel;
    private IGirlPresenter mGirlPresenter;

    public GirlPicturePresentImpl(){
        mGirlPictureModel = new GirlPictureImpl();
    }

    @Override
    public int getPictureHeight(String url) {
        mGirlPictureModel.getGirlPicture(this,url);
        return 0;
    }

    @Override
    public void onPictrueSuccess(int height) {

    }
}
