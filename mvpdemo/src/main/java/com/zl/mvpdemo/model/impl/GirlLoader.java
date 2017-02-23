package com.zl.mvpdemo.model.impl;

import android.content.Context;

import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;
import com.zl.mvpdemo.model.model.IGirlPictureUrlModel;

import java.io.InputStream;

/**
 * Created by ZL on 2017/2/19.
 */

public class GirlLoader extends BaseGlideUrlLoader<IGirlPictureUrlModel> {


    public GirlLoader(Context context) {
        super(context);
    }

    public GirlLoader(ModelLoader<GlideUrl, InputStream> concreteLoader) {
        super(concreteLoader);
    }

    @Override
    protected String getUrl(IGirlPictureUrlModel model, int width, int height) {
        return model.BuildUrlModel(width);
    }

    public static class GirlLoaderFactory implements ModelLoaderFactory<IGirlPictureUrlModel , InputStream>{

        @Override
        public ModelLoader<IGirlPictureUrlModel, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new GirlLoader(factories.buildModelLoader(GlideUrl.class , InputStream.class));
        }

        @Override
        public void teardown() {

        }
    };



}
