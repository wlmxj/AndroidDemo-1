package com.dingmouren.androiddemo.net;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by dingmouren on 2017/3/31.
 *
 */

public class HttpUrlConnConfig {
    public String userAgent = "default";
    public int soTimeOut = 10 * 1000;
    public int connTimeOut = 10 * 1000;
    private SSLSocketFactory mSslSocketFactory = null;
    private HostnameVerifier mHostnameVerifier = null;
    private static HttpUrlConnConfig sConfig;
    private HttpUrlConnConfig(){}

    public static HttpUrlConnConfig getConfig(){
        if (sConfig == null){
            sConfig = new HttpUrlConnConfig();
        }
        return sConfig;
    }

    /**
     * 配置https请求的SSLSocketFactory与HostnameVerifier
     */
    public void setHttpsConfig(SSLSocketFactory sslSocketFactory,HostnameVerifier hostnameVerifier){
        mSslSocketFactory = sslSocketFactory;
        mHostnameVerifier = hostnameVerifier;
    }

    public SSLSocketFactory getmSslSocketFactory() {
        return mSslSocketFactory;
    }

    public HostnameVerifier getmHostnameVerifier() {
        return mHostnameVerifier;
    }
}
