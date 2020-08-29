package com.example.dildil.video.model;

import com.example.dildil.api.ApiEngine;
import com.example.dildil.video.bean.VideoDetailsBean;
import com.example.dildil.video.contract.VideoDetailsContract;

import io.reactivex.Observable;

public class VideoDetailsModel implements VideoDetailsContract.Model {
    @Override
    public Observable<VideoDetailsBean> getVideoDetails(int id, int uid) {
        return ApiEngine.getInstance().getApiService().getVideoDetails(id, uid);
    }
}
