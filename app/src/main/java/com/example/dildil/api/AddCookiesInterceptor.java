package com.example.dildil.api;

import com.example.dildil.util.SharedPreferencesUtil;

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
        //HashSet<String> preferences = SharePreferenceUtil.getInstance(MyApplication.getContext()).getCookies("cookie");
        HashSet<String> preferences = SharedPreferencesUtil.getCookies("cookie");
        if (preferences != null) {
/*            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
            }*/
            builder.addHeader("Cookie",preferences.iterator().next());
        }
        return chain.proceed(builder.build());
    }

}
