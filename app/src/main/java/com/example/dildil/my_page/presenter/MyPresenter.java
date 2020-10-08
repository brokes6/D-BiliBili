package com.example.dildil.my_page.presenter;

import com.example.dildil.home_page.bean.VersionBean;
import com.example.dildil.my_page.bean.LogoutBean;
import com.example.dildil.my_page.contract.MyContract;
import com.example.dildil.my_page.model.MyModel;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyPresenter extends MyContract.Presenter {

    @Inject
    public MyPresenter(){

    }

    public void attachView(MyContract.View view) {
        this.mView = view;
        this.mModel = new MyModel();
    }

    public void detachView() {
        this.mView = null;
    }

    @Override
    public void Logout() {
        mModel.Logout().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LogoutBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LogoutBean logoutBean) {
                        mView.onGetLogoutSuccess(logoutBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onGetLogoutFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getVersion(String url) {
        mModel.getVersion(url).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VersionBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VersionBean versionBean) {
                        mView.onGetVersionSuccess(versionBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onGetVersionFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
