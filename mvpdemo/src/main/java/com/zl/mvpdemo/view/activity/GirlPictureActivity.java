package com.zl.mvpdemo.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zl.mvpdemo.R;
import com.zl.mvpdemo.presenter.impl.ManagerPresenter;
import com.zl.mvpdemo.presenter.presenter.IManagerPresenter;
import com.zl.mvpdemo.view.adapter.ViewPageAdapter;
import com.zl.mvpdemo.view.view.IView;
import com.zl.mvpdemo.view.widget.ViewPagerFixed;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by ZL on 2017/2/20.
 */

public class GirlPictureActivity extends AppCompatActivity implements IView<String>, ViewPager.OnPageChangeListener {

    public static String EXTRA_ISSAVE = "isSave";
    public static final String EXTRA_GIRLS = "girls";
    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_RESULT = "result";

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
    @BindView(R.id.save_delete_text)
    TextView saveDeleteText;
    @BindView(R.id.imageview_save_delete)
    ImageView imageviewSaveDelete;

    //private String mUrl;
    private List<String> mImages;
    private int mPosition;
    private PhotoViewAttacher mPhotoAttacher;
    private boolean mIsHidden = false;
    private IManagerPresenter mPresenter;

    private ViewPageAdapter mPageAdapter;

    private boolean isSavedPicture = false;
    private ArrayList<String> mResults;

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
                goBack();
            }
        });

        mPresenter = new ManagerPresenter(this);

        initView();

    }

    private void initView() {

        mResults = new ArrayList<>();

        mImages = getIntent().getStringArrayListExtra(EXTRA_GIRLS);
        mPosition = getIntent().getIntExtra(EXTRA_POSITION, 0);
        isSavedPicture = getIntent().getBooleanExtra(EXTRA_ISSAVE, false);

        saveDeleteText.setText(isSavedPicture ? "删除" : "保存");
        imageviewSaveDelete.setImageResource(isSavedPicture ?android.R.drawable.ic_menu_delete : android.R.drawable.star_big_off);

        mPageAdapter = new ViewPageAdapter(this, mImages);
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
    public void saveOrDeleteImageToGallery() {
        if (isSavedPicture) { //如果是加载的save的图片，则是删除按钮
            mPresenter.delete(mImages.get(mPosition));
        } else {
            mPresenter.save(mImages.get(mPosition));
        }

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
        if (s.equals("delete")) {
            setResult(100);
            mResults.add(mImages.get(mPosition));
            mImages.remove(mPosition);
            if(mImages.size() == 0){
                goBack();

            }
            if(mImages.size() == mPosition){
                mPosition -= 1;
            }
            mPageAdapter = new ViewPageAdapter(this,mImages);
            mPageAdapter.setOnTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    hideOrShowToolbar();
                }
            });
            viewPagerPicture.setAdapter(mPageAdapter);
            viewPagerPicture.setCurrentItem(mPosition);
            return;
        }
        if (s.contains("share")) {
            startShare(s);
        } else {
            Toast.makeText(GirlPictureActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
        }
    }

    public void goBack(){
        Intent intent = new Intent();
        intent.putStringArrayListExtra(EXTRA_RESULT,mResults);
        setResult(100,intent);
        finish();
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
        Log.i("zltag", "p:" + mPosition + "");
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
