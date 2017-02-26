package com.zl.mvpdemo.presenter.impl;

import android.graphics.Bitmap;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.orhanobut.logger.Logger;
import com.zl.mvpdemo.MyApplication;
import com.zl.mvpdemo.model.constant.Constant;
import com.zl.mvpdemo.presenter.presenter.IManagerPresenter;
import com.zl.mvpdemo.view.activity.GirlPictureActivity;
import com.zl.mvpdemo.view.view.IView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ZL on 2017/2/25.
 */

public class ManagerPresenter implements IManagerPresenter {

    private IView<String> mView;

    public ManagerPresenter(IView<String> view){
        mView = view;
    }

    @Override
    public void save(final String url) {
        run(url , Constant.SAVE_PATH);

    }

    @Override
    public void share(String url) {
        run(url , Constant.SHARE_PATH);
    }

    @Override
    public void delete(String filePath) {
        File file = new File(Constant.SAVE_PATH);
        if(file.exists()){
            File[] files = file.listFiles();

            for(File f:files){
                if(f.getAbsolutePath().equals(filePath)){
                    f.delete();
                    break;
                }
            }
        }
        mView.setData("delete");
    }

    private String newFileName(String url){
        String[] urls = url.split("/");
        String s = urls[urls.length - 1];
        return s + ( s.endsWith(".jpg")?"" : ".jpg");
    }

    private void run(final  String url , String path){
        File appDir = new File(path);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }

        String fileName = newFileName(url);
        final File currentFile = new File(appDir, fileName);
        if(currentFile.exists()) {
            Logger.d("exists");
            mView.setData(currentFile.getAbsolutePath());
            return;
        }
//        }else {
//            try {
//                currentFile.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {



                Bitmap bitmap = null;
                try {
                    bitmap = Glide.with(MyApplication.getAppContext())
                            .load(url)
                            .asBitmap()
                            .into(Target.SIZE_ORIGINAL , Target.SIZE_ORIGINAL)
                            .get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(currentFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                }catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onNext(currentFile.getAbsolutePath());
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        mView.setData(s);
                    }
                });
    }
}
