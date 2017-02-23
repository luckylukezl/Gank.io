package com.zl.mvpdemo.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;

import com.zl.mvpdemo.R;
import com.zl.mvpdemo.model.constant.Constant;
import com.zl.mvpdemo.view.fragment.GirlFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZL on 2017/2/9.
 */

public class GirlActivity extends BaseActivity {

    @BindView(R.id.container)
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);
        init();

    }

    private void init() {

        //获取屏幕尺寸
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Constant.SCREEN_HEIGHT = metrics.heightPixels;
        Constant.SCREEN_WIDTH = metrics.widthPixels;
        Constant.SCREEN_DENSITY = metrics.density;

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container , new GirlFragment());
        transaction.commit();



    }

}
