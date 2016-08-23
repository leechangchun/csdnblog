package com.langchen.xlib.api;

import com.langchen.xlib.base.AppUtil;
import com.langchen.xlib.base.BaseApp;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2016/8/22.
 * 网络缓存拦截器
 */
public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        //当网络不可用时 从缓存中取数据
       if (!AppUtil.isNetworkReachable(BaseApp.context)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE).build();
//            UIUtils.showToastSafe("暂无网络");//子线程安全显示Toast
        }

        Response response = chain.proceed(request);
        if (AppUtil.isNetworkReachable(BaseApp.context)) {
            int maxAge = 0 * 60; // 有网络时 设置缓存超时时间0个小时
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();

        } else {
            int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        }
        return response;
    }
}
