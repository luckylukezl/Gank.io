package com.zl.mvpdemo.presenter.impl;

import com.zl.mvpdemo.model.bean.GirlData;
import com.zl.mvpdemo.model.constant.Constant;
import com.zl.mvpdemo.model.impl.IGirlImpl;
import com.zl.mvpdemo.model.model.IGirlModel;
import com.zl.mvpdemo.model.model.IGirlPictureModel;
import com.zl.mvpdemo.presenter.presenter.IGirlPicturePresenter;
import com.zl.mvpdemo.presenter.presenter.IGirlPresenter;
import com.zl.mvpdemo.presenter.listener.OnGirlListener;
import com.zl.mvpdemo.view.view.IGirlView;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZL on 2017/2/9.
 */

public class GirlPresentImpl implements IGirlPresenter , OnGirlListener {

    private IGirlModel mGirlModel;
    private IGirlView mGirView;
    private IGirlPicturePresenter mGirlPicturePresenter;

    private File[] mFiles;

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
    public void getGirlsFromLoc(int page) {

        mGirView.showLoading();
        List<GirlData> girlDatas = new ArrayList<>();

        File file = new File(Constant.SAVE_PATH);
        if(file.exists()){
            mFiles = file.listFiles();

            int i = 0;
            for(File f : mFiles){
                if(i>= 10*(page-1) && i < 10*page){
                    GirlData girlData = new GirlData();
                    girlData.setUrl(f.getAbsolutePath());
                    girlData.setPublishedAt(new Date());
                    girlDatas.add(girlData);

                }
                i++;

            }
        }

        mGirView.setGirlInfo(girlDatas);
        mGirView.getDataCompleted();
    }

    @Override
    public void init() {


    }

    @Override
    public void onDestory() {
        mGirlModel.onDestory();
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
