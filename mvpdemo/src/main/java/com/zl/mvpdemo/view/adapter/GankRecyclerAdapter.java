package com.zl.mvpdemo.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zl.mvpdemo.R;
import com.zl.mvpdemo.model.bean.GankData;
import com.zl.mvpdemo.model.constant.Constant;
import com.zl.mvpdemo.view.activity.GifActivity;
import com.zl.mvpdemo.view.activity.GirlPictureActivity;
import com.zl.mvpdemo.view.activity.WebViewActivity;
import com.zl.mvpdemo.view.util.StringStyles;
import com.zl.mvpdemo.view.widget.GirlImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZL on 2017/2/22.
 */

public class GankRecyclerAdapter extends RecyclerView.Adapter<GankRecyclerAdapter.GankViewHolder> {

    private List<GankData> mList;
    private Context mContext;

    public GankRecyclerAdapter(List<GankData> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public GankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GankViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.gank_item_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(GankViewHolder holder, int position) {
        final GankData gankData = mList.get(position);
        List<String> images = gankData.getImages();

        String date = new SimpleDateFormat("yyyy-MM-dd").format(gankData.getPublishedAt());

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(StringStyles.format(mContext,date + "\n", R.style.DateTextAppearance));
        builder.append(StringStyles.format(mContext,gankData.getDesc(),R.style.UrlTextAppearance));
        builder.append(StringStyles.format(mContext," (via." + gankData.getWho() + ") ", R.style.ViaTextAppearance));

        holder.gankItemTextView.setText(builder.subSequence(0,builder.length()));
        holder.gankItemTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext , WebViewActivity.class);
                intent.putExtra(WebViewActivity.EXTRA_URL , gankData.getUrl());
                intent.putExtra(WebViewActivity.EXTRA_TITLE,gankData.getDesc());
                mContext.startActivity(intent);
            }
        });

        if(images!=null){
            holder.linearLayout_gank_item.setVisibility(View.VISIBLE);
            int i=0;
            holder.imageView1GankItem.setVisibility(View.VISIBLE);
            loadImageView(images , i ,holder.imageView1GankItem);
            i++;
            if(i < images.size()){
                holder.imageView2GankItem.setVisibility(View.VISIBLE);
                loadImageView(images , i ,holder.imageView2GankItem);
            }
            i++;
            if(i < images.size()){
                holder.imageView3GankItem.setVisibility(View.VISIBLE);
                loadImageView(images , i ,holder.imageView3GankItem);
            }
        }else {
            holder.linearLayout_gank_item.setVisibility(View.GONE);
        }

    }

    public void loadImageView(final List<String> images , final int i , ImageView view){

        Glide.with(mContext)
                .load(images.get(i) + "?imageView2/0/w/" + Constant.SCREEN_WIDTH / 3)
                .asBitmap()
                .error(R.mipmap.material_img)
                //.placeholder(R.mipmap.material_img)
                //.centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext , GifActivity.class);
                intent.putExtra(GifActivity.EXTRA_LIST,new ArrayList<>(images));
                intent.putExtra(GifActivity.EXTRA_PAGE,i);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addDataList(List<GankData> datas) {
        mList.addAll(datas);
        notifyDataSetChanged();
    }

    public class GankViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.gank_item_textView)
        TextView gankItemTextView;

        @BindView(R.id.imageView1_gank_item)
        GirlImageView imageView1GankItem;
        @BindView(R.id.imageView2_gank_item)
        GirlImageView imageView2GankItem;
        @BindView(R.id.imageView3_gank_item)
        GirlImageView imageView3GankItem;
        @BindView(R.id.linearLayout_gank_item)
        LinearLayout linearLayout_gank_item;

        public GankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imageView1GankItem.setPic(50,50);
            imageView2GankItem.setPic(50,50);
            imageView3GankItem.setPic(50,50);
        }
    }
}
