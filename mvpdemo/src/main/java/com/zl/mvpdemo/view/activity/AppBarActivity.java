package com.zl.mvpdemo.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import com.zl.mvpdemo.R;
import com.zl.mvpdemo.R2;
import com.zl.mvpdemo.model.constant.Constant;
import com.zl.mvpdemo.view.adapter.GankPaperAdapter;
import com.zl.mvpdemo.view.fragment.GankFragment;
import com.zl.mvpdemo.view.fragment.GirlFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZL on 2017/2/13.
 */

public class AppBarActivity extends AppCompatActivity {


    @BindView(R2.id.bar_info)
    ImageView barInfo;
    @BindView(R2.id.bar_logo)
    ImageView barLogo;
    @BindView(R2.id.textView)
    TextView textView;
    @BindView(R2.id.textView3)
    TextView textView3;
    @BindView(R.id.bar_tabLayout)
    TabLayout barTabLayout;
    @BindView(R.id.bar_viewPager)
    ViewPager barViewPager;

    private List<Fragment> mFragments;
    private GankPaperAdapter mPaperAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appbar);
        ButterKnife.bind(this);
        init();

        initTab();
    }



    private void init() {

        //获取屏幕尺寸
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Constant.SCREEN_HEIGHT = metrics.heightPixels;
        Constant.SCREEN_WIDTH = metrics.widthPixels;
        Constant.SCREEN_DENSITY = metrics.density;

    }

    private void initTab() {
        mFragments = new ArrayList<>();

        mFragments.add(new GirlFragment());
        mFragments.add(GankFragment.newInstance("Android"));
        mFragments.add(GankFragment.newInstance("iOS"));
        mFragments.add(GankFragment.newInstance("休息视频"));
        mFragments.add(GankFragment.newInstance("拓展资源"));
        mFragments.add(GankFragment.newInstance("前端"));


        mPaperAdapter = new GankPaperAdapter(getSupportFragmentManager(),mFragments);
        barViewPager.setAdapter(mPaperAdapter);
        barViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(barTabLayout));

        barTabLayout.setupWithViewPager(barViewPager);

    }

}
