package com.zcy.hnkjxy.customview.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.zcy.hnkjxy.customview.MyView.ChoosePhotoView;
import com.zcy.hnkjxy.customview.bean.ImageBean;
import com.zcy.hnkjxy.customview.bean.ImageFolderBean;
import com.zcy.hnkjxy.customview.util.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang chen yang on 2017/10/11 0011.
 */

public class PhotoChooseAdapter extends BaseAdapter {
    //Context文件
    private Activity mContext;
    //所有的图片
    private List<ImageBean> mList;
    //单击事件
    ChoosePhotoView.OnSelectedListener mOnSelectedListener;
    private List<ChoosePhotoView> photoViewList;

    public PhotoChooseAdapter(Activity mContext, List<ImageBean> mList, ChoosePhotoView.OnSelectedListener mOnSelectedListener) {
        this.mContext = mContext;
        this.mList = mList;
        this.mOnSelectedListener = mOnSelectedListener;
        this.photoViewList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    int count = 0;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new ChoosePhotoView(mContext);
            photoViewList.add((ChoosePhotoView) convertView);
            count++;
            Log.i("TAGXY", "getView: count:" + count);
        }
        ChoosePhotoView chooseView = (ChoosePhotoView) convertView;
        chooseView.setOnSelectedListener(mOnSelectedListener);
        chooseView.setImagePath(mList.get(position));
        return chooseView;
    }

    public void updateView() {
        for (ChoosePhotoView v : photoViewList)
            v.updateView();
    }

    public void clear() {
        GlideLoader.clear(mContext);
        for (ChoosePhotoView v : photoViewList) {
            v.removeAllViews();
        }
    }
}
