package com.zcy.hnkjxy.customview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhang chen yang on 2017/10/7 0007.
 */

public class RefreshListView extends ListView {
    private final int PULL_REFRESH = 0;//下拉刷新的状态
    private final int RELEASE_REFRESH = 1;//松开刷新的状态
    private final int REFRESHING = 2;//正在刷新的状态
    private final int RELEASE_LOAD = 3;//松开加载的状态
    private final int LOADING = 2;
    private final int DO_REFRESH = 4;//需要执行刷新
    private final int DO_NOT_REFRESH = 5;//不需要执行刷新
    private int STATE_HEADER = -1;//下拉加载状态
    private int STATE_FOOTER = -1;//上拉刷新加载状态
    private Context mContext;
    private View headerView, footerView;
    private int headerHeight, footerHeight;
    private int maxPullLength = 160;
    //头部的控件
    private ImageView imgJt, imgLoad;
    private TextView textHeader, textTime;
    //监听器
    private OnLoadMoreListener onLoadMoreListener;
    private OnRefreshListener onRefreshListener;

    public RefreshListView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        headerView = LayoutInflater.from(mContext).inflate(R.layout.pull_header, null);
        footerView = LayoutInflater.from(mContext).inflate(R.layout.push_footer, null);
        imgJt = (ImageView) headerView.findViewById(R.id.imgPullJt);
        imgLoad = (ImageView) headerView.findViewById(R.id.imgPullLoad);
        textHeader = (TextView) headerView.findViewById(R.id.tvPullLayHeader);
        textTime = (TextView) headerView.findViewById(R.id.tvPullTime);
        //
        measureView(headerView);
        measureView(footerView);
        headerHeight = headerView.getMeasuredHeight();
        footerHeight = footerView.getMeasuredHeight();
        headerView.setPadding(0, headerView.getMeasuredHeight() * -1, 0, 0);
        footerView.setPadding(0, 0, 0, footerView.getMeasuredHeight() * -1);
        this.addHeaderView(headerView);
        this.addFooterView(footerView);

    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    // 此方法直接照搬自网络上的一个下拉刷新的demo，此处是“测量”headView的width以及height
    private void measureView(View child) {
        ViewGroup.LayoutParams params = child.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0,
                params.width);
        int lpHeight = params.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    //dx to dp方法
    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    public void closeLoadMore() {

        post(new Runnable() {
            @Override
            public void run() {
                footerAnim(false);
            }
        });
    }

    public void closeRefresh() {
        post(new Runnable() {
            @Override
            public void run() {
                headerAnimControl(false);
                setSelection(1);
            }
        });
    }


    private void headerAnimControl(boolean isStart) {
//        setSelection(0);
        if (isStart) {
            STATE_HEADER = REFRESHING;
            headerView.setPadding(0, 0, 0, 0);
            imgJt.setVisibility(GONE);
            imgLoad.setVisibility(VISIBLE);
            textHeader.setText("正在刷新");
            if (headerRoteAnim == null) {
                headerRoteAnim = ObjectAnimator.ofFloat(imgLoad, "Rotation", 0, 360);
                headerRoteAnim.setDuration(1500).setRepeatCount(ValueAnimator.INFINITE);
                headerRoteAnim.setInterpolator(new LinearInterpolator());
            }
            headerRoteAnim.start();

        } else {
            headerView.setPadding(0, -1 * headerHeight, 0, 0);
            imgJt.setVisibility(VISIBLE);
            imgLoad.setVisibility(GONE);
            textHeader.setText("下拉刷新");
            textTime.setText("上次刷新时间：" + getCurrentTime());
            if (headerRoteAnim != null)
                headerRoteAnim.cancel();
            STATE_HEADER = PULL_REFRESH;
        }
    }

    //底部加载更多动画
    private ObjectAnimator footerAnim, headerRoteAnim;

