package com.zhuwb.moudle_main.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.zhuwb.moudle_main.R;
import com.zhuwb.moudle_main.R2;
import com.zhuwb.moudle_main.Utils.Util;
import com.zhuwb.moudle_main.bean.ListMessageitem;
import com.zhuwb.moudle_main.presenter.BannerParticularsPresenter;
import com.zhuwb.moudle_main.presenter.IBannerParticularsPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListParticularsActivity extends AppCompatActivity {
    private FragmentManager manager = getSupportFragmentManager();
    @BindView(R2.id.main_list_back)
    LinearLayout mainListBack;
    @BindView(R2.id.main_lv_item_typearea)
    TextView mainLvItemTypearea;
    @BindView(R2.id.main_lv_item_type)
    TextView mainLvItemType;
    @BindView(R2.id.main_lv_item_time)
    TextView mainLvItemTime;
    @BindView(R2.id.main_lv_item_address)
    TextView mainLvItemAddress;
    @BindView(R2.id.main_lv_item_message)
    TextView mainLvItemMessage;
    @BindView(R2.id.main_lv_girdView)
    NineGridImageView mainLvGirdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_particulars);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventList(ListMessageitem.MessageBean data) {
        mainLvItemTypearea.setText(Util.whitchArea(data.getMessage_zone_id()));
        mainLvItemType.setText(data.getMsg_type_name());
        mainLvItemTime.setText(Util.getData(data.getMessage_verify_date()));
        mainLvItemAddress.setText(data.getMessage_ads());
        mainLvItemMessage.setText(data.getMessage_content());
        if (!"".equals(data.getMessage_images())) {
            IBannerParticularsPresenter iBannerParticularsPresenter = new BannerParticularsPresenter();
            iBannerParticularsPresenter.setImageAdapter(mainLvGirdView, data.getMessage_images(), manager);
        } else {
            mainLvGirdView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R2.id.main_list_back)
    public void onViewClicked() {
        this.finish();
    }
}
