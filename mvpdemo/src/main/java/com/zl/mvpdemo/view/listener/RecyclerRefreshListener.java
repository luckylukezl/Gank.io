package com.zl.mvpdemo.view.listener;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by ZL on 2017/2/20.
 * 滑动加载更多
 */

public abstract class RecyclerRefreshListener extends
        RecyclerView.OnScrollListener implements SwipeRefreshLayout.OnRefreshListener{

    private static int PRELOAD = 6; //提前6个开始加载
    private boolean isLoading = true;

    private int currentPage = 1;

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

            if (!isLoading && (totalItemCount - firstVisibleItemPosition) <= PRELOAD) {

                onLoadMore(currentPage);

            }

            if(mLayoutManager.findFirstCompletelyVisibleItemPositions(new int[2])[1] <= 2){
                //因为每次滚到顶部，都会出现顶部空白，所以滚动到顶部时，就重新布局。
                onRefreshLayout();
            }
        }else if(mLinearLayoutManager != null){
            int totalItemCount = mLinearLayoutManager.getItemCount();
            int lastItemPostion = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();

            if(!isLoading && (totalItemCount - lastItemPostion) <= 2){
                onLoadMore(currentPage);
            }
        }


    }

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

    public abstract void onRefreshLayout();
}
