package com.dingmouren.androiddemo.listener;

import android.view.View;

/**
 * Created by dingmouren on 2017/4/6.
 */

public interface OnItemClickListener {
    void onItemClick(View view,int position);//item点击回调
    void onDeleteClick(int position);//删除按钮回调
}
