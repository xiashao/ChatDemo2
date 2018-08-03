package com.example.xinxin.chatdemo.bean;

/**
 * 发送和接收的头的类型
 *
 * @author sixgod
 */
public enum InfoType {

    Log(1), // 登录：长连接注册
    Msg(2), // 文字消息 消息放在INFO中，无Data部分DataLen =0 优先级1
    Reg(3), // 手机号注册

    SendFile(4), // 发送大文件
    MsgConfirm(5), // 发送确认信息,告诉服务器收到消息
    GetFile(6), // 从服务器下载文件

    ReloadSet(9), //  暂未启用
    getUserListSet(10),//  暂未启用
    ReloadTbl(11),//

    GetFileList(12), // 获得文件列表:若为null,返回值为nil
    GetDirList(13), // 获得目录列表:若为null,返回值为nil

    DelFiles(14),// 删除文件或目录（没有用到）

    FileUpdate(17), //文件更新状态

//    SearchFile(18),//查找文件目录（没有用到）
//    getUserList(19),//获取客户列表（没有用到）
    getUserStat(20), //获取客户状态
//    FileSendOK(21), //接收文件通知（没有用到）
//    ChangeRegCode(22), //修改客户端认证码（没有用到）

    /**
     * 与服务器不一致：服务器是获取二维码？？？？？？
     */
    GetQrCode(23),//获取二维码
   // delClientFile(23),//删除端群

    FileCocy(25),//文件复制（没有用到）
    txt(31),//文本消息（没有用到）
    /**
     * 32、33、34本质都没有用到，
     * 因为无论传入什么infotype，SendFileAysncTask中都统一为 InfoType.SendFile
     */
    voice(32),//语音消息
    img(33),//图片消息
    vedio(34),//视频

    addmac(35),
    delmac(36),

    DataObj(38),//数据类型
    Line(40),//涂鸦
    Page(41),//涂鸦翻页

    SearchRows(58),//查询row: 不同字段且关系用∫分隔;
    SearchRowsLike(59),//模糊查询row:
//    Merge(64),
    UpdateTbl(67),//??????
    GetRowById(69),//根据rowid1查询row: 比SearchRows更快
    updateColById(70),//修改col
    updateById(71),//增，改
    delById(72),//删

    SearchRowsIn(73),
    getIcons(74),//下载图片
    updateRowIcon(75)//更新图片
    ;

    private int _value;

    private InfoType(int value) {
        _value = value;
    }

    public int value() {
        return _value;
    }

    public static InfoType getInfoType(int val) {
        for (InfoType InfoType2 : InfoType.values()) {
            if (InfoType2.value() == val) {
                return InfoType2;
            }
        }
        return null;
    }
}
