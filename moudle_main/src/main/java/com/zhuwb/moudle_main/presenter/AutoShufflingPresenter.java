package com.zhuwb.moudle_main.presenter;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.guiying.module.common.base.BaseApplication;
import com.guiying.module.common.config.Config;
import com.guiying.module.common.utils.Logg;
import com.guiying.module.common.utils.MD5;
import com.guiying.module.common.utils.MyTime;
import com.guiying.module.common.utils.Tools;
import com.zhuwb.moudle_main.HttpUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/14 10:34
 */

public class AutoShufflingPresenter {
    private static final String TAG = "AutoShufflingPresenter";

    public static void getImages() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("action", "adv");
        map.put("func", "getAdvList");
        map.put("userId", "");
//        TelephonyManager telephonyManager = (TelephonyManager) this
//                .getSystemService(Context.TELEPHONY_SERVICE);
//        Datas.IMEI = telephonyManager.getDeviceId();
        map.put("imei", Config.androidIMEI);
        map.put("mold", "1");
        String url = getUrl(map);
        Log.i(TAG, "getImages: " + url);
        HttpUtils.requestNetwork(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: ");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

    public static String getUrl(HashMap<String, String> params) {
        // params.put("controlType", "1");

        String timestamp = MyTime.getTimestamp();
        String spm = Config.androidID + "." + Tools.getAppVersionName(BaseApplication.getIns()) + ".0." + timestamp + ".0";
        Log.i(TAG, "getUrl: " + spm);
        HashMap<String, String> map = new HashMap<String, String>();
        Set<String> myKey = params.keySet();
        for (String key : myKey) {
            map.put(key, params.get(key));
        }
        //map.put("time", timestamp);
        map.put("token_key", Config.KEY);
        map.put("sourceID", Config.androidID);
        String token_sort[] = new String[map.size()];
        myKey = map.keySet();
        int index = 0;
        for (String str : myKey) {
            token_sort[index++] = str;
        }
        String str = Tools.AsciiSort(token_sort, map);
        String bobao_token = MD5.getMD5(str);
        params.put("spm", URLEncoder.encode(spm));
        params.put("bobao_token", bobao_token);
        String sq_url = Config.HTTP;
        myKey = params.keySet();
        for (String key : myKey) {
            sq_url += key + "=" + params.get(key) + "&";
        }
        sq_url = sq_url.substring(0, sq_url.length() - 1);
        Logg.i("Util", "Current_URL" + sq_url);
        return sq_url;
    }

}
