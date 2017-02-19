package com.dingmouren.androiddemo.demos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dingmouren.androiddemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 adb shell dumpsys battery 命令行获取到的电池信息
 Current Battery Service state:
 AC powered: false
 USB powered: true
 Wireless powered: false
 status: 2
 status: 1
 health: 2
 present: true
 present: false
 level: 79
 level: 0
 scale: 100
 voltage: 4139
 temperature: 290
 technology: Li-ion

 */

public class BatteryActivity extends AppCompatActivity {
    @BindView(R.id.tv1) TextView mTv1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn1,R.id.btn2,R.id.btn3})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn1:
                registerReceiver(batteryReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                break;
            case R.id.btn2:
                unregisterReceiver(batteryReceiver);
        }
    }

    private BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == Intent.ACTION_BATTERY_CHANGED){
                int level = intent.getIntExtra("level",0);
                int scale = intent.getIntExtra("scale",100);
                mTv1.setText("广播方式剩余电量：" + level + "%");

            }
        }
    };
}
