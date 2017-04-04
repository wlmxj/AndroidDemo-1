package com.dingmouren.androiddemo.net.cache;

/**
 * Created by dingmouren on 2017/3/31.
 */

public interface Cache<K,V> {
    public V get(K key);
    public void put(K key,V value);
    public void remove(K key);
}
