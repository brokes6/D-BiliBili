package com.example.dildil.home_page.presenter;

import com.example.dildil.home_page.bean.DynamicNumBean;
import com.example.dildil.home_page.contract.HomeContract;
import com.example.dildil.home_page.model.HomeModel;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter extends HomeContract.Presenter {

    @Inject
    public HomePresenter() {
    }

    public void attachView(HomeContract.View view) {
        this.mView = view;
        this.mModel = new HomeModel();
    }

    public void detachView() {
        this.mView = null;
    }

    @Override
    public void getDynamicNum(int uid) {
        mModel.getDynamicNum(uid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DynamicNumBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DynamicNumBean dynamicNumBean) {
                        mView.onGetDynamicNumSuccess(dynamicNumBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onGetDynamicNumFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
