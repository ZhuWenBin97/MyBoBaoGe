package com.zhuwb.moudle_main.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.zhuwb.moudle_main.R;
import com.zhuwb.moudle_main.R2;
import com.zhuwb.moudle_main.bean.BannerMessage;
import com.zhuwb.moudle_main.presenter.BannerParticularsPresenter;
import com.zhuwb.moudle_main.presenter.IBannerParticularsPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/18 16:23
 */

public class BannerParticularsActivity extends AppCompatActivity {
    FragmentManager manager = getSupportFragmentManager();
    private static final String TAG = "BannerParticularsActivi";
    @BindView(R2.id.main_banner_item_typearea)
    TextView mainBannerItemTypearea;
    @BindView(R2.id.main_banner_item_time)
    TextView mainBannerItemTime;
    @BindView(R2.id.main_banner_item_call)
    RelativeLayout mainBannerItemCall;
    @BindView(R2.id.main_banner_item_address)
    TextView mainBannerItemAddress;
    @BindView(R2.id.main_banner_item_message)
    TextView mainBannerItemMessage;
    @BindView(R2.id.main_banner_ninegridimageview)
    NineGridImageView mainBannerNinegridimageview;
    @BindView(R2.id.main_banner_back)
    LinearLayout mainBannerBack;
    private BannerMessage bannerMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_banner_message);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        Log.i(TAG, "onEventGetMessageBean: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(BannerMessage.BannerBean bannerBean) {
        mainBannerItemTypearea.setText(bannerBean.getMsg_type_name());
        mainBannerItemTime.setText(bannerBean.getMessage_verify_date());
        mainBannerItemAddress.setText(bannerBean.getMessage_ads());
        mainBannerItemMessage.setText(bannerBean.getMessage_content());
        IBannerParticularsPresenter iBannerParticularsPresenter = new BannerParticularsPresenter();
        iBannerParticularsPresenter.setImageAdapter(mainBannerNinegridimageview, bannerBean.getMessage_images(), manager);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R2.id.main_banner_back)
    public void onViewClicked() {
        this.finish();
    }
}
