package com.zl.mvpdemo.view.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zl.mvpdemo.R;
import com.zl.mvpdemo.model.bean.GirlData;
import com.zl.mvpdemo.presenter.impl.GirlPresentImpl;
import com.zl.mvpdemo.presenter.presenter.IGirlPresenter;
import com.zl.mvpdemo.view.activity.GirlPictureActivity;
import com.zl.mvpdemo.view.adapter.GirlRecyclerAdatper;
import com.zl.mvpdemo.view.listener.OnGirlTouchListener;
import com.zl.mvpdemo.view.listener.RecyclerRefreshListener;
import com.zl.mvpdemo.view.view.IGirlView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ZL on 2017/2/22.
 */

public class GirlFragment extends BaseFragment implements IGirlView{

    @BindView(R.id.girl_recyclerView)
    RecyclerView mGirlRecyclerView;
    @BindView(R.id.girl_SwipeRefresh)
    SwipeRefreshLayout mGirlSwipeRefresh;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton mFloatingActionButton;

    private IGirlPresenter mGirlPresenter;
    private GirlRecyclerAdatper mGirlAdapter;
    private List<GirlData> mGirlUrl;
    private List<GirlData> mNewDatas;

    private RecyclerRefreshListener mRefreshListener;


    @Override
    public int getContentViewId() {
        return R.layout.activity_gilr;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        init();
    }

    private void init(){
        mGirlUrl = new ArrayList<>();
        mNewDatas = new ArrayList<>();
        mGirlAdapter = new GirlRecyclerAdatper(mGirlUrl,mContext);
        mGirlAdapter.setGirlTouchListener(getOnGirlTouchListener());

        mGirlPresenter = new GirlPresentImpl(this);

        mGirlPresenter.init();

        iniView();
        initData();
    }




    private void iniView() {

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //防止位置跳动
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRefreshListener = new RecyclerRefreshListener(layoutManager) {
            @Override
            public void onRefresh() {
                if(!isLoading()){
                    mGirlUrl.clear();
                    mGirlPresenter.getGirls(1);
                    setCurrentPage(1);
                }
            }

            @Override
            public void onDateChanged() {
                if(mNewDatas.size()>0){
                    mGirlAdapter.addDataList(mNewDatas);
                    setUpdated(true);
                    mNewDatas.clear();
                }
            }

            @Override
            public void onLoadMore(int currentPage) {
                mGirlPresenter.getGirls(currentPage);
            }

        };


        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mGirlRecyclerView.setLayoutManager(layoutManager);
        mGirlRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mGirlRecyclerView.setAdapter(mGirlAdapter);
        mGirlRecyclerView.addOnScrollListener(mRefreshListener);

        mGirlSwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorBlue));
        mGirlSwipeRefresh.setOnRefreshListener(mRefreshListener);

    }


    private void initData() {
        if(mGirlUrl.size() == 0){
            mGirlPresenter.getGirls(1);
        }
    }

    private OnGirlTouchListener getOnGirlTouchListener() {
        return new OnGirlTouchListener() {
            @Override
            public void onTouch(final GirlData girlData, final View view) {
                if (girlData == null) return;
                startPictureActivity(girlData, view);
            }
        };
    }

    private void startPictureActivity(GirlData girlData, View transitView) {
        Intent intent = new Intent(mContext , GirlPictureActivity.class);
        intent.putExtra(GirlPictureActivity.EXTRA_URL , girlData.getUrl());
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(transitView,
                transitView.getWidth() / 2, transitView.getHeight() / 2, 0, 0);
        ActivityCompat.startActivity(getActivity(), intent, compat.toBundle());
    }


    @Override
    public void setGirlInfo(List<GirlData> datas) {
        //Log.i("zlTag",girlInfo.getResults().get(0).getType() + "");
        //mGirlAdapter.addDataList(datas);
        if(mRefreshListener.getIsScrolling()){
            mNewDatas.addAll(datas);
        }else{
            mGirlAdapter.addDataList(datas);
            mRefreshListener.setUpdated(true);
        }

    }

    @Override
    public void setGirlData(GirlData girlData) {

        //mGirlAdapter.addData(girlData);
    }

    @Override
    public void showLoading() {
        //mTextView.setText("loading");
        mGirlSwipeRefresh.setRefreshing(true);
        mRefreshListener.onStart();
    }

    @Override
    public void getDataCompleted() {
        //mTextView.setText("hideLoading");
        mGirlSwipeRefresh.setRefreshing(false);
        mRefreshListener.onCompleted();
    }

    @Override
    public void showError() {

    }
}
