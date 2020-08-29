package com.example.dildil;

import android.app.Application;
import android.content.Context;

import com.example.dildil.component.app.AppComponent;
import com.example.dildil.component.app.AppModule;
import com.example.dildil.component.app.DaggerAppComponent;
import com.example.dildil.util.SharedPreferencesUtil;
import com.xuexiang.xui.BuildConfig;
import com.xuexiang.xui.XUI;

import cn.alien95.resthttp.request.RestHttp;


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
    public static MyApplication instance;
    public static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
        XUI.init(this);
        XUI.debug(MyApplication.isDebug());
        SharedPreferencesUtil.getInstance(this,"SPy");
        RestHttp.initialize(this);
        if(BuildConfig.DEBUG){
            RestHttp.setDebug(true,"network");
        }
    }

    public static synchronized MyApplication getInstance() {
        return instance;
    }

    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .build();
        }
        return appComponent;
    }

    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public static Context getContext() {
        return mContext;
    }


}
