package com.dingmouren.androiddemo.widgets;

import android.content.Context;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

import com.dingmouren.androiddemo.R;
import com.dingmouren.androiddemo.listener.ToggleListener;

/**
 * Created by dingmouren on 2017/4/6.
 * 自定义滑动开关
 */

public class ToggleButton extends ViewGroup {
    private static final String TAG = ToggleButton.class.getSimpleName();
    private Context mContext;
    private Scroller mScroller;
    private int mLastX;
    private int mTouchSlop;
    //开关的状态，true就是开 false 就是关
    private boolean isOpen;
    //是否是一次有效的开关操作
    private boolean isValidToggle;
    private ToggleListener mToggleListener;
    public ToggleButton(Context context) {
        this(context,null);
    }

    public ToggleButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();//获取到是一个距离，手的移动距离大于这个距离才开始移动控件
        mScroller = new Scroller(mContext);
        ImageView slide = new ImageView(mContext);
        slide.setBackgroundResource(R.mipmap.slide);//滑动的那个图片
        slide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen){//打开状态的话，就执行关闭滚动动画
                    mScroller.startScroll(getScrollX(),0,getMeasuredWidth()/2,0,500);
                }else {//关闭状态的话，就执行打开的滚动动画
                    mScroller.startScroll(getScrollX(),0,-getMeasuredWidth()/2,0,500);
                }
                isOpen = !isOpen;
                isValidToggle = true;
                invalidate();//重绘
            }
        });
        setBackgroundResource(R.mipmap.background);//按钮的背景图片
        addView(slide);//将滑动按钮添加到这个开关控件上
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View view = getChildAt(0);
        view.layout(0,0,getMeasuredWidth() /2,getMeasuredHeight());
    }

    @Override//是否拦截触摸事件
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = true;
        int x = (int) ev.getX();
        Log.e(TAG,"onIntercept--x:" + x +" mLastX:"+mLastX);
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();//停止滚动
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(x - mLastX) > mTouchSlop){
                    intercepted = true;//手指滑动的时候开始拦截触摸事件
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;//手指抬起的手不再拦截触摸事件
                break;
        }
        mLastX = x;
        Log.e(TAG,"拦截吗："+ intercepted);
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        Log.e(TAG,"onTouchEvent--x:" + x +" mLastX:"+mLastX);
        isValidToggle = false;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){//按下的话就停止滚动
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = mLastX - x;
                Log.e(TAG,"onTouchEvent--deltaX:" + deltaX);
                Log.e(TAG,"onTouchEvent--deltaX + getScrollX():" + deltaX + getScrollX());
                Log.e(TAG,"onTouchEvent--deltaX + getScrollX() + getMeasuredWidth() /2:" + (deltaX + getScrollX() + getMeasuredWidth() /2));
                //边界检测判断，防止滑块越界
                if (deltaX + getScrollX() > 0){
                    scrollTo(0,0);
                    return true;
                }else if (deltaX + getScrollX() + getMeasuredWidth() /2 < 0){
                    scrollTo(-getMeasuredWidth() / 2,0);
                    return true;
                }
                scrollBy(deltaX,0);
                break;
            case MotionEvent.ACTION_UP:
                smoothScroll();
                break;
        }
        mLastX = x;
        return super.onTouchEvent(event);
    }

    private void smoothScroll() {
        int deltaX = 0;
        if (getScrollX() < - getMeasuredWidth() /4){
            deltaX = -getScrollX() - getMeasuredWidth() /2;
            if (!isOpen){
                isOpen = true;
                isValidToggle = true;
            }
        }
        if (getScaleX() >= - getMeasuredWidth() /4){
            deltaX = - getScrollX();
            if (isOpen){
                isOpen = false;
                isValidToggle = true;
            }
        }
        Log.e(TAG,"smoothScroll--deltaX:"+deltaX);
        mScroller.startScroll(getScrollX(),0,deltaX,0,500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }else {
            if (isValidToggle){
                if (mToggleListener != null){
                    mToggleListener.onToggled(isOpen);
                }
            }
        }
    }

    public void setOnToggledListener(ToggleListener listener){
        this.mToggleListener = listener;
    }
}
