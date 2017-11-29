package com.zhuwb.moudle_main.view.indicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guiying.module.common.base.LazyFragment;
import com.youth.banner.Banner;
import com.zhuwb.moudle_main.R;
import com.zhuwb.moudle_main.R2;
import com.zhuwb.moudle_main.adpter.MyRVAdapter;
import com.zhuwb.moudle_main.bean.ListMessageitem;
import com.zhuwb.moudle_main.contract.MessageContract;
import com.zhuwb.moudle_main.presenter.MainMessagePresenter;
import com.zhuwb.moudle_main.view.ListParticularsActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/13 10:46
 */

public class Fragmentrecruitment extends LazyFragment implements MessageContract.IFragmentView {
    private Banner mainbanner;
    @BindView(R2.id.main_mold_recruitment_refreshlistview)
    RecyclerView mainMoldRecruitmentRefreshlistview;
    Unbinder unbinder;
    private FragmentManager manager;
    private MainMessagePresenter messagePresenter;
    private MyRVAdapter adapter;
    private List<ListMessageitem.MessageBean> messageBeanList = new ArrayList<>();


    public Fragmentrecruitment(FragmentManager manager) {
        this.manager = manager;
    }

    /**
     * 招聘街mold值为4
     */
    private int mold = 4;
    /**
     * 页码
     */
    private int curPage = 1;
    /**
     * 招聘街type值为3
     */
    private int type = 3;
    /**
     * 标志位，标致初始化已完成
     */
    private boolean isPrepared;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_recruitment, container, false);
        unbinder = ButterKnife.bind(this, view);
        isPrepared = true;
        lazyload();

        return view;

    }

    private void init() {
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.main_banner, null);
        mainbanner = (Banner) view1.findViewById(R.id.main_lv_banner);

        //newListView的实例

        messagePresenter = new MainMessagePresenter(mold, type, this, getActivity());
        messagePresenter.loadListMessage(curPage);
        messagePresenter.loadBannerMessage(mainbanner);
        //设置recycleView
        mainMoldRecruitmentRefreshlistview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyRVAdapter(R.layout.main_message_item, messageBeanList, manager);
        adapter.addHeaderView(view1);
        mainMoldRecruitmentRefreshlistview.setAdapter(adapter);

        /**
         * 上拉加载
         */
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mainMoldRecruitmentRefreshlistview.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //当返回数据小于10，说明已无数据，停止下拉刷新
                        if (messageBeanList.size() < 10) {
                            adapter.loadMoreEnd();

                        } else {
                            curPage++;
                            messagePresenter.loadListMessage(curPage);
                            adapter.loadMoreComplete();
                        }
                    }
                }, 2000);
            }
        }, mainMoldRecruitmentRefreshlistview);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EventBus.getDefault().postSticky(messageBeanList.get(position));
                startActivity(new Intent(getActivity(), ListParticularsActivity.class));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Glide.get(getContext()).clearMemory();
        curPage = 1;
        messageBeanList.clear();
        messagePresenter.destory();
        messagePresenter = null;
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
        messageBeanList.addAll(messageBeans);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (curPage != 1) {
                    adapter.notifyDataSetChanged();
                }
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
