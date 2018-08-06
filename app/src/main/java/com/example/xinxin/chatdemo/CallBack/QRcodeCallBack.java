package com.example.xinxin.chatdemo.CallBack;

import android.graphics.Bitmap;
public interface QRcodeCallBack {
    void qrSuccess(Bitmap bitmap);
    void qrError();
}
