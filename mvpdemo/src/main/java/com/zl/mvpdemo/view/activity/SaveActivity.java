package com.zl.mvpdemo.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;
import com.zl.mvpdemo.R;
import com.zl.mvpdemo.model.bean.GankData;
import com.zl.mvpdemo.presenter.impl.DBPresenter;
import com.zl.mvpdemo.presenter.presenter.IDBPresenter;
import com.zl.mvpdemo.view.adapter.GankRecyclerAdapter;
import com.zl.mvpdemo.view.listener.OnSaveLongListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by ZL on 2017/2/28.
 */

public class SaveActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_view)
    Toolbar toolbarView;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.recyclerView_save)
    RecyclerView recyclerViewSave;
    private IDBPresenter<GankData> mDBPresenter;

    private List<GankData> mGankDatas;
    private List<GankData> mTodayDatas;
    private GankRecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        ButterKnife.bind(this);

        init();
    }

    public void init() {
        mDBPresenter = new DBPresenter(this);
        mGankDatas = new ArrayList<>();
        mDBPresenter.getDatas(mGankDatas);

        toolbarView.setTitle("收藏");
        setSupportActionBar(toolbarView);
        toolbarView.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbarView.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        mRecyclerAdapter = new GankRecyclerAdapter(mGankDatas,this);
        mRecyclerAdapter.setIsSave();
        mRecyclerAdapter.setOnLongClickListener(getSaveLongListener());

        recyclerViewSave.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSave.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSave.setAdapter(mRecyclerAdapter);
//
    }

    public OnSaveLongListener getSaveLongListener(){
        return new OnSaveLongListener() {
            @Override
            public void OnLongClickListener(final GankData gankData) {
                new AlertDialog.Builder(SaveActivity.this)
                        .setMessage("确定删除")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mDBPresenter.deleteData(gankData);
                                mGankDatas.remove(gankData);
                                mRecyclerAdapter.notifyDataSetChanged();
                            }
                        }).show();
            }
        };
    }
}
