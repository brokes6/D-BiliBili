package com.example.dildil.dynamic_page.presenter;

import com.example.dildil.dynamic_page.bean.AttentionDetailsBean;
import com.example.dildil.dynamic_page.bean.DynamicBean;
import com.example.dildil.dynamic_page.bean.DynamicDetailsBean;
import com.example.dildil.dynamic_page.contract.DynamicContract;
import com.example.dildil.dynamic_page.model.DynamicModel;
import com.example.dildil.video.bean.CommentBean;
import com.example.dildil.video.bean.CommentDetailBean;
import com.example.dildil.video.bean.dto;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DynamicPresenter extends DynamicContract.Presenter {

    @Inject
    public DynamicPresenter() {

    }

    public void attachView(DynamicContract.View view) {
        this.mView = view;
        this.mModel = new DynamicModel();
    }

    public void detachView() {
        this.mView = null;
    }

    @Override
    public void getDynamic(int pageNum, int pageSize, int uid) {
        mModel.getDynamic(pageNum, pageSize, uid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DynamicBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DynamicBean dynamicBean) {
                        mView.onGetDynamicSuccess(dynamicBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onGetDynamicFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getVideoDynamic(int pageNum, int pageSize, int uid) {
        mModel.getVideoDynamic(pageNum, pageSize, uid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DynamicBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull DynamicBean dynamicBean) {
                        mView.onGetVideoDynamicSuccess(dynamicBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onGetVideoDynamicFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void AddDynamicComment(dto dto, int uid) {
        mModel.AddDynamicComment(dto, uid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CommentBean commentBean) {
                        mView.onGetAddDynamicCommentSuccess(commentBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onGetAddDynamicCommentFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getDynamicComment(int id, int pageNum, int pageSize, int uid) {
        mModel.getDynamicComment(id, pageNum, pageSize, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentDetailBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CommentDetailBean commentDetailBean) {
                        mView.onGetDynamicCommentSuccess(commentDetailBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onGetDynamicCommentFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getDynamicDetails(int did, int uid) {
        mModel.getDynamicDetails(did, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DynamicDetailsBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull DynamicDetailsBean dynamicDetailsBean) {
                        mView.onGetDynamicDetailsSuccess(dynamicDetailsBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onGetDynamicDetailsFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getAttentionDetails(int uid) {
        mModel.getAttentionDetails(uid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AttentionDetailsBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull AttentionDetailsBean attentionDetailsBean) {
                        mView.onGetAttentionDetailsSuccess(attentionDetailsBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onGetAttentionDetailsFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
