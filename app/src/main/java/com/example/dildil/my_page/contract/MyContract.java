package com.example.dildil.my_page.contract;

import com.example.dildil.abstractclass.BaseModel;
import com.example.dildil.abstractclass.BaseView;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.home_page.bean.VersionBean;
import com.example.dildil.my_page.bean.LogoutBean;

import io.reactivex.Observable;


public interface MyContract {
    interface View extends BaseView{

        void onGetLogoutSuccess(LogoutBean logoutBean);

        void onGetLogoutFail(String e);

        void onGetVersionSuccess(VersionBean versionBean);

        void onGetVersionFail(String e);

    }
    interface Model extends BaseModel{

        Observable<LogoutBean> Logout();

        Observable<VersionBean> getVersion(String url);

    }
    abstract class Presenter extends BasePresenter<View,Model>{

        public abstract void Logout();

        public abstract void getVersion(String url);

    }
}
