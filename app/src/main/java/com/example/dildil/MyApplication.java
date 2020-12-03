package com.example.dildil;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.commonsware.cwac.saferoom.SafeHelperFactory;
import com.example.dildil.base.AppDatabase;
import com.example.dildil.component.app.AppComponent;
import com.example.dildil.component.app.AppModule;
import com.example.dildil.component.app.DaggerAppComponent;
import com.example.dildil.util.SharedPreferencesUtil;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xuexiang.xui.XUI;


/**
 * Author:fuxinbo
 * 应用初始化
 * 初始化 XUI框架
 * 初始化 SharedPreferencesUtil
 * 初始化Dagger的AppComponent
 * 需要去Android Manifest里使用
 *
 * @author fuxinbo
 * @since 2020/04/09 10:07
 */

public class MyApplication extends Application {
    private static Context mContext;
    public static MyApplication instance;
    public static AppComponent appComponent;
    private static final char[] passphrase = {'m', 'a', 'r', 'k'};
    private static SafeHelperFactory factory;
    private static AppDatabase db;

    static {//使用static代码段可以防止内存泄漏
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
                //开始设置全局的基本参数（可以被下面的DefaultRefreshHeaderCreator覆盖）
                layout.setEnableLoadMore(false);
                layout.setDisableContentWhenRefresh(true);
                layout.setDisableContentWhenLoading(true);
            }
        });

        //全局设置默认的 Header
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                //开始设置全局的基本参数（这里设置的属性只跟下面的MaterialHeader绑定，其他Header不会生效，能覆盖DefaultRefreshInitializer的属性和Xml设置的属性）
                layout.setEnableHeaderTranslationContent(false);
                return new MaterialHeader(context).setColorSchemeResources(R.color.Pink, R.color.While, R.color.While);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
        XUI.init(this);
        SharedPreferencesUtil.getInstance(this, "SPy");
        factory = new SafeHelperFactory(passphrase);

    }

    /**
     * 获取数据库
     *
     * @return
     */
    public static AppDatabase getDatabase(Context context) {
        if (db == null) {
            synchronized (AppDatabase.class) {
                if (db == null) {
                    db = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "userData")
                            //.openHelperFactory(factory)  //encrypt
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return db;
    }

    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .build();
        }
        return appComponent;
    }

    public static Context getContext() {
        return mContext;
    }

}
