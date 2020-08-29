package com.example.dildil.video.presenter;

import com.example.dildil.video.bean.VideoDetailsBean;
import com.example.dildil.video.contract.VideoDetailsContract;
import com.example.dildil.video.model.VideoDetailsModel;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VideoDetailsPresenter extends VideoDetailsContract.Presenter {

    @Inject
    public VideoDetailsPresenter(){

    }

    public void attachView(VideoDetailsContract.View view) {
        this.mView = view;
        this.mModel = new VideoDetailsModel();
    }

    public void detachView() {
        this.mView = null;
    }

    @Override
    public void getVideoDetails(int id, int uid) {
        mModel.getVideoDetails(id,uid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideoDetailsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VideoDetailsBean videoDetailsBean) {
                        mView.onGetVideoDetailsSuccess((VideoDetailsBean.BeanData) videoDetailsBean.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onGetVideoDetailsFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}