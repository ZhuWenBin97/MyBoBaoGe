package com.zhuwb.moudle_main.view.indicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guiying.module.common.base.LazyFragment;
import com.youth.banner.Banner;
import com.zcy.hnkjxy.customview.RefreshListView;
import com.zhuwb.moudle_main.R;
import com.zhuwb.moudle_main.R2;
import com.zhuwb.moudle_main.bean.BannerMessage;
import com.zhuwb.moudle_main.presenter.BannerPresenter;
import com.zhuwb.moudle_main.presenter.IBannerPresenter;
import com.zhuwb.moudle_main.presenter.IListMessage;
import com.zhuwb.moudle_main.presenter.ListMessage;
import com.zhuwb.moudle_main.view.BannerParticularsActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/13 10:46
 */

public class Fragmentmoldbaby extends LazyFragment implements RefreshListView.OnLoadMoreListener, RefreshListView.OnRefreshListener {
    private static final String TAG = "Fragmentmoldbaby";
    private Banner mainbanner;
    @BindView(R2.id.main_mold_baby_refreshlistview)
    RefreshListView mainMoldBabyRefreshlistview;
    Unbinder unbinder;
    private FragmentManager manager ;

    public Fragmentmoldbaby(FragmentManager manager) {
        this.manager = manager;
    }

    /**
     * 爆款街mold值为2
     */
    private int mold = 2;

    /**
     * 标志位，标致初始化已完成
     */
    private boolean isPrepared;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_moldbaby, container, false);
        unbinder = ButterKnife.bind(this, view);
        isPrepared = true;
        lazyload();
        return view;
    }

    private void init() {
        //注册EventBus
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.main_banner, null);
        mainbanner = (Banner) view1.findViewById(R.id.main_lv_banner);
        mainMoldBabyRefreshlistview.addHeaderView(view1);

        //接口new 轮播图实例
        IBannerPresenter iBannerPresenter = new BannerPresenter();
        iBannerPresenter.getImages(getActivity(), mainbanner, mold);
        //newListView的实例
        IListMessage iListMessage = new ListMessage();
        iListMessage.getMessage(getActivity(), mainMoldBabyRefreshlistview, mold,manager);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void lazyload() {
        if (!isPrepared || isVisible) {
            return;
        }
        init();

    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mainMoldBabyRefreshlistview.closeRefresh();
            }
        }).start();

    }

    @Override
    public void onLoadMore() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mainMoldBabyRefreshlistview.closeLoadMore();
            }
        }).start();
    }
}
