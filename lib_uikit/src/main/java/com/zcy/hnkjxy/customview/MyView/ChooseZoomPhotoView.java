package com.zcy.hnkjxy.customview.MyView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zcy.hnkjxy.customview.R;
import com.zcy.hnkjxy.customview.adapter.PhotoAdapter;
import com.zcy.hnkjxy.customview.adapter.PhotoAdapterS;
import com.zcy.hnkjxy.customview.bean.ImageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang chen yang on 2017/9/29 0029.
 */

public class ChooseZoomPhotoView extends DialogFragment implements ViewPager.OnPageChangeListener, View.OnClickListener {
    //适配器
    private PhotoAdapterS adapter;
    private List<ImageBean> mList = new ArrayList<>();
    //指示器和左上角返回按钮
    private TextView tvIndia,btnBack;
    //ViewPager容纳图片
    private MyViewPager vpPhoto;
    //当前页，指示器颜色，界面背景
    private int currentItem=0, textColor=Color.WHITE, bacColor=Color.BLACK;
    //指示器字体大小
    private float textSize=20;
    //右上角圆形选择按钮
    private TextView btnCircleChoose;
    //右下角提交按钮
    private Button btnPost;
    //头部布局
    private View relayHeader;
    //底部布局
    private View relayFooter;
    //
    private OnSelectedListener onSelectedListener;

    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //帧布局
        FrameLayout flay = new FrameLayout(getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //图片显示位置
        vpPhoto = new MyViewPager(getContext());
        vpPhoto.setLayoutParams(params);
        adapter = new PhotoAdapterS(this, mList);
        vpPhoto.setAdapter(adapter);
        vpPhoto.addOnPageChangeListener(this);
        //加载头布局
        relayHeader = LayoutInflater.from(getContext()).inflate(R.layout.choose_header_item,null);
        //加载底布局
        relayFooter = LayoutInflater.from(getContext()).inflate(R.layout.choose_footer_item,null);
        //得到View实例
        btnBack = (TextView) relayHeader.findViewById(R.id.tvChooseHeaderBack);
        tvIndia = (TextView) relayHeader.findViewById(R.id.tvChooseHeaderInd);
        btnCircleChoose = (TextView) relayHeader.findViewById(R.id.tvChooseHeaderSelect);
        btnPost = (Button) relayFooter.findViewById(R.id.btnChooseFooterPost);
        //设置布局属性
        FrameLayout.LayoutParams paramsHeader = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsHeader.gravity = Gravity.TOP;
        relayHeader.setLayoutParams(paramsHeader);
        FrameLayout.LayoutParams paramsFooter = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsFooter.gravity = Gravity.BOTTOM;
        relayFooter.setLayoutParams(paramsFooter);
        //设置相关属性
        tvIndia.setTextSize(textSize);
        tvIndia.setTextColor(textColor);
        vpPhoto.setBackgroundColor(bacColor);
        //加入flay布局
        flay.addView(vpPhoto);
        flay.addView(relayHeader);
        flay.addView(relayFooter);
        return flay;
    }


    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    public ChooseZoomPhotoView setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
        return this;
    }

    public ChooseZoomPhotoView addImages(ArrayList<ImageBean> images) {
        mList.clear();
        mList.addAll(images);
        return this;
    }

    private void initView() {
        adapter.notifyDataSetChanged();
        tvIndia.setText(1 + currentItem + "/" + mList.size());
        vpPhoto.setCurrentItem(currentItem);
        onPageSelected(currentItem);
        btnCircleChoose.setOnClickListener(this);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseZoomPhotoView.this.dismiss();
            }
        });
        if(onSelectedListener.getCuntSum()>0){
            btnPost.setText("确定("+onSelectedListener.getCuntSum()+")");
        }
        else
            btnPost.setText("确定");
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectedListener.addImages(mList.get(vpPhoto.getCurrentItem()));
                onSelectedListener.finish();
            }
        });
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
    private static final String TAG = "ChooseZoomPhotoView";
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tvIndia.setText(1 + position + "/" + mList.size());
        if (mList.get(position).isSelected()){
            btnCircleChoose.setBackgroundResource(R.drawable.radio_no_selected);
            btnCircleChoose.setText(""+onSelectedListener.getCurrentCount(mList.get(position)));
        }
        else{
            btnCircleChoose.setBackgroundResource(R.drawable.radio_selected);
            btnCircleChoose.setText("");
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        if(onSelectedListener==null)
            throw new NullPointerException("onSelectedListener is null");
        boolean isChecked = mList.get(vpPhoto.getCurrentItem()).isSelected();
        //如果选中则取消选中，反之亦然
        mList.get(vpPhoto.getCurrentItem()).setSelected(!isChecked);
        if (isChecked){
            btnCircleChoose.setBackgroundResource(R.drawable.radio_selected);
            onSelectedListener.removeImages(mList.get(vpPhoto.getCurrentItem()));
            if(onSelectedListener.getCuntSum()!=0) {
                btnCircleChoose.setText("");
                btnPost.setText("确定("+onSelectedListener.getCuntSum()+")");
            }
            else{
                btnCircleChoose.setText("");
                btnPost.setText("确定");
            }
        }
        else{
            btnCircleChoose.setBackgroundResource(R.drawable.radio_no_selected);
            onSelectedListener.addImages(mList.get(vpPhoto.getCurrentItem()));
            btnPost.setText("确定("+onSelectedListener.getCuntSum()+")");
            btnCircleChoose.setText(""+onSelectedListener.getCurrentCount(mList.get(vpPhoto.getCurrentItem())));
        }

    }
//  清理数据
    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach: ");
        mList.clear();
        adapter.clear();
        mList = null;
        adapter = null;
        onSelectedListener = null;
    }

    public interface OnSelectedListener{
        void addImages(ImageBean imageBean);
        void removeImages(ImageBean imageBean);
        void finish();
        int getCurrentCount(ImageBean imageBean);
        int getCuntSum();
    }

}
