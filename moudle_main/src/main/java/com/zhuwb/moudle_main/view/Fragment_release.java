package com.zhuwb.moudle_main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuwb.moudle_main.R;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/12 14:42
 */

public class Fragment_release extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_release, container, false);
        return view;
    }
}
