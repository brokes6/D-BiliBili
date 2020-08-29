package com.example.dildil.video.contract;

import com.example.dildil.abstractclass.BaseModel;
import com.example.dildil.abstractclass.BaseView;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.video.bean.VideoDetailsBean;

import io.reactivex.Observable;


public interface VideoDetailsContract{
    interface View extends BaseView{

        void onGetVideoDetailsSuccess(VideoDetailsBean.BeanData videoDetailsBean);

        void onGetVideoDetailsFail(String e);

    }
    interface Model extends BaseModel{

        Observable<VideoDetailsBean> getVideoDetails(int id, int uid);

    }
    abstract class Presenter extends BasePresenter<View,Model>{

        public abstract void getVideoDetails(int id,int uid);

    }
}
