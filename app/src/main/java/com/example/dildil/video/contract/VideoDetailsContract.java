package com.example.dildil.video.contract;

import com.example.dildil.abstractclass.BaseModel;
import com.example.dildil.abstractclass.BaseView;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.video.bean.CoinBean;
import com.example.dildil.video.bean.ThumbsUpBean;
import com.example.dildil.video.bean.VideoDetailsBean;
import com.example.dildil.video.bean.dto;
import com.example.dildil.video.bean.str;

import io.reactivex.Observable;


public interface VideoDetailsContract {
    interface View extends BaseView {

        void onGetVideoDetailsSuccess(VideoDetailsBean.BeanData videoDetailsBean);

        void onGetVideoDetailsFail(String e);

        void onGetCoinOperatedSuccess(CoinBean coinBean);

        void onGetCoinOperatedFail(String e);

        void onGetThumbsUpSuccess(ThumbsUpBean thumbsUpBean);

        void onGetThumbsUpFail(String e);

    }

    interface Model extends BaseModel {

        Observable<VideoDetailsBean> getVideoDetails(int id, int uid);

        Observable<CoinBean> getCoinOperated(dto bean, int uid);

        Observable<ThumbsUpBean> getThumbsUp(String url, str str);

    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void getVideoDetails(int id, int uid);

        public abstract void getCoinOperated(dto bean, int uid);

        public abstract void getThumbsUp(String url, str str);

    }
}
