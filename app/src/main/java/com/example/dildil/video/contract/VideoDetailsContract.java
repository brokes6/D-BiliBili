package com.example.dildil.video.contract;

import com.example.dildil.abstractclass.BaseModel;
import com.example.dildil.abstractclass.BaseView;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.dynamic_page.bean.AttentionBean;
import com.example.dildil.dynamic_page.bean.params;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.video.bean.CoinBean;
import com.example.dildil.video.bean.CollectionBean;
import com.example.dildil.video.bean.CommentBean;
import com.example.dildil.video.bean.CommentDetailBean;
import com.example.dildil.video.bean.DanmuBean;
import com.example.dildil.video.bean.SeadDanmuBean;
import com.example.dildil.video.bean.ThumbsUpBean;
import com.example.dildil.video.bean.VideoDetailsBean;
import com.example.dildil.video.bean.danmu;
import com.example.dildil.video.bean.dto;

import io.reactivex.Observable;


public interface VideoDetailsContract {
    interface View extends BaseView {

        void onGetVideoDetailsSuccess(VideoDetailsBean.BeanData videoDetailsBean);

        void onGetVideoDetailsFail(String e);

        void onGetCoinOperatedSuccess(CoinBean coinBean);

        void onGetCoinOperatedFail(String e);

        void onGetThumbsUpSuccess(ThumbsUpBean thumbsUpBean);

        void onGetThumbsUpFail(String e);

        void onGetCollectionVideoSuccess(CollectionBean collectionBean);

        void onGetCollectionVideoFail(String e);

        void onGetVideoCommentSuccess(CommentDetailBean commentDetailBean);

        void onGetVideoCommentFail(String e);

        void onGetDanMuSuccess(DanmuBean danmuBean);

        void onGetDanMuFail(String e);

        void onGetSeadDanMuSuccess(SeadDanmuBean seadDanmuBean);

        void onGetSedaDanMuFail(String e);

        void onGetRelatedVideosSuccess(RecommendVideoBean recommendVideoBean);

        void onGetRelatedVideosFail(String e);

        void onGetAddCommentSuccess(CommentBean commentBean);

        void onGetAddCommentFail(String e);

        void onAttentionSuccess(AttentionBean attentionBean);

        void onAttentionFail(String e);

    }

    interface Model extends BaseModel {

        Observable<VideoDetailsBean> getVideoDetails(int id, int uid);

        Observable<CoinBean> getCoinOperated(dto bean, int uid);

        Observable<ThumbsUpBean> getThumbsUp(String url, dto dto);

        Observable<CollectionBean> CollectionVideo(dto dto, int uid);

        Observable<CommentDetailBean> getVideoComment(int id, int num, int size, int uid);

        Observable<DanmuBean> getDanMu(int value, int vid);

        Observable<SeadDanmuBean> seadDanMu(danmu danmu, int uid);

        Observable<RecommendVideoBean> getRelatedVideos();

        Observable<CommentBean> AddComment(dto dto, int uid);

        Observable<AttentionBean> Attention(params params, int uid);

    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void getVideoDetails(int id, int uid);

        public abstract void getCoinOperated(dto bean, int uid);

        public abstract void getThumbsUp(String url, dto dto);

        public abstract void CollectionVideo(dto dto, int uid);

        public abstract void getVideoComment(int id, int num, int size, int uid);

        public abstract void getDanMu(int value, int vid);

        public abstract void seadDanMu(danmu danmu, int uid);

        public abstract void getRelatedVideos();

        public abstract void AddComment(dto dto, int uid);

        public abstract void Attention(params params, int uid);
    }
}
