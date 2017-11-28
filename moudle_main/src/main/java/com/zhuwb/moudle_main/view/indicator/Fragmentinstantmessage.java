package com.zhuwb.moudle_main.view.indicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youth.banner.Banner;
import com.zhuwb.moudle_main.R;
import com.zhuwb.moudle_main.R2;
import com.zhuwb.moudle_main.adpter.MyRVAdapter;
import com.zhuwb.moudle_main.bean.ListMessageitem;
import com.zhuwb.moudle_main.contract.MessageContract;
import com.zhuwb.moudle_main.presenter.MainMessagePresenter;
import com.zhuwb.moudle_main.view.BannerParticularsActivity;
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

public class Fragmentinstantmessage extends Fragment implements MessageContract.IFragmentView {
    private static final String TAG = "Fragment_instantmessage";
    private Banner mainbanner;
    private FragmentManager manager;
    private MainMessagePresenter messagePresenter;
    private MyRVAdapter adapter;
    private List<ListMessageitem.MessageBean> messageBeanList = new ArrayList<>();

    Unbinder unbinder;
    @BindView(R2.id.main_ins_refreshlistview)
    RecyclerView mainRefreshlistview;
    /**
     * 即时信息mold值为1
     */
    private int mold = 1;

    /**
     * 页码
     */
    private int curPage = 1;
    /**
     * 即时消息街type值为1
     */
    private int type = 1;


    public Fragmentinstantmessage(FragmentManager manager) {
        this.manager = manager;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_instantmessage, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();

        return view;
    }


    private void init() {

//        //把轮播图添加到ListView中
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.main_banner, null);
        mainbanner = (Banner) view1.findViewById(R.id.main_lv_banner);

        //调取P层中加载列表数据和轮播图数据
        messagePresenter = new MainMessagePresenter(mold, type, this,getActivity());
        messagePresenter.loadListMessage(curPage);
        messagePresenter.loadBannerMessage(mainbanner);

        //设置recycleView
        mainRefreshlistview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyRVAdapter(R.layout.main_message_item, messageBeanList, manager);
        adapter.addHeaderView(view1);
        mainRefreshlistview.setAdapter(adapter);

        /**
         * 上拉加载
         */
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mainRefreshlistview.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //当返回数据小于10，说明已无数据，停止下拉刷新
                        if (messageBeanList.size() < 10) {
                            adapter.loadMoreEnd();
                        } else {
                            curPage++;
                            adapter.loadMoreComplete();
                        }
                        messagePresenter.loadListMessage(curPage);
                    }
                }, 2000);
            }
        }, mainRefreshlistview);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EventBus.getDefault().postSticky(messageBeanList.get(position));
                startActivity(new Intent(getActivity(), ListParticularsActivity.class));
            }
        });

//        adapter.setUpFetchListener(new BaseQuickAdapter.UpFetchListener() {
//            @Override
//            public void onUpFetch() {
//                adapter.setUpFetchEnable(true);
//                mainRefreshlistview.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        curPage = 1;
//                        messageBeanList.clear();
//                        messagePresenter.loadListMessage(curPage);
//                    }
//                }, 300);
//
//            }
//        });

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: " + "instant is onPause");
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

        Log.i(TAG, "onDestroyView: " + "messageBeanList的大小" + messageBeanList.size());
    }


    @Override
    public void showList(final List<ListMessageitem.MessageBean> datas) {
        messageBeanList.addAll(datas);
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
