package com.zl.mvpdemo.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zl.mvpdemo.R;
import com.zl.mvpdemo.model.constant.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private String mPage;
    private String mUrl;

    public static GifFragment newInstance(String page , String url) {
        Bundle args = new Bundle();
        args.putString(ARGS_PAGE, page);
        args.putString(ARGS_URL,url);
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

        Log.i("zlTag" , mUrl);

        Glide.with(mContext)
                .load(mUrl + "?imageView2/0/w/" + Constant.SCREEN_WIDTH)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                //.asBitmap()
                //.listener(new RequestListener<GlideDrawable,>)
                .into(imageViewGif);

    }

}
