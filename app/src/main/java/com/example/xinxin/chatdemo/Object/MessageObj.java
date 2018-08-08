package com.example.xinxin.chatdemo.Object;
import com.example.xinxin.chatdemo.bean.GetType;
import com.example.xinxin.chatdemo.bean.InfoType;
public class MessageObj {
    public String content;
    public InfoType msgType;
    public GetType getType;
    public GetType getGetType() {
        return getType;
    }

    public void setGetType(GetType getType) {
        this.getType = getType;
    }

    public MessageObj(InfoType msgType, String content) {
        this.content = content;
        this.msgType = msgType;
    }
    public MessageObj(InfoType msgType, GetType getType,String content) {
        this.content = content;
        this.msgType = msgType;
        this.getType=getType;
    }
    public MessageObj(InfoType msgType) {
        this.msgType = msgType;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public void setMsgType(InfoType msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public InfoType getMsgType() {
        return msgType;
    }
}
