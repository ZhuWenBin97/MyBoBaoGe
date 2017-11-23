package com.zhuwb.moudle_main.view.indicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.guiying.module.common.base.LazyFragment;
import com.youth.banner.Banner;
import com.zcy.hnkjxy.customview.RefreshListView;
import com.zhuwb.moudle_main.HttpUtils.HttpUtil;
import com.zhuwb.moudle_main.R;
import com.zhuwb.moudle_main.R2;
import com.zhuwb.moudle_main.adpter.MyLvAdapter;
import com.zhuwb.moudle_main.bean.ListMessageitem;
import com.zhuwb.moudle_main.contract.MessageContract;
import com.zhuwb.moudle_main.presenter.MainMessagePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/14 08:46
 */

public class Fragmentlostandfound extends LazyFragment implements RefreshListView.OnLoadMoreListener, RefreshListView.OnRefreshListener, MessageContract.IFragmentView {
    private Banner mainbanner;
    @BindView(R2.id.main_mold_lof_refreshlistview)
    RefreshListView mainMoldLofRefreshlistview;
    Unbinder unbinder;
    private FragmentManager manager;
    private MainMessagePresenter messagePresenter;
    private MyLvAdapter adapter;

    public Fragmentlostandfound(FragmentManager manager) {
        this.manager = manager;
    }

    /**
     * 失物招领mold值为6
     */
    private int mold = 6;
    /**
     * 页码
     */
    private int curPage = 1;
    /**
     * 失物招领街type值为5
     */
    private int type = 5;
    /**
     * 标志位，标致初始化已完成
     */
    private boolean isPrepared;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_losandfound, container, false);
        unbinder = ButterKnife.bind(this, view);
        isPrepared = true;
        lazyload();
        return view;
    }

    private void init() {
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.main_banner, null);
        mainbanner = (Banner) view1.findViewById(R.id.main_lv_banner);
        mainMoldLofRefreshlistview.addHeaderView(view1);

        //newListView的实例
        messagePresenter = new MainMessagePresenter(mold,  type, this);
        messagePresenter.loadListMessage(curPage);
        messagePresenter.loadBannerMessage(mainbanner);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Glide.get(getContext()).clearMemory();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

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
    public void showList(List<ListMessageitem.MessageBean> messageBeans) {
        adapter = new MyLvAdapter(getContext(), messageBeans, manager);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainMoldLofRefreshlistview.setAdapter(adapter);
            }
        });
    }

    @Override
    public void showBanner(final List<String> listImgs) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainbanner.setImages(listImgs);
                mainbanner.start();
            }
        });
    }
}
