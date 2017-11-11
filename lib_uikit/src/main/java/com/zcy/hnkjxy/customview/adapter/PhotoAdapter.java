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

public class PhotoAdapter extends PagerAdapter{
    private Fragment mContext;
    private List<String> mList;
    private List<PhotoView> mPhoto = new ArrayList<>();
    public PhotoAdapter(Fragment mContext, List<String> mList) {
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
        Log.i(TAG, "instantiateItem: 添加："+position);
        container.addView(v);
        Log.i(TAG, "    : 完成添加："+position);
        GlideLoader.load(mContext,mList.get(position), v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.i(TAG, "destroyItem: 移除："+(position%5));
        PhotoView v = mPhoto.get(position%5);
        Glide.clear(v);
        container.removeView(v);
        Log.i(TAG, "destroyItem: 移除一个view:"+position);
    }

    private static final String TAG = "PhotoAdapter";
    public void clear(){
        this.mPhoto.clear();
        this.mList.clear();
        mPhoto = null;
        mList = null;
        GlideLoader.clear(mContext.getContext());
        this.mContext = null;
    }
}
