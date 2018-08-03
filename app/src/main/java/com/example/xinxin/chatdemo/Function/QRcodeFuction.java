package com.example.xinxin.chatdemo.Function;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.xinxin.chatdemo.CallBack.QRcodeCallBack;
import com.example.xinxin.chatdemo.Object.MessageObj;
import com.example.xinxin.chatdemo.bean.InfoType;
public class QRcodeFuction {
    public static void QRshow(final String msg, final QRcodeCallBack callBackToMain) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MessageObj messageObj = new MessageObj(InfoType.GetQrCode, msg);
                    SocketFuncation socketFuncation = new SocketFuncation();
                    int c = socketFuncation.IOfuncition(messageObj);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(socketFuncation.bs1, 0, c);
                    callBackToMain.qrSuccess(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    callBackToMain.qrError();
                }
            }
        }).start();
    }
}
