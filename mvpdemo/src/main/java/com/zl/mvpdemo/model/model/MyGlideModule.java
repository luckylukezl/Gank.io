package com.zl.mvpdemo.model.model;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.module.GlideModule;
import com.zl.mvpdemo.MyApplication;

import java.io.File;

/**
 * Created by ZL on 2017/2/19.
 */

public class MyGlideModule implements GlideModule {
    @Override
    public void applyOptions(final Context context, GlideBuilder builder) {

            builder.setDiskCache(new DiskCache.Factory() {
                @Override
                public DiskCache build() {
                    File file = new File(context.getExternalCacheDir() ,
                            "girl_cache");
                    if(!file.exists()){
                        file.mkdirs();
                    }
                    //Log.i("zlTag",file.getAbsolutePath());
                    return DiskLruCacheWrapper.get(file,1024 * 1024 * 100);
                }
            });
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
