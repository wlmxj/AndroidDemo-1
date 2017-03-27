package com.dingmouren.androiddemo.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dingmouren.androiddemo.BuildConfig;
import com.dingmouren.androiddemo.R;

/**
 * Created by cuiqingdong on 2017/3/25.
 */
public class Demo1Activity extends AppCompatActivity {
    private static final String TAG = Demo1Activity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);
        findViewById(R.id.btn_call).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                checkCallPhonePermission();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkCallPhonePermission() {
        Log.e(TAG, "检查权限：" + ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE));
        Log.e(TAG, "是否要给予解释：" + shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {//检查activity是否具有打电话的权限
            //没有权限，第一次去申请权限
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE))
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 101);
            /*请求过权限但用户拒绝了请求，此方法返回true,如果设备规范禁止应用具有该权限或者在权限申请系统对话框中勾选了不再提醒，此方法会返回false，*/
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                Log.e(TAG, "请求过权限但用户拒绝了请求，要给用户一个需要这个权限的解释");
                new AlertDialog.Builder(this)
                        .setMessage("打电话功能需要这个权限")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("去申请权限", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(Demo1Activity.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                            }
                        }).show();
            }
        }
    }

    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        final Uri data = Uri.parse("tel:" + "10086");
        intent.setData(data);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "授权成功，可以直接使用功能");
                    callPhone();
                } else {
                    Log.e(TAG, "授权失败，不能使用此功能");
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                        new AlertDialog.Builder(this)
                                .setMessage("使用打电话功能的话，就需要这个权限哦，要使用此功能可以去设置里面哟")
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("跳往设置界面", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", Demo1Activity.this.getPackageName(), null);
                                        intent.setData(uri);
                                        Demo1Activity.this.startActivityForResult(intent, 102);
                                    }
                                }).show();
                    }
                }
                return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 102) {
            System.out.println("从设置界面回来");
        }
    }

}
