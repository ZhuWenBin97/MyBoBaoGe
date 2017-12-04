package com.zhuwb.moudle_main.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;
import com.zhuwb.moudle_main.HttpUtils.HttpUtil;
import com.zhuwb.moudle_main.R;
import com.zhuwb.moudle_main.R2;
import com.zhuwb.moudle_main.Utils.FullyGridLayoutManager;
import com.zhuwb.moudle_main.adpter.GridImageAdapter;
import com.zhuwb.moudle_main.bean.ListMessageitem;
import com.zhuwb.moudle_main.contract.MessageContract;
import com.zhuwb.moudle_main.model.InstantModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/12 14:42
 */

public class Fragmentrelease extends Fragment implements MessageContract.IFragmentModel.OnLoadListener {
    private static final String TAG = "Fragmentrelease";
    @BindView(R2.id.main_release_telephone)
    TextView mainReleaseTelephone;
    private InstantModel instantModel=new InstantModel(this);
    private GridImageAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();

    Unbinder unbinder;
    @BindView(R2.id.main_release_recycler)
    RecyclerView mainReleaseRecycler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_release, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        Drawable drawable = getResources().getDrawable(R.mipmap.main_call_icon_blue);
        drawable.setBounds(20, 0, 100, 100);
        mainReleaseTelephone.setCompoundDrawables(drawable, null, null, null);


        //设置选择图片
        FullyGridLayoutManager manager = new FullyGridLayoutManager(getActivity(), 4, GridLayoutManager.VERTICAL, false);
        mainReleaseRecycler.setLayoutManager(manager);
        adapter = new GridImageAdapter(getActivity(), onAddPicClickListener);
        adapter.setList(selectList);
        //最多为9张
        adapter.setSelectMax(9);
        mainReleaseRecycler.setAdapter(adapter);
        //预览图片
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                PictureConfig.getInstance().externalPicturePreview(getActivity(), position, selectList);
            }
        });

        instantModel.requestNetwork(HttpUtil.getType(), getActivity());
        Log.i(TAG, "initView: " + HttpUtil.getType());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onFail(Exception e) {

    }

    @Override
    public void onSucced(String json) {


    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            PictureConfig.getInstance().openPhoto(getActivity(), new PictureConfig.OnSelectResultCallback() {
                @Override
                public void onSelectSuccess(List<LocalMedia> list) {
                    selectList = list;
                    LocalMedia media = list.get(0);
                    if (media.isCut() && !media.isCompressed()) {
                        // 裁剪过
                        String path = media.getCutPath();
                    } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                        // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                        String path = media.getCompressPath();
                    } else {
                        // 原图地址
                        String path = media.getPath();
                    }
                    if (selectList != null) {
                        adapter.setList(selectList);
                        adapter.notifyDataSetChanged();
                    }
                    Log.i(TAG, "onSelectSuccess: " + selectList.size());
                }

                @Override
                public void onSelectSuccess(LocalMedia localMedia) {
                    selectList.add(localMedia);
                    Log.i(TAG, "onSelectSuccess: " + selectList.size());
                    if (selectList != null) {
                        adapter.setList(selectList);
                        adapter.notifyDataSetChanged();

                    }
                }
            });
        }
    };


}
