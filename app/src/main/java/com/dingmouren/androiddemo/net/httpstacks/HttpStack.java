package com.dingmouren.androiddemo.net.httpstacks;

import com.dingmouren.androiddemo.net.base.Request;
import com.dingmouren.androiddemo.net.base.Response;

/**
 * Created by dingmouren on 2017/3/31.
 * 执行网络请求的接口
 */

public interface HttpStack {
    public Response performRequest(Request<?> request);
}
