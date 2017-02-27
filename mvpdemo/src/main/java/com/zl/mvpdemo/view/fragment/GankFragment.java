package com.zl.mvpdemo.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.zl.mvpdemo.R;
import com.zl.mvpdemo.model.bean.GankData;
import com.zl.mvpdemo.model.bean.GirlData;
import com.zl.mvpdemo.presenter.impl.GankPresenterImpl;
import com.zl.mvpdemo.presenter.presenter.IGankPresenter;
import com.zl.mvpdemo.view.adapter.GankRecyclerAdapter;
import com.zl.mvpdemo.view.listener.RecyclerRefreshListener;
import com.zl.mvpdemo.view.view.IView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ZL on 2017/2/21.
 */

public class GankFragment extends BaseLazyLoadFragment implements IView<List<GankData>> {

    public static final String ARGS_TYPE = "args_type";

    @BindView(R.id.gank_recyclerView)
    RecyclerView gankRecyclerView;
    @BindView(R.id.gank_SwipeRefresh)
    SwipeRefreshLayout gankSwipeRefresh;

    private IGankPresenter mPresenter;
    private List<GankData> mGankDatas;
    private List<GankData> mNewDatas;
    private GankRecyclerAdapter mGankAdapter;

    private String mType;

    private RecyclerRefreshListener mRefreshListener;

    // 标志位，标志已经初始化完成。
    private boolean isPrepared = false;

    public static GankFragment newInstance(String type) {

        Bundle args = new Bundle();
        args.putString(ARGS_TYPE, type);
        GankFragment fragment = new GankFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_gank;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        if(mGankDatas.size() == 0){
            Log.i("zlTag","lazy");
            getDate(1);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        lazyLoad();
    }

    private void init(){
        mType = getArguments().getString(ARGS_TYPE);

        mGankDatas = new ArrayList<>();
        mNewDatas = new ArrayList<>();
        mGankAdapter = new GankRecyclerAdapter(mGankDatas,mContext);

        mPresenter = new GankPresenterImpl(this);

        iniView();
        isPrepared = true;
        //lazyLoad();
    }

    private void iniView() {

        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRefreshListener = new RecyclerRefreshListener(layoutManager) {
            @Override
            public void onRefresh() {
                if(!isLoading()){
                    mGankDatas.clear();
                    Log.i("zlTag","refresh");
                    getDate(1);
                    setCurrentPage(1);
                }
            }

            @Override
            public void onDateChanged() {
                if(mNewDatas.size() > 0){
                    addDate(mNewDatas);
                    mNewDatas.clear();
                }
            }

            @Override
            public void onLoadMore(int currentPage) {
                Log.i("zlTag","onLoadMore");
                getDate(currentPage);
            }

        };

        gankRecyclerView.setLayoutManager(layoutManager);
        gankRecyclerView.setItemAnimator(new DefaultItemAnimator());
        gankRecyclerView.setAdapter(mGankAdapter);
        gankRecyclerView.addOnScrollListener(mRefreshListener);

        gankSwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorBlue));
        gankSwipeRefresh.setOnRefreshListener(mRefreshListener);

    }

    public void getDate(int page){
        mPresenter.getData(mType,page);
        mRefreshListener.setUpdated(false);
    }

    public void addDate(List<GankData> gankDatas){
        mGankAdapter.addDataList(gankDatas);
        mRefreshListener.setUpdated(true);
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
       init();
    }

    @Override
    public void setData(List<GankData> gankDatas) {
        if(mRefreshListener.getIsScrolling()){
            mNewDatas.addAll(gankDatas);
        }else {
            addDate(gankDatas);
        }

    }

    @Override
    public void showLoading() {
        Log.i("zlTag","load:" + mRefreshListener.isLoading());
        gankSwipeRefresh.setRefreshing(true);
        mRefreshListener.onStart();
    }

    @Override
    public void getDataCompleted() {
        gankSwipeRefresh.setRefreshing(false);
        mRefreshListener.onCompleted();
    }

    @Override
    public void showError() {
        Log.i("zlTag","error");
    }
}
