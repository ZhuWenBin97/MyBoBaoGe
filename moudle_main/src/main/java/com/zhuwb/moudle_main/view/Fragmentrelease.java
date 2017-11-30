package com.zhuwb.moudle_main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;
import com.zcy.hnkjxy.customview.ImageChooseActivity;
import com.zhuwb.moudle_main.R;
import com.zhuwb.moudle_main.R2;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/12 14:42
 */

public class Fragmentrelease extends Fragment {


    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_release, container, false);


        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R2.id.main_release_selectImg)
    public void onViewClicked() {
        PictureConfig.getInstance().openPhoto(getActivity(), new PictureConfig.OnSelectResultCallback() {
            @Override
            public void onSelectSuccess(List<LocalMedia> list) {

            }

            @Override
            public void onSelectSuccess(LocalMedia localMedia) {

            }
        });
        // startActivity(new Intent(getActivity(), ImageChooseActivity.class));
    }
}
