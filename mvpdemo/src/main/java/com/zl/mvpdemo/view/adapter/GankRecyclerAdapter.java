package com.zl.mvpdemo.view.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import com.zl.mvpdemo.view.listener.OnSaveLongListener;
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
    private boolean isToady = false;
    private boolean isSave = false; // 收藏

    private OnSaveLongListener mOnSaveLongClickListener;

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

        SpannableStringBuilder builder = new SpannableStringBuilder();

        if(isToady || isSave){
            if(position == 0){
                showCatogory(holder.category_day , gankData.getType());
            }else{
                boolean isFirstItemOfType = !mList.get(position - 1).getType()
                        .equals(mList.get(position).getType());
                if(isFirstItemOfType){
                    showCatogory(holder.category_day , gankData.getType());
                }else {
                    hideCatogory(holder.category_day);

                }
            }

            if(isSave){
                String date = new SimpleDateFormat("yyyy-MM-dd").format(gankData.getPublishedAt());
                builder.append(StringStyles.format(mContext,date + "\n", R.style.DateTextAppearance));
                holder.linearLayout_content_gank_item.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if(mOnSaveLongClickListener!=null){
                            mOnSaveLongClickListener.OnLongClickListener(gankData);
                        }
                        return true;
                    }
                });
                holder.gankItemTextView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if(mOnSaveLongClickListener!=null){
                            mOnSaveLongClickListener.OnLongClickListener(gankData);
                        }
                        return true;
                    }
                });
            }
        }else {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(gankData.getPublishedAt());
            builder.append(StringStyles.format(mContext,date + "\n", R.style.DateTextAppearance));
            hideCatogory(holder.category_day);
        }

        builder.append(StringStyles.format(mContext,gankData.getDesc(),R.style.UrlTextAppearance));
        builder.append(StringStyles.format(mContext," (via." + gankData.getWho() + ") ", R.style.ViaTextAppearance));

        holder.gankItemTextView.setText(builder.subSequence(0,builder.length()));
        holder.gankItemTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext , WebViewActivity.class);
                intent.putExtra(WebViewActivity.EXTRA_GANK,gankData);
                mContext.startActivity(intent);
            }
        });

        if(images!=null && images.size()>0){
            holder.linearLayout_gank_item.setVisibility(View.VISIBLE);
            int i=0;
            holder.imageView1GankItem.setVisibility(View.VISIBLE);
            loadImageView(images , i ,holder.imageView1GankItem, position);
            i++;
            if(i < images.size()){
                holder.imageView2GankItem.setVisibility(View.VISIBLE);
                loadImageView(images , i ,holder.imageView2GankItem, position);
            }else {
                holder.imageView2GankItem.setVisibility(View.INVISIBLE);
            }
            i++;
            if(i < images.size()){
                holder.imageView3GankItem.setVisibility(View.VISIBLE);
                loadImageView(images , i ,holder.imageView3GankItem , position);
            }else {
                holder.imageView3GankItem.setVisibility(View.INVISIBLE);
            }
        }else {
            holder.linearLayout_gank_item.setVisibility(View.GONE);
        }

    }

    public void setOnLongClickListener(OnSaveLongListener listener){
        mOnSaveLongClickListener = listener;
    }

    public void loadImageView(final List<String> images , final int i , ImageView view , final int position){

        Glide.with(mContext)
                .load(images.get(i) + "?imageView2/0/w/" + Constant.SCREEN_WIDTH / 3)
                .asBitmap()
                .error(R.mipmap.material_img)
                //.placeholder(R.mipmap.material_img)
                //.centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext , GifActivity.class);
                intent.putExtra(GifActivity.EXTRA_LIST,new ArrayList<>(images));
                intent.putExtra(GifActivity.EXTRA_PAGE,i);
                intent.putExtra(GifActivity.EXTRA_DESC,mList.get(position).getDesc());
                mContext.startActivity(intent);
            }
        });
    }

    public void setIsToady(){
        isToady = true;
    }

    public void setIsSave(){
        isSave = true;
    }

    private void showCatogory(TextView view,String type){
        view.setText(type);
        view.setVisibility(View.VISIBLE);
    }

    private void hideCatogory(TextView view){
        view.setVisibility(View.GONE);
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
        @BindView(R.id.category_day)
        TextView category_day;

        @BindView(R.id.imageView1_gank_item)
        GirlImageView imageView1GankItem;
        @BindView(R.id.imageView2_gank_item)
        GirlImageView imageView2GankItem;
        @BindView(R.id.imageView3_gank_item)
        GirlImageView imageView3GankItem;
        @BindView(R.id.linearLayout_gank_item)
        LinearLayout linearLayout_gank_item;
        @BindView(R.id.linearLayout_content_gank_item)
        LinearLayout linearLayout_content_gank_item;

        public GankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imageView1GankItem.setPic(50,50);
            imageView2GankItem.setPic(50,50);
            imageView3GankItem.setPic(50,50);
        }
    }
}
