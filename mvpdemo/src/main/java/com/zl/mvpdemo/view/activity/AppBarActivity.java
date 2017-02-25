package com.zl.mvpdemo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zl.mvpdemo.R;
import com.zl.mvpdemo.model.bean.GirlData;
import com.zl.mvpdemo.model.constant.Constant;
import com.zl.mvpdemo.presenter.impl.UtilPresenterImpl;
import com.zl.mvpdemo.presenter.presenter.UtilPresenter;
import com.zl.mvpdemo.view.adapter.GankPaperAdapter;
import com.zl.mvpdemo.view.fragment.TabFragment;
import com.zl.mvpdemo.view.view.IView;
import com.zl.mvpdemo.view.widget.GirlImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZL on 2017/2/13.
 */

public class AppBarActivity extends AppCompatActivity implements IView<GirlData>{

    @BindView(R.id.toolbar_main)
    Toolbar toolbarMain;
    @BindView(R.id.content_linearLayout)
    LinearLayout contentLinearLayout;
    @BindView(R.id.navigation_appBar)
    NavigationView navigationAppBar;
    @BindView(R.id.drawer_appBar)
    DrawerLayout drawerAppBar;

    private List<Fragment> mFragments;
    private GankPaperAdapter mPaperAdapter;
    private UtilPresenter mUtilPresenter;

    private GirlImageView imageViewDrawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appbar);
        ButterKnife.bind(this);

        init();

        initTab();

        setupDrawerContent(navigationAppBar);
    }


    private void init() {

        //获取屏幕尺寸
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Constant.SCREEN_HEIGHT = metrics.heightPixels;
        Constant.SCREEN_WIDTH = metrics.widthPixels;
        Constant.SCREEN_DENSITY = metrics.density;

        toolbarMain.setTitle("Gank.io");
        setSupportActionBar(toolbarMain);

        mUtilPresenter = new UtilPresenterImpl(this);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(android.R.drawable.ic_menu_sort_by_size);
        ab.setDisplayHomeAsUpEnabled(true);

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawerAppBar.closeDrawers();
                        return true;
                    }
                });

        View view = navigationView.getHeaderView(0);
        imageViewDrawable = (GirlImageView) view.findViewById(R.id.imageView_header_navigation);
        imageViewDrawable.setPic(1,1);
        mUtilPresenter.getLastPicture();
        //imageView.setImageResource(R.mipmap.material_img);

    }

    private void initTab() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_linearLayout, new TabFragment());
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

                Intent intent = new Intent(this, GirlActivity.class);
                startActivity(intent);
                break;
            case R.id.action_home:

                break;
            case android.R.id.home:
                drawerAppBar.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setData(GirlData girlData) {
        Glide.with(this)
                .load(girlData.getUrl() + "?imageView2/0/w/" + Constant.SCREEN_WIDTH)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageViewDrawable);
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
