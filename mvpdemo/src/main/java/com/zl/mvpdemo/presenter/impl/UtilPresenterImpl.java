package com.zl.mvpdemo.presenter.impl;

import com.zl.mvpdemo.model.bean.GirlData;
import com.zl.mvpdemo.model.impl.IGirlImpl;
import com.zl.mvpdemo.model.model.IGirlModel;
import com.zl.mvpdemo.presenter.listener.OnGirlListener;
import com.zl.mvpdemo.presenter.presenter.UtilPresenter;
import com.zl.mvpdemo.view.view.IView;

import java.util.List;

/**
 * Created by ZL on 2017/2/25.
 */

public class UtilPresenterImpl implements UtilPresenter , OnGirlListener{

    private IView<GirlData> mView;
    private IGirlModel mModel;

    private List<GirlData> mGirls;

    public UtilPresenterImpl(IView<GirlData> view){
        mView = view;
        mModel = new IGirlImpl();
    }

    @Override
    public void getLastPicture() {
        mModel.getGirls(this,1,1);

    }

    @Override
    public void onSuccess(List<GirlData> info) {
        if(info != null && info.size() > 0){
            mView.setData(info.get(0));
        }

    }

    @Override
    public void onSuccess(GirlData girlData) {

    }

    @Override
    public void onFailed() {

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