    private void footerAnim(boolean isStart) {

        ImageView b = (ImageView) footerView.findViewById(R.id.imgLoading);
        TextView tv = (TextView) footerView.findViewById(R.id.tvFooterText);
        if (isStart) {
            STATE_FOOTER = LOADING;
            b.setVisibility(VISIBLE);
            tv.setText("正在加载");
            footerView.setPadding(0, 0, 0, 0);
            if (footerAnim == null) {
                footerAnim = ObjectAnimator.ofFloat(b, "Rotation", 0, 360);
                footerAnim.setDuration(2000).setRepeatCount(ValueAnimator.INFINITE);
                footerAnim.setInterpolator(new LinearInterpolator());
            }
            footerAnim.start();
            setSelection(getCount());
        } else {
            STATE_FOOTER = -1;
            if (footerAnim != null)
                footerAnim.cancel();
            tv.setText("加载完成");
            footerView.setPadding(0, 0, 0, footerHeight * -1);
        }
    }

    //下拉刷新动画
    private ObjectAnimator headerAnimDown, headerAnimUp = ObjectAnimator.ofFloat(null, "Rotation", 180, 0);

    private void headerAnim() {

        if (headerAnimDown == null && STATE_HEADER == DO_REFRESH) {
            textHeader.setText("松开刷新");
            headerAnimDown = ObjectAnimator.ofFloat(imgJt, "Rotation", 0, 180);
            headerAnimDown.setDuration(300);
            headerAnimDown.start();
        }
        if (headerAnimUp == null && STATE_HEADER == DO_NOT_REFRESH) {
            Log.i("TAG", "headerAnim: ");
            textHeader.setText("下拉刷新");
            headerAnimUp = ObjectAnimator.ofFloat(imgJt, "Rotation", 180, 0);
            headerAnimUp.setDuration(300);
            headerAnimUp.start();
        }

    }


    private static final String TAG = "RefreshListView";
    int downY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            downY = (int) ev.getY();
            Log.i(TAG, "downY: " + downY);
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            //滑动到头部
            if (onRefreshListener != null && getFirstVisiblePosition() == 0 && ev.getY() - downY > 0 && STATE_HEADER != REFRESHING) {
                headerView.setPadding(0, (int) ((ev.getY() - downY - headerHeight)), 0, 0);
                if (ev.getY() - downY > maxPullLength) {
                    STATE_HEADER = DO_REFRESH;
                    headerAnimUp = null;
                } else {
                    STATE_HEADER = DO_NOT_REFRESH;
                    headerAnimDown = null;
                }
                headerAnim();
                Log.i(TAG, "1getY - downY = " + (ev.getY() - downY));
                return true;
            }
            Log.i(TAG, "2getY - downY = " + (ev.getY() - downY));
            //滑动到底部
            if (onLoadMoreListener != null && getLastVisiblePosition() == getCount() - 1 && ev.getY() - downY < 0 && STATE_FOOTER != LOADING) {
                STATE_FOOTER = RELEASE_LOAD;
            }

        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            //需要刷新
            if (STATE_HEADER == DO_REFRESH) {
                STATE_HEADER = RELEASE_REFRESH;
//                headerAnimUp = new ObjectAnimator();
            }
            //不需要刷新
            else if (STATE_HEADER == DO_NOT_REFRESH) {
                headerView.setPadding(0, -1 * headerHeight, 0, 0);
            }
            Log.i(TAG, "onTouchEvent: up:"+STATE_HEADER);
            Log.i(TAG, "onScrollStateChanged: ");
            if (STATE_FOOTER == RELEASE_LOAD) {
                Log.i(TAG, "onScrollStateChanged: 释放手指加载更多");
                footerAnim(true);
                onLoadMoreListener.onLoadMore();
            }
            //下拉刷新
            if (STATE_HEADER == RELEASE_REFRESH) {
                headerAnimControl(true);
                onRefreshListener.onRefresh();
                Log.i(TAG, "onScrollStateChanged: 开始刷新");
                headerAnimUp = ObjectAnimator.ofFloat(imgJt, "Rotation", 180, 0);
                headerAnimUp.setDuration(0);
                headerAnimUp.start();
            }
        }

        return super.onTouchEvent(ev);
    }

    private String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yy年MM月dd日 HH:mm:ss");
        return format.format(new Date());
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
