package com.dingmouren.androiddemo.net.core;

import com.dingmouren.androiddemo.net.httpstacks.HttpStack;

/**
 * Created by dingmouren on 2017/4/1.
 *
 */

public final class SimpleNet {

    /**
     * 创建一个请求队列，NetworkExecutor数量为默认的数量
     */
    public static RequestQueue newRequestQueue(){
        return newRequestQueue(RequestQueue.DEFAULT_CORE_NUMS);
    }

    /**
     * 创建一个请求队列，NetworkExecutor数量为coreNums
     * @param coreNums
     * @return
     */
    public static RequestQueue newRequestQueue(int coreNums){
        return newRequestQueue(coreNums,null);
    }

    public static RequestQueue newRequestQueue(int coreNums, HttpStack httpStack){
        RequestQueue queue = new RequestQueue(Math.max(0,coreNums),httpStack);
        queue.start();
        return queue;
    }
}
