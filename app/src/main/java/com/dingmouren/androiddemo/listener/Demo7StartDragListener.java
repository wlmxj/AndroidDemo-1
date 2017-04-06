package com.dingmouren.androiddemo.listener;

import android.support.v7.widget.RecyclerView;

/**
 * Created by dingmouren on 2017/4/6.
 * 点击item布局右边图片就可可以实现拖拽，默认是长按才能实现拖拽
 */

public interface Demo7StartDragListener {
    void startDrag(RecyclerView.ViewHolder viewHolder);
}
