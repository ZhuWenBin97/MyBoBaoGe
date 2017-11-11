package com.zcy.hnkjxy.customview.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhang chen yang on 2017/9/25 0025.
 */

public class CircleView extends View implements View.OnTouchListener {
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(TAG, "dispatchTouchEvent: "+CircleView.class.getName());
        return super.dispatchTouchEvent(event);
    }

    private Paint paint;
    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private static final String TAG = "TAG";
    private void init(){
        Log.i(TAG, "init: ");
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        setOnTouchListener(this);
//        setOnClickListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: "+CircleView.class.getName());
        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private int getModeSize(int measureSpec){

        int defaultSize = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode){
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
            default:break;
        }
        return defaultSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int pWidth = getWidth()-getPaddingLeft()-getPaddingRight();
        int pHeight = getHeight()-getPaddingBottom()-getPaddingTop();
        float r = Math.min(pWidth,pHeight)/2;
        canvas.drawCircle(pWidth/2+getPaddingLeft(),pHeight/2+getPaddingTop(),r,paint);
        Log.i(TAG, "onDraw: x:"+pWidth+" y:"+pHeight+"left:"+getLeft());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i(TAG, "onTouch: "+CircleView.class.getName());
        return true;
    }


}
