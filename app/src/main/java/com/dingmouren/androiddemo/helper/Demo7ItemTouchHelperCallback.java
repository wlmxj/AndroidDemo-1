package com.dingmouren.androiddemo.helper;

import android.graphics.Canvas;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.dingmouren.androiddemo.listener.Demo7MoveAndSwipedListener;
import com.dingmouren.androiddemo.listener.Demo7StateChangeListener;

/**
 * Created by dingmouren on 2017/4/6.
 */

public class Demo7ItemTouchHelperCallback  extends ItemTouchHelper.Callback{
    public static final float ALPHA_FULL = 1.0f;
    private Demo7MoveAndSwipedListener mAdapterListener;//适配器实现了这个接口，在发生拖拽或者侧滑时调用接口中的方法，让adapter做出对应的操作

    public Demo7ItemTouchHelperCallback(Demo7MoveAndSwipedListener listener) {
        this.mAdapterListener = listener;
    }

    @Override//用来设置我们拖动方向以及侧滑方向
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager){
            //列表 设置拖拽方向为上下
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            //列表 设置侧滑方向从左向右和从右向左都可以
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            //将方向参数设置进去
            return makeMovementFlags(dragFlags,swipeFlags);
        }else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;//拖拽方向
            final int swipeFlags = 0;//不支持侧滑
            return makeMovementFlags(dragFlags,swipeFlags);
        }
    }

    @Override//当我们拖动item时会回调此方法
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //如果两个item不是同一类型，禁止拖拽
        if (viewHolder.getItemViewType() != target.getItemViewType()) return  false;
        //回调适配器中的onItemMove方法，就是拖拽动作
        mAdapterListener.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }

    @Override//当侧滑item时回调此方法
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //回调适配器中侧滑删除的方法
        mAdapterListener.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override//当状态改变时回调此方法
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        //当前状态不是idel空闲状态时，说明正在拖拽或者侧滑,在这里看可以改变item的背景颜色
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && viewHolder instanceof Demo7StateChangeListener){
            Demo7StateChangeListener stateChangeListener = (Demo7StateChangeListener) viewHolder;
            stateChangeListener.onItemSelected();//调用,修改item背景颜色
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override//拖拽完或者侧滑完一个item回调此方法，用来清除加在item上的一些状态
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (viewHolder instanceof  Demo7StateChangeListener){
            Demo7StateChangeListener stateChangeListener = (Demo7StateChangeListener) viewHolder;
            stateChangeListener.onItemClear();//清理item的背景颜色
        }
    }

    @Override//这个方法能判断当前是拖拽还是侧滑，可以实现侧滑时颜色逐渐变浅的效果
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            //根据侧滑位移来修改item的透明度
            final float alpha = ALPHA_FULL - Math.abs(dX)/(float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
