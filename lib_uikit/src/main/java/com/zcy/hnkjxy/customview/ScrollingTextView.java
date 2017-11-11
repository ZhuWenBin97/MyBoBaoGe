package com.zcy.hnkjxy.customview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by zhang chen yang on 2017/9/28 0028.
 * 文字上下左右滑动View
 */

public class ScrollingTextView extends View {
    /**
     * 进入的方向
     */
    private static final int RIGHT = 2;
    /**
     * XML定义的属性
     */
    //     哪个方向进入
    private int scrollFlag = 2;
    //     播放速度
    private int scrollSpeed = 10;
    //     轮播文本
    private String text = "你好世界";
    //    轮播文本的字体大小
    private int textSize;
    //轮播字体的颜色
    private int textColor;
    //滑动时一句话之间的间隔
    private int textInterval;
    //     画笔
    private Paint mPaint;
    //    借助rect计算文本大小
    private Rect rect;
    //    超出值
    private int outSide;
    //    文字的宽度和高度
    private int tWidth, tHeight;
    //     界面刷新速率
    private final  int REFRESH_ROTE = 10;

    public ScrollingTextView(Context context) {
        super(context);
        init();

    }

    public ScrollingTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScrollingTextView);
        this.scrollSpeed = a.getInteger(R.styleable.ScrollingTextView_scrollSpeed, 1);
        this.text = a.getString(a.getIndex(R.styleable.ScrollingTextView_text));
        this.textSize = (int) a.getDimension(R.styleable.ScrollingTextView_textSize, sp2px(12));
        this.textColor = a.getColor(R.styleable.ScrollingTextView_textColor, Color.BLACK);
        this.textInterval = (int) a.getDimension(R.styleable.ScrollingTextView_textInterval, dp2px(20));
        init();
    }

    private void init() {
        Paint paint = new Paint();
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        mPaint = paint;
        rect = new Rect();
    }


    int h ;
    int b ;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mPaint.getTextBounds(text, 0, text.length(), rect);
        // FontMetrics对象
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        b = (int) fontMetrics.bottom;
        h = (int) Math.abs(fontMetrics.bottom - fontMetrics.top);
        Log.i(TAG, rect.height()+"  "+"onMeasure: hhhhhhhhhhh:"+h+"  fontMetrics.top:"+fontMetrics.top+"  fontMetrics.bottom:"+fontMetrics.bottom);
        int width = getMySize(rect.width() + dp2px(15), widthMeasureSpec);
        int height = getMySize(h, heightMeasureSpec);
        Log.i(TAG, "onMeasure: Width:" + width + "__height:" + height);
        setMeasuredDimension(width, height);
        //计算文本高度和宽度
        tHeight = rect.height();
        tWidth = rect.width();
    }

    @Override
    protected void onLayout(boolean changed,
                            int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private static final String TAG = "ScrollingTextView";
    //开始绘制文本的坐标
    int drawStart = REFRESH_ROTE;
    @Override
    protected void onDraw(Canvas canvas) {
        if (scrollFlag == RIGHT) {
            if (-drawStart >= tWidth)
                drawStart = tWidth < this.getWidth() ? getWidth() - tWidth + textInterval : textInterval;
//        超出部分
            outSide = drawStart;
//          有超出部分则在一边绘制一个同样的text
            if (outSide + tWidth <= getWidth() - textInterval && outSide < 0) {
                //文字宽度大于控件宽度
                if (tWidth > getWidth())
                    canvas.drawText(text, tWidth + outSide + textInterval, getMeasuredHeight()/2+h/2-b, mPaint);
                //文字宽度小于控件宽度
                else
                    canvas.drawText(text, textInterval + outSide + getWidth(), getMeasuredHeight()/2+h/2-b, mPaint);
            }
            canvas.drawText(text, drawStart, getMeasuredHeight()/2+h/2-b, mPaint);
            drawStart -= scrollSpeed;

        }
        postInvalidateDelayed(10);
    }

    //测量
    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                Log.i(TAG, "getMySize: 未指定");
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //WRAP_CONTENT
                //我们将大小取最小值,也可以取其他值
                mySize = Math.min(size, defaultSize);
                Log.i(TAG, "getMySize: 最大值");
                break;
            }
            case MeasureSpec.EXACTLY: {
                //如果是固定的大小，那就不要去改变它,MatchParent或者明确的数值
                mySize = size;
                Log.i(TAG, "getMySize: 固定大小");
                break;
            }
        }
        return mySize;
    }

    //dp to dx方法
    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
    //sp转px
    private int sp2px(float spValue) {
        float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
