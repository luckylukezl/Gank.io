package com.zl.mvpdemo.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zl.mvpdemo.R;
import com.zl.mvpdemo.model.bean.GirlData;
import com.zl.mvpdemo.model.constant.Constant;
import com.zl.mvpdemo.presenter.impl.GirlPresentImpl;
import com.zl.mvpdemo.presenter.presenter.IGirlPresenter;
import com.zl.mvpdemo.view.adapter.GirlRecyclerAdatper;
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

    private RecyclerRefreshListener mRefreshListener;

    // 标志位，标志已经初始化完成。
    private boolean isPrepared;

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
        mGirlAdapter = new GirlRecyclerAdatper(mGirlUrl,mContext);

        mGirlPresenter = new GirlPresentImpl(this);

        mGirlPresenter.init();

        iniView();
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        initData();
    }

    private void iniView() {

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
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
            public void onLoadMore(int currentPage) {
                mGirlPresenter.getGirls(currentPage);
            }

            @Override
            public void onRefreshLayout() {
                try{
                    mGirlAdapter.notifyDataSetChanged();
                }catch (IllegalStateException e){
                    //Log.i("zlTag",e.toString());
                }

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


    @Override
    public void setGirlInfo(List<GirlData> datas) {
        //Log.i("zlTag",girlInfo.getResults().get(0).getType() + "");
        mGirlAdapter.addDataList(datas);
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
