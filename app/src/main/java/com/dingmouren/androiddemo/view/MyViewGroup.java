package com.dingmouren.androiddemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by dingmouren on 2017/2/20.
 */

public class MyViewGroup extends ViewGroup {
    private static final String TAG = "event-viewgroup";

    private MyView mChildView;
    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyViewGroup(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed){
            mChildView = (MyView) getChildAt(0);
            mChildView.layout(l,t,l + mChildView.getMeasuredWidth(),t + mChildView.getMeasuredHeight() );
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG,"dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG,"onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
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
