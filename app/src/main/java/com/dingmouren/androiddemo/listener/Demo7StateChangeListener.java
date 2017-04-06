package com.dingmouren.androiddemo.listener;

/**
 * Created by dingmouren on 2017/4/6.
 * 监听在选中时，进行实际操作
 */

public interface Demo7StateChangeListener {
    void onItemSelected();//选中时，修改背景颜色
    void onItemClear();//未选中时恢复颜色
}
