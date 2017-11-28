package com.zhuwb.moudle_main.base;

import android.view.View;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * ======================================
 * 创建人   ： ZhuWB
 * 创建时间 :2017/11/24 09:07
 * ======================================
 */

public abstract class BasePresent<V> {
    /**
     * View接口类型引用
     */
    protected Reference<V> mViewRef;

    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    protected V getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public abstract void start();



}
