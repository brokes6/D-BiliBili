package com.example.dildil.component.activity;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * @desc ActivityModule
 * @author fuxinbo
 * @date 2020/8/28
 */

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}