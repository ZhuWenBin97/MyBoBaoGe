package com.zhuwb.moudle_main.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.guiying.module.common.base.LazyFragment;

/**
 * ======================================
 * 创建人   ： ZhuWB
 * 创建时间 :2017/11/24 09:04
 * ======================================
 */

public abstract class MVPBaseFragment<V,P extends BasePresent> extends LazyFragment{

    private static final String TAG = "MVPFragment";
    protected P mPresent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresent=creatPresenter();
        mPresent.attachView(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresent.detachView();
    }

    protected abstract P creatPresenter();
}
