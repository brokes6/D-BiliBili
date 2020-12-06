package com.example.dildil.home_page.contract;

import com.example.dildil.abstractclass.BaseModel;
import com.example.dildil.abstractclass.BaseView;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.home_page.bean.BannerBean;
import com.example.dildil.home_page.bean.RecommendVideoBean;

import io.reactivex.Observable;


public interface RecommendContract {
    interface View extends BaseView {

        void onGetRecommendVideoSuccess(RecommendVideoBean videoBean);

        void onGetRecommendVideoFail(String e);

        void onGetRefreshRecommendVideoSuccess(RecommendVideoBean videoBean);

        void onGetRefreshRecommendVideoFail(String e);

        void onGetVideoLoadSuccess(RecommendVideoBean videoBean);

        void onGetVideoLoadFail(String e);

        void onGetBannerSuccess(BannerBean bannerBean);

        void onGetBannerFail(String e);

    }

    interface Model extends BaseModel {

        Observable<RecommendVideoBean> getRandomRecommendation();

        Observable<RecommendVideoBean> getRefreshRecommendVideo();

        Observable<RecommendVideoBean> LoadVideo();

        Observable<BannerBean> findBanner();

    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void getRandomRecommendation();

        public abstract void getRefreshRecommendVideo();

        public abstract void LoadVideo();

        public abstract void findBanner();
    }
}
