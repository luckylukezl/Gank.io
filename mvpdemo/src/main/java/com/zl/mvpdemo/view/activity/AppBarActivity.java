package com.zl.mvpdemo.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.zl.mvpdemo.R;
import com.zl.mvpdemo.model.constant.Constant;
import com.zl.mvpdemo.view.adapter.GankPaperAdapter;
import com.zl.mvpdemo.view.fragment.GankFragment;
import com.zl.mvpdemo.view.fragment.GirlFragment;
import com.zl.mvpdemo.view.fragment.TabFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZL on 2017/2/13.
 */

public class AppBarActivity extends AppCompatActivity {
    
    @BindView(R.id.toolbar_main)
    Toolbar toolbarMain;
    @BindView(R.id.content_linearLayout)
    LinearLayout contentLinearLayout;

    private List<Fragment> mFragments;
    private GankPaperAdapter mPaperAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appbar);
        ButterKnife.bind(this);

        init();

        initTab();
    }


    private void init() {

        //获取屏幕尺寸
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Constant.SCREEN_HEIGHT = metrics.heightPixels;
        Constant.SCREEN_WIDTH = metrics.widthPixels;
        Constant.SCREEN_DENSITY = metrics.density;

        toolbarMain.setTitle("未登录");
        toolbarMain.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbarMain);

    }

    private void initTab() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_linearLayout,new TabFragment());
        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_girl:

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_linearLayout,new GirlFragment());
                transaction.commit();
                break;
            case R.id.action_home:

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
