package com.zhuwb.moudle_main.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.guiying.module.common.base.LazyFragment;
import com.zhuwb.lib_magicindicator.MagicIndicator;
import com.zhuwb.moudle_main.R;
import com.zhuwb.moudle_main.R2;
import com.zhuwb.moudle_main.bean.BannerMessage;
import com.zhuwb.moudle_main.presenter.IMessagePresenter;
import com.zhuwb.moudle_main.presenter.MessagePresenter;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragmentmessage extends LazyFragment {
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
        //注册EventBus
        EventBus.getDefault().register(this);
        manager = getFragmentManager();
        IMessagePresenter iMessagePresenter = new MessagePresenter();
        iMessagePresenter.initView(getContext(), manager, mainFragmentViewPager, mainFragmentMagicIndicator);
        return view;
    }

    //接收到轮播图点击事件的传参
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BannerMessage bannerMessage) {
        BannerMessage.BannerBean bannerBean = bannerMessage.getMessage().get(bannerMessage.getCode());
        EventBus.getDefault().postSticky(bannerBean);
        startActivity(new Intent(getActivity().getApplicationContext(), BannerParticularsActivity.class));
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
