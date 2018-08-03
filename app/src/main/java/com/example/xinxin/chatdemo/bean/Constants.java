package com.example.xinxin.chatdemo.bean;

import android.os.Build;

/**
 * 常量类
 */
public class Constants {
    public static final String basePath = "www.vmeet.cc";
    public static final String Version = "4";
    public static final String ip = "123.56.3.72";
    public static final int port = 30003;
    public static final int HeaderLength = 32;//默认消息头长度
    public static String getPhoneType() {
        // 硬件制造商（手机品牌）
        String type = Build.MANUFACTURER;
        // 手机版本
        String model = Build.MODEL;
        return type + ":" + model;
    }

    public static void showLog(Object text) {
        android.util.Log.e("com.vmeet.net", "---" + text + "---");
    }
}
