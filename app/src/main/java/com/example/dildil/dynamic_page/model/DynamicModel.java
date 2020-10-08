package com.example.dildil.dynamic_page.model;


import com.example.dildil.api.ApiEngine;
import com.example.dildil.dynamic_page.bean.DynamicBean;
import com.example.dildil.dynamic_page.contract.DynamicContract;

import io.reactivex.Observable;

public class DynamicModel implements DynamicContract.Model {
    @Override
    public Observable<DynamicBean> getDynamic(int pageNum, int pageSize, int uid) {
        return ApiEngine.getInstance().getApiService().getDynamic(pageNum, pageSize, uid);
    }
}
