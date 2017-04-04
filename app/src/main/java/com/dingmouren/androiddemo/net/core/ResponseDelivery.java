package com.dingmouren.androiddemo.net.core;

import android.os.Handler;
import android.os.Looper;

import com.dingmouren.androiddemo.net.base.Request;
import com.dingmouren.androiddemo.net.base.Response;

import java.util.concurrent.Executor;

/**
 * Created by dingmouren on 2017/3/31.
 * 请求结果投递类，将请求结果投递到UI线程
 */

public class ResponseDelivery implements Executor {
    //主线程的handler
    public Handler mResponseHandler = new Handler(Looper.getMainLooper());

    /**
     * 处理请求结果，将其执行在UI线程
     */
    public void deliveryResponse(final Request<?> request, final Response response){
        Runnable respRunnable = new Runnable() {
            @Override
            public void run() {
                request.deliveryResponse(response);
            }
        };
        execute(respRunnable);
    }
    @Override
    public void execute(Runnable command) {
        mResponseHandler.post(command);
    }
}
