package com.zl.mvpdemo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zl.mvpdemo.R;
import com.zl.mvpdemo.model.bean.GankData;
import com.zl.mvpdemo.model.bean.GirlData;
import com.zl.mvpdemo.model.constant.Constant;
import com.zl.mvpdemo.presenter.impl.UtilPresenterImpl;
import com.zl.mvpdemo.presenter.presenter.UtilPresenter;
import com.zl.mvpdemo.view.adapter.GankPaperAdapter;
import com.zl.mvpdemo.view.fragment.TabFragment;
import com.zl.mvpdemo.view.view.IView;
import com.zl.mvpdemo.view.widget.GirlImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    private long mQuiteTime = 0;

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

        toolbarMain.setTitle("干货集中营");
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
                        switch (menuItem.getItemId()){
                            case R.id.collect_drawer:
                                startActivity(new Intent(AppBarActivity.this,SaveActivity.class));

                                break;
                            case R.id.love_drawer:
                                Intent intent = new Intent(AppBarActivity.this , GirlActivity.class);
                                intent.putExtra(GirlActivity.EXTRA_ISSAVE,true);
                                startActivity(intent);
                                break;
                            case R.id.settings_drawer:
                                Toast.makeText(AppBarActivity.this,"敬请期待",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.about_drawer:
                                GankData data = new GankData();
                                data.setUrl("https://github.com/luckylukezl/Gank.io");
                                data.setDesc("源码 github ，by zl");
                                Intent intent1 = new Intent(AppBarActivity.this , WebViewActivity.class);
                                intent1.putExtra(WebViewActivity.EXTRA_GANK,data);
                                intent1.putExtra(WebViewActivity.EXTRA_AUTHOR,true);
                                startActivity(intent1);
                                break;
                        }
                        menuItem.setCheckable(false);
                        drawerAppBar.closeDrawers();
                        return true;
                    }
                });

        View view = navigationView.getHeaderView(0);
        imageViewDrawable = (GirlImageView) view.findViewById(R.id.imageView_header_navigation);
        imageViewDrawable.setPic(1,1);
        TextView dateText = (TextView) view.findViewById(R.id.date_header_navigation);
        dateText.setText(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
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
                startActivity(new Intent(this, DayActivity.class));
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:{
                if(System.currentTimeMillis() - mQuiteTime < 2000){
                    finish();
                    //System.exit(0);

                }else {
                    mQuiteTime = System.currentTimeMillis();
                    Toast.makeText(AppBarActivity.this,"再按一次退出",Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
        return true;
    }

    @Override
    public void setData(GirlData girlData) {
        //Constant.DATE_TODAY = girlData.getPublishedAt();
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
