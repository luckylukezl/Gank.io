package com.zl.mvpdemo.view.listener;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

/**
 * Created by ZL on 2017/2/20.
 * 滑动加载更多
 */

public abstract class RecyclerRefreshListener extends
        RecyclerView.OnScrollListener implements SwipeRefreshLayout.OnRefreshListener{

    private static int PRELOAD = 4; //提前x个开始加载
    private boolean isLoading = true;

    private int currentPage = 1;

    protected boolean isScrolling = false;

    private boolean isUpdated = true; //获取数据之后，可能还在滚动，必须等数据更新到adapter再进行下次的数据请求。

    private StaggeredGridLayoutManager mLayoutManager;
    private LinearLayoutManager mLinearLayoutManager;

    public RecyclerRefreshListener(StaggeredGridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    public RecyclerRefreshListener(LinearLayoutManager layoutManager){
        mLinearLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if(mLayoutManager != null){
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLayoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1];

            if (!isLoading && (totalItemCount - firstVisibleItemPosition) <= PRELOAD && isUpdated) {
                isUpdated = false;
                onLoadMore(currentPage);

            }
            //防止顶部留白
            mLayoutManager.invalidateSpanAssignments();
        }else if(mLinearLayoutManager != null){
            int totalItemCount = mLinearLayoutManager.getItemCount();
            int lastItemPostion = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();

            if(!isLoading && (totalItemCount - lastItemPostion) <= PRELOAD && isUpdated){
                isUpdated = false;
                onLoadMore(currentPage);
            }
        }


    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

        //Log.i("zlTag",newState + "");
        //super.onScrollStateChanged(recyclerView, newState);
        switch (newState){
            case RecyclerView.SCROLL_STATE_IDLE:
                //Log.i("zlTag","stop");
                isScrolling = false;
                onDateChanged();
                break;
            default:
                isScrolling = true;

        }
    }

    public void setUpdated(boolean is){
        isUpdated = is;
    }

    public boolean getIsScrolling(){
        return isScrolling;
    }

    public abstract void onDateChanged();

    public boolean isLoading(){
        return isLoading;
    }

    public void setCurrentPage(int page){
        currentPage = page;
    }

    public void onStart(){
        isLoading = true;
    }

    public void onCompleted(){
        currentPage += 1;
        isLoading = false;
    }

    public abstract void onLoadMore(int currentPage);

}
