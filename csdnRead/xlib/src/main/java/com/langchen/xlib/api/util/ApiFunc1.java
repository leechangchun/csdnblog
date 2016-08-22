package com.langchen.xlib.api.util;

import android.util.Log;


import com.langchen.xlib.api.resp.BaseResp;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by admin on 2016/8/18.
 * 对后台返回的数据进行预处理
 */
public class ApiFunc1<T extends BaseResp> implements Func1<T, Observable<T>> {
    @Override
    public Observable<T> call(final T t) {
         return Observable.create(new Observable.OnSubscribe<T>() {
             @Override
             public void call(Subscriber<? super T> subscriber) {
                 Log.e("xx",t.toString());
                 if (t.getError_code()!=null) {
                     subscriber.onError(new ApiExecption(t.getRequest(),t.getError_code(),t.getError()));
                 } else {
                     subscriber.onNext(t);
                 }
                 subscriber.onCompleted();
             }
         });
    }
}
