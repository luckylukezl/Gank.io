package com.zl.mvpdemo.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zl.mvpdemo.R;

/**
 * Created by ZL on 2017/2/20.
 */

public class GirlPictureActivity extends Activity {

    public static String EXTRA_URL = "URL";

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        String url = getIntent().getStringExtra(EXTRA_URL);
        initView();

        Log.i("zlTag",url);
        Glide.with(this)
                .load(url)
                .error(R.mipmap.material_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mImageView);
    }

    private void initView() {
        mImageView = (ImageView)findViewById(R.id.picture_imageView);
    }


}
