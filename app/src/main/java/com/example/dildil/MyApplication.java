package com.example.dildil;

import android.app.Application;
import android.content.Context;

import com.example.dildil.util.SharedPreferencesUtil;
import com.xuexiang.xui.BuildConfig;
import com.xuexiang.xui.XUI;



/**
 * 应用初始化
 * 初始化 XUI框架
 * 初始化 SharedPreferencesUtil
 * 需要去Android Manifest里使用
 * @author fuxinbo
 * @since 2020/04/09 10:07
 */

public class MyApplication extends Application {
    private static Context mContext;
    public static final String DATA_BASE_NAME = "MusicPlay";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        XUI.init(this);
        XUI.debug(MyApplication.isDebug());
        SharedPreferencesUtil.getInstance(this,"SPy");
    }

    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public static Context getContext() {
        return mContext;
    }


}
