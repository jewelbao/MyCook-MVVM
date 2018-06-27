package com.jewel.http.business;

import com.jewel.http.core.IHttpCallback;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/04/02
 */

public class CookCallback<T> implements IHttpCallback<T> {


    @Override
    public void onStart(int what) {

    }

    @Override
    public void onSuccess(int what, T data) {

    }

    @Override
    public void onFail(int what, String msg) {

    }

    @Override
    public void onError(int what, Throwable e) {

    }

    @Override
    public void onFinish(int what) {

    }
}
