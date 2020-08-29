package com.example.dildil.component.app;


import com.example.dildil.MyApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final MyApplication application;

    public AppModule(MyApplication application) {
        this.application = application;
    }

    @AppScope
    @Provides
    public MyApplication provideApplicationContext(){
        return application;
    }
}
