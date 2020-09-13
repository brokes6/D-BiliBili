package com.example.dildil.home_page.contract;

import com.example.dildil.abstractclass.BaseModel;
import com.example.dildil.abstractclass.BaseView;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.home_page.bean.DynamicNumBean;

import io.reactivex.Observable;

public interface HomeContract {
    interface View extends BaseView {

        void onGetDynamicNumSuccess(DynamicNumBean dynamicNumBean);

        void onGetDynamicNumFail(String e);

    }

    interface Model extends BaseModel {

        Observable<DynamicNumBean> getDynamicNum(int uid);

    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void getDynamicNum(int uid);

    }
}
