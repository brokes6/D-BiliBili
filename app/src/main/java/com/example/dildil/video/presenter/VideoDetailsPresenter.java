package com.example.dildil.video.presenter;

import com.example.dildil.dynamic_page.bean.AttentionBean;
import com.example.dildil.dynamic_page.bean.params;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.video.bean.CoinBean;
import com.example.dildil.video.bean.CollectionBean;
import com.example.dildil.video.bean.CommentBean;
import com.example.dildil.video.bean.CommentDetailBean;
import com.example.dildil.video.bean.DanmuBean;
import com.example.dildil.video.bean.SeadDanmuBean;
import com.example.dildil.video.bean.ThumbsUpBean;
import com.example.dildil.video.bean.VideoDetailsBean;
import com.example.dildil.video.bean.danmu;
import com.example.dildil.video.bean.dto;
import com.example.dildil.video.contract.VideoDetailsContract;
import com.example.dildil.video.model.VideoDetailsModel;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VideoDetailsPresenter extends VideoDetailsContract.Presenter {

    @Inject
    public VideoDetailsPresenter() {

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
        mModel.getVideoDetails(id, uid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideoDetailsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VideoDetailsBean videoDetailsBean) {
                        mView.onGetVideoDetailsSuccess(videoDetailsBean.getData());
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

    @Override
    public void getCoinOperated(dto bean, int uid) {
        mModel.getCoinOperated(bean, uid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CoinBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CoinBean coinBean) {
                        mView.onGetCoinOperatedSuccess(coinBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onGetCoinOperatedFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getThumbsUp(String url, dto dto) {
        mModel.getThumbsUp(url, dto).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ThumbsUpBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ThumbsUpBean thumbsUpBean) {
                        mView.onGetThumbsUpSuccess(thumbsUpBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onGetThumbsUpFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void CollectionVideo(dto dto, int uid) {
        mModel.CollectionVideo(dto, uid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CollectionBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CollectionBean collectionBean) {
                        mView.onGetCollectionVideoSuccess(collectionBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onGetCoinOperatedFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getVideoComment(int id, int num, int size, int uid) {
        mModel.getVideoComment(id, num, size, uid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentDetailBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CommentDetailBean commentDetailBean) {
                        mView.onGetVideoCommentSuccess(commentDetailBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onGetVideoCommentFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getDanMu(int second, int vid) {
        mModel.getDanMu(second, vid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DanmuBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DanmuBean danmuBean) {
                        mView.onGetDanMuSuccess(danmuBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onGetDanMuFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void seadDanMu(danmu danmu, int uid) {
        mModel.seadDanMu(danmu, uid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SeadDanmuBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SeadDanmuBean seadDanmuBean) {
                        mView.onGetSeadDanMuSuccess(seadDanmuBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onGetSedaDanMuFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getRelatedVideos() {
        mModel.getRelatedVideos().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecommendVideoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RecommendVideoBean recommendVideoBean) {
                        mView.onGetRelatedVideosSuccess(recommendVideoBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onGetRelatedVideosFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void AddComment(dto dto, int uid) {
        mModel.AddComment(dto, uid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CommentBean commentBean) {
                        mView.onGetAddCommentSuccess(commentBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onGetAddCommentFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void Attention(params params, int uid) {
        mModel.Attention(params, uid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AttentionBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull AttentionBean attentionBean) {
                        mView.onAttentionSuccess(attentionBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onAttentionFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
