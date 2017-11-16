package com.zhuwb.moudle_main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youth.banner.Banner;
import com.zcy.hnkjxy.customview.AutoShufflingView;
import com.zhuwb.moudle_main.R;
import com.zhuwb.moudle_main.R2;
import com.zhuwb.moudle_main.presenter.AutoShufflingPresenter;
import com.zhuwb.moudle_main.presenter.IAutoShufflingPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/13 10:46
 */

public class Fragment_instantmessage extends Fragment {
    @BindView(R2.id.main_fragment_banner)
    Banner mainbanner;
    Unbinder unbinder;
    private String[] imgs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_instantmessage, container, false);
        unbinder = ButterKnife.bind(this, view);
        //接口new 轮播图实例
        IAutoShufflingPresenter iAutoShufflingPresenter = new AutoShufflingPresenter();
        iAutoShufflingPresenter.getImages(getActivity(), mainbanner);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R2.id.main_fragment_banner)
    public void onViewClicked() {
    }
}
