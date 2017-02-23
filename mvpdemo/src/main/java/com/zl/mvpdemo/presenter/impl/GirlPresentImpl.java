package com.zl.mvpdemo.presenter.impl;

import com.zl.mvpdemo.model.bean.GirlData;
import com.zl.mvpdemo.model.impl.IGirlImpl;
import com.zl.mvpdemo.model.model.IGirlModel;
import com.zl.mvpdemo.model.model.IGirlPictureModel;
import com.zl.mvpdemo.presenter.presenter.IGirlPicturePresenter;
import com.zl.mvpdemo.presenter.presenter.IGirlPresenter;
import com.zl.mvpdemo.presenter.listener.OnGirlListener;
import com.zl.mvpdemo.view.view.IGirlView;

import java.util.List;

/**
 * Created by ZL on 2017/2/9.
 */

public class GirlPresentImpl implements IGirlPresenter , OnGirlListener {

    private IGirlModel mGirlModel;
    private IGirlView mGirView;
    private IGirlPicturePresenter mGirlPicturePresenter;

    public GirlPresentImpl(IGirlView girlView){
        mGirView = girlView;
        mGirlModel = new IGirlImpl();
        mGirlPicturePresenter = new GirlPicturePresentImpl();
    }


    @Override
    public void getGirls(int page) {
        mGirlModel.getGirls(this,page);
    }

    @Override
    public void init() {


    }

    @Override
    public void onSuccess(List<GirlData> datas) {
        //获取到了girlInfo，缓存图片
//        for(GirlData girlData: info.getResults()){
//            mGirlPicturePresenter.getPictureHeight();
//        }

        mGirView.setGirlInfo(datas);
    }

    @Override
    public void onSuccess(GirlData girlData) {
        mGirView.setGirlData(girlData);
    }

    @Override
    public void onFailed() {
        mGirView.showError();
    }

    @Override
    public void onStart() {
        mGirView.showLoading();
    }

    @Override
    public void onCompleted() {
        mGirView.getDataCompleted();
    }
}
