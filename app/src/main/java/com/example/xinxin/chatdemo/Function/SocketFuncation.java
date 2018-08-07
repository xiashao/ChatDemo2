package com.example.xinxin.chatdemo.Function;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.example.xinxin.chatdemo.CallBack.GetFileCallBack;
import com.example.xinxin.chatdemo.CallBack.GetFileListCallBack;
import com.example.xinxin.chatdemo.CallBack.QRcodeCallBack;
import com.example.xinxin.chatdemo.CallBack.SearchCallBack;
import com.example.xinxin.chatdemo.Object.MessageObj;
import com.example.xinxin.chatdemo.bean.Constants;
import com.example.xinxin.chatdemo.bean.InfoType;
import com.example.xinxin.chatdemo.bean.PkgHead;
import com.example.xinxin.chatdemo.data.Row;
import com.example.xinxin.chatdemo.tool.GzipTool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
public class SocketFuncation {
    public String searchResult;
    public byte[] bs1 = new byte[30000];
    Socket socket=new Socket();
    public MessageObj messageObj;
    public SocketFuncation(MessageObj messageObj){
        this.messageObj=messageObj;
    }
    public SocketFuncation(){}
    //发送头信息
    public void sendHeadMessage(MessageObj messageObj) throws IOException {
        PkgHead head = new PkgHead();

        head.set_InfoType(messageObj.msgType);
        head.set_InfoLen(messageObj.content.getBytes().length);
        outputToServer(head.get_NetHeadByte(),head.get_NetHeadByte().length);
    }
    //从服务器接收头
    public void recHeadMessage() throws IOException {
        PkgHead head=new PkgHead();
        int headLength=intputToClient(head.get_NetHeadByte(),head.get_NetHeadByte().length);
    }
    //从服务器接收info
    public int recinfoMessage() throws IOException {
        int infoLengh=intputToClient(bs1,bs1.length);
        return infoLengh;
    }
    //发送info到服务器
    public void sendInfoMessage(MessageObj messageObj) throws IOException {
        outputToServer(messageObj.content.getBytes(),messageObj.content.getBytes().length);
    }
    //读取流到服务器
    public void outputToServer(byte[] bs,int length) throws IOException {

        try {
            socket.connect(new InetSocketAddress(Constants.ip, Constants.port), 2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(bs, 0, length);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //读取流到客户端
    public int intputToClient(byte[] bs,int length) throws IOException {
        int n = 0;
        try {
            socket.connect(new InetSocketAddress(Constants.ip, Constants.port), 2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            InputStream inputStream = socket.getInputStream();
            n=inputStream.read(bs, 0, length);
            if(messageObj.msgType.equals(InfoType.ZSearchRowsLike)){
                searchResult= GzipTool.decompressStrForGzip(inputStream);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return n;
    }
    //发送和读取方法集成
    public int IOfuncition(MessageObj messageObj) throws IOException {
        sendHeadMessage(messageObj);
        sendInfoMessage(messageObj);
        recHeadMessage();
        return recinfoMessage();
    }
    //获取文件
    public void GetFile(final String path, final String FileName, final String FilePath, final GetFileCallBack getFileCallBack) throws IOException {
        @SuppressLint("HandlerLeak") final Handler handler=new Handler(){
            public void handleMessage (Message msg){
                switch(msg.what) {
                    case 0:
                        getFileCallBack.getFileSuccess();
                        break;
                    case 1:
                        getFileCallBack.getFileError();
                        break;
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MessageObj messageObj=new MessageObj(InfoType.GetFile,path);
                    IOfuncition(messageObj);
                    InputStream inputStream = socket.getInputStream();
                    File dir = new File(FilePath); // 创建文件的存储路径
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    String savePath = FilePath+"/"+FileName; // 定义完整的存储路径
                    FileOutputStream file = new FileOutputStream(savePath, false);
                    byte[] buffer = new byte[1024];
                    int size = -1;//我赋值的很随意
                    while ((size = inputStream.read(buffer)) != -1) {
                        file.write(buffer, 0, size);
                    }
                    file.close();
                    inputStream.close();
                    handler.obtainMessage(0).sendToTarget();
                }catch (Exception e) {
                    e.printStackTrace();
                    handler.obtainMessage(1).sendToTarget();
                }
            }
        }).start();
    }
    public static void QRshow(final String msg, final QRcodeCallBack qRcodeCallBack) throws InterruptedException {
        @SuppressLint("HandlerLeak") final Handler handler=new Handler(){
            public void handleMessage (Message msg){
                switch(msg.what) {
                    case 0:
                        Bitmap bitmap=(Bitmap) msg.obj;
                        qRcodeCallBack.qrSuccess(bitmap);
                        break;
                    case 1:
                        qRcodeCallBack.qrError();
                        break;
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MessageObj messageObj = new MessageObj(InfoType.GetQrCode, msg);
                    SocketFuncation socketFuncation = new SocketFuncation();
                    int c = socketFuncation.IOfuncition(messageObj);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(socketFuncation.bs1, 0, c);
                    handler.obtainMessage(0,bitmap).sendToTarget();
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.obtainMessage(1).sendToTarget();
                }
            }
        }).start();
    }
    public static void Searchshow(final String mac, final SearchCallBack searchCallBack) throws InterruptedException {
        @SuppressLint("HandlerLeak") final Handler handler=new Handler(){
            public void handleMessage (Message msg){
                switch(msg.what) {
                    case 0:
                        String result=(String)msg.obj;
                        searchCallBack.searhSuccess(result);
                        break;
                    case 1:
                        searchCallBack.searchError();
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MessageObj messageObj=new MessageObj(InfoType.ZSearchRowsLike);
                    // messageObj.content="BASE∈rolelist∈"+mac;
                    // messageObj.content="BASE∈epuserlist∈name≈∫company≈威";
                    Row row=new Row();
                    row.setName="BASE";
                    row.tblName="epuserlist";
                    row.setVal("name","秀");
                    row.setVal("company","威米信");
                    messageObj.content=row.toString();
                    Log.e("smx","messageObj.content:"+messageObj.content);
                    SocketFuncation socketFuncation=new SocketFuncation(messageObj);
                    int c =socketFuncation.IOfuncition(messageObj);
                   // String searchResult=new String(socketFuncation.bs1,0,c);
                    Log.e("smx","msearchResult:"+socketFuncation.searchResult);
                    handler.obtainMessage(0,socketFuncation.searchResult).sendToTarget();
                }catch (Exception e) {
                    e.printStackTrace();
                    handler.obtainMessage(1).sendToTarget();
                }
            }
        }).start();
    }
    public static void getFileList(final String string,final GetFileListCallBack getFileListCallBack) throws InterruptedException {
        @SuppressLint("HandlerLeak") final Handler handler=new Handler(){
            public void handleMessage (Message msg){
                switch(msg.what) {
                    case 0:
                        String result=(String)msg.obj;
                        getFileListCallBack.gflSuccess(result);
                        break;
                    case 1:
                        getFileListCallBack.gflError();
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MessageObj messageObj=new MessageObj(InfoType.GetFileList);
                    messageObj.content=string;
                    Log.e("smx","messageObj.content:"+messageObj.content);
                    SocketFuncation socketFuncation=new SocketFuncation(messageObj);
                    int c =socketFuncation.IOfuncition(messageObj);
                    String searchResult=new String(socketFuncation.bs1,0,c);
                    Log.e("smx","msearchResult:"+searchResult);
                    handler.obtainMessage(0,searchResult).sendToTarget();
                }catch (Exception e) {
                    e.printStackTrace();
                    handler.obtainMessage(1).sendToTarget();
                }
            }
        }).start();
    }
}