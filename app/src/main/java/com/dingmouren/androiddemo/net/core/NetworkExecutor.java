package com.dingmouren.androiddemo.net.core;

import com.dingmouren.androiddemo.net.base.Request;
import com.dingmouren.androiddemo.net.base.Response;
import com.dingmouren.androiddemo.net.cache.Cache;
import com.dingmouren.androiddemo.net.cache.LruMemCache;
import com.dingmouren.androiddemo.net.httpstacks.HttpStack;

import java.util.concurrent.BlockingQueue;

/**
 * Created by dingmouren on 2017/3/31.
 * 从网络请求队列中循环读取请求并执行
 */

final class NetworkExecutor extends Thread {
    //网络请求队列
    private BlockingQueue<Request<?>> mRequestQueue;
    //网络请求栈
    private HttpStack mHttpStack;
    //结果分发器，将结果传递给主线程
    private static ResponseDelivery mResponseDelivery = new ResponseDelivery();
    //请求缓存
    private static Cache<String,Response> mReqCache = new LruMemCache();
    //是否停止
    private boolean isStop = false;

    public NetworkExecutor(BlockingQueue<Request<?>> queue,HttpStack httpStack){
        mRequestQueue = queue;
        mHttpStack = httpStack;
    }

    @Override
    public void run() {
        try {
            while (!isStop){
                final Request<?> request = mRequestQueue.take();
                if (request.isCanceld()){//取消执行请求
                    continue;
                }
                Response response = null;
                if (isUseCache(request)){
                    response = mHttpStack.performRequest(request);//从缓存中读取
                }else {
                    response = mHttpStack.performRequest(request);//从网络中读取
                    if (request.shouldCache() && isSuccess(response)){//如果需要缓存该请求，请求成功后就可以缓存到mReqCache
                        mReqCache.put(request.getUrl(),response);
                    }
                }
                //分发请求结果
                mResponseDelivery.deliveryResponse(request,response);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 请求是否成功
     */
    private boolean isSuccess(Response response){
        return response != null && response.getStatusCode() == 200;
    }

    /**
     * 是否要缓存
     */
    private boolean isUseCache(Request<?> request){
        return request.shouldCache() && mReqCache.get(request.getUrl()) != null;
    }

    public void quit(){
        isStop = true;
        interrupt();//中断线程n
    }
}
