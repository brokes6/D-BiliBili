package com.example.dildil.component.activity;

import android.app.Activity;
import com.example.dildil.component.app.AppComponent;
import com.example.dildil.home_page.fragment.fragment_tab.RecommendedFragment;
import com.example.dildil.home_page.view.HomeActivity;
import com.example.dildil.login_page.view.LoginActivity;
import com.example.dildil.video.view.VideoActivity;

import dagger.Component;

/**
 * @desc ActivityComponent 用于管理需要进行依赖注入的Activity
 * @author fuxinbo
 * @date 2020/8/28
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(HomeActivity mainActivity);//用于注入HomeActivity

    void inject(LoginActivity loginActivity);//用于注入LoginActivity

    void inject(RecommendedFragment fragment);//用于注入LoginActivity

    void inject(VideoActivity activity);//用于注入LoginActivity
    //TODO 后续需要注入的Activity类都可以在这里添加 中像上面一样写就好了
}