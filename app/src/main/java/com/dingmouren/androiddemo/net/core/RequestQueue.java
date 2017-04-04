package com.dingmouren.androiddemo.net.core;

import android.util.Log;

import com.dingmouren.androiddemo.net.base.Request;
import com.dingmouren.androiddemo.net.httpstacks.HttpStack;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dingmouren on 2017/3/31.
 * 请求队列，使用优先队列，使得请求可以按照优先级进行处理，线程安全
 */

public final class RequestQueue {
    private static final String TAG = RequestQueue.class.getName();
    //请求队列，线程安全
    private BlockingQueue<Request<?>> mRequestQueue = new PriorityBlockingQueue<>();
    //请求的序列化生成器
    private AtomicInteger mSerialNumGenerator = new AtomicInteger(0);
    //默认的核心数
    public static int DEFAULT_CORE_NUMS = Runtime.getRuntime().availableProcessors() + 1;//获取Java虚拟机返回的可用处理器的数目
    //CPU核心数 + 1 个分发线程数
    private int mDispatcherNums = DEFAULT_CORE_NUMS;
    //NetworkExcutor，执行网络请求的线程
    private NetworkExecutor[] mDispatchers = null;
    //Http请求的真正执行者
    private HttpStack mHttpStack;

    public RequestQueue(int mDispatcherNums, HttpStack mHttpStack) {
        this.mDispatcherNums = mDispatcherNums;
        this.mHttpStack = mHttpStack;
    }

    /**
     * 启动NetworkExecutor
     */
    private final void startNetworkExecutors(){
        mDispatchers = new NetworkExecutor[mDispatcherNums];
        for (int i = 0; i < mDispatcherNums; i++) {
            mDispatchers[i] = new NetworkExecutor(mRequestQueue,mHttpStack);
            mDispatchers[i].start();
        }
    }

    public void start(){
        stop();
        startNetworkExecutors();
    }

    public void stop(){
        if (mDispatchers != null && mDispatchers.length > 0){
            for (int i = 0; i < mDispatchers.length; i++) {
                mDispatchers[i].quit();
            }
        }
    }

    /**
     * 不能重复添加请求
     */
    public void addRequest(Request<?> request){
        if (!mRequestQueue.contains(request)){
            mRequestQueue.add(request);
        }else {
            Log.d(TAG,"此请求已经存在于请求队列中");
        }
    }

    /**
     * 清空队列
     */
    public void clear(){
        mRequestQueue.clear();
    }

    public BlockingQueue<Request<?>> getAllRequests(){
        return mRequestQueue;
    }

    /**
     * 为每一个请求生成一个序列号
     */
    private int generateSerialNumber(){
        return mSerialNumGenerator.incrementAndGet();//以原子方式在当前值+1
    }
}
