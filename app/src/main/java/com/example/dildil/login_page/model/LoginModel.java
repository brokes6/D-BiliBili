package com.example.dildil.login_page.model;

import com.example.dildil.api.ApiEngine;
import com.example.dildil.login_page.bean.LoginBean;
import com.example.dildil.login_page.bean.inputDto;
import com.example.dildil.login_page.contract.LoginContract;

import io.reactivex.Observable;

public class LoginModel implements LoginContract.Mode1 {
    @Override
    public Observable<LoginBean> userLogin(inputDto inputDto) {
        return ApiEngine.getInstance().getApiService().Login(inputDto);
    }
}
