package com.langchen.xlib.api.util;

import android.util.Log;

import rx.Observer;

/**
 * Created by admin on 2016/8/18.
 * 用于处理服务器返回的错误信息
 */
public abstract class ApiObserver<T> implements Observer<T> {
    @Override
    public void onCompleted(){

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiExecption) {
            //接口调用出错
            Log.e("xx","err"+e.toString());
        } else {
            //网络环境异常
            Log.e("xx","网络环境出错");
            e.printStackTrace();
        }
    }

    @Override
    public abstract void onNext(T t);
}