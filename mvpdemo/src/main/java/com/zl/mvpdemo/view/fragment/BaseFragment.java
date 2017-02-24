package com.zl.mvpdemo.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zl.mvpdemo.MainActivity;
import com.zl.mvpdemo.model.bean.GirlData;
import com.zl.mvpdemo.view.activity.GirlPictureActivity;
import com.zl.mvpdemo.view.listener.OnGirlTouchListener;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ZL on 2017/2/23.
 */

public abstract class BaseFragment extends Fragment {
    public abstract int getContentViewId();
    protected Context mContext;
    protected View mRootView;
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView =inflater.inflate(getContentViewId(),container,false);
        mUnbinder = ButterKnife.bind(this,mRootView);//绑定framgent
        this.mContext = getActivity();
        initAllMembersView(savedInstanceState);
        return mRootView;
    }

    protected abstract void initAllMembersView(Bundle savedInstanceState);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();//解绑
    }

}
