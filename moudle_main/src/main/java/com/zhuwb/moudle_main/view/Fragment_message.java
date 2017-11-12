package com.zhuwb.moudle_main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zcy.hnkjxy.customview.BottomTabBar;
import com.zhuwb.moudle_main.R;
import com.zhuwb.moudle_main.presenter.IMainPresenter;
import com.zhuwb.moudle_main.presenter.MainPresenter;


public class Fragment_message extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        return view;
    }


}
