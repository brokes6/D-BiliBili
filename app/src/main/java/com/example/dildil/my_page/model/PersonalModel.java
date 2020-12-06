package com.example.dildil.my_page.model;

import com.example.dildil.api.ApiEngine;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.login_page.bean.UserBean;
import com.example.dildil.my_page.contract.PersonalContract;

import io.reactivex.Observable;

public class PersonalModel implements PersonalContract.Model {
    @Override
    public Observable<UserBean> getFindDetails(int uid) {
        return ApiEngine.getInstance().getApiService().findUserDetails(uid);
    }

    @Override
    public Observable<RecommendVideoBean> findHasCoinVideo(int pageNum, int pageSize, int uid) {
        return ApiEngine.getInstance().getApiService().findHasCoinVideo(pageNum, pageSize, uid);
    }

    @Override
    public Observable<RecommendVideoBean> findPublishVideo(int pageNum, int pageSize, int uid) {
        return ApiEngine.getInstance().getApiService().findPublishVideo(pageNum, pageSize, uid);
    }
}
