package com.dingmouren.androiddemo.listener;

/**
 * Created by dingmouren on 2017/4/6.
 * 当item被侧滑或者拖拽的时候，相应的adapter也要做出变化，adapter要实现此接口来实现相应的变化
 */

public interface Demo7MoveAndSwipedListener {
    boolean onItemMove(int fromPosition,int toPosition);//拖拽
    void onItemDismiss(int position);//侧滑删除
}
