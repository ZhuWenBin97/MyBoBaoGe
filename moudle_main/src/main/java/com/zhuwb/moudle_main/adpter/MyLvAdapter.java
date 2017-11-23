package com.zhuwb.moudle_main.adpter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.zhuwb.moudle_main.R;
import com.zhuwb.moudle_main.bean.ListMessageitem;
import com.zhuwb.moudle_main.presenter.BannerParticularsPresenter;
import com.zhuwb.moudle_main.presenter.IBannerParticularsPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/16 15:55
 */

public class MyLvAdapter extends BaseAdapter implements View.OnClickListener {
    private LayoutInflater mlLayoutInflater;
    private Context mContext;
    private List<ListMessageitem.MessageBean> mDatas;
    private FragmentManager manager;

    public MyLvAdapter(Context mContext, List<ListMessageitem.MessageBean> mDatas, FragmentManager manager) {
        this.mlLayoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.manager = manager;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mlLayoutInflater.inflate(R.layout.main_message_item, null);
            viewHolder.like = (RelativeLayout) convertView.findViewById(R.id.h_like_ll);
            viewHolder.Imglike = (ImageView) convertView.findViewById(R.id.main_lv_item_plike);
            viewHolder.tvlike = (TextView) convertView.findViewById(R.id.main_lv_item_Tlike);
            viewHolder.marquee = (TextView) convertView.findViewById(R.id.main_lv_item_marquee);
            viewHolder.marquee.setSelected(true);
            viewHolder.typearea = (TextView) convertView.findViewById(R.id.main_lv_item_typearea);
            viewHolder.typename = (TextView) convertView.findViewById(R.id.main_lv_item_type);
            viewHolder.date = (TextView) convertView.findViewById(R.id.main_lv_item_time);
            viewHolder.address = (TextView) convertView.findViewById(R.id.main_lv_item_address);
            viewHolder.content = (TextView) convertView.findViewById(R.id.main_lv_item_message);
            viewHolder.count = (TextView) convertView.findViewById(R.id.main_lv_item_see);
            viewHolder.nineGridImageView = (NineGridImageView) convertView.findViewById(R.id.main_lv_girdView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mDatas != null) {
            viewHolder.typearea.setText(whitchArea(Integer.parseInt(mDatas.get(position).getMessage_zone_id())));
            viewHolder.typename.setText(mDatas.get(position).getMsg_type_name());
            // viewHolder.date.setText(getData(mDatas.get(position).getMessage_verify_date()));
            viewHolder.date.setText(mDatas.get(position).getMessage_verify_date());
            viewHolder.address.setText(mDatas.get(position).getMessage_ads());
            viewHolder.content.setText(mDatas.get(position).getMessage_content());
            viewHolder.count.setText(mDatas.get(position).getBrow_user_cont() + "人浏览");
            viewHolder.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolder.Imglike.setImageResource(R.mipmap.like_icon_o);
                    viewHolder.tvlike.setText("1赞");
                }
            });
            if (mDatas.get(position).getMessage_images().length() != 0) {
                viewHolder.nineGridImageView.setVisibility(View.VISIBLE);
                //item下图片的GridView
                IBannerParticularsPresenter iBannerParticularsPresenter = new BannerParticularsPresenter();
                iBannerParticularsPresenter.setImageAdapter(viewHolder.nineGridImageView, mDatas.get(position).getMessage_images(), manager);
            } else {
                viewHolder.nineGridImageView.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {


    }

    class ViewHolder {
        TextView marquee;
        TextView typearea;
        TextView typename;
        TextView date;
        TextView content;
        TextView address;
        TextView count;
        NineGridImageView nineGridImageView;
        RelativeLayout like;
        ImageView Imglike;
        TextView tvlike;

    }

    private String whitchArea(int id) {
        switch (id) {
            case 1:
                return "商圈";
            case 2:
                return "洪合";
            case 3:
                return "濮院";
            case 4:
                return "海宁";
            default:
                return "横扇";
        }

    }

    private String getData(String data) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            data = simpleDateFormat.format(simpleDateFormat.parse(data)).substring(11);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

}
