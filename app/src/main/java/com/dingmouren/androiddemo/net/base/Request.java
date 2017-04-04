package com.dingmouren.androiddemo.net.base;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dingmouren on 2017/3/31.
 * 网络请求类，GET和DELETE将参数构建到url后边
 */

public abstract class Request<T> implements Comparable<Request<T>> {

    /**
     * 请求方式枚举
     */
    public static enum HttpMethod{
        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE");
        private String mHttpMethod = "";
        private HttpMethod(String method){
            mHttpMethod = method;
        }

        @Override
        public String toString() {
            return "HttpMethod{" +
                    "mHttpMethod='" + mHttpMethod + '\'' +
                    '}';
        }
    }

    /**
     * 请求优先级
     */
    public static enum Priority{
        LOW,NORMAL,HIGH,IMMEDIATE
    }

    //编码格式
    public static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    //默认的Content-type
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    //请求序列号
    public int mSerialNum = 0;
    //默认的优先级为Normal
    public Priority mPriority = Priority.NORMAL;
    //是否取消该请求
    public boolean isCancel = false;
    //该请求是否应该缓存
    private boolean mShouldCache = true;
    //请求监听器
    public RequestListener<T> mRequestListener;
    //请求url
    private String mUrl = "";
    //请求方式
    HttpMethod mHttpMethod = HttpMethod.GET;
    //请求的Header
    private Map<String ,String> mHeaders = new HashMap<>();
    //请求参数
    private Map<String,String> mBodyParams = new HashMap<>();


    public Request(HttpMethod mHttpMethod, String mUrl, RequestListener<T> mRequestListener) {
        this.mHttpMethod = mHttpMethod;
        this.mUrl = mUrl;
        this.mRequestListener = mRequestListener;
    }

    public void addHeader(String name,String value){
        mHeaders.put(name,value);
    }

    /**
     * 从原声的网络请求中解析结果
     */
    public abstract T parseReponse(Response response);

    /**
     * 处理Response 该方法运行在UI线程
     */
    public  final void deliveryResponse(Response response){
        T result = parseReponse(response);
        if (mRequestListener != null){
            int stCode = response != null ? response.getStatusCode() : -1;
            String msg = response != null ? response.getMessage() : "unkown error";
            mRequestListener.onComplete(stCode,result,msg);
        }
    }

    public String getUrl(){
        return mUrl;
    }
    public RequestListener<T> getRequestListener(){
        return mRequestListener;
    }
    public int getSerialNumber(){
        return mSerialNum;
    }
    public void setSerialNumber(int serialNum){
        this.mSerialNum = serialNum;
    }
    public Priority getPriority(){
        return mPriority;
    }
    public  void setPriority(Priority priority){
        this.mPriority = priority;
    }
    public String getParamEncoding(){
        return DEFAULT_PARAMS_ENCODING;
    }
    public String getBodyContentType(){
        return "application/x-www-form-urlencoded;charset="+getParamEncoding();
    }
    public HttpMethod getHttpMethod(){
        return mHttpMethod;
    }
    public Map<String,String> getHeaders(){
        return mHeaders;
    }
    public Map<String,String> getParams(){
        return mBodyParams;
    }
    public boolean isHttps(){
        return mUrl.startsWith("https");
    }
    public void setShouldCache(boolean shouldCache){
        this.mShouldCache = shouldCache;
    }
    public boolean shouldCache(){
        return mShouldCache;
    }
    public void cancel(){
        this.isCancel = true;
    }
    public boolean isCanceld(){
        return isCancel;
    }
    /**
     * 返回POST或者PUT请求时的Body参数字节数组
     */
    public byte[] getBody(){
        Map<String,String> params = getParams();
        if (params != null && params.size() > 0){
            return encodeParameters(params,getParamEncoding());
        }
        return null;
    }
    /**
     * 将参数转换成URL编码的参数串，格式：key1=value1&key2=value2
     */
    private byte[] encodeParameters(Map<String,String> params,String paramsEncoding){
        StringBuilder encodedParmas = new StringBuilder();
        try {
            for (Map.Entry<String,String> entry : params.entrySet()){
                encodedParmas.append(URLEncoder.encode(entry.getKey(),paramsEncoding));
                encodedParmas.append("=");
                encodedParmas.append(URLEncoder.encode(entry.getValue(),paramsEncoding));
                encodedParmas.append("&");
            }
            return encodedParmas.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException e) {
           throw new RuntimeException("Encoding not supported:"+paramsEncoding);
        }
    }

    @Override
    public int compareTo(Request<T> another) {
        Priority myPriority = this.getPriority();
        Priority anotherPriority = another.getPriority();
        //如果优先级相同，按照添加到队列的序列号的顺序执行,ordinal()此方法返回的是枚举的序数
        return myPriority.equals(anotherPriority) ? this.getSerialNumber() - another.getSerialNumber() : myPriority.ordinal() - anotherPriority.ordinal();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mHeaders == null) ? 0 : mHeaders.hashCode());
        result = prime * result + ((mHttpMethod == null) ? 0 : mHttpMethod.hashCode());
        result = prime * result + ((mBodyParams == null) ? 0 : mBodyParams.hashCode());
        result = prime * result + ((mPriority == null) ? 0 : mPriority.hashCode());
        result = prime * result + (mShouldCache ? 1231 : 1237);
        result = prime * result + ((mUrl == null) ? 0 : mUrl.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {//判断是不是同一个请求
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Request<?> other = (Request<?>) obj;
        if (mHeaders == null) {
            if (other.mHeaders != null)
                return false;
        } else if (!mHeaders.equals(other.mHeaders))
            return false;
        if (mHttpMethod != other.mHttpMethod)
            return false;
        if (mBodyParams == null) {
            if (other.mBodyParams != null)
                return false;
        } else if (!mBodyParams.equals(other.mBodyParams))
            return false;
        if (mPriority != other.mPriority)
            return false;
        if (mShouldCache != other.mShouldCache)
            return false;
        if (mUrl == null) {
            if (other.mUrl != null)
                return false;
        } else if (!mUrl.equals(other.mUrl))
            return false;
        return true;
    }

    /**
     * 网络请求的监听器
     */
    public static interface RequestListener<T>{
        public void onComplete(int stCode,T response,String errMsg);
    }
}
