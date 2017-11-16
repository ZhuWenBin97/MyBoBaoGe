package com.zcy.hnkjxy.customview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zcy.hnkjxy.customview.adapter.CliCkListener;
import com.zcy.hnkjxy.customview.adapter.ShufflingAdapter;
import com.zcy.hnkjxy.customview.callback.OnShufflingItemClickListener;
import com.zcy.hnkjxy.customview.util.GlideLoader;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhang chen yang on 2017/9/26 0026.
 * 轮播图
 */

public class AutoShufflingView extends LinearLayout implements ViewPager.OnPageChangeListener, CliCkListener {
    //父Activity
    private Activity mActivity;
    //图片的地址
    private String[] mImgUrl;
    //ViewPager
    private ViewPager mVpShuffling;
    //LinearLayout指示器
    private LinearLayout mLlIndicator;
    //指示器图标
    private ImageView[] indImages = null;
    //事件回调
    private OnShufflingItemClickListener onShufflingItemClickListener;
    //适配器
    private ShufflingAdapter adapter = null;
    //是否按下
    private boolean isTouch = false;
    /**
     * 自定义属性
     */
    //指示器图标地址
    private int[] indicatorImage = new int[2];
    //是否启用自动轮播
    private boolean isAuto;
    //轮播切换时间
    private int shufflingTime;
    //加载图片时的过渡图片和加载错误时的图片
    private int loading, loadError;
    //指示器所在的位置
    private int indicatorGravityFlag;

    public AutoShufflingView(Context context) {
        super(context);
        init((Activity) context);
    }


    public AutoShufflingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //获取XML文件的属性
        //即属性集合的标签，在R文件中名称为R.styleable+name
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoShufflingView);
        indicatorImage[0] = a.getResourceId(R.styleable.AutoShufflingView_radio_on, R.drawable.radio_button_on);
        indicatorImage[1] = a.getResourceId(R.styleable.AutoShufflingView_radio_off, R.drawable.radio_button_off);
        isAuto = a.getBoolean(R.styleable.AutoShufflingView_autoShuffling, false);
        shufflingTime = a.getInteger(R.styleable.AutoShufflingView_autoShufflingTime, 3000);
        loading = a.getResourceId(R.styleable.AutoShufflingView_loading, R.drawable.image_loading);
        loadError = a.getResourceId(R.styleable.AutoShufflingView_load_error, R.drawable.image_load_fail);
        indicatorGravityFlag = a.getInteger(R.styleable.AutoShufflingView_indicator_gravity, 1);
        GlideLoader.loadErrorImg = loadError;
        GlideLoader.loadingImg = loading;
        if (shufflingTime <= 0) {
            shufflingTime = 3000;
        }
        init((Activity) context);
    }

    //初始化操作
    private void init(Activity activity) {
        this.mActivity = activity;
        //添加FrameLayout界面，用于存放ViewPager和指示器
        FrameLayout pFlayout = new FrameLayout(mActivity);
        pFlayout.setBackgroundColor(Color.RED);
        LinearLayout.LayoutParams fParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(pFlayout, fParam);
        //添加ViewPager界面
        mVpShuffling = new ViewPager(mActivity);
        pFlayout.addView(mVpShuffling, fParam);
        //添加指示器
        mLlIndicator = new LinearLayout(mActivity);
        FrameLayout.LayoutParams indicatorParam = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dp2px(20));
        mLlIndicator.setOrientation(LinearLayout.HORIZONTAL);
        if (indicatorGravityFlag == -1) {
            indicatorParam.gravity = Gravity.BOTTOM | Gravity.LEFT;
        } else if (indicatorGravityFlag == 0) {
            indicatorParam.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        } else {
            indicatorParam.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        }
        indicatorParam.setMargins(dp2px(20), dp2px(10), dp2px(20), dp2px(10));
        pFlayout.addView(mLlIndicator, indicatorParam);
        //

    }

    public void setImages(String[] imageUrl) {
        if (imageUrl.length <= 0 || imageUrl[0] == null) {
            throw new NullPointerException("imageUrl is null,图片URL为空");
        }
        //添加数据到ViewPager
        mImgUrl = imageUrl;
        if (adapter == null) {
            adapter = new ShufflingAdapter(mActivity, mImgUrl);
        }
        mVpShuffling.setAdapter(adapter);
        //添加指示器
        indImages = new ImageView[imageUrl.length];
        for (int i = 0; i < imageUrl.length; i++) {
            ImageView imageView = new ImageView(mActivity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, 30);
            params.setMargins(5, 0, 5, 0);
            imageView.setBackgroundResource(indicatorImage[0]);
            indImages[i] = imageView;
            mLlIndicator.addView(imageView, params);
        }
        mVpShuffling.addOnPageChangeListener(this);
        mVpShuffling.setCurrentItem(1);
        adapter.notifyDataSetChanged();
        autoShuffling();
    }

    private void autoShuffling() {
        if (isAuto && mImgUrl.length > 1) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    AutoShufflingView.this.post(new Runnable() {
                        @Override
                        public void run() {
                            if (isTouch) {
                                return;
                            }
                            int next = mVpShuffling.getCurrentItem() + 1;
                            mVpShuffling.setCurrentItem(next);
                        }
                    });
                }
            }, shufflingTime, shufflingTime);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // 一定几个图片，几个圆点，但注意是从0开始的
        int total = indImages.length;
//        Log.drawStart("TAG", "onPageScrolled: " + position);
        for (int j = 0; j < total; j++) {
            if (j == position - 1) {
                indImages[j].setBackgroundResource(indicatorImage[0]);
            } else {
                indImages[j].setBackgroundResource(indicatorImage[1]);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            if (mVpShuffling.getCurrentItem() == 0) {
                mVpShuffling.setCurrentItem(mImgUrl.length, false);
            } else if (mVpShuffling.getCurrentItem() == mImgUrl.length + 1) {
                mVpShuffling.setCurrentItem(1, false);
            }
        }
    }

    public void setOnShufflingItemClickListener(OnShufflingItemClickListener onShufflingItemClickListener) {
        this.onShufflingItemClickListener = onShufflingItemClickListener;
        this.adapter.setCallBack(this);
    }

    //dp to px方法
    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private static final String TAG = "TAG";


    @Override
    public void onClick(int position) {
        if (onShufflingItemClickListener != null) {
            this.onShufflingItemClickListener.onClickItem(position);
        }
    }

    public void clearCache(Context context) {
        GlideLoader.clear(context);
    }

    @Override
    public void onLongClick(int position) {
        if (onShufflingItemClickListener != null) {
            this.onShufflingItemClickListener.onLongClickItem(position);
        }
    }
}
