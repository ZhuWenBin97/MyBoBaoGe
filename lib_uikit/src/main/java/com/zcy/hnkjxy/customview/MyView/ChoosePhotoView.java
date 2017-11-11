package com.zcy.hnkjxy.customview.MyView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcy.hnkjxy.customview.R;
import com.zcy.hnkjxy.customview.bean.ImageBean;
import com.zcy.hnkjxy.customview.util.GlideLoader;
import com.zcy.hnkjxy.customview.util.ImageLoader;

/**
 * Created by zhang chen yang on 2017/10/11 0011.
 * qq空间发表照片时选择照片时的view
 */

public class ChoosePhotoView extends ViewGroup implements View.OnClickListener {
    //图片路径
    private ImageBean mImageBean;
    //是否给图片蒙灰
    private boolean mIsGray;
    //使用ImageView作为展示图片的控价
    private ImageView mImage, grayImage;
    //选择的按钮
    private TextView btnSelect;
    //监听事件
    private OnSelectedListener mOnSelectedListener;
    //设置选中按钮的边距
    private int mRadioMargin;
    //上下文对象
    private Context mContext;

    public ChoosePhotoView(Context context) {
        super(context);
        init(context);
    }

    public ChoosePhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setOnSelectedListener(OnSelectedListener mOnSelectedListener) {
        this.mOnSelectedListener = mOnSelectedListener;
    }

    public void setImagePath(ImageBean imagePath) {
        this.mImageBean = imagePath;
        if (mImageBean != null) {
            GlideLoader.load(mContext, mImageBean.getImgUrl(), mImage);
        }
        updateView();
    }

    //初始化group
    private void init(Context context) {
        mContext = context;
        mRadioMargin = dp2px(2);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(80, 80);
        mImage = new ImageView(context);
        mImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        grayImage = new ImageView(context);
        grayImage.setLayoutParams(params1);
        grayImage.setBackgroundColor(0x55000000);
        grayImage.setVisibility(GONE);
        mImage.setLayoutParams(params1);
        btnSelect = new TextView(context);
        btnSelect.setTextColor(Color.WHITE);
        btnSelect.setLayoutParams(params2);
        btnSelect.setBackgroundResource(R.drawable.radio_selected);
        btnSelect.setGravity(Gravity.CENTER);
        btnSelect.setOnClickListener(this);
        this.addView(mImage, 0);
        this.addView(grayImage);
        this.addView(btnSelect, 2);
        mImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnSelectedListener.onClick(v, mImageBean);
            }
        });
    }

    /**
     * 测量布局
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        measureChildren(widthMeasureSpec, widthMeasureSpec);
    }

    private static final String TAG = "ChoosePhotoView";

    /**
     * 定位子布局
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //子view进行布局
        //image
        View v1 = getChildAt(0);
        //蒙版
        View v2 = getChildAt(1);
        //btn
        View v3 = getChildAt(2);
        v1.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        v2.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        v3.layout(getMeasuredWidth() - v3.getMeasuredWidth() - mRadioMargin, mRadioMargin + 0, getMeasuredWidth() - mRadioMargin, mRadioMargin + v3.getMeasuredHeight());

    }

    public void updateView() {
        Log.i("TAGXY", "updateView: ");
        //给ImageView添加灰色蒙版
        mIsGray = mImageBean.isSelected();
        if (mIsGray) {
            grayImage.setVisibility(VISIBLE);
            btnSelect.setBackgroundResource(R.drawable.radio_no_selected);
            btnSelect.setText("" + mOnSelectedListener.getCurrentCount(mImageBean));

        }
        else {
            grayImage.setVisibility(GONE);
            btnSelect.setText("");
            btnSelect.setBackgroundResource(R.drawable.radio_selected);
        }
    }

    @Override
    public void onClick(View v) {
        Log.i("TAGXY", "onClick: 单击");
        mIsGray = mImageBean.isSelected();
        mIsGray = !mIsGray;
        mImageBean.setSelected(mIsGray);
        //给ImageView添加灰色蒙版
        if (mIsGray) {
            if (mOnSelectedListener != null) {
                mOnSelectedListener.onAdd(mImageBean);
            }
        } else {
            if (mOnSelectedListener != null) {
                mOnSelectedListener.onRemove(mImageBean);
            }
        }
    }





    //dx to dp方法
    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

public interface OnSelectedListener {
     void onAdd(ImageBean imageBean);
     void onRemove(ImageBean imageBean);
     void onClick(View view, ImageBean imageBean);
     int getCurrentCount(ImageBean imageBean);
}

}
