package com.example.dildil.api;

import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.video.bean.VideoDetailsBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    String BASE_URL = "http://116.196.105.203:8081";

//    @GET("login/cellphone")
//    Observable<LoginBean> login(@Query("phone") String phone, @Query("password") String password);


    @GET("category/random/all")
    Observable<RecommendVideoBean> randomRecommendation();

    @GET("video/{id}")
    Observable<VideoDetailsBean> getVideoDetails(@Path("id") int id, @Query("userId") int uid);
}
