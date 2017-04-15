package com.dingmouren.androiddemo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dingmouren.androiddemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingmouren on 2017/4/15.
 *
 * ScanResult:描述有关检测到的接入点的信息。
 * BSSID:接入点的地址
 * SSID:网络名称。
 * capabilities:描述接入点支持的认证，密钥管理和加密方案。
 * centerFreq0:如果AP带宽为20 MHz，则不使用如果AP使用40,80或160 MHz，则如果AP使用80 + 80 MHz，则这是中心频率（以MHz为单位），这是第一段的中心频率（MHz ）
 * centerFreq1:如果AP使用80 + 80 MHz，则AP带宽为80 + 80 MHz时才使用，这是第二段的中心频率（MHz）
 * channelWidth:AP信道带宽; CHANNEL_WIDTH_20MHZ，CHANNEL_WIDTH_40MHZ，CHANNEL_WIDTH_80MHZ，CHANNEL_WIDTH_160MHZ或CHANNEL_WIDTH_80MHZ_PLUS_MHZ之一。
 * frequency:客户端正在与接入点通信的信道的主要20 MHz频率（MHz）。
 * level:检测到的信号电平以dBm为单位，也称为RSSI。
 * operatorFriendlyName:表示由接入点发布的Passpoint操作员名称。
 * timestamp:最后一次看到这个结果的时间戳（以微秒为单位）。
 * venueName:表示接入点发布的场地名称（如“旧金山机场”）仅在通过点网络上可用，如果由接入点发布。
 */

public class Demo8Activity extends AppCompatActivity {
    private static final String TAG = Demo8Activity.class.getName();
    private WifiManager wifi;
    private boolean wasConnected;
    private TextView ssidView,rssiView,bssidView;
    private WifiInfo wifiInfo;
    final List<String> SSID = new ArrayList<>();
    final List<Integer> RSSI = new ArrayList<>();
    final List<String> BSSID = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo8);
        ssidView = (TextView) findViewById(R.id.ssid);
        rssiView=(TextView) findViewById(R.id.rssi);
        bssidView=(TextView) findViewById(R.id.bssid);
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wasConnected = wifi.isWifiEnabled();//手机wifi是启用状态还是禁用状态
        scanAndShow();
        wifiInfo = wifi.getConnectionInfo();//手机连接的wifi信息
        Log.e(TAG,"连接的wifi信息："+ wifiInfo.toString());
    }

    private void scanAndShow() {
        //注册扫描wifi的广播
        registerReceiver(mWifiScanReceiver,new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifi.startScan();

    }
    public void refreshButton(View view){
        unregisterReceiver(mWifiScanReceiver);//解绑扫描wifi的广播
        scanAndShow();//开始扫描
    }

    private BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)){
                SSID.clear();
                RSSI.clear();
                BSSID.clear();
                ssidView.setText("");
                rssiView.setText("");
                bssidView.setText("");
                List<ScanResult> mScanResults = wifi.getScanResults();
                for (int i = 0; i < mScanResults.size(); i++) {
                    SSID.add(mScanResults.get(i).SSID);//网络名称
                    RSSI.add(mScanResults.get(i).level);//信号强度
                    BSSID.add(mScanResults.get(i).BSSID);//接入点地址
                }
                String ssidString="";
                String bssidString="";
                String rssiString="";
                for (int i = 0; i < SSID.size(); i++) {
                    ssidString=ssidString+"\n"+SSID.get(i);
                    bssidString=bssidString+"\n"+BSSID.get(i);
                    rssiString=rssiString+"\n"+RSSI.get(i);
                }
                ssidView.setText(ssidString);
                rssiView.setText(rssiString);
                bssidView.setText(bssidString);
                Log.e(TAG,mScanResults.get(0).toString());
            }
        }
    };
}
