package com.example.dildil.home_page.contract;

import com.example.dildil.abstractclass.BaseModel;
import com.example.dildil.abstractclass.BaseView;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.home_page.bean.RecommendVideoBean;

import io.reactivex.Observable;


public interface RecommendContract {
    interface View extends BaseView {

        void onGetRecommendVideoSuccess(RecommendVideoBean videoBean);

        void onGetRecommendVideoFail(String e);

        void onGetRefreshRecommendVideoSuccess(RecommendVideoBean videoBean);

        void onGetRefreshRecommendVideoFail(String e);

    }
    interface Model extends BaseModel {

        Observable<RecommendVideoBean> getRandomRecommendation();

        Observable<RecommendVideoBean> getRefreshRecommendVideo();

    }
    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void getRandomRecommendation();

        public abstract void getRefreshRecommendVideo();
    }
}
