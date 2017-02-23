package com.zl.mvpdemo.view.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.zl.mvpdemo.R;
import com.zl.mvpdemo.view.adapter.GirlRecyclerAdatper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZL on 2017/2/15.
 */

public class RecyclerActivity extends BaseActivity{

    private RecyclerView mRecyclerView;
    private List<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        init();
    }


    protected void init() {

        initData();

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        //mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(new GirlRecyclerAdatper(mData,this));
    }

    public void initData(){
        mData = new ArrayList<String>();

        for(int i=0;i<100;i++){
            mData.add("hello" + i);
        }

    }
}
