package com.zl.mvpdemo.view.activity;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zl.mvpdemo.R;
import com.zl.mvpdemo.model.constant.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZL on 2017/2/20.
 */

public class GirlPictureActivity extends AppCompatActivity {

    public static final String TRANSIT_PIC = "picture";
    public static String EXTRA_URL = "URL";
    public static final String EXTRA_RES = "res";

    @BindView(R.id.picture_imageView)
    ImageView pictureImageView;

    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ButterKnife.bind(this);

        ViewCompat.setTransitionName(pictureImageView, TRANSIT_PIC);

        initView();

        Glide.with(this)
                .load(mUrl)
                .thumbnail((float)0.3)
                .error(R.mipmap.material_img)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(pictureImageView);
    }

    private void initView() {

        mUrl = getIntent().getStringExtra(EXTRA_URL);
    }


}
