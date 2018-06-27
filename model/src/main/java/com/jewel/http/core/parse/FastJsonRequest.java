package com.jewel.http.core.parse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jewel.model.BaseData;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.StringRequest;

import java.lang.reflect.Type;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/04/02
 */

public class FastJsonRequest<T> extends Request<BaseData<T>> {

    private Class<T> clz;
    private final Type type ;

    public FastJsonRequest(String url, Class<T> clz) {
        this(url, RequestMethod.GET, clz);
    }

    public FastJsonRequest(String url, RequestMethod requestMethod, Class<T> clz) {
        super(url, requestMethod);
        setAccept(Headers.HEAD_VALUE_CONTENT_TYPE_JSON);
        type = new TypeReference<BaseData<T>>(clz){}.getType();
    }

    @Override
    public BaseData<T> parseResponse(Headers responseHeaders, byte[] responseBody) {
        String result = StringRequest.parseResponseString(responseHeaders, responseBody);
        return JSON.parseObject(result, type);
    }
}
