package com.example.dildil.home_page.model;

import com.example.dildil.api.ApiEngine;
import com.example.dildil.home_page.contract.WholeStationContract;
import com.example.dildil.home_page.bean.WholeStationBean;

import io.reactivex.Observable;

public class WholeStationModel implements WholeStationContract.Model {
    @Override
    public Observable<WholeStationBean> getWholeStation(int categoryId, int pageSize) {
        return ApiEngine.getInstance().getApiService().getWholeStation(categoryId,pageSize);
    }
}
