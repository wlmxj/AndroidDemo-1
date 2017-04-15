package com.dingmouren.androiddemo.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.dingmouren.androiddemo.R;
import com.dingmouren.androiddemo.listener.OnItemClickListener;
import com.dingmouren.androiddemo.viewholder.RemoveItemViewHolder;

/**
 * Created by dingmouren on 2017/4/6.
 */

public class RemoveItemRecyclerView extends RecyclerView {
    private Context mContext;
    //上一次的触摸点
    private int mLastX,mLastY;
    //当前触摸的item的位置
    private int mPosition;
    //item对应的布局
    private LinearLayout mItemLayout;
    //删除按钮
    private TextView mDelete;
    //最大滑动距离（删除按钮的宽度）
    private int mMaxLength;
    //是否在垂直滑动列表
    private boolean isDragging;
    //item是否在跟随手指移动
    private boolean isItemMoving;
    //item是否开始自动滑动
    private boolean isStartScroll;
    //删除按钮状态 ：0表示关闭 ，1表示将要关闭，2表示将要打开 ，3表示打开
    private int mDeleteBtnState;
    //检测手指在滑动过程中的速度
    private VelocityTracker mVelocityTracker;//用于跟踪触摸屏事件的速率
    private Scroller mScroller;
    private OnItemClickListener mListener;
    public RemoveItemRecyclerView(Context context) {
        this(context,null);
    }

    public RemoveItemRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RemoveItemRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mScroller = new Scroller(context,new LinearInterpolator());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        mVelocityTracker.addMovement(e);//将事件添加到VelocityTracker实例中
        int x = (int) e.getX();
        int y = (int) e.getY();
        switch(e.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (mDeleteBtnState == 0){//删除按钮关闭状态
                    View view = findChildViewUnder(x,y);//获取到触摸点坐标对应的View对象
                    if (view == null) return false;
                    RemoveItemViewHolder viewHolder = (RemoveItemViewHolder) getChildViewHolder(view);//通过View获取到对应的ViewHolder对象
                    mItemLayout = viewHolder.layout;//item布局
                    mPosition = viewHolder.getAdapterPosition();//item的位置
                    mDelete = (TextView) mItemLayout.findViewById(R.id.item_delete);//获取到删除按钮的View对象
                    mMaxLength = mDelete.getWidth();//获取到删除按钮的宽度，也就是滑动的最大距离
                    mDelete.setOnClickListener(new OnClickListener() {//点击删除
                        @Override
                        public void onClick(View v) {
                            mListener.onDeleteClick(mPosition);//触发删除的回调
                            mItemLayout.scrollTo(0,0);//item布局复位到原来的位置
                            mDeleteBtnState = 0;//将删除按钮的状态还原成关闭
                        }
                    });
                }else if (mDeleteBtnState == 3){//删除按钮打开状态
                    mScroller.startScroll(mItemLayout.getScrollX(),0,-mMaxLength,0,200);//删除按钮打开状态的话，触摸就隐藏删除按钮
                    invalidate();
                    mDeleteBtnState = 0;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = mLastX - x;
                int dy = mLastY - y;
                int scrollX = mItemLayout.getScrollX();//当前View的左上角相对于母视图左上角的距离，偏移量
                if(Math.abs(dx) > Math.abs(dy)){
                    isItemMoving = true;
                    if (scrollX + dx <= 0){//左边边界检测
                        mItemLayout.scrollTo(0,0);
                        return true;
                    }else if (scrollX + dx >= mMaxLength){//右边边界检测
                        mItemLayout.scrollTo(mMaxLength,0);
                        return true;
                    }
                   mItemLayout.scrollBy(dx,0);//item随手指移动
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isItemMoving && !isDragging && mListener != null){
                    mListener.onItemClick(mItemLayout,mPosition);
                }
                isItemMoving = false;
                mVelocityTracker.computeCurrentVelocity(1000);//计算手指滑动的速度，1秒移动了多少像素，获取x或y方向上的速度前，必须先调用这个方法
                float xVelocity = mVelocityTracker.getXVelocity();//水平方向速度，向左为负
                float yVelocity = mVelocityTracker.getYVelocity();//垂直方向速度
                int deltaX = 0;
                int upScrollX = mItemLayout.getScrollX();
                if (Math.abs(xVelocity) > 100 && Math.abs(xVelocity) > Math.abs(yVelocity)){
                    if (xVelocity <= -100){
                        deltaX = mMaxLength - upScrollX;
                        mDeleteBtnState = 2;
                    }else if (xVelocity > 100){//右滑速度大于100，隐藏删除按钮
                        deltaX = -upScrollX;
                        mDeleteBtnState = 1;
                    }
                }else {
                    if (upScrollX >= mMaxLength / 2){//item的左滑距离大于删除按钮宽度的一半时，显示删除按钮
                        deltaX = mMaxLength - upScrollX;
                        mDeleteBtnState = 2;
                    }else if (upScrollX < mMaxLength / 2){//item的左滑距离小于删除按钮宽度的一半时，显示隐藏按钮
                        deltaX = -upScrollX;
                        mDeleteBtnState = 1;
                    }
                }
                //item自动滑动到指定位置
                mScroller.startScroll(upScrollX,0,deltaX,0,200);
                isStartScroll = true;
                invalidate();
                mVelocityTracker.clear();
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.onTouchEvent(e);
    }

    @Override
    public void computeScroll() {//在绘制View时调用
        if (mScroller.computeScrollOffset()){//mScroller.computeScrollOffset()计算滚动偏移量，返回true表示滚动尚未完成，false表示滚动已经结束
            mItemLayout.scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }else if (isStartScroll){
            isStartScroll = false;
            if (mDeleteBtnState == 1){
                mDeleteBtnState = 0;
            }
            if (mDeleteBtnState == 2){
                mDeleteBtnState = 3;
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        isDragging = (state == SCROLL_STATE_DRAGGING);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
}
