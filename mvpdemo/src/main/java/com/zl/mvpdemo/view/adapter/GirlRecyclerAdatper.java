package com.zl.mvpdemo.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.zl.mvpdemo.R;
import com.zl.mvpdemo.model.bean.GankData;
import com.zl.mvpdemo.model.bean.GirlData;
import com.zl.mvpdemo.model.bean.GirlPicture;
import com.zl.mvpdemo.model.constant.Constant;
import com.zl.mvpdemo.model.impl.GirlLoader;
import com.zl.mvpdemo.model.impl.GirlPictureUrlModel;
import com.zl.mvpdemo.view.activity.GirlActivity;
import com.zl.mvpdemo.view.activity.GirlPictureActivity;
import com.zl.mvpdemo.view.listener.OnGirlTouchListener;
import com.zl.mvpdemo.view.widget.GirlImageView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

/**
 * Created by ZL on 2017/2/15.
 */

public class GirlRecyclerAdatper extends RecyclerView.Adapter<GirlRecyclerAdatper.MyViewHolder>{

    private List<GirlData> mList;
    private Context mContext;
    //private RequestListener<String,GlideDrawable> mGlideListener;
    private final int VERTICAL_TYPE = 0;
    private final int HORIZONTAL_TYPE = 1;

    private OnGirlTouchListener mGirlTouchListener;
    private boolean isSavePicture = false;

    public GirlRecyclerAdatper(List list, Context context){
        mList = list;
        mContext = context;
    }

    //加载条目布局
    @Override
    public GirlRecyclerAdatper.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext)
            .inflate(R.layout.item_recycler,parent,false));
    }

    //将数据与视图绑定
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final GirlData girlData = mList.get(position);
        int width = Constant.SCREEN_WIDTH / 2;

        String url = girlData.getUrl() + ( isSavePicture?"":"?imageView2/0/w/" + width );

        holder.mTextView.setText(isSavePicture?"":new SimpleDateFormat("yyyy/MM/dd").format(girlData.getPublishedAt()));

        //Log.i("zlTag",mList.get(position).getDesc() +  ",h:" + holder.mImageView.getHeight() + ",w:" +holder.mImageView.getWidth());
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mGirlTouchListener!=null){
                    mGirlTouchListener.onTouch(girlData,holder.mImageView,position);

                }

            }
        });


        Glide.with(mContext)
                //.using(new GirlLoader(mContext))
                .load(url)
                //.placeholder(R.drawable.material_img)
                .error(R.drawable.material_img)
                //.listener(mGlideListener
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mImageView);
    }

    public void setGirlTouchListener(OnGirlTouchListener listener){
        mGirlTouchListener = listener;
    }

//    @Override
//    public int getItemViewType(int position) {
//        GirlData image = mList.get(position);
//        if(image.getWidth() >= image.getHeight()){
//            return HORIZONTAL_TYPE;
//        }else{
//            return VERTICAL_TYPE;
//        }
//        //return Math.round((float) image.getWidth() / (float) image.getHeight() * 10f);
//    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addData(GirlData data){
        mList.add(data);
        notifyDataSetChanged();
    }

    public void setSavePicture(boolean is){
        isSavePicture = is;
    }

    public void addDataList(List<GirlData> datas){
        mList.addAll(datas);
        notifyDataSetChanged();
    }

    public void remove(int positon){
        mList.remove(positon);
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        GirlImageView mImageView;
        TextView mTextView;
        View mItemView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            //mItemView.setTag(this);
            mImageView = (GirlImageView) itemView.findViewById(R.id.item_recycler_imageView);
            mTextView = (TextView) itemView.findViewById(R.id.item_recycler_textView);
            mImageView.setPic(50,50);
        }

    }
}
