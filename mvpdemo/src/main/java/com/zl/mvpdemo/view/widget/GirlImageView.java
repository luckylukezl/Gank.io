package com.zl.mvpdemo.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by ZL on 2017/2/16.
 */

public class GirlImageView extends ImageView {

    private int picWidth;
    private int picHeight;

    public GirlImageView(Context context) {
        super(context);
    }

    public GirlImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GirlImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPic(int width,int height){
        picWidth = width;
        picHeight = height;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(picWidth > 0 && picHeight > 0){

            //根据图片比例调节ImageView的长宽

            float ratio = (float)picWidth / (float)picHeight;
            //保证width<=height
            if(ratio > 1)ratio = 1;
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);

            //Log.i("zlTag" , picWidth + ":" + picHeight + "," + width + ":" + height);

            //固定宽度,width<=height
            if(width > 0){

                height = (int) ( (float)width / ratio );

            }

            setMeasuredDimension(width,height);

        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }
}
