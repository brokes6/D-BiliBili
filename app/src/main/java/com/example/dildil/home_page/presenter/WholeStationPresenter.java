package com.example.dildil.home_page.presenter;

import com.example.dildil.home_page.contract.WholeStationContract;
import com.example.dildil.home_page.model.WholeStationModel;
import com.example.dildil.home_page.bean.WholeStationBean;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WholeStationPresenter extends WholeStationContract.Presenter {

    @Inject
    public WholeStationPresenter() {

    }

    public void attachView(WholeStationContract.View view) {
        this.mView = view;
        this.mModel = new WholeStationModel();
    }

    public void detachView() {
        this.mView = null;
    }

    @Override
    public void getWholeStation(int categoryId, int pageSize) {
        mModel.getWholeStation(categoryId, pageSize).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WholeStationBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull WholeStationBean bean) {
                        mView.onGetWholeStationSuccess(bean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onGetWholeStationFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
