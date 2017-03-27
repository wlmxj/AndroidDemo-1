package com.dingmouren.androiddemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.widget.Toast;

import com.dingmouren.androiddemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dingmouren on 2017/2/18.
 * 获取SIM卡信息的权限：<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
 */

public class SIMActivity extends AppCompatActivity {
    @BindView(R.id.tv1)   TextView tv1;
    @BindView(R.id.tv2)   TextView tv2;
    @BindView(R.id.tv3)   TextView tv3;
    @BindView(R.id.tv4)   TextView tv4;
    @BindView(R.id.tv5)   TextView tv5;
    @BindView(R.id.tv6)   TextView tv6;
    @BindView(R.id.tv7)   TextView tv7;
    private TelephonyManager manager ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim);
        ButterKnife.bind(this);
        manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (manager.getSimState() == manager.SIM_STATE_ABSENT){

        }
        switch (manager.getSimState()){

            case TelephonyManager.SIM_STATE_READY:
                showInfo();
                break;
            case TelephonyManager.SIM_STATE_ABSENT:
                Toast.makeText(SIMActivity.this,"无SIM卡",Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(SIMActivity.this,"SIM卡被锁定或者未知状态",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showInfo() {
        tv1.setText("手机号码："+ manager.getLine1Number());
        tv2.setText("IMEI:" + manager.getDeviceId());
        tv3.setText("运营商：" + manager.getNetworkOperatorName());
        tv4.setText("SIM卡序列号：" + manager.getSimSerialNumber() );
        tv5.setText("IMSI:" + manager.getSubscriberId());
        tv6.setText("ISO:" + manager.getNetworkCountryIso());
        tv7.setText("运营商编号：" + manager.getNetworkOperator());
    }
}
