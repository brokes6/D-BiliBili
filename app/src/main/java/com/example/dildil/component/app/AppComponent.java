package com.example.dildil.component.app;

import com.example.dildil.MyApplication;

import dagger.Component;

/**
 * @desc AppComponent 用于提供Application
 * @author fuxinbo
 * @date 2020/8/28
 */
@AppScope
@Component(modules = AppModule.class)
public interface AppComponent {
    MyApplication getContext(); // 提供App的Context

}
