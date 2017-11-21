package com.zhuwb.moudle_main.view.indicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guiying.module.common.base.LazyFragment;
import com.youth.banner.Banner;
import com.zcy.hnkjxy.customview.RefreshListView;
import com.zhuwb.moudle_main.R;
import com.zhuwb.moudle_main.R2;
import com.zhuwb.moudle_main.presenter.BannerPresenter;
import com.zhuwb.moudle_main.presenter.IBannerPresenter;
import com.zhuwb.moudle_main.presenter.IListMessage;
import com.zhuwb.moudle_main.presenter.ListMessage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/13 10:46
 */

public class Fragmentfood extends LazyFragment implements RefreshListView.OnLoadMoreListener, RefreshListView.OnRefreshListener {
    private Banner mainbanner;
    @BindView(R2.id.main_mold_food_refreshlistview)
    RefreshListView mainMoldFoodRefreshlistview;
    Unbinder unbinder;
    private FragmentManager manager = getFragmentManager();

    public Fragmentfood(FragmentManager manager) {
        this.manager = manager;
    }

    /**
     * 美食街mold值为7
     */
    private int mold = 7;
    //标志位，标致初始化已完成
    private boolean isPrepared;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        unbinder = ButterKnife.bind(this, view);
        isPrepared = true;
        lazyload();
        return view;
    }

    private void init() {
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.main_banner, null);
        mainbanner = (Banner) view1.findViewById(R.id.main_lv_banner);
        mainMoldFoodRefreshlistview.addHeaderView(view1);

        //接口new 轮播图实例
        IBannerPresenter iBannerPresenter = new BannerPresenter();
        iBannerPresenter.getImages(getActivity(), mainbanner, mold);
        //newListView的实例
        IListMessage iListMessage = new ListMessage();
        iListMessage.getMessage(getActivity(), mainMoldFoodRefreshlistview, mold, manager);
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

    }

    @Override
    public void onLoadMore() {

    }
}
