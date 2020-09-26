package com.example.dildil.api;

import android.util.Log;

import com.example.dildil.MyApplication;
import com.example.dildil.util.SharePreferenceUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author:fuxinbo
 * 请求时添加Cookies
 */
public class AddCookiesInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> preferences = SharePreferenceUtil.getInstance(MyApplication.getContext()).getCookies("cookie");
        if (preferences != null) {
            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
                Log.v("why", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
            }
        }
        return chain.proceed(builder.build());
    }

}
