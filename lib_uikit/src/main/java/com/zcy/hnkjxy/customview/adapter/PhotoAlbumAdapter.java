package com.zcy.hnkjxy.customview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zcy.hnkjxy.customview.R;
import com.zcy.hnkjxy.customview.bean.ImageFolderBean;

import java.util.List;

/**
 * Created by zhang chen yang on 2017/10/18 0018.
 */

public class PhotoAlbumAdapter extends BaseAdapter{
    private List<ImageFolderBean> mList;
    private Context mContext;

    public PhotoAlbumAdapter(List<ImageFolderBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.photo_album_item,null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imgPhotoAlbumFirst);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tvPhotoAlbumName);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        //填充图片
        Glide.with(mContext).load(mList.get(position).getImgFirstName()).into(viewHolder.imageView);
        //填充相册名字
        viewHolder.textView.setText(""+mList.get(position).getImgFolderName()+"("+mList.get(position).getImgFolderCount()+")");
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
