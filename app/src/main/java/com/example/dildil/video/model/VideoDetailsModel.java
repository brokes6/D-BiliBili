package com.example.dildil.video.model;

import com.example.dildil.api.ApiEngine;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.video.bean.CoinBean;
import com.example.dildil.video.bean.CollectionBean;
import com.example.dildil.video.bean.CommentDetailBean;
import com.example.dildil.video.bean.DanmuBean;
import com.example.dildil.video.bean.SeadDanmuBean;
import com.example.dildil.video.bean.ThumbsUpBean;
import com.example.dildil.video.bean.VideoDetailsBean;
import com.example.dildil.video.bean.danmu;
import com.example.dildil.video.bean.dto;
import com.example.dildil.video.contract.VideoDetailsContract;

import io.reactivex.Observable;

public class VideoDetailsModel implements VideoDetailsContract.Model {
    @Override
    public Observable<VideoDetailsBean> getVideoDetails(int id, int uid) {
        return ApiEngine.getInstance().getApiService().getVideoDetails(id, uid);
    }

    @Override
    public Observable<CoinBean> getCoinOperated(dto bean, int uid) {
        return ApiEngine.getInstance().getApiService().coin_Operated(bean,uid);
    }

    @Override
    public Observable<ThumbsUpBean> getThumbsUp(String url, dto dto) {
        return ApiEngine.getInstance().getApiService().thumbsUp(url,dto);
    }

    @Override
    public Observable<CollectionBean> CollectionVideo(dto dto) {
        return ApiEngine.getInstance().getApiService().CollectionVideo(dto);
    }

    @Override
    public Observable<CommentDetailBean> getVideoComment(int id, int num, int size, int uid) {
        return ApiEngine.getInstance().getApiService().getVideoComment(id,num,size,uid);
    }

    @Override
    public Observable<DanmuBean> getDanMu(int value, int uid) {
        return ApiEngine.getInstance().getApiService().getDanMu(value,uid);
    }

    @Override
    public Observable<SeadDanmuBean> seadDanMu(danmu danmu, int uid) {
        return ApiEngine.getInstance().getApiService().seadDanMu(danmu);
    }

    @Override
    public Observable<RecommendVideoBean> getRelatedVideos() {
        return ApiEngine.getInstance().getApiService().randomRecommendation();
    }
}
