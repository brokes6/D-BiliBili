package com.example.dildil.channel_page.model;

import com.example.dildil.api.ApiEngine;
import com.example.dildil.channel_page.bean.PartitionBean;
import com.example.dildil.channel_page.contract.PartitionContract;

import io.reactivex.Observable;

public class PartitionModel implements PartitionContract.Model {
    @Override
    public Observable<PartitionBean> getCategory() {
        return ApiEngine.getInstance().getApiService().getCategory();
    }
}
