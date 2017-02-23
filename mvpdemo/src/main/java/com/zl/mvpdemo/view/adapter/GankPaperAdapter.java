package com.zl.mvpdemo.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZL on 2017/2/21.
 */

public class GankPaperAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    private List<String> mTitleList;

    public GankPaperAdapter(FragmentManager fm , List<Fragment> fragments) {
        super(fm);
        mFragmentList = fragments;
        mTitleList =new ArrayList<>();
        mTitleList.add("福利");
        mTitleList.add("Android");
        mTitleList.add("IOS");
        mTitleList.add("视频");
        mTitleList.add("拓展");
        mTitleList.add("前端");

    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mTitleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position % mTitleList.size());
    }
}
