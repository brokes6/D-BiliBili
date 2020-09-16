package com.example.dildil.api;


import com.example.dildil.MyApplication;
import com.example.dildil.util.SharePreferenceUtil;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Author:fuxinbo
 * 定义拦截Cookies
 */
public class ReceivedCookiesInterceptor implements Interceptor {
    private static final String TAG = "ReceivedCookiesIntercep";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (originalResponse.header("Set-Cookie") != null) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            SharePreferenceUtil.getInstance(MyApplication.getContext()).setCookies("cookie", cookies);
        }
        return originalResponse;
    }
}