package com.zhuwb.moudle_main.view.indicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.zcy.hnkjxy.customview.RefreshListView;
import com.zhuwb.moudle_main.R;
import com.zhuwb.moudle_main.R2;
import com.zhuwb.moudle_main.adpter.MyLvAdapter;
import com.zhuwb.moudle_main.bean.ListMessageitem;
import com.zhuwb.moudle_main.contract.MessageContract;
import com.zhuwb.moudle_main.presenter.MainMessagePresenter;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/13 10:46
 */

public class Fragmentinstantmessage extends Fragment implements RefreshListView.OnLoadMoreListener, RefreshListView.OnRefreshListener, MessageContract.IFragmentView {
    private static final String TAG = "Fragment_instantmessage";
    private Banner mainbanner;
    private FragmentManager manager;
    private MainMessagePresenter messagePresenter;
    private MyLvAdapter adapter;
    private List<ListMessageitem.MessageBean> messageBeanList = new ArrayList<>();

    public Fragmentinstantmessage(FragmentManager manager) {
        this.manager = manager;
    }

    Unbinder unbinder;
    @BindView(R2.id.main_ins_refreshlistview)
    RefreshListView mainRefreshlistview;
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

        //把轮播图添加到ListView中
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.main_banner, null);
        mainbanner = (Banner) view1.findViewById(R.id.main_lv_banner);
        mainRefreshlistview.addHeaderView(view1);
        mainRefreshlistview.setOnLoadMoreListener(this);
        mainRefreshlistview.setOnRefreshListener(this);

        messagePresenter = new MainMessagePresenter(mold, type, this);
        messagePresenter.loadListMessage(curPage);
        messagePresenter.loadBannerMessage(mainbanner);

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
        Log.i(TAG, "onDestroyView: " + "instant is onDestroy");
//        viewPager = null;
//        Fragmentinstantmessage = null;

    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        curPage++;
        messagePresenter.loadListMessage(curPage);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mainRefreshlistview.closeLoadMore();
            }
        }).start();
    }

    @Override
    public void showList(final List<ListMessageitem.MessageBean> datas) {

        messageBeanList.addAll(datas);
        if (curPage == 1) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter = new MyLvAdapter(getContext(), datas, manager);
                    mainRefreshlistview.setAdapter(adapter);
                    Log.i(TAG, "run: " + "数据跟新+messageBeanList.size=" + messageBeanList.size());

                }
            });
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter = new MyLvAdapter(getContext(), datas, manager);
                    adapter.notifyDataSetChanged();
                    Log.i(TAG, "run: " + "数据跟新+messageBeanList.size=" + messageBeanList.size());
                }
            });

        }

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
