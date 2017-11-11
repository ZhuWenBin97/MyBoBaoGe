package com.zcy.hnkjxy.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcy.hnkjxy.customview.bean.BarEntity;

import java.util.List;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/11 19:50
 */

public class BottomTabBar extends LinearLayout {

    private FragmentManager mManager;

    /**
     * 文本颜色
     */
    private int textColor;

    /**
     * 文字选中后的颜色
     */
    private int textSelectColor;

    /**
     * 设置整体背景颜色
     */
    private int backgroundColor;

    public BottomTabBar setManager(FragmentManager mManager) {
        this.mManager = mManager;
        return this;
    }

    public void setBar(List<BarEntity> bars) {
        init(bars);
    }

    public BottomTabBar(Context context) {
        super(context);
    }

    public BottomTabBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomTabBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BottomTabBar);
        textColor = typedArray.getColor(R.styleable.BottomTabBar_textColor, Color.parseColor("#66FFFFFF"));
        textSelectColor = typedArray.getColor(R.styleable.BottomTabBar_textSelectColor, Color.parseColor("#E51C23"));
        backgroundColor = typedArray.getColor(R.styleable.BottomTabBar_backgroundColor, Color.parseColor("#e9e9e9"));

        typedArray.recycle();
    }

    /**
     * 初始化
     *
     * @param bars
     */
    private void init(final List<BarEntity> bars) {
        setOrientation(LinearLayout.VERTICAL);
        setBackgroundColor(backgroundColor);
        if (bars == null || bars.size() <= 0) {
            return;
        }

        /**
         * 最上面添加一个的Fragment
         */
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setId(R.id.f_content);
        frameLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        LinearLayout.LayoutParams layoutParams = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        addView(frameLayout, layoutParams);
        //添加一根分割线
        View line = new View(getContext());
        line.setBackgroundColor(Color.parseColor("#cccccc"));
        ViewGroup.LayoutParams llp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        addView(line, llp);

        /**
         * 添加底部菜单导航栏
         */
        LinearLayout bottom = new LinearLayout(this.getContext());
        bottom.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lp = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < bars.size(); i++) {
            LinearLayout bar = new LinearLayout(this.getContext());
            bar.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams blp = new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            bar.setGravity(Gravity.CENTER);
            bar.setPadding(10, 16, 10, 16);
            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams ilp = new LayoutParams((int) getResources().getDimension(R.dimen.image_height), (int) getResources().getDimension(R.dimen.image_height));
            imageView.setImageResource(bars.get(i).getTableUnSelectResId());
            bar.addView(imageView, ilp);

            TextView textView = new TextView(getContext());
            textView.setText(bars.get(i).getTableText());
            LinearLayout.LayoutParams tlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textView.setTextColor(textColor);
            bar.addView(textView, tlp);

            final int position = i;
            bar.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    select(position, bars);
                }
            });
            bottom.addView(bottom, lp);
        }
        /**
         * 默认选中第一个菜单栏
         */
        select(0, bars);
    }

    private void select(int position, List<BarEntity> bars) {
        if (getChildAt(1) == null) {
            return;
        }
        ImageView imageView;
        TextView textView;
        LinearLayout bottom = (LinearLayout) getChildAt(2);
        for (int i = 0; i < bottom.getChildCount(); i++) {
            LinearLayout bar = (LinearLayout) bottom.getChildAt(0);
            imageView = (ImageView) bar.getChildAt(0);
            textView = (TextView) bar.getChildAt(0);
            if (position == i) {
                imageView.setImageResource(bars.get(i).getTableSelectResId());
                textView.setTextColor(textSelectColor);
            } else {
                imageView.setImageResource(bars.get(i).getTableUnSelectResId());
                textView.setTextColor(textColor);
            }

        }
        switchByPosition(position);
    }

    private OnSelectListener onSelectLinstener;

    /**
     * 切换的时候调用接口
     *
     * @param position
     */
    private void switchByPosition(int position) {
        if (onSelectLinstener != null) {
            onSelectLinstener.onSelect(position);
        }

    }

    private Fragment current;

    public void switchContent(Fragment to) {
        if (current != to) {
            FragmentTransaction transaction = mManager.beginTransaction();

            if (current != null) {
                transaction.hide(current);
            }
            if (!to.isAdded()) {
                transaction.add(R.id.f_content, to).commit();
            } else {
                //隐藏当前fragment，显示下一个
                transaction.show(to).commit();
            }
            current = to;
        }
    }


    /**
     * 选择切换的监听，在这里处理Fragment，防止重复创建
     */
    public interface OnSelectListener {
        public void onSelect(int position);
    }

    public BottomTabBar setOnSelectListener(OnSelectListener onSelectLinstener) {
        this.onSelectLinstener = onSelectLinstener;
        return this;
    }
}
