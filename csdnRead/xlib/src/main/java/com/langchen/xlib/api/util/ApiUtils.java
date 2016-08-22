package com.langchen.xlib.api.util;

import com.langchen.xlib.base.BaseApp;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2016/8/18.
 */
public class ApiUtils {
    private static ApiUtils apiUtils;
    private Retrofit retrofit;

    public static ApiUtils getInstance(){
        if (apiUtils==null) {
            apiUtils = new ApiUtils();
        }
        return apiUtils;
    }

    private ApiUtils() {
        //如果服务器支持缓存 则可以这样写  否则会报错，需要自己写缓存逻辑
        Cache cache = new Cache(BaseApp.context.getCacheDir(), 1024 * 1024 * 100); //100Mb
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new CacheInterceptor())
                .connectTimeout(3000, TimeUnit.SECONDS)
                .readTimeout(2000, TimeUnit.SECONDS)
                .cache(cache)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.csdn.net/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }


}
