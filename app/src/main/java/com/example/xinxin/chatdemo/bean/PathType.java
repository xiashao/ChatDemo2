package com.example.xinxin.chatdemo.bean;

/**
 *
 */
public enum PathType {

    pub(0), company(1), unit(2), prv(3);
    private int _value;

    private PathType(int value) {
        _value = value;
    }

    public static PathType getPathType(int value) {
        switch (value) {
            case 0:
                return pub;
            case 1:
                return company;
            case 2:
                return unit;
            case 3:
                return prv;
            default:
                return pub;
        }
    }


    public int value() {
        return _value;
    }


}
