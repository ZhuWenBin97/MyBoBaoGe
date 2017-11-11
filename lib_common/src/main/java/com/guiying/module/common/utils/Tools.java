package com.guiying.module.common.utils;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.Map;

public class Tools {


    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        int versioncode = 0;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 根据ASCII码排序
     *
     * @param
     * @return
     */
    public static String AsciiSort(String[] str, Map<String, String> map) {
        String temp = "";
        for (int i = str.length - 1; i > 0; --i) {
            for (int j = 0; j < i; ++j) {
                int maxStr = str[j].length();
                if (str[j].length() < str[j + 1].length()) {
                    maxStr = str[j + 1].length();
                }
                if (isExchange(str[j], str[j + 1], maxStr)) {
                    temp = str[j];
                    str[j] = str[j + 1];
                    str[j + 1] = temp;
                }

            }
        }
        String str_token = "";
        for (int i = 0; i < str.length; i++) {
            str_token += map.get(str[i]);
        }
        return str_token;

    }


    private static boolean isExchange(String str1, String str2, int maxStr) {
        for (int i = 0; i < maxStr; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                return str1.charAt(i) > str2.charAt(i);
            }
        }

        return true;
    }
}
