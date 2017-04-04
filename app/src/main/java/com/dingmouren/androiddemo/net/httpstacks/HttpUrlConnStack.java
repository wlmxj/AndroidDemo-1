package com.dingmouren.androiddemo.net.httpstacks;

import com.dingmouren.androiddemo.net.HttpUrlConnConfig;
import com.dingmouren.androiddemo.net.base.Request;
import com.dingmouren.androiddemo.net.base.Response;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by dingmouren on 2017/3/31.
 */

public class HttpUrlConnStack implements HttpStack {
    HttpUrlConnConfig mConfig = HttpUrlConnConfig.getConfig();
    @Override
    public Response performRequest(Request<?> request) {
        HttpURLConnection urlConnection = null;
        try {
            //构建HttpURLConnection
            urlConnection = createUrlConnection(request.getUrl());
            //设置Headers
            setRequestHeaders(urlConnection,request);
            //设置Body参数
            setRequestParams(urlConnection,request);
            //Https配置
            configHttps(request);
            return fetchResponse(urlConnection);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
        }
        return null;
    }

    /**
     * 构建HttpURLConnection
     */
    private HttpURLConnection createUrlConnection(String url) throws IOException{
        URL newURL = new URL(url);
        URLConnection urlConnection = newURL.openConnection();
        urlConnection.setConnectTimeout(mConfig.connTimeOut);
        urlConnection.setReadTimeout(mConfig.soTimeOut);
        urlConnection.setDoInput(true);//设置可以接收输入流
        urlConnection.setUseCaches(false);//忽略缓存
        return (HttpURLConnection) urlConnection;
    }

    /**
     * Https配置
     */
    private void configHttps(Request<?> request){
        if (request.isHttps()){
            SSLSocketFactory sslSocketFactory = mConfig.getmSslSocketFactory();
            if (sslSocketFactory != null){
                HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);
                HttpsURLConnection.setDefaultHostnameVerifier(mConfig.getmHostnameVerifier());
            }
        }
    }

    /**
     * 设置Header
     */
    private void setRequestHeaders(HttpURLConnection connection,Request<?> request){
        Set<String> headersKeys = request.getHeaders().keySet();
        for (String headerName : headersKeys){
            connection.addRequestProperty(headerName,request.getHeaders().get(headerName));
        }
    }


    /**
     * 设置Body参数
     */
    private void  setRequestParams(HttpURLConnection connection,Request<?> request) throws IOException {
        Request.HttpMethod method = request.getHttpMethod();
        connection.setRequestMethod(method.toString());
        //添加参数
        byte[] body = request.getBody();
        if (body != null){
            connection.setDoOutput(true);
            connection.addRequestProperty(Request.HEADER_CONTENT_TYPE,request.getBodyContentType());
            //将参数添加到connection
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.write(body);
            dataOutputStream.close();
        }
    }

    private Response fetchResponse(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        if (responseCode == -1){
            throw new IOException("Could not retrieve response code from HttpUrlConnection");
        }
        Response response = new Response();
        parseStream(connection,response);
        return response;
    }

    /**
     * 执行完请求后获取数据流，就是返回结果的结果流
     */
    private void parseStream(HttpURLConnection connection, Response response) {
        InputStream inputStream = null;
        try {
            byte[] buffer = new byte[1024];
            int len = -1;
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            inputStream = connection.getInputStream();
            while ((len = inputStream.read(buffer))!= -1){
                byteStream.write(buffer,0,len);
            }
            response.setRawData(byteStream.toByteArray());
            response.setMessage(connection.getResponseMessage());
            response.setStatusCode(connection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
            inputStream = connection.getErrorStream();
        }
    }


}
