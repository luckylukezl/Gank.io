package com.zl.mvpdemo.view.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.zl.mvpdemo.R;
import com.zl.mvpdemo.view.fragment.GirlFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZL on 2017/2/9.
 */

public class GirlActivity extends BaseActivity {

    @BindView(R.id.toolbar_view)
    Toolbar toolbarView;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.linearLayout_girl)
    LinearLayout linearLayoutGirl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);

        toolbarView.setTitle("福利");
        setSupportActionBar(toolbarView);

        toolbarView.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbarView.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        init();

    }



    private void init() {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.linearLayout_girl, new GirlFragment());
        transaction.commit();


    }

}
