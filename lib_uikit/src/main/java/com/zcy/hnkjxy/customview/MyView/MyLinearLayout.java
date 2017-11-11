package com.zcy.hnkjxy.customview.MyView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhang chen yang on 2017/9/25 0025.
 */

public class MyLinearLayout extends ViewGroup implements View.OnTouchListener, View.OnClickListener {

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
//        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }

    private static final String TAG = "TAG";
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent: "+MyLinearLayout.class.getName());
        if(ev.getAction()==MotionEvent.ACTION_MOVE){
            Log.i(TAG, "onInterceptTouchEvent: 移动拦截事件");
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int i = getChildCount();
        int vCurrentHeight = 0;
        int pWidth = getMeasuredWidth();
        for (int j = 0;j < i;j++) {
            View v = getChildAt(j);
            v.getMeasuredHeight();
            v.layout(pWidth/2-v.getMeasuredWidth()/2,vCurrentHeight,
                    pWidth/2-v.getMeasuredWidth()/2+v.getMeasuredWidth(),v.getMeasuredHeight()+j*v.getMeasuredHeight());
            vCurrentHeight+=v.getMeasuredHeight();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i(TAG, "onTouch: "+MyLinearLayout.class.getName());
        return false;
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: "+MyLinearLayout.class.getName());
    }
}
