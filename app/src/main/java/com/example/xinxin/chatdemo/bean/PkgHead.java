package com.example.xinxin.chatdemo.bean;

import android.text.TextUtils;
import android.util.Log;

/**
 * socket封装头文件
 *
 * @author sixgod
 */
public class PkgHead {
    private byte _VerNum; // 1Bytes 0
    private String _MacFrom; // 6Bytes 1-6
    private String _MacTo = "00-00-00-00-00-00"; // 6Bytes 7-12
    private InfoType _InfoType; // 1Bytes    13
    private int _ClientType; // 1 Bytes     14
    private long _InfoLen = 0;// 8 Bytes    15-22
    private long _DataLen = 0;// 8 Bytes    23-30
    private int _pathType; // 1Bytes  		 31

    private byte[] _NetHeadByte = new byte[Constants.HeaderLength]; // 37Bytes

    public PkgHead() {
        _NetHeadByte[0] = 2;
        _ClientType = ClientType.Android.value();
        _NetHeadByte[14] = (byte) _ClientType;
        _pathType = PathType.pub.value();
    }

    public byte get_VerNum() {
        return _VerNum;
    }

    public void set_VerNum(byte _VerNum) {
        this._VerNum = _VerNum;
        _NetHeadByte[0] = _VerNum;
    }

    public String get_MacFrom() {
        return _MacFrom;
    }

    public void set_MacFrom(String _MacFrom) {
        this._MacFrom = _MacFrom;
        if (TextUtils.isEmpty(_MacFrom)) return;
        String[] strs1 = _MacFrom.split("-");
        for (int i = 0; i <= strs1.length - 1; i++) {
            _NetHeadByte[i + 1] = (byte) Integer.parseInt(strs1[i], 16);
        }
    }

    public String get_MacTo() {
        return _MacTo;
    }

    public void set_MacTo(String _MacTo) {
        this._MacTo = _MacTo;
        if (TextUtils.isEmpty(_MacTo)) return;
        String[] strs1 = _MacTo.split("-");
        for (int i = 0; i <= strs1.length - 1; i++) {
            _NetHeadByte[i + 7] = (byte) Integer.parseInt(strs1[i], 16);
        }
    }

    public InfoType get_InfoType() {
        return _InfoType;
    }

    public void set_InfoType(InfoType _InfoType) {
        this._InfoType = _InfoType;
        _NetHeadByte[13] = (byte) _InfoType.value();
    }

    public int get_ClientType() {
        return _ClientType;
    }

    public void set_ClientType(int _ClientType) {
        this._ClientType = _ClientType;
        _NetHeadByte[14] = (byte) _ClientType;
    }

    public long get_InfoLen() {
        return _InfoLen;
    }

    public void set_InfoLen(long _InfoLen) {
        this._InfoLen = _InfoLen;
        for (int i = 0; i < 8; i++) {
            _NetHeadByte[i + 15] = new Long(_InfoLen & 0xff).byteValue();//
            _InfoLen = _InfoLen >> 8; // 向右便移
        }
    }

    public long get_DataLen() {
        return _DataLen;
    }

    public void set_DataLen(long _DataLen) {
        this._DataLen = _DataLen;
        for (int i = 0; i < 8; i++) {
            _NetHeadByte[i + 23] = new Long(_DataLen & 0xff).byteValue();//
            _DataLen = _DataLen >> 8; // 向右便宜
        }
    }

    public int get_pathType(PathType pub) {
        return _pathType;
    }

    public void set_pathType(int _pathType) {
        this._pathType = _pathType;
        _NetHeadByte[31] = (byte) _pathType;
    }

    public byte[] get_NetHeadByte() {
        return _NetHeadByte;
    }

    public void set_NetHeadByte(byte[] _NetHeadByte) {
        this._NetHeadByte = _NetHeadByte;

        _VerNum = _NetHeadByte[0];
        String str1 = Integer.toString(_NetHeadByte[1] & 0xff | 0x100, 16)
                .substring(1);
        for (int i = 1; i <= 5; i++) {
            str1 += "-"
                    + Integer.toString(_NetHeadByte[i + 1] & 0xff | 0x100, 16)
                    .substring(1);
        }
        _MacFrom = str1.toUpperCase();
        String str2 = Integer.toString(_NetHeadByte[7] & 0xff | 0x100, 16)
                .substring(1);
        for (int i = 7; i <= 11; i++)
            str2 += "-"
                    + Integer.toString(_NetHeadByte[i + 1] & 0xff | 0x100, 16)
                    .substring(1);
        _MacTo = str2.toUpperCase();

//		String str3 = Integer.toString(_NetHeadByte[13] & 0xff, 10);
//		for (int i = 13; i <= 15; i++)
//			str3 += "." + Integer.toString(_NetHeadByte[i + 1] & 0xff, 10);
//		_LocalIp = str3;

        _InfoType = InfoType.getInfoType(_NetHeadByte[13]);
        _ClientType = _NetHeadByte[14];
        Log.i("set_NetHeadByte", "_ClientType " + _ClientType);
//		_InfoLen = byteToShort(_NetHeadByte, 19);
        _InfoLen = byteToLong(_NetHeadByte, 15);
        _DataLen = byteToLong(_NetHeadByte, 23);

        _pathType = _NetHeadByte[31];
//		_Port = byteToShort(_NetHeadByte,29);
//		String str4 = Integer.toString(_NetHeadByte[31] & 0xff | 0x100, 16)
//				.substring(1);
//		for (int i = 31; i <= 35; i++)
//			str4 += "-"
//					+ Integer.toString(_NetHeadByte[i + 1] & 0xff | 0x100, 16)
//							.substring(1);
//		_GroupTo = str4.toUpperCase();
    }

    /**
     * byte字节数组转long类型值
     *
     * @param b
     * @param offset
     * @return
     */
    public static long byteToLong(byte[] b, int offset) {
        long s = 0;
        long s0 = b[0 + offset] & 0xff;//
        long s1 = b[1 + offset] & 0xff;
        long s2 = b[2 + offset] & 0xff;
        long s3 = b[3 + offset] & 0xff;
        long s4 = b[4 + offset] & 0xff;//
        long s5 = b[5 + offset] & 0xff;
        long s6 = b[6 + offset] & 0xff;
        long s7 = b[7 + offset] & 0xff;
        s1 <<= 8;
        s2 <<= 16;
        s3 <<= 24;
        s4 <<= 8 * 4;
        s5 <<= 8 * 5;
        s6 <<= 8 * 6;
        s7 <<= 8 * 7;
        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
        return s;
    }

    /**
     * byte字节数组转int类型
     *
     * @param b
     * @param i
     * @return
     */
    public static int byteToShort(byte[] b, int i) {
        int s = 0;
        short s0 = (short) (b[0 + i] & 0xff);
        short s1 = (short) (b[1 + i] & 0xff);
        s1 <<= 8;
        s = s0 | s1;
        return s & 0xffff;
    }

    public void Clear() {
        _NetHeadByte = null;
        _MacFrom = null;
        _MacTo = null;
    }
}
