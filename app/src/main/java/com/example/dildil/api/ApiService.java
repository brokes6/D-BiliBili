package com.example.dildil.api;

import com.example.dildil.channel_page.bean.PartitionBean;
import com.example.dildil.dynamic_page.bean.AttentionBean;
import com.example.dildil.dynamic_page.bean.AttentionDetailsBean;
import com.example.dildil.dynamic_page.bean.DynamicBean;
import com.example.dildil.dynamic_page.bean.DynamicDetailsBean;
import com.example.dildil.dynamic_page.bean.params;
import com.example.dildil.home_page.bean.BannerBean;
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

    @POST("videoservice/video/collection")
    Observable<CollectionBean> CollectionVideo(@Body dto dto, @Query("uid") int uid);

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

    @POST("commentservice/comment")
    Observable<CommentBean> addDynamicComment(@Body dto dto, @Query("uid") int uid);

    @GET("videoservice/video/top/{categoryStr}/{pageNum}/{pageSize}")
    Observable<WholeStationBean> getWholeStation(@Path("categoryStr") String categoryStr, @Path("pageNum") int pageNum, @Path("pageSize") int pageSize);

    @GET("userservice/user/details/{uid}")
    Observable<UserBean> findUserDetails(@Path("uid") int uid);

    @GET("videoservice/category/tops")
    Observable<PartitionBean> getCategory();

    @GET("commentservice/comment/list/praise/DYNAMIC/{id}/{pageNum}/{pageSize}")
    Observable<CommentDetailBean> getDynamicDetailComment(@Path("id") int id, @Path("pageNum") int pageNum, @Path("pageSize") int pageSize, @Query("uid") int uid);

    @GET("userservice/user/info/coin/{uid}/{pageNum}/{pageSize}")
    Observable<RecommendVideoBean> findHasCoinVideo(@Path("pageNum") int pageNum, @Path("pageSize") int pageSize, @Path("uid") int uid);

    @GET("userservice/user/info/publish/{uid}/{pageNum}/{pageSize}")
    Observable<RecommendVideoBean> findPublishVideo(@Path("pageNum") int pageNum, @Path("pageSize") int pageSize, @Path("uid") int uid);

    @GET("userservice/user/info/collect/{uid}/{pageNum}/{pageSize}")
    Observable<RecommendVideoBean> findCollectVideo(@Path("pageNum") int pageNum, @Path("pageSize") int pageSize, @Path("uid") int uid);

    @GET("videoservice/banner/list")
    Observable<BannerBean> findBanner();

    @GET("userservice/dynamic/{did}")
    Observable<DynamicDetailsBean> findDynamicDetails(@Path("did") int did, @Query("uid") int uid);

    @GET("userservice/follow/user")
    Observable<AttentionDetailsBean> getAttentionDetails(@Query("uid") int uid);

    @POST("userservice/dynamic_follow")
    Observable<AttentionBean> Attention(@Body params params, @Query("uid") int uid);
}
