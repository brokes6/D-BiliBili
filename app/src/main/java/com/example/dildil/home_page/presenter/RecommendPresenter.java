package com.example.dildil.home_page.presenter;

import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.home_page.contract.RecommendContract;
import com.example.dildil.home_page.model.RecommendModel;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RecommendPresenter extends RecommendContract.Presenter {

    @Inject
    public RecommendPresenter() { }

    public void attachView(RecommendContract.View view) {
        this.mView = view;
        this.mModel = new RecommendModel();
    }

    public void detachView() {
        this.mView = null;
    }

    @Override
    public void getRandomRecommendation() {
        mModel.getRandomRecommendation().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecommendVideoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RecommendVideoBean videoBean) {
                        mView.onGetRecommendVideoSuccess(videoBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onGetRecommendVideoFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getRefreshRecommendVideo() {
        mModel.getRefreshRecommendVideo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecommendVideoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RecommendVideoBean videoBean) {
                        mView.onGetRefreshRecommendVideoSuccess(videoBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onGetRefreshRecommendVideoFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
