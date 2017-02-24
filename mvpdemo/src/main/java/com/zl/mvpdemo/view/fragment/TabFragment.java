package com.zl.mvpdemo.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zl.mvpdemo.R;
import com.zl.mvpdemo.view.adapter.GankPaperAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ZL on 2017/2/23.
 */

public class TabFragment extends Fragment {

    @BindView(R.id.bar_tabLayout)
    TabLayout barTabLayout;
    @BindView(R.id.bar_viewPager)
    ViewPager barViewPager;

    private Unbinder mUnbinder;

    private List<Fragment> mFragments;
    private GankPaperAdapter mPaperAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tablayout, container, false);

        mUnbinder = ButterKnife.bind(this, view);
        initTab();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    private void initTab() {
        mFragments = new ArrayList<>();

        mFragments.add(GankFragment.newInstance("Android"));
        mFragments.add(GankFragment.newInstance("iOS"));
        mFragments.add(GankFragment.newInstance("休息视频"));
        mFragments.add(GankFragment.newInstance("拓展资源"));
        mFragments.add(GankFragment.newInstance("前端"));


        mPaperAdapter = new GankPaperAdapter(getActivity().getSupportFragmentManager(), mFragments);
        barViewPager.setAdapter(mPaperAdapter);
        barViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(barTabLayout));

        barTabLayout.setupWithViewPager(barViewPager);

    }
}
