package com.dingmouren.androiddemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by dingmouren on 2017/2/20.
 */

public class MyView extends View {
    private static final String TAG = "event-view";
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(TAG,"dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG,"onTouchEvent");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG,"onTouchEvent-down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG,"onTouchEvent-move");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG,"onTouchEvent-up");
                break;
        }
        return super.onTouchEvent(event);
    }
}
