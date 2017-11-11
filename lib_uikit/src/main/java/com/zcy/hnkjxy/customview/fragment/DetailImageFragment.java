package com.zcy.hnkjxy.customview.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.zcy.hnkjxy.customview.MyView.ChoosePhotoView;
import com.zcy.hnkjxy.customview.MyView.ChooseZoomPhotoView;
import com.zcy.hnkjxy.customview.R;
import com.zcy.hnkjxy.customview.ZoomPhotoView;
import com.zcy.hnkjxy.customview.adapter.PhotoChooseAdapter;
import com.zcy.hnkjxy.customview.bean.ImageBean;
import com.zcy.hnkjxy.customview.bean.ImageFolderBean;
import com.zcy.hnkjxy.customview.util.GlideLoader;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DetailImageFragment extends Fragment implements ChoosePhotoView.OnSelectedListener, ChooseZoomPhotoView.OnSelectedListener {
    //选择完毕后的回调
    private OnSelectedFinishListener mListener;
    //相册的集合
    private ImageFolderBean mListFolderBean;
    private SwipeRefreshLayout srfLayout;
    private GridView gvPhoto;
    //    private TextView btnPreview;
    private Button btnPost, btnPreview;
    //相册下数据聚合
    private List<ImageBean> mListImagePath;
    //存储被选中的图片
    private ArrayList<ImageBean> mSaveImages = new ArrayList<>();
    //适配器
    private PhotoChooseAdapter mAdapter;

    public DetailImageFragment() {

    }

    public DetailImageFragment(OnSelectedFinishListener mListener, ImageFolderBean mListFolderBean) {
        this.mListener = mListener;
        this.mListFolderBean = mListFolderBean;
    }

    public void setListFolderBean(ImageFolderBean mListFolderBean) {
        this.mListFolderBean = mListFolderBean;
        updateView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_image, container, false);
    }

    //界面打开
    @Override
    public void onResume() {
        super.onResume();
        setViews();
    }

    private void updateView() {
        mAdapter.notifyDataSetChanged();
        //添加数据
        List<ImageBean> imageBeen = new ArrayList<>();
        for (int i = 0; i < mListFolderBean.getImageBeens().length; i++) {
            imageBeen.add(mListFolderBean.getImageBeens()[i]);
        }
        mListImagePath.clear();
        mListImagePath.addAll(imageBeen);
        mAdapter.notifyDataSetChanged();
    }

    private void setViews() {
        List<ImageBean> imgPath = new ArrayList<>();
        srfLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.srfDetailRefresh);
        gvPhoto = (GridView) getActivity().findViewById(R.id.gvDetailPhoto);
        btnPreview = (Button) getActivity().findViewById(R.id.btnDetailPreview);
        btnPost = (Button) getActivity().findViewById(R.id.btnDetailPost);
        //添加数据
        ImageFolderBean imageFolderBean = mListFolderBean;
        for (int i = 0; i < imageFolderBean.getImageBeens().length; i++) {
            imgPath.add(imageFolderBean.getImageBeens()[i]);
        }
        mListImagePath = imgPath;
        //创建设配器
        mAdapter = new PhotoChooseAdapter(getActivity(), mListImagePath, this);
        gvPhoto.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        //设置监听器
        setViewListener();
    }

    private void setViewListener() {
        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseZoomPhotoView zoomPhotoView = new ChooseZoomPhotoView().addImages(mSaveImages);
                zoomPhotoView.setOnSelectedListener(DetailImageFragment.this);
                zoomPhotoView.show(getFragmentManager(), "tag");
            }
        });
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSelectedFinishListener) {
            mListener = (OnSelectedFinishListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSelectedFinishListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListImagePath.clear();
        mSaveImages.clear();
        mAdapter.clear();
        mSaveImages = null;
        mListImagePath = null;
        mListener = null;
        mAdapter = null;
        mListFolderBean = null;
        Log.i("TAGF", "onDetach: 缓存清理");

    }

    @Override
    public void onAdd(ImageBean imagePath) {
        if (!mSaveImages.contains(imagePath))
            mSaveImages.add(imagePath);
        updateView(true);
    }

    @Override
    public void onRemove(ImageBean imagePath) {
        if (mSaveImages.contains(imagePath))
            mSaveImages.remove(imagePath);
        updateView(false);
    }

    //刷新界面
    private void updateView(boolean isAdd) {
        if (isAdd) {
            btnPost.setEnabled(true);
            btnPost.setText("确定(" + mSaveImages.size() + ")");
            btnPreview.setEnabled(true);
        } else {
            if (mSaveImages.size() == 0) {
                btnPost.setEnabled(false);
                btnPreview.setEnabled(false);
                btnPost.setText("确定");
            } else {
                btnPost.setText("确定(" + mSaveImages.size() + ")");
            }
        }
        mAdapter.updateView();
    }

    @Override
    public void onClick(View view, ImageBean imgPath) {
        Log.i("TAF", "onClick: 单击：");
        int index = mListImagePath.indexOf(imgPath);
        ChooseZoomPhotoView zoomPhotoView = new ChooseZoomPhotoView()
                .addImages((ArrayList<ImageBean>) mListImagePath)
                .setCurrentItem(index);
        zoomPhotoView.setOnSelectedListener(DetailImageFragment.this);
        zoomPhotoView.show(getFragmentManager(), "tag");
    }

    @Override
    public void addImages(ImageBean imageBean) {
        if (!mSaveImages.contains(imageBean))
            mSaveImages.add(imageBean);
        updateView(true);
    }

    @Override
    public void removeImages(ImageBean imageBean) {
        if (mSaveImages.contains(imageBean))
            mSaveImages.remove(imageBean);
        updateView(false);
    }

    @Override
    public void finish() {
        List<String> list = new ArrayList<String>();
        for (ImageBean imageBean : mSaveImages)
            list.add(imageBean.getImgUrl());
        mListener.onSelectImages(list);
    }

    @Override
    public int getCurrentCount(ImageBean imgPath) {
        return mSaveImages.indexOf(imgPath) + 1;
    }

    @Override
    public int getCuntSum() {
        return mSaveImages.size();
    }

    public interface OnSelectedFinishListener {
        // TODO: Update argument type and name
        void onSelectImages(List<String> selectedImages);
    }

}
