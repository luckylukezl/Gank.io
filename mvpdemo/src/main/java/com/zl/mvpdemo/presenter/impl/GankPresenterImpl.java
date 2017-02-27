package com.zl.mvpdemo.presenter.impl;

import com.zl.mvpdemo.model.bean.GankData;
import com.zl.mvpdemo.model.impl.GankImpl;
import com.zl.mvpdemo.model.model.IGankModel;
import com.zl.mvpdemo.presenter.listener.OnGankDataListener;
import com.zl.mvpdemo.presenter.presenter.IGankPresenter;
import com.zl.mvpdemo.view.view.IView;

import java.util.List;

/**
 * Created by ZL on 2017/2/22.
 */

public class GankPresenterImpl implements IGankPresenter , OnGankDataListener {

    private IGankModel mModel;
    private IView<List<GankData>> mView;

    public GankPresenterImpl(IView<List<GankData>> view){
        mModel = new GankImpl();
        mView = view;
    }


    @Override
    public void getData(String type, int page) {
        mModel.getGankData(this,type,page);
    }

    @Override
    public void getDayDatas(String date) {
        mModel.getDayData(this,date);
    }

    @Override
    public void onSuccess(List<GankData> gankDatas) {
        mView.setData(gankDatas);
    }

    @Override
    public void onFailed() {
        mView.showError();
    }

    @Override
    public void onStart() {
        mView.showLoading();
    }

    @Override
    public void onCompleted() {
        mView.getDataCompleted();
    }
}
