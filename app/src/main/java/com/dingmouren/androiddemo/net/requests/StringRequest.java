package com.dingmouren.androiddemo.net.requests;

import com.dingmouren.androiddemo.net.base.Request;
import com.dingmouren.androiddemo.net.base.Response;

/**
 * Created by dingmouren on 2017/4/1.
 */

public class StringRequest extends Request<String> {
    public StringRequest(HttpMethod mHttpMethod, String mUrl, RequestListener<String> mRequestListener) {
        super(mHttpMethod, mUrl, mRequestListener);
    }

    @Override
    public String parseReponse(Response response) {
        return new String(response.getRawData());
    }
}
