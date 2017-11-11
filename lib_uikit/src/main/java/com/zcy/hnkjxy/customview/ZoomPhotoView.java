package com.zcy.hnkjxy.customview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zcy.hnkjxy.customview.MyView.HackyViewPager;
import com.zcy.hnkjxy.customview.MyView.MyViewPager;
import com.zcy.hnkjxy.customview.adapter.PhotoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang chen yang on 2017/9/29 0029.
 */

public class ZoomPhotoView extends DialogFragment implements ViewPager.OnPageChangeListener {
    private PhotoAdapter adapter;
    private List<String> mList = new ArrayList<>();
    private TextView tvIndia;
    private HackyViewPager vpPhoto;
    private int currentItem,textColor,bacColor;
    private float textSize;
    public static Creator Create;
    static {
        Create = new Creator();
    }

    static class Instance {
        static ZoomPhotoView Instance = new ZoomPhotoView();
    }

    private ZoomPhotoView() {
        currentItem = Create.currentItem;
        textColor = Create.color;
        bacColor = Create.bacColor;
        textSize = Create.size;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //帧布局
        FrameLayout flay = new FrameLayout(getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        下标显示
        FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvIndia = new TextView(getContext());
        param.gravity = Gravity.CENTER | Gravity.BOTTOM;
        param.setMargins(50, 50, 50, 50);
        tvIndia.setLayoutParams(param);

        //图片显示位置
        vpPhoto = new HackyViewPager(getContext());
        vpPhoto.setLayoutParams(params);
        adapter = new PhotoAdapter(this, mList);
        vpPhoto.setAdapter(adapter);
        vpPhoto.addOnPageChangeListener(this);
        //设置相关属性
        tvIndia.setTextSize(textSize);
        tvIndia.setTextColor(textColor);
        vpPhoto.setBackgroundColor(bacColor);
        //加入flay布局
        flay.addView(vpPhoto);
        flay.addView(tvIndia);
        return flay;
    }


    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    public ZoomPhotoView addImages(ArrayList<String> images) {
        mList.clear();
        mList.addAll(images);
        return Instance.Instance;
    }

    private void initView() {
        adapter.notifyDataSetChanged();
        tvIndia.setText(1 + currentItem + "/" + mList.size());
        vpPhoto.setCurrentItem(currentItem);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private static final String TAG = "ZoomPhotoView";

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: ");
        adapter.clear();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tvIndia.setText(1 + position + "/" + mList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static class Creator {
        int color;
        float size;
        int bacColor;
        int currentItem;
        public Creator(){
            this.color = Color.WHITE;
            this.size = 28;
            this.bacColor = Color.BLACK;
            this.currentItem = 0;
        }
        public Creator color(int textColor){
            this.color = textColor;
            return this;
        }
        public Creator size(float textSize){
            this.size = textSize;
            return this;
        }
        public Creator bacColor(int backgroundColor){
            this.bacColor = backgroundColor;
            return this;
        }
        public Creator current(int currentItem){
            this.currentItem = currentItem;
            return this;
        }
        public ZoomPhotoView build(){
            return Instance.Instance;
        }
    }
}
