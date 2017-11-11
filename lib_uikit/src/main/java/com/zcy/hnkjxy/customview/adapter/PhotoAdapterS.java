package com.zcy.hnkjxy.customview.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.zcy.hnkjxy.customview.bean.ImageBean;
import com.zcy.hnkjxy.customview.util.GlideLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhang chen yang on 2017/9/29 0029.
 */

public class PhotoAdapterS extends PagerAdapter{
    private Fragment mContext;
    private List<ImageBean> mList;
    private List<PhotoView> mPhoto = new ArrayList<>();
    public PhotoAdapterS(Fragment mContext, List<ImageBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        initView();
    }
    private void initView(){
        PhotoView v;
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();
        params.width = ViewPager.LayoutParams.MATCH_PARENT;
        params.height = ViewPager.LayoutParams.WRAP_CONTENT;
        for (int i = 0; i < 5; i++) {
            v = new PhotoView(mContext.getContext());
            v.setLayoutParams(params);
            mPhoto.add(v);
        }

    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView v = mPhoto.get(position%5);
        container.addView(v);
        GlideLoader.load(mContext,mList.get(position).getImgUrl(), v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        PhotoView v = mPhoto.get(position%5);
        Log.i(TAG, "destroyItem: "+container.getChildCount());
        Log.i(TAG, "destroyItem: "+mPhoto.size());
        container.removeView(v);
    }

    private static final String TAG = "PhotoAdapterS";
    public void clear(){
        this.mPhoto.clear();
        this.mList.clear();
        GlideLoader.clear(mContext.getContext());
        mPhoto = null;
        mList = null;
        this.mContext = null;
    }
}
