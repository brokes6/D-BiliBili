package com.example.dildil.home_page.contract;

import com.example.dildil.abstractclass.BaseModel;
import com.example.dildil.abstractclass.BaseView;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.home_page.bean.WholeStationBean;

import io.reactivex.Observable;


public interface WholeStationContract {

    interface View extends BaseView {

        void onGetWholeStationSuccess(WholeStationBean bean);

        void onGetWholeStationFail(String e);

    }

    interface Model extends BaseModel {

        Observable<WholeStationBean> getWholeStation(String categoryStr, int pageNum, int pageSize);
    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void getWholeStation(String categoryStr, int pageNum, int pageSize);

    }
}
