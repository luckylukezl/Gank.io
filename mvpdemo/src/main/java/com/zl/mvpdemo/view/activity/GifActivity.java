package com.zl.mvpdemo.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.zl.mvpdemo.R;
import com.zl.mvpdemo.view.adapter.GifPagerAdapter;
import com.zl.mvpdemo.view.fragment.GifFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZL on 2017/2/24.
 */

public class GifActivity extends AppCompatActivity {

    public static final String EXTRA_LIST = "list";
    public static final String EXTRA_PAGE = "page";

    @BindView(R.id.viewPager_gif)
    ViewPager viewPagerGif;

    private GifPagerAdapter mPagerAdapter;
    private List<Fragment> mFragments;

    private List<String> mList;
    private int mPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        ButterKnife.bind(this);

        init();

    }

    private void init() {
        mList = getIntent().getStringArrayListExtra(EXTRA_LIST);
        mPage = getIntent().getIntExtra(EXTRA_PAGE,0);

        mFragments = new ArrayList<>();
        for(int i=0;i<mList.size();i++){
            mFragments.add(GifFragment.newInstance((1+i) + "/" + mList.size() , mList.get(i)));
        }
        mPagerAdapter = new GifPagerAdapter(getSupportFragmentManager() , mFragments);

        viewPagerGif.setAdapter(mPagerAdapter);
        viewPagerGif.setCurrentItem(mPage);
    }


}
