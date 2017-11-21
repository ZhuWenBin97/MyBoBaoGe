package com.zhuwb.moudle_main.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhuwb.moudle_main.R;

public class Fragmentgroupbying extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_groupbying, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.test);
        Glide.with(getContext()).load("http://bobaoge.oss-cn-shanghai.aliyuncs.com/bobaoge20171120/20171120154301855_115.jpg").into(imageView);
        return view;
    }
}
