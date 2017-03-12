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
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.orhanobut.logger.Logger;
import com.zl.mvpdemo.R;
import com.zl.mvpdemo.model.bean.GirlData;
import com.zl.mvpdemo.model.bean.GirlPicture;
import com.zl.mvpdemo.presenter.impl.GirlPresentImpl;
import com.zl.mvpdemo.presenter.impl.UtilPresenterImpl;
import com.zl.mvpdemo.presenter.presenter.IGirlPresenter;
import com.zl.mvpdemo.presenter.presenter.UtilPresenter;
import com.zl.mvpdemo.view.activity.GirlPictureActivity;
import com.zl.mvpdemo.view.adapter.GirlRecyclerAdatper;
import com.zl.mvpdemo.view.listener.OnGirlTouchListener;
import com.zl.mvpdemo.view.listener.RecyclerRefreshListener;
import com.zl.mvpdemo.view.view.IGirlView;
import com.zl.mvpdemo.view.view.IView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ZL on 2017/2/22.
 */

public class GirlFragment extends BaseFragment implements IGirlView{

    public static final String ARG_SAVE = "isSave";

    @BindView(R.id.girl_recyclerView)
    RecyclerView mGirlRecyclerView;
    @BindView(R.id.girl_SwipeRefresh)
    SwipeRefreshLayout mGirlSwipeRefresh;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton mFloatingActionButton;

    private IGirlPresenter mGirlPresenter;
    private UtilPresenter mUtilPresenter;
    private GirlRecyclerAdatper mGirlAdapter;
    private List<GirlData> mGirlUrl;
    private List<GirlData> mNewDatas;

    private RecyclerRefreshListener mRefreshListener;

    private boolean isSavedPicture = false;
    private boolean isRefresh = false;

    public static GirlFragment newInstance(boolean isSave){

        Bundle args = new Bundle();
        args.putBoolean(ARG_SAVE,isSave);
        GirlFragment fragment = new GirlFragment();
        fragment.setArguments(args);
        return fragment;
    }

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
        initUtilPresenter();

        mGirlPresenter.init();

        isSavedPicture = getArguments().getBoolean(ARG_SAVE);
        mGirlAdapter.setSavePicture(isSavedPicture);

        iniView();
        initData();
    }

    private void initUtilPresenter() {
        mUtilPresenter = new UtilPresenterImpl(new IView<GirlData>() {
            @Override
            public void setData(GirlData girlData) {
                GirlData last = mGirlUrl.get(0);
                if(!last.get_id().equals(girlData.get_id())){
                    mGirlUrl.add(0,last);
                    mGirlAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void showLoading() {
                mRefreshListener.onStart();
                mGirlSwipeRefresh.setRefreshing(true);
            }

            @Override
            public void getDataCompleted() {
                mGirlSwipeRefresh.setRefreshing(false);
                mRefreshListener.setLoading(false);
            }

            @Override
            public void showError() {

            }
        });
    }


    private void iniView() {

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //防止位置跳动
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRefreshListener = new RecyclerRefreshListener(layoutManager) {
            @Override
            public void onRefresh() {
                if(!isLoading() && !isSavedPicture){
                    mUtilPresenter.getLastPicture();
                }

                if(isSavedPicture){
                    mGirlSwipeRefresh.setRefreshing(false);
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
                getData(currentPage);
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

    public void updateDate(){
        getData(1);
    }

    private void initData() {
        if(mGirlUrl.size() == 0){
            getData(1);
        }
    }

    public void getData(int page){

        mRefreshListener.setCurrentPage(page);
        if(isSavedPicture){
            mGirlPresenter.getGirlsFromLoc(page);
        }else {
            mGirlPresenter.getGirls(page);
        }


    }

    private OnGirlTouchListener getOnGirlTouchListener() {
        return new OnGirlTouchListener() {
            @Override
            public void onTouch(final GirlData girlData, final View view , int position) {
                if (girlData == null) return;
                startPictureActivity(mGirlUrl, view , position);
            }
        };
    }

    private void startPictureActivity(List<GirlData> girlDatas, View transitView , int position) {
        ArrayList<String> images = new ArrayList<>();
        for(GirlData data : girlDatas){
            images.add(data.getUrl());
        }
        Intent intent = new Intent(mContext , GirlPictureActivity.class);
        intent.putStringArrayListExtra(GirlPictureActivity.EXTRA_GIRLS , images);
        intent.putExtra(GirlPictureActivity.EXTRA_POSITION , position);
        intent.putExtra(GirlPictureActivity.EXTRA_ISSAVE,isSavedPicture);
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(transitView,
                transitView.getWidth() / 2, transitView.getHeight() / 2, 0, 0);
        ActivityCompat.startActivityForResult(getActivity(), intent, 1 , compat.toBundle());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 100){
            ArrayList<String> urls = data.getStringArrayListExtra(GirlPictureActivity.EXTRA_RESULT);
            Logger.d(urls);
            if(urls==null)return;
            for(String s:urls){
                for(int i=mGirlUrl.size() - 1;i>=0;i--){
                    if(mGirlUrl.get(i).getUrl().equals(s)){
                        mGirlUrl.remove(i);
                    }
                }
            }
            mGirlAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGirlPresenter.onDestory();
    }

    @Override
    public void setGirlInfo(List<GirlData> datas) {

        if(isRefresh){
            mGirlUrl.clear();
        }

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
