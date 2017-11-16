package com.guiying.module.common.utils;


import com.guiying.module.common.base.BaseApplication;
import com.guiying.module.common.config.Config;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhang chen yang on 2017/9/21 0021.
 */

public class Util {

    public static String calculateSpm(String timestamp) {
        return Config.androidID + "." + Tools.getAppVersionName(BaseApplication.getIns()) + ".0." + timestamp + ".0";
    }

    public static String calculateToken(Map<String, String> map, String time) {
        map.put("time", time);
        map.put("token_key", Config.KEY);
        map.put("sourceID", Config.androidID);
        String boBaoToken = null;
        int tokenSortLength = map.keySet().size();
        String token_sort[] = new String[tokenSortLength];
        int i = 0;
        //构建token_sort
        for (String key : map.keySet()) {
            token_sort[i++] = key;
        }
        for (int j = 0; j < tokenSortLength; j++) {
            Logg.i("Util", "calculateToken: " + token_sort[j] + ":" + map.get(token_sort[j]));
        }
        String str = Tools.AsciiSort(token_sort, map);

        boBaoToken = MD5.getMD5(str);

        return boBaoToken;
    }

    //根据传递的参数计算spm和token组成url
    public static String getUrl(HashMap<String, String> params) {
        params.put("controlType", "1");
        String timestamp = MyTime.getTimestamp();
        String spm = Config.androidID + "." + Tools.getAppVersionName(BaseApplication.getIns()) + ".0." + timestamp + ".0";
        HashMap<String, String> map = new HashMap<String, String>();
        Set<String> myKey = params.keySet();
        for (String key : myKey) {
            map.put(key, params.get(key));
        }
        map.put("time", timestamp);
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
        String sq_url = Config.Httpall;
        myKey = params.keySet();
        for (String key : myKey) {
            sq_url += key + "=" + params.get(key) + "&";
        }
        sq_url = sq_url.substring(0, sq_url.length() - 1);
        Logg.i("Util", "Current_URL" + sq_url);
        return sq_url;
    }

    /**
     * Unicode码转换成中文
     *
     * @param unicodeStr Unicode码
     * @return
     */
    public static String decodeUnicode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5)
                        && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
                        .charAt(i + 1) == 'U'))) {
                    try {
                        retBuf.append((char) Integer.parseInt(
                                unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                } else {
                    retBuf.append(unicodeStr.charAt(i));
                }
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }
}
