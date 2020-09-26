package com.example.dildil.home_page.contract;

import com.example.dildil.abstractclass.BaseModel;
import com.example.dildil.abstractclass.BaseView;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.home_page.bean.DynamicNumBean;
import com.example.dildil.home_page.bean.VersionBean;

import io.reactivex.Observable;

public interface HomeContract {
    interface View extends BaseView {

        void onGetDynamicNumSuccess(DynamicNumBean dynamicNumBean);

        void onGetDynamicNumFail(String e);

        void onGetVersionSuccess(VersionBean versionBean);

        void onGetVersionFail(String e);

    }

    interface Model extends BaseModel {

        Observable<DynamicNumBean> getDynamicNum(int uid);

        Observable<VersionBean> getVersion(String url);

    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void getDynamicNum(int uid);

        public abstract void getVersion(String url);

    }
}
