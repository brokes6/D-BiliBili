package com.example.dildil.home_page.model;

import com.example.dildil.api.ApiEngine;
import com.example.dildil.home_page.bean.DynamicNumBean;
import com.example.dildil.home_page.bean.VersionBean;
import com.example.dildil.home_page.contract.HomeContract;

import io.reactivex.Observable;

public class HomeModel implements HomeContract.Model {
    @Override
    public Observable<DynamicNumBean> getDynamicNum(int uid) {
        return ApiEngine.getInstance().getApiService().getDynamicNum(uid);
    }

    @Override
    public Observable<VersionBean> getVersion(String url) {
        return ApiEngine.getInstance().getApiService().getVersion(url);
    }
}
