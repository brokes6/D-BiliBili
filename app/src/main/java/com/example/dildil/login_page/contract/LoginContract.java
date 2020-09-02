package com.example.dildil.login_page.contract;

import com.example.dildil.abstractclass.BaseModel;
import com.example.dildil.abstractclass.BaseView;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.login_page.bean.LoginBean;
import com.example.dildil.login_page.bean.inputDto;

import io.reactivex.Observable;


public interface LoginContract {
    interface View extends BaseView{

        void onGetLoginSuccess(LoginBean loginBean);

        void onGetLoginFail(String e);

    }
    interface Mode1 extends BaseModel{

        Observable<LoginBean> userLogin(inputDto inputDto);

    }
    abstract class Presenter extends BasePresenter<View,Mode1>{

        public abstract void userLogin(inputDto inputDto);

    }
}
