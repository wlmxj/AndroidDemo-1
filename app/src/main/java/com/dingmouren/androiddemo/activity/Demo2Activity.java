package com.dingmouren.androiddemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.dingmouren.androiddemo.R;
import com.dingmouren.androiddemo.net.base.Request;
import com.dingmouren.androiddemo.net.core.RequestQueue;
import com.dingmouren.androiddemo.net.core.SimpleNet;
import com.dingmouren.androiddemo.net.requests.StringRequest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dingmouren on 2017/3/30.
 */

public class Demo2Activity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        httpGetReqest();
                    }
                }).start();
                break;
            case R.id.btn2:
                getData();
                break;
        }
    }

    /**
     * HttpURLConnection的get方式请求数据+
     */
    private void httpGetReqest() {
        try {
            URL newUrl = new URL("http://edu.xueyuanwang.com/mapi_v2/School/getIndexCourses");
            HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
            conn.setReadTimeout(10*1000);//设置读取超时时间
            conn.setConnectTimeout(15 * 1000);//设置连接超时时间
            conn.setRequestMethod("POST");//设置请求方法
            conn.setDoInput(true);//接收输入流
            conn.setDoOutput(true);//启动输出流，当需要传递参数时需要开启
            //添加Header
            conn.setRequestProperty("Connection","Keep-Alive");
            conn.setRequestProperty("Accept-Encoding","gzip");
            conn.setRequestProperty("User-Agent","Android");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
            conn.connect();//发起请求
            InputStream inputStream = null;
            BufferedReader reader = null;
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = conn.getInputStream();//获取连接的输入流
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String json = reader.readLine();
            }
            reader.close();
            inputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }

    private void getData(){
        RequestQueue mQueue = SimpleNet.newRequestQueue();
        StringRequest request =  new StringRequest(Request.HttpMethod.POST, "http://edu.xueyuanwang.com/mapi_v2/School/getIndexCourses",
                new Request.RequestListener<String>() {
                    @Override
                    public void onComplete(int stCode, String response, String errMsg) {
                        Log.e("simplenet",response);
                    }
                });
        mQueue.addRequest(request);
    }
}
