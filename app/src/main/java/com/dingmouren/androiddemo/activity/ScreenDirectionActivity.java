package com.dingmouren.androiddemo.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.dingmouren.androiddemo.R;

import butterknife.BindView;

/**
 * Created by dingmouren on 2017/2/19.
 * 根据重力感应显示手机屏幕方向 android:screenOrientation="sensor"
 *
 * 屏幕切换不销毁activity   android:configChanges="orientation|screenSize"
 通过设置这个属性可以使Activity捕捉设备状态变化，以下是可以被识别的内容：

 设置方法：将下列字段用“|”符号分隔开，例如：“locale|navigation|orientation

 "mcc" 国际移动用户识别码所属国家代号是改变了----- sim被侦测到了，去更新mcc mcc是移动用户所属国家代号

 "mnc" 国际移动用户识别码的移动网号码是改变了------ sim被侦测到了，去更新mnc MNC是移动网号码，最多由两位数字组成，用于识别移动用户所归属的移动通信网

 "locale" 地址改变了-----用户选择了一个新的语言会显示出来

 "touchscreen" 触摸屏是改变了------通常是不会发生的

 "keyboard" 键盘发生了改变----例如用户用了外部的键盘

 "keyboardHidden" 键盘的可用性发生了改变

 "navigation" 导航发生了变化-----通常也不会发生

 "screenLayout" 屏幕的显示发生了变化------不同的显示被激活

 "fontScale" 字体比例发生了变化----选择了不同的全局字体

 "uiMode" 用户的模式发生了变化

 "orientation" 屏幕方向改变了

 "screenSize" 屏幕大小改变了

 "smallestScreenSize" 屏幕的物理大小改变了，如：连接到一个外部的屏幕上


 */

public class ScreenDirectionActivity extends AppCompatActivity {
    @BindView(R.id.btn)
    Button mBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_direc);
        Toast.makeText(this,"创建咯",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Toast.makeText(this,"屏幕切换了",Toast.LENGTH_SHORT).show();
    }
}
