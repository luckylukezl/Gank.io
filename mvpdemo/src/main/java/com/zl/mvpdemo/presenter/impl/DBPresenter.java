package com.zl.mvpdemo.presenter.impl;

import android.content.Context;

import com.zl.mvpdemo.model.bean.GankData;
import com.zl.mvpdemo.model.bean.dao.gankDataDb.GankDataDB;
import com.zl.mvpdemo.model.bean.dao.imagesDB.ImagesDB;
import com.zl.mvpdemo.model.impl.DBManager;
import com.zl.mvpdemo.presenter.presenter.IDBPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZL on 2017/3/1.
 */

public class DBPresenter implements IDBPresenter<GankData> {

    private Context mContext;

    public DBPresenter(Context context){
        mContext = context;
    }


    @Override
    public void saveToDB(GankData gankData) {
        if(gankData == null)return;
        GankDataDB gankDataDB = new GankDataDB();
        List<ImagesDB> imagesDBs = new ArrayList<>();
        toDB(gankData , gankDataDB , imagesDBs);

        DBManager dbManager = DBManager.getInstance(mContext);
        dbManager.insertGankData(gankDataDB);
        dbManager.insertGankDataList(imagesDBs);
    }

    @Override
    public void getDatas(List<GankData> gankDatas) {
        if(gankDatas == null)gankDatas = new ArrayList<>();
        DBManager dbManager = DBManager.getInstance(mContext);
        List<GankDataDB> gankDataDBList = dbManager.queryGankDataList();
        for(GankDataDB gankDataDB : gankDataDBList){
            List<ImagesDB> imagesDBs =  dbManager.queryImageList(gankDataDB.getImage_id());
            GankData gankData = new GankData();
            toData(gankDataDB ,imagesDBs,gankData);
            gankDatas.add(gankData);
        }


    }

    @Override
    public void getData(GankData gankData) {


    }

    @Override
    public void deleteData(GankData gankData) {
        DBManager dbManager = DBManager.getInstance(mContext);
        dbManager.deleteGankData(gankData.getUrl());
    }

    @Override
    public void onDestory() {
        mContext = null;
    }

    private void toDB(GankData gankData , GankDataDB dataDB , List<ImagesDB> imagesDBs){
        int image_id = (int) System.currentTimeMillis();

        dataDB.setDesc(gankData.getDesc());
        dataDB.setPublishedAt(gankData.getPublishedAt());
        dataDB.setType(gankData.getType());
        dataDB.setUrl(gankData.getUrl());
        dataDB.setWho(gankData.getWho());
        dataDB.setImage_id(image_id);
        dataDB.setId(null);

        if(gankData.getImages() == null)return;
        for(String s:gankData.getImages()){
            ImagesDB imagesDB = new ImagesDB();
            imagesDB.setId((long) image_id);
            imagesDB.setImage_id(image_id);
            imagesDB.setImage_url(s);
            imagesDBs.add(imagesDB);
        }

    }

    private void toData(GankDataDB dataDB , List<ImagesDB> imagesDBs , GankData gankData){
        gankData.setDesc(dataDB.getDesc());
        gankData.setPublishedAt(dataDB.getPublishedAt());
        gankData.setType(dataDB.getType());
        gankData.setUrl(dataDB.getUrl());
        gankData.setWho(dataDB.getWho());

        List<String> images = new ArrayList<>();
        for(ImagesDB imagesDB : imagesDBs){
            images.add(imagesDB.getImage_url());
        }

        gankData.setImages(images);
    }
}
