package com.zcy.hnkjxy.customview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zhang chen yang on 2017/9/29 0029.
 * 底部分享Dialog
 */

public class BottomShareDialog extends DialogFragment implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        if (onClickShareItemListener!=null)
            onClickShareItemListener.onClick((Integer) v.getTag());
        Log.i("TAG", "onClick: "+v.getTag());
    }

    //  单例模式
    static class Instance{
       static BottomShareDialog instance = new BottomShareDialog();
    }
    public static BottomShareDialog Instance(){
        return Instance.instance;
    }
    private BottomShareDialog() {
        super();
    }
    //分享元素
    private ArrayList<Integer> rIdlist = new ArrayList<>();
    private ArrayList<String> textlist = new ArrayList<>();
    public void addToShare(int resourceId,String text){
        rIdlist.add(resourceId);
        textlist.add(text);
    }
    //
    private OnClickShareItemListener onClickShareItemListener;

    public void setOnClickShareItemListener(OnClickShareItemListener onClickShareItemListener) {
        this.onClickShareItemListener = onClickShareItemListener;
    }

    //线性布局
    private LinearLayout layout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.bottom_share,null);
        layout = (LinearLayout) view.findViewById(R.id.llShare);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.setMargins(50,0,0,0);
        View iview;
        ImageView imageView;
        TextView textView;
        for (int i=0;i<rIdlist.size();i++){
            iview = inflater.inflate(R.layout.share_item,null);
            imageView = (ImageView) iview.findViewById(R.id.imgShare);
            textView = (TextView) iview.findViewById(R.id.tvShare);
            try {
                imageView.setBackgroundResource(rIdlist.get(i));
            }
            catch (Exception e){
                Log.e("TAG", "onCreateView:img参数不为整数形式 errorMsg--->:"+e.toString());
            }
            textView.setText(textlist.get(i));
            iview.setTag(i);
            iview.setOnClickListener(this);
            layout.addView(iview,params);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setWindowAnimations(R.style.dialogAnim);
    }
    public interface OnClickShareItemListener{
        void onClick(int position);
    }
}
