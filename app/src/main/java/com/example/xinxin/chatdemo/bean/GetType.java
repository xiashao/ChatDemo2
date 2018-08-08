package com.example.xinxin.chatdemo.bean;

/**
 * 想要获取的数据类型
 *
 * @author EricShang
 */
public enum GetType {

    String(1), // 登录：长连接注册
    Picture(2), // 文字消息 消息放在INFO中，无Data部分DataLen =0 优先级1
    Vedio(3),// 手机号注册
    File(4),
    Zip(5)
    ;
    private int _value;

    private GetType(int value) {
        _value = value;
    }

    public int value() {
        return _value;
    }

    public static GetType getTheType(int val) {
        for (GetType GetType2 : GetType.values()) {
            if (GetType2.value() == val) {
                return GetType2;
            }
        }
        return null;
    }
}
