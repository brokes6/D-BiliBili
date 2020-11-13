package com.example.dildil.login_page.presenter;

import com.example.dildil.login_page.bean.UserBean;
import com.example.dildil.login_page.bean.inputDto;
import com.example.dildil.login_page.contract.LoginContract;
import com.example.dildil.login_page.model.LoginModel;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends LoginContract.Presenter {

    @Inject
    public LoginPresenter(){

    }

    public void attachView(LoginContract.View view) {
        this.mView = view;
        this.mModel = new LoginModel();
    }

    public void detachView() {
        this.mView = null;
    }

    @Override
    public void userLogin(inputDto inputDto) {
        mModel.userLogin(inputDto).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserBean userBean) {
                        mView.onGetLoginSuccess(userBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onGetLoginFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
