package com.dingmouren.androiddemo.demos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.dingmouren.androiddemo.R;

/**
 * Created by dingmouren on 2017/2/20.
 * 事件分发机制
 */

public class EventActivity extends AppCompatActivity {
    private static final String TAG = "event-activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG,"dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }
}
