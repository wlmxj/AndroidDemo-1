package com.dingmouren.androiddemo.net.requests;

import com.dingmouren.androiddemo.net.base.Request;
import com.dingmouren.androiddemo.net.base.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dingmouren on 2017/4/1.
 * 返回的数据类型为Json的请求，Json对应的对象类型为JSONObject
 */

public class JsonRequest extends Request<JSONObject> {
    public JsonRequest(HttpMethod mHttpMethod, String mUrl, RequestListener<JSONObject> mRequestListener) {
        super(mHttpMethod, mUrl, mRequestListener);
    }

    /**
     * 将Response的结果转换成JSONObject
     * @param response
     * @return
     */
    @Override
    public JSONObject parseReponse(Response response) {
        String jsonString = new String(response.getRawData());
        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
