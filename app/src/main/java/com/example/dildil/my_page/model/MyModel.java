package com.example.dildil.my_page.model;

import com.example.dildil.api.ApiEngine;
import com.example.dildil.my_page.bean.LogoutBean;
import com.example.dildil.my_page.contract.MyContract;

import io.reactivex.Observable;

public class MyModel implements MyContract.Model {
    @Override
    public Observable<LogoutBean> Logout() {
        return ApiEngine.getInstance().getApiService().Logout();
    }
}
