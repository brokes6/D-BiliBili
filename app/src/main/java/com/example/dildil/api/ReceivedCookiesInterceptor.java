package com.example.dildil.api;


import com.example.dildil.util.SharedPreferencesUtil;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Author:fuxinbo
 * 定义拦截Cookies
 */
public class ReceivedCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (originalResponse.header("Set-Cookie") != null) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            SharedPreferencesUtil.saveHashSet("cookie",cookies);
            //SharePreferenceUtil.getInstance(MyApplication.getContext()).setCookies("cookie", cookies);
        }
        return originalResponse;
    }
}