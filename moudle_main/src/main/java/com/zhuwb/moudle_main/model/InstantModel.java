package com.zhuwb.moudle_main.model;

import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.zhuwb.moudle_main.contract.MessageContract;
import com.zhuwb.moudle_main.HttpUtils.OKHttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/21 10:29
 */

public class InstantModel implements MessageContract.IFragmentModel {
    private static final String TAG = "InstantModel";
    public OnLoadListener loadListener;


    public InstantModel(OnLoadListener loadListener) {
        this.loadListener = loadListener;
    }

    @Override
    public void requestNetwork(String url, FragmentActivity activity) {
        OKHttp.requestNetwork(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadListener.onFail(e);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                loadListener.onSucced(json);
            }

        }, activity);

    }

}
