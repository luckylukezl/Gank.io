package com.zl.mvpdemo.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.orhanobut.logger.Logger;
import com.zl.mvpdemo.R;
import com.zl.mvpdemo.presenter.impl.ManagerPresenter;
import com.zl.mvpdemo.presenter.presenter.IManagerPresenter;
import com.zl.mvpdemo.view.adapter.ViewPageAdapter;
import com.zl.mvpdemo.view.view.IView;
import com.zl.mvpdemo.view.widget.ViewPagerFixed;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by ZL on 2017/2/20.
 */

public class GirlPictureActivity extends AppCompatActivity implements IView<String> , ViewPager.OnPageChangeListener {

    public static String EXTRA_URL = "URL";
    public static final String EXTRA_GIRLS = "girls";
    public static final String EXTRA_POSITION = "position";

    //    @BindView(R.id.picture_imageView)
//    ImageView pictureImageView;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.linearLayout_picture)
    LinearLayout linearLayoutPicture;
    @BindView(R.id.frameLayout_picture)
    FrameLayout frameLayoutPicture;
    @BindView(R.id.toolbar_view)
    Toolbar toolbarView;
    @BindView(R.id.viewPager_picture_activity)
    ViewPagerFixed viewPagerPicture;

    //private String mUrl;
    private List<String> mImages;
    private int mPosition;
    private PhotoViewAttacher mPhotoAttacher;
    private boolean mIsHidden = false;
    private IManagerPresenter mPresenter;

    private ViewPageAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ButterKnife.bind(this);

        toolbarView.setTitle("");
        setSupportActionBar(toolbarView);

        toolbarView.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbarView.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mPresenter = new ManagerPresenter(this);

        initView();

//        Glide.with(this)
//                .load(mUrl)
//                .thumbnail((float) 0.3)
//                .error(R.mipmap.material_img)
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        mPhotoAttacher.update();
//                        return false;
//                    }
//                })
//                .into(pictureImageView);
    }

    private void initView() {

//        mUrl = getIntent().getStringExtra(EXTRA_URL);
        mImages = getIntent().getStringArrayListExtra(EXTRA_GIRLS);
        mPosition = getIntent().getIntExtra(EXTRA_POSITION , 0);

        mPageAdapter = new ViewPageAdapter(this,mImages);
        mPageAdapter.setOnTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                hideOrShowToolbar();
            }
        });
        viewPagerPicture.setAdapter(mPageAdapter);
        viewPagerPicture.setCurrentItem(mPosition);
        viewPagerPicture.addOnPageChangeListener(this);

//        mPhotoAttacher = new PhotoViewAttacher(pictureImageView);
//        mPhotoAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
//            @Override
//            public void onViewTap(View view, float x, float y) {
//                hideOrShowToolbar();
//            }
//        });


    }


    @OnClick(R.id.imageView_save_picture)
    public void saveImageToGallery() {
        mPresenter.save(mImages.get(mPosition));
    }

    @OnClick(R.id.image_share_picture)
    public void shareIntent() {
        mPresenter.share(mImages.get(mPosition));
    }

    public void startShare(String s) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        File file = new File(s);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, this.getTitle()));
    }

    protected void hideOrShowToolbar() {
        appBarLayout.animate()
                .translationY(mIsHidden ? 0 : -appBarLayout.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        linearLayoutPicture.animate()
                .translationY(mIsHidden ? 0 : linearLayoutPicture.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        frameLayoutPicture.setBackgroundColor(getResources().getColor(mIsHidden ? R.color.white : R.color.black));
        mIsHidden = !mIsHidden;
    }


    @Override
    public void setData(String s) {
        if (s.contains("share")) {
            startShare(s);
        } else {
            Toast.makeText(GirlPictureActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
