package com.example.xinxin.chatdemo;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.xinxin.chatdemo.CallBack.GetFileCallBack;
import com.example.xinxin.chatdemo.CallBack.QRcodeCallBack;
import com.example.xinxin.chatdemo.CallBack.SearchCallBack;
import com.example.xinxin.chatdemo.Function.SocketFuncation;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements QRcodeCallBack,GetFileCallBack,SearchCallBack{
    private ProgressBar progressBar;
    private ImageView imageView;
    private TextView textView;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress);
        imageView= findViewById(R.id.imageView);
        textView=findViewById(R.id.seachText);
        editText=findViewById(R.id.editSearch);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    searchById(editText.getText().toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getQRcode(editText.getText().toString());
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                getFile("epuserlist.txt§Data//Rows//BASE//§","smx.txt","/sdcard/Mydata");
            }
        });
    }
    public void searchById(String mac) throws InterruptedException {
        SocketFuncation socketFuncation=new SocketFuncation();
        socketFuncation.Searchshow(mac,this);
    }
    public void getQRcode(String string){
        SocketFuncation socketFuncation=new SocketFuncation();
        try {
            socketFuncation.QRshow(string,this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void getFile(String path,String fileName,String filePath){
        SocketFuncation socketFuncation=new SocketFuncation();
        try {
            socketFuncation.GetFile(path,fileName,filePath,this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void qrSuccess(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
        Toast.makeText(this, "获取成功", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void qrError() {
        Toast.makeText(this, "图片获取失败", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void getFileSuccess() {
        Toast.makeText(this, "下载完成", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }
    @Override
    public void getFileError() {
        Toast.makeText(this, "下载失败", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void searhSuccess(String result) {
        textView.setText(result);
    }
    @Override
    public void searchError() {
        Toast.makeText(this, "查询失败", Toast.LENGTH_SHORT).show();
    }
}

