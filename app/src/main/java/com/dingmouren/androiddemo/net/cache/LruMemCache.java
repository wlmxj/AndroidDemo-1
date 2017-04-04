package com.dingmouren.androiddemo.net.cache;


import android.support.v4.util.LruCache;

import com.dingmouren.androiddemo.net.base.Response;

/**
 * Created by dingmouren on 2017/3/31.
 * 将请求结果缓存到内存中
 */

public class LruMemCache implements Cache<String,Response> {
    //Response缓存
    private LruCache<String,Response> mResponseCache;

    public LruMemCache() {
        //计算可使用的最大内存
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1204);
        //取八分之一可用内存作为缓存
        final int cacheSize = maxMemory / 8;
        mResponseCache = new LruCache<String, Response>(cacheSize){
            @Override
            protected int sizeOf(String key, Response value) {
                return value.rawData.length/1024;
            }
        };

    }

    @Override
    public Response get(String key) {
        return mResponseCache.get(key);
    }

    @Override
    public void put(String key, Response value) {
        mResponseCache.put(key,value);
    }

    @Override
    public void remove(String key) {
        mResponseCache.remove(key);
    }
}
