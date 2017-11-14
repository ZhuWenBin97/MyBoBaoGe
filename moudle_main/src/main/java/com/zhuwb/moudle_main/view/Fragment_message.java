package com.zhuwb.moudle_main.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.guiying.module.common.base.LazyFragment;
import com.zhuwb.lib_magicindicator.MagicIndicator;
import com.zhuwb.moudle_main.R;
import com.zhuwb.moudle_main.R2;
import com.zhuwb.moudle_main.presenter.IMessagePresenter;
import com.zhuwb.moudle_main.presenter.MessagePresenter;


import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_message extends LazyFragment {
    @BindView(R2.id.main_fragment_magicIndicator)
    MagicIndicator mainFragmentMagicIndicator;
    @BindView(R2.id.main_fragment_viewPager)
    ViewPager mainFragmentViewPager;

    private FragmentManager manager;
    // 标志位，标志已经初始化完成。
    private boolean isPrepared;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);
        isPrepared = true;
        lazyload();
        manager = getFragmentManager();
        IMessagePresenter iMessagePresenter = new MessagePresenter();
        iMessagePresenter.initView(getContext(), manager, mainFragmentViewPager, mainFragmentMagicIndicator);
        return view;
    }


    @Override
    protected void onInvisible() {

    }

    @Override
    protected void lazyload() {
        if (!isPrepared || !isVisible) {
            return;
        }
        //填充各控件的数据
    }
}
