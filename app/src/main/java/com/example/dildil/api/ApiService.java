package com.example.dildil.api;

import com.example.dildil.dynamic_page.bean.DynamicBean;
import com.example.dildil.home_page.bean.DynamicNumBean;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.home_page.bean.VersionBean;
import com.example.dildil.home_page.bean.WholeStationBean;
import com.example.dildil.login_page.bean.RegisterBean;
import com.example.dildil.login_page.bean.UserBean;
import com.example.dildil.login_page.bean.inputDto;
import com.example.dildil.my_page.bean.LogoutBean;
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

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Author:fuxinbo
 * 网络接口
 */

public interface ApiService {

    String BASE_URL = "http://116.196.105.203:6380";

    @GET("videoservice/category/random/all")
    Observable<RecommendVideoBean> randomRecommendation();

    @GET("videoservice/video/{id}")
    Observable<VideoDetailsBean> getVideoDetails(@Path("id") int id, @Query("userId") int uid);

    @POST("videoservice/video/coin")
    Observable<CoinBean> coin_Operated(@Body dto coinBean, @Query("uid") int id);

    @POST()
    Observable<ThumbsUpBean> thumbsUp(@Url String url, @Body dto dto);

    @POST("userservice/login")
    Observable<UserBean> Login(@Body inputDto inputDto);

    @POST("userservice/user")
    Observable<RegisterBean> Register(@Body inputDto inputDto);

    @POST("videoservice/video/dynamic_like")
    Observable<CollectionBean> CollectionVideo(@Body dto dto);

    @GET("commentservice/comment/list/praise/VIDEO/{id}/{pageNum}/{pageSize}")
    Observable<CommentDetailBean> getVideoComment(@Path("id") int vid, @Path("pageNum") int num, @Path("pageSize") int size, @Query("uid") int uid);

    @GET("userservice/logout")
    Observable<LogoutBean> Logout();

    @GET("commentservice/danmu/{vid}/{second}")
    Observable<DanmuBean> getDanMu(@Path("second") int second, @Path("vid") int vid);

    @POST("commentservice/danmu")
    Observable<SeadDanmuBean> seadDanMu(@Body danmu danmu);

    @GET("userservice/dynamic/unread")
    Observable<DynamicNumBean> getDynamicNum(@Query("uid") int uid);

    @GET
    Observable<VersionBean> getVersion(@Url String url);

    @GET("userservice/dynamic/list/{pageNum}/{pageSize}")
    Observable<DynamicBean> getDynamic(@Path("pageNum") int pageNum, @Path("pageSize") int pageSize, @Query("uid") int uid);

    @GET("userservice/dynamic/list/video/{pageNum}/{pageSize}")
    Observable<DynamicBean> getVideoDynamic(@Path("pageNum") int pageNum, @Path("pageSize") int pageSize, @Query("uid") int uid);

    @POST("commentservice/comment")
    Observable<CommentBean> addComment(@Body dto dto, @Query("uid") int uid);

    @GET("videoservice/video/top/{categoryId}/{pageSize}")
    Observable<WholeStationBean> getWholeStation(@Path("categoryId") int categoryId, @Path("pageSize") int pageSize);

    @GET("userservice/user/details/{uid}")
    Observable<UserBean> findUserDetails(@Path("uid") int uid);
}
