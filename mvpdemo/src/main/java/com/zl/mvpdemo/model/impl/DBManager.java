package com.zl.mvpdemo.model.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zl.mvpdemo.MyApplication;
import com.zl.mvpdemo.model.bean.dao.gankDataDb.DaoMaster;
import com.zl.mvpdemo.model.bean.dao.gankDataDb.DaoSession;
import com.zl.mvpdemo.model.bean.dao.gankDataDb.GankDataDB;
import com.zl.mvpdemo.model.bean.dao.gankDataDb.GankDataDBDao;
import com.zl.mvpdemo.model.bean.dao.imagesDB.ImagesDB;
import com.zl.mvpdemo.model.bean.dao.imagesDB.ImagesDBDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by ZL on 2017/2/28.
 */

public class DBManager {
    private static final String dbName = "save";
    private static DBManager mDBManager;
    private Context mContext;
    private  DaoMaster.DevOpenHelper openHelper;

    private DBManager(){
        mContext = MyApplication.getAppContext();
        openHelper = new DaoMaster.DevOpenHelper(mContext,dbName,null);
    }

    public static DBManager getInstance(){
        if(mDBManager == null){
            synchronized (DBManager.class){
                if(mDBManager == null){
                    mDBManager = new DBManager();
                }
            }
        }
        return mDBManager;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(mContext, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(mContext, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }

    /**
     * 插入一条记录
     *
     * @param gankDataDb
     */
    public void insertGankData(GankDataDB gankDataDb) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        GankDataDBDao dao = daoSession.getGankDataDBDao();
        dao.insertOrReplace(gankDataDb);
    }


    /**
     * 插入images
     *
     * @param imagesDB
     */
    public void insertGankDataList(List<ImagesDB> imagesDB) {
        if (imagesDB == null || imagesDB.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ImagesDBDao dao = daoSession.getImagesDBDao();
        dao.insertOrReplaceInTx(imagesDB);
    }

    /**
     * 删除一条记录
     *
     * @param gankDataDb
     */
    public void deleteGankData(GankDataDB gankDataDb) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        GankDataDBDao dao = daoSession.getGankDataDBDao();
        dao.delete(gankDataDb);
    }

    /**
     * 删除一条记录
     *
     * @param url
     */
    public void deleteGankData(String url) {
        List<GankDataDB> gankDataDBs = queryGankData(url);
        if(gankDataDBs!=null && gankDataDBs.size()>0){
            DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            GankDataDBDao dao = daoSession.getGankDataDBDao();
            dao.delete(gankDataDBs.get(0));
            deleteImages(gankDataDBs.get(0).getImage_id());
        }

    }

    /**
     * 删除images
     *
     * @param imageId
     */
    public void deleteImages(int imageId) {
        List<ImagesDB> list = queryImageList(imageId);
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ImagesDBDao dao = daoSession.getImagesDBDao();
        for(ImagesDB image:list){
            dao.delete(image);
        }

    }

    /**
     * 查询记录列表
     */
    public List<GankDataDB> queryGankDataList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        GankDataDBDao dao = daoSession.getGankDataDBDao();
        QueryBuilder<GankDataDB> qb = dao.queryBuilder();
        qb.orderAsc(GankDataDBDao.Properties.Type).orderDesc(GankDataDBDao.Properties.PublishedAt);
        List<GankDataDB> list = qb.list();
        return list;
    }

    /**
     * 查询一条记录
     */
    public List<GankDataDB> queryGankData(String url) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        GankDataDBDao dao = daoSession.getGankDataDBDao();
        QueryBuilder<GankDataDB> qb = dao.queryBuilder();
        qb.where(GankDataDBDao.Properties.Url.eq(url));
        qb.orderAsc(GankDataDBDao.Properties.Type).orderDesc(GankDataDBDao.Properties.PublishedAt);
        List<GankDataDB> list = qb.list();
        return list;
    }

    /**
     * 查询images
     */
    public List<ImagesDB> queryImageList(int imageId) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ImagesDBDao dao = daoSession.getImagesDBDao();
        QueryBuilder<ImagesDB> qb = dao.queryBuilder();
        qb.where(ImagesDBDao.Properties.Image_id.eq(imageId));
        List<ImagesDB> list = qb.list();
        return list;
    }

}
