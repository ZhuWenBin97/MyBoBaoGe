package com.zhuwb.moudle_main.view.indicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.zhuwb.moudle_main.model.HttpUtils;
import com.zhuwb.moudle_main.presenter.BannerPresenter;
import com.zhuwb.moudle_main.presenter.IBannerPresenter;
import com.zhuwb.moudle_main.presenter.IListMessage;
import com.zhuwb.moudle_main.presenter.ListMessage;
import com.zhuwb.moudle_main.view.BannerParticularsActivity;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.guiying.module.common.utils.Util.getUrl;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/13 10:46
 */

public class Fragmentinstantmessage extends Fragment implements RefreshListView.OnLoadMoreListener, RefreshListView.OnRefreshListener {
    private static final String TAG = "Fragment_instantmessage";
    private Banner mainbanner;
    private FragmentManager manager = getFragmentManager();

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_instantmessage, container, false);
        unbinder = ButterKnife.bind(this, view);
        mainRefreshlistview.setOnLoadMoreListener(this);
        mainRefreshlistview.setOnRefreshListener(this);
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
        //接口new 轮播图实例
        IBannerPresenter iBannerPresenter = new BannerPresenter();
        iBannerPresenter.getImages(getActivity(), mainbanner, mold);
        //newListView的实例
        IListMessage iListMessage = new ListMessage();
        iListMessage.getMessage(getActivity(), mainRefreshlistview, mold,manager);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
                mainRefreshlistview.closeRefresh();
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
                mainRefreshlistview.closeLoadMore();
            }
        }).start();
    }

}
