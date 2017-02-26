package com.zl.mvpdemo.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.zl.mvpdemo.R;
import com.zl.mvpdemo.model.constant.Constant;

import java.util.List;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 图片浏览ViewPageAdapter
 * Created by ZL on 2017/2/25.
 */
public class ViewPageAdapter extends PagerAdapter {

    private Context context;
    private List<String> images;
    private SparseArray<View> cacheView;
    private ViewGroup containerTemp;

    private boolean isDateChanged = false;

    private PhotoViewAttacher.OnViewTapListener mOnViewTapListener;

    public ViewPageAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
        cacheView = new SparseArray<>(images.size());
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        if (containerTemp == null) containerTemp = container;

        View view = cacheView.get(position);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.viewpager_picture, container, false);
            view.setTag(position);
            final ImageView image = (ImageView) view.findViewById(R.id.picture_imageView);
            final PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(image);

            Glide.with(context)
                    .load(images.get(position))
                    .thumbnail((float) 0.3)
                    .error(R.mipmap.material_img)
                    //.centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .fitCenter()
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            image.setImageDrawable(resource);
                            photoViewAttacher.update();
                        }
                    });

            photoViewAttacher.setOnViewTapListener(mOnViewTapListener);
            cacheView.put(position, view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    public void notifyDataSetChanged(int position) {
        isDateChanged = true;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if ( isDateChanged ) {
            isDateChanged = false;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    public void setOnTapListener(PhotoViewAttacher.OnViewTapListener listener){
        mOnViewTapListener = listener;
    }

}
