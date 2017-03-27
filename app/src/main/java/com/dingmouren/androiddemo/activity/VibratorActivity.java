package com.dingmouren.androiddemo.activity;

import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dingmouren.androiddemo.R;
/**
 * 控制手机振动的api,注意在AndroidManifest.xml中添加权限   <uses-permission android:name="android.permission.VIBRATE"/>
 *  mVibrator.vibrate(new long[]{1000,2000},0);
 *  第一个参数是振动间隔时间和振动时间 例如new long[]{1000,2000,3000,1000} 意思是一秒后振动2秒，三秒后振动四秒
 *  第二个参数是振动模式，-1表示一次振动，0表示一直振动
 */
public class VibratorActivity extends AppCompatActivity {
    private Button mBtn1,mBtn2,mBtnClose;
    private Vibrator mVibrator;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibrator);
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtnClose = (Button) findViewById(R.id.btn_close);
        mVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);//振动对象

        //振动一次
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(new long[]{1000,1000},-1);
            }
        });

        //一直有节奏的振动
        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(new long[]{1000,3000,1000,1000,1000,2000,1000,5000},0);
            }
        });

        //关闭振动
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.cancel();
                Toast.makeText(v.getContext(),"振动关闭",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
