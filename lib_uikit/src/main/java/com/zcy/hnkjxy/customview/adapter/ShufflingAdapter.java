package com.zcy.hnkjxy.customview.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zcy.hnkjxy.customview.util.GlideLoader;
import com.zcy.hnkjxy.customview.R;

/**
 * Created by zhang chen yang on 2017/9/26 0026.
 */

public class ShufflingAdapter extends PagerAdapter implements View.OnClickListener, View.OnLongClickListener{
    private Activity mActivity;
    private String[] imgUrl;
    private ImageView[] imageViews;
    private CliCkListener callBack;

    public void setCallBack(CliCkListener callBack) {
        this.callBack = callBack;
    }

    public ShufflingAdapter(Activity mActivity, String[] imgsUrl) {
        this.mActivity = mActivity;
        this.imgUrl = imgsUrl;
        this.imageViews = new ImageView[imgsUrl.length+2];
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i] = new ImageView(mActivity);
            imageViews[i].setImageResource(R.drawable.chageinfo);
            imageViews[i].setOnClickListener(this);
            imageViews[i].setOnLongClickListener(this);
            Log.i(TAG, "ShufflingAdapter: imageViews"+i);
        }
    }

    private static final String TAG = "ShufflingAdapter";
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.i(TAG, "instantiateItem: aaaaa"+imageViews[position]);
        //第一项加载倒数最后一项
        if(position == 0){
            GlideLoader.load(mActivity, imgUrl[imgUrl.length-1],imageViews[position]);
        }
        //最后一项加载第一项
        else if(position == imgUrl.length+1){
            GlideLoader.load(mActivity, imgUrl[0],imageViews[position]);
        }
        //加载imgUrl.length-1项
        else {
            GlideLoader.load(mActivity, imgUrl[position-1],imageViews[position]);
        }
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();
        imageViews[position].setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imageViews[position]);
        return imageViews[position];
    }

    @Override
    public int getCount() {
        return imgUrl.length+2;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView(imageViews[position]);
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void onClick(View v) {
        if(callBack==null)
            return;
        int position = -1;
        for (int i = 0; i < imageViews.length; i++) {
            if(v == imageViews[i]){
                Log.i(TAG, "onClick: "+i);
                position = i;
            }
        }
        if(position ==0){
            position = imageViews.length-2;
        }else if(position == imageViews.length-1){
            position = 0;
        }
        else position = position-1;
        callBack.onClick(position);
        Log.i(TAG, "onClick: position:"+position);

    }

    @Override
    public boolean onLongClick(View v) {
        if (callBack == null)
            return false;
        int position = -1;
        for (int i = 0; i < imageViews.length; i++) {
            if(v == imageViews[i]){
                Log.i(TAG, "onClick: "+i);
                position = i;
            }
        }
        if(position ==0){
            position = imageViews.length-2;
        }else if(position == imageViews.length-1){
            position = 0;
        }
        else position = position-1;
        callBack.onLongClick(position);
        return true;
    }

}
