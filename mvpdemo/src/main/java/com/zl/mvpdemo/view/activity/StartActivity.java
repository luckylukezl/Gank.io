package com.zl.mvpdemo.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.zl.mvpdemo.R;
import com.zl.mvpdemo.model.bean.GirlData;
import com.zl.mvpdemo.model.constant.Constant;
import com.zl.mvpdemo.presenter.impl.UtilPresenterImpl;
import com.zl.mvpdemo.presenter.presenter.UtilPresenter;
import com.zl.mvpdemo.view.view.IView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.prefs.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Clytze on 2017/3/11.
 */

public class StartActivity extends AppCompatActivity implements IView<GirlData> {

    @BindView(R.id.version_text_start)
    TextView versionTextStart;

    private UtilPresenter mUtilPresenter;
    private SharedPreferences mPreference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        init();

        mUtilPresenter = new UtilPresenterImpl(this);

        PackageManager pm = getPackageManager();

        try {
            PackageInfo info = pm.getPackageInfo("com.zl.mvpdemo", 0);
            String s = String.format("Version %s\nby zl", info.versionName);
            versionTextStart.setText(s);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        mUtilPresenter.getLastPicture();


    }

    private void init() {
        //获取屏幕尺寸
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Constant.SCREEN_HEIGHT = metrics.heightPixels;
        Constant.SCREEN_WIDTH = metrics.widthPixels;
        Constant.SCREEN_DENSITY = metrics.density;

        mPreference = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit = mPreference.edit();
        edit.putBoolean(Constant.NEW_PREFERENCE,false);
        edit.putBoolean(Constant.FIRST_CHECK_PREFERENCE,true);
        edit.commit();
    }

    @Override
    public void setData(GirlData girlData) {
        Constant.DATE_TODAY = girlData.getPublishedAt();

        boolean isOldNew = mPreference.getBoolean(Constant.NEW_PREFERENCE,false);
        boolean isNew = DateEqual(new Date() , girlData.getPublishedAt());
        SharedPreferences.Editor edit = mPreference.edit();
        edit.putBoolean(Constant.NEW_PREFERENCE,isNew);
        edit.putBoolean(Constant.FIRST_CHECK_PREFERENCE,!isOldNew && isNew);
        edit.commit();

        final Intent intent = new Intent();
        if(isNew && mPreference.getBoolean(Constant.FIRST_CHECK_PREFERENCE,false)){
            intent.setClass(StartActivity.this,DayActivity.class);
        }else {
            intent.setClass(StartActivity.this,AppBarActivity.class);
        }

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(intent);
                StartActivity.this.finish();
            }
        }, 500);

    }

    private boolean DateEqual(Date date1, Date date2) {
        String d1 = new SimpleDateFormat("yyyy/MM/dd").format(date1);
        String d2 = new SimpleDateFormat("yyyy/MM/dd").format(date2);
        return d1.equals(d2);
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
