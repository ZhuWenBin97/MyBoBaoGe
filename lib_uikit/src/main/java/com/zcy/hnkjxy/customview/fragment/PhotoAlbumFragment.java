package com.zcy.hnkjxy.customview.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.zcy.hnkjxy.customview.R;
import com.zcy.hnkjxy.customview.adapter.PhotoAlbumAdapter;
import com.zcy.hnkjxy.customview.bean.ImageFolderBean;

import java.util.List;


public class PhotoAlbumFragment extends Fragment implements AdapterView.OnItemClickListener {
    private OnSelectPhotoListener mListener;
    private List<ImageFolderBean> imageFolderBeen;
    private PhotoAlbumAdapter adapter;
    private ListView lvPhotoAlbum;

    public void setImageFolderBeen(List<ImageFolderBean> imageFolderBeen) {
        this.imageFolderBeen = imageFolderBeen;
    }

    public PhotoAlbumFragment(OnSelectPhotoListener mListener) {
        this.mListener = mListener;
    }

    public PhotoAlbumFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo_album, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lvPhotoAlbum = (ListView) getActivity().findViewById(R.id.lvPhotoAlbum);
        adapter = new PhotoAlbumAdapter(imageFolderBeen, getActivity());
        lvPhotoAlbum.setAdapter(adapter);
        lvPhotoAlbum.setOnItemClickListener(this);
    }

    private static final String TAG = "PhotoAlbumFragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach: ");
        if (context instanceof OnSelectPhotoListener) {
            mListener = (OnSelectPhotoListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSelectedFinishListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach: ");
        imageFolderBeen.clear();
        imageFolderBeen = null;
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mListener.onSelectPhoto(imageFolderBeen.get(position));
    }

    public interface OnSelectPhotoListener {
        // TODO: Update argument type and name
        void onSelectPhoto(ImageFolderBean imageFolder);
    }
}
