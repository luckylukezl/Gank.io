package com.zl.mvpdemo.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zl.mvpdemo.R;
import com.zl.mvpdemo.model.bean.GankData;
import com.zl.mvpdemo.presenter.impl.GankPresenterImpl;
import com.zl.mvpdemo.presenter.presenter.IGankPresenter;
import com.zl.mvpdemo.view.adapter.GankRecyclerAdapter;
import com.zl.mvpdemo.view.view.IView;
import com.zl.mvpdemo.view.widget.GirlImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZL on 2017/2/27.
 */

public class DayActivity extends AppCompatActivity implements IView<List<GankData>> {

    @BindView(R.id.imageview_today)
    GirlImageView imageviewToday;
    @BindView(R.id.toolbar_toady)
    Toolbar toolbarToady;
    @BindView(R.id.recyclerView_today)
    RecyclerView recyclerViewToday;
    @BindView(R.id.collapsing_day)
    CollapsingToolbarLayout collapsingDay;
    @BindView(R.id.progressBar_day)
    ProgressBar progressBarDay;
    @BindView(R.id.empty_day)
    TextView emptyDay;

    private IGankPresenter mPresenter;
    private String mToday;
    private List<GankData> mTodayDatas;
    private GankRecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        ButterKnife.bind(this);

        initView();
        initData();


    }

    private void initView() {

        toolbarToady.setTitle("今日推荐");
        //toolbarToady.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbarToady);

        emptyDay.setVisibility(View.GONE);
        progressBarDay.setVisibility(View.VISIBLE);

        collapsingDay.setExpandedTitleColor(getResources().getColor(R.color.white));
        collapsingDay.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        toolbarToady.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbarToady.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initData() {
        getToday();
        mPresenter = new GankPresenterImpl(this);
        mPresenter.getDayDatas(mToday);

        mTodayDatas = new ArrayList<GankData>();
        mRecyclerAdapter = new GankRecyclerAdapter(mTodayDatas, this);
        mRecyclerAdapter.setIsToady();

        recyclerViewToday.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewToday.setItemAnimator(new DefaultItemAnimator());
        recyclerViewToday.setAdapter(mRecyclerAdapter);

    }

    public void getToday() {
        mToday = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    }

    @Override
    public void setData(List<GankData> gankDatas) {
        //Logger.d(gankDatas.size());
        progressBarDay.setVisibility(View.GONE);
        if (gankDatas.size() > 0 && gankDatas.get(0).getType().equals("福利")) {
            getToolPicture(gankDatas.get(0).getUrl());
            gankDatas.remove(0);
        }else {
            emptyDay();
        }
        mTodayDatas.addAll(gankDatas);
        mRecyclerAdapter.notifyDataSetChanged();
    }

    private void emptyDay() {
        emptyDay.setVisibility(View.VISIBLE);
    }

    private void getToolPicture(String url) {
        Glide.with(this)
                .load(url)
                .centerCrop()
                .into(imageviewToday);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void getDataCompleted() {

    }

    @Override
    public void showError() {

    }

}
