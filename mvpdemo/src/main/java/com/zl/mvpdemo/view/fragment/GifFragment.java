package com.zl.mvpdemo.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.zl.mvpdemo.R;
import com.zl.mvpdemo.model.constant.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by ZL on 2017/2/24.
 */

public class GifFragment extends BaseFragment {
    public static final String ARGS_PAGE = "page";
    public static final String ARGS_URL = "url";

    @BindView(R.id.imageView_gif)
    ImageView imageViewGif;
    @BindView(R.id.text_gif)
    TextView textGif;
    @BindView(R.id.progressBar_gif)
    ProgressBar progressBarGif;



    private String mPage;
    private String mUrl;

    private Bitmap mBitmap;
    private PhotoViewAttacher mAttacher;
    private View.OnClickListener mOnClickListener;

    public static GifFragment newInstance(String page, String url) {
        Bundle args = new Bundle();
        args.putString(ARGS_PAGE, page);
        args.putString(ARGS_URL, url);
        GifFragment fragment = new GifFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_gif;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        mPage = getArguments().getString(ARGS_PAGE);
        mUrl = getArguments().getString(ARGS_URL);

        textGif.setText(mPage);

        Log.i("zlTag", mUrl);

        progressBarGif.setVisibility(View.VISIBLE);
        //imageViewGif.setOnClickListener(mOnClickListener);
        Glide.with(mContext)
                .load(mUrl)// + "?imageView2/0/w/" + Constant.SCREEN_WIDTH)
                //.thumbnail((float) 0.3)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                //.asBitmap()
                .fitCenter()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (resource instanceof GifDrawable) {
                            return false;
                        } else {
                            imageViewGif.setImageDrawable(resource);
                            mAttacher = new PhotoViewAttacher(imageViewGif);
                        }
                        progressBarGif.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageViewGif);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void setOnClicke(View.OnClickListener listener){
        mOnClickListener = listener;
    }
}
