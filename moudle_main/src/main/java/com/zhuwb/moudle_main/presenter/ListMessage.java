package com.zhuwb.moudle_main.presenter;


import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.google.gson.Gson;
import com.zcy.hnkjxy.customview.RefreshListView;
import com.zhuwb.moudle_main.adpter.MyLvAdapter;
import com.zhuwb.moudle_main.bean.ListMessageitem;
import com.zhuwb.moudle_main.model.HttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.guiying.module.common.utils.Util.getUrl;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/17 08:37
 */

public class ListMessage implements IListMessage {
    private static final String TAG = "ListMessage";
    private Context context;

    @Override
    public void getMessage(final FragmentActivity context, final RefreshListView listView, Integer mold, final FragmentManager manager) {
        this.context = context;

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("action", "message");
        map.put("func", "getMessageList");
        map.put("categoryId", "");
        map.put("pageSize", "10");
        map.put("state", "1,4");
        map.put("mold", mold.toString());
        map.put("keyValue", "");
        map.put("userid", "0");
        map.put("curPage", "1");
        map.put("type", "1");
        map.put("lastId", "0");
        map.put("zoneIDs", "1");
        String url = getUrl(map);
        Log.i(TAG, "getMessage: " + url);

        HttpUtils.requestNetwork(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                ListMessageitem Message = gson.fromJson(json, ListMessageitem.class);

                final List<ListMessageitem.MessageBean> MessageBeans = Message.getMessage();
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(new MyLvAdapter(context, MessageBeans, manager));
                    }
                });

            }
        });

    }


}
