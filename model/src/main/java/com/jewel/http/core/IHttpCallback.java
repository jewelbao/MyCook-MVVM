package com.jewel.http.core;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/04/02
 */

public interface IHttpCallback<T> {

    void onStart(int what);

    void onSuccess(int what, T data);

    void onFail(int what,String msg);

    void onError(int what,Throwable e);

    void onFinish(int what);
}
