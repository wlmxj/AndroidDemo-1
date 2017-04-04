package com.dingmouren.androiddemo.net.base;

/**
 * Created by dingmouren on 2017/3/31.
 */

public class Response  {
    private int statusCode ;
    private String message ;
    public byte[] rawData = new byte[0];



    public byte[] getRawData() {
        return rawData;
    }

    public void setRawData(byte[] rawData) {
        this.rawData = rawData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
