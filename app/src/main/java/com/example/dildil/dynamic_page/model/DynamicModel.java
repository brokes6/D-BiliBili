package com.example.dildil.dynamic_page.model;


import com.example.dildil.api.ApiEngine;
import com.example.dildil.dynamic_page.bean.DynamicBean;
import com.example.dildil.dynamic_page.contract.DynamicContract;
import com.example.dildil.video.bean.CommentBean;
import com.example.dildil.video.bean.CommentDetailBean;
import com.example.dildil.video.bean.dto;

import io.reactivex.Observable;

public class DynamicModel implements DynamicContract.Model {
    @Override
    public Observable<DynamicBean> getDynamic(int pageNum, int pageSize, int uid) {
        return ApiEngine.getInstance().getApiService().getDynamic(pageNum, pageSize, uid);
    }

    @Override
    public Observable<DynamicBean> getVideoDynamic(int pageNum, int pageSize, int uid) {
        return ApiEngine.getInstance().getApiService().getVideoDynamic(pageNum, pageSize, uid);
    }

    @Override
    public Observable<CommentBean> AddDynamicComment(dto dto, int uid) {
        return ApiEngine.getInstance().getApiService().addDynamicComment(dto, uid);
    }

    @Override
    public Observable<CommentDetailBean> getDynamicComment(int id, int pageNum, int pageSize, int uid) {
        return ApiEngine.getInstance().getApiService().getDynamicDetailComment(id, pageNum, pageSize, uid);
    }
}
