package com.zl.mvpdemo.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextSwitcher;
import android.widget.ViewSwitcher;

import com.zl.mvpdemo.R;
import com.zl.mvpdemo.view.adapter.GifPagerAdapter;
import com.zl.mvpdemo.view.fragment.GifFragment;
import com.zl.mvpdemo.view.widget.MarqueeTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZL on 2017/2/24.
 */

public class GifActivity extends AppCompatActivity {

    public static final String EXTRA_LIST = "list";
    public static final String EXTRA_DESC = "desc";
    public static final String EXTRA_PAGE = "page";

    @BindView(R.id.viewPager_gif)
    ViewPager viewPagerGif;
    @BindView(R.id.toolbar_view_gif)
    Toolbar toolbarView;
    @BindView(R.id.textSwitcher_gif)
    TextSwitcher textSwitcherGif;

    private GifPagerAdapter mPagerAdapter;
    private List<Fragment> mFragments;

    private List<String> mList;
    private int mPage;
    private String mDesc;

    private boolean mIsHidden = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        ButterKnife.bind(this);

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
        mList = getIntent().getStringArrayListExtra(EXTRA_LIST);
        mPage = getIntent().getIntExtra(EXTRA_PAGE, 0);
        mDesc = getIntent().getStringExtra(EXTRA_DESC);

        textSwitcherGif.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                MarqueeTextView textView = new MarqueeTextView(GifActivity.this);
                //textView.setTextAppearance(WebViewActivity.this, R.style.WebTitle);
                textView.setSingleLine(true);
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                //textView.postDelayed(() -> textView.setSelected(true), 1738);
                return textView;
            }
        });

        if(mDesc!=null) textSwitcherGif.setText(mDesc);

        mFragments = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            GifFragment fragment = GifFragment.newInstance((1 + i) + "/" + mList.size(), mList.get(i));
            fragment.setOnClicke(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hideOrShowToolBar();
                }
            });
            mFragments.add(fragment);
        }
        mPagerAdapter = new GifPagerAdapter(getSupportFragmentManager(), mFragments);

        viewPagerGif.setAdapter(mPagerAdapter);
        viewPagerGif.setCurrentItem(mPage);
    }

    public void hideOrShowToolBar(){
        Log.i("zltag","" + mIsHidden);
        toolbarView.animate()
                .translationY(mIsHidden ? 0 : -toolbarView.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsHidden = !mIsHidden;

    }


}
