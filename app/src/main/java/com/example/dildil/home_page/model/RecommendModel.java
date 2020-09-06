package com.example.dildil.home_page.model;

import com.example.dildil.api.ApiEngine;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.home_page.contract.RecommendContract;

import io.reactivex.Observable;

public class RecommendModel implements RecommendContract.Model {
    @Override
    public Observable<RecommendVideoBean> getRandomRecommendation() {
        return ApiEngine.getInstance().getApiService().randomRecommendation();
    }

    @Override
    public Observable<RecommendVideoBean> getRefreshRecommendVideo() {
        return ApiEngine.getInstance().getApiService().randomRecommendation();
    }
}
