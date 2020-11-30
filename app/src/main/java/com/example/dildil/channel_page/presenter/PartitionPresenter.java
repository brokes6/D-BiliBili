package com.example.dildil.channel_page.presenter;

import com.example.dildil.channel_page.bean.PartitionBean;
import com.example.dildil.channel_page.contract.PartitionContract;
import com.example.dildil.channel_page.model.PartitionModel;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PartitionPresenter extends PartitionContract.Presenter {

    @Inject
    public PartitionPresenter() {

    }

    public void attachView(PartitionContract.View view) {
        this.mView = view;
        this.mModel = new PartitionModel();
    }

    public void detachView() {
        this.mView = null;
    }

    @Override
    public void getCategory() {
        mModel.getCategory().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PartitionBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PartitionBean partitionBean) {
                        mView.onGetCategorySuccess(partitionBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onGetCategoryFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
