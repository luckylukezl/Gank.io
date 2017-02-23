package com.zl.mvpdemo.view.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.zl.mvpdemo.model.bean.GirlData;
import com.zl.mvpdemo.model.constant.Constant;
import com.zl.mvpdemo.model.impl.GirlLoader;
import com.zl.mvpdemo.model.impl.GirlPictureUrlModel;
import com.zl.mvpdemo.view.activity.GirlPictureActivity;
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

        int width = Constant.SCREEN_WIDTH / 2;

        String url = mList.get(position).getUrl() + "?imageView2/0/w/" + width;

        int limit = 84;

        if(getItemViewType(position) == HORIZONTAL_TYPE){
            holder.mImageView.setPic(1,1);
        }else{
            holder.mImageView.setPic(3,4);
        }
        //holder.mImageView.setPic(mList.get(position).getWidth() , mList.get(position).getHeight());
        //Log.i("zlTag",mList.get(position).getDesc() +  ",h1:" + mList.get(position).getHeight() + ",w1:" +mList.get(position).getWidth());
        holder.mTextView.setText(new SimpleDateFormat("yyyy/MM/dd").format(mList.get(position).getPublishedAt()));

        //Log.i("zlTag",mList.get(position).getDesc() +  ",h:" + holder.mImageView.getHeight() + ",w:" +holder.mImageView.getWidth());
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext , GirlPictureActivity.class);
                intent.putExtra(GirlPictureActivity.EXTRA_URL , mList.get(position).getUrl());
                mContext.startActivity(intent);
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

    @Override
    public int getItemViewType(int position) {
        GirlData image = mList.get(position);
        if(image.getWidth() >= image.getHeight()){
            return HORIZONTAL_TYPE;
        }else{
            return VERTICAL_TYPE;
        }
        //return Math.round((float) image.getWidth() / (float) image.getHeight() * 10f);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addData(GirlData data){
        mList.add(data);
        notifyDataSetChanged();
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
            //mImageView.setPic(50,50);
        }

    }
}
