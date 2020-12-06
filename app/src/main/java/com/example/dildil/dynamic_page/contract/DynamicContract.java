package com.example.dildil.dynamic_page.contract;


import com.example.dildil.abstractclass.BaseModel;
import com.example.dildil.abstractclass.BaseView;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.dynamic_page.bean.AttentionDetailsBean;
import com.example.dildil.dynamic_page.bean.DynamicBean;
import com.example.dildil.dynamic_page.bean.DynamicDetailsBean;
import com.example.dildil.video.bean.CommentBean;
import com.example.dildil.video.bean.CommentDetailBean;
import com.example.dildil.video.bean.dto;

import io.reactivex.Observable;


public interface DynamicContract {
    interface View extends BaseView {

        void onGetDynamicSuccess(DynamicBean dynamicBean);

        void onGetDynamicFail(String e);

        void onGetVideoDynamicSuccess(DynamicBean dynamicBean);

        void onGetVideoDynamicFail(String e);

        void onGetAddDynamicCommentSuccess(CommentBean commentBean);

        void onGetAddDynamicCommentFail(String e);

        void onGetDynamicCommentSuccess(CommentDetailBean commentDetailBean);

        void onGetDynamicCommentFail(String e);

        void onGetDynamicDetailsSuccess(DynamicDetailsBean dynamicDetailsBean);

        void onGetDynamicDetailsFail(String e);

        void onGetAttentionDetailsSuccess(AttentionDetailsBean attentionDetailsBean);

        void onGetAttentionDetailsFail(String e);

    }

    interface Model extends BaseModel {

        Observable<DynamicBean> getDynamic(int pageNum, int pageSize, int uid);

        Observable<DynamicBean> getVideoDynamic(int pageNum, int pageSize, int uid);

        Observable<CommentBean> AddDynamicComment(dto dto, int uid);

        Observable<CommentDetailBean> getDynamicComment(int id, int pageNum, int pageSize, int uid);

        Observable<DynamicDetailsBean> getDynamicDetails(int did, int uid);

        Observable<AttentionDetailsBean> getAttentionDetails(int uid);

    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void getDynamic(int pageNum, int pageSize, int uid);

        public abstract void getVideoDynamic(int pageNum, int pageSize, int uid);

        public abstract void AddDynamicComment(dto dto, int uid);

        public abstract void getDynamicComment(int id, int pageNum, int pageSize, int uid);

        public abstract void getDynamicDetails(int did, int uid);

        public abstract void getAttentionDetails(int uid);

    }
}
