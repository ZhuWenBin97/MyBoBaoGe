package com.zcy.hnkjxy.customview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhang chen yang on 2017/11/2 0002.
 */

public class TimeButton extends Button implements View.OnClickListener{
    private long lenght = 60 * 1000;// 倒计时长度,这里给了默认60秒
    private String textafter = "s重新获取";
    private String textbefore = "获取验证码";
    private final String TIME = "time";
    private final String CTIME = "ctime";
    public static boolean isRun = false;
    private OnClickListener onClickListener;
    private Timer t;
    private TimerTask tt;
    private long time;
    Map<String, Long> map = new HashMap<String, Long>();
    public TimeButton(Context context) {
        super(context);
        setOnClickListener(this);

    }

    public TimeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    Handler han = new Handler() {
        public void handleMessage(android.os.Message msg) {
            TimeButton.this.setText(time / 1000 + textafter);
            time -= 1000;
            if (time < 0) {
                TimeButton.this.setEnabled(true);
                TimeButton.this.setText(textbefore);
                TimeButton.this.setBackgroundResource(R.drawable.shape_verification);
                clearTimer();
            }
        }
    };

    private void initTimer() {
        time = lenght;
        t = new Timer();
        tt = new TimerTask() {

            @Override
            public void run() {
                han.sendEmptyMessage(0x01);
            }
        };

    }

    private void clearTimer() {
        if (tt != null) {
            tt.cancel();
            tt = null;
        }
        if (t != null)
            t.cancel();
        t = null;
    }

    /** * 设置计时时候显示的文本 */
    public TimeButton setTextAfter(String text1) {
        this.textafter = text1;
        return this;
    }

    /** * 设置点击之前的文本 */
    public TimeButton setTextBefore(String text0) {
        this.textbefore = text0;
        this.setText(textbefore);
        return this;
    }

    /**
     * 设置到计时长度
     *
     * @param lenght
     *            时间 默认毫秒
     * @return
     */
    public TimeButton setLenght(long lenght) {
        this.lenght = lenght;
        return this;
    }
    /*

*
*/
    public void goRun()
    {

        initTimer();
        this.setText(time / 1000 + textafter);
        this.setEnabled(false);
        this.setBackgroundResource(R.drawable.shape_verification_off);
        t.schedule(tt, 0, 1000);
    }

    @Override
    public void onClick(View v) {
        if (onClickListener != null){
            onClickListener.onClick();
        }
    }
    public interface OnClickListener{
        void onClick();
    }
}
