package com.example.dildil.my_page.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.customcontrollibs.ImageTopView;
import com.example.dildil.R;
import com.example.dildil.my_page.bean.RecommendationServiceBean;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

public class RecommendationServiceAdapter extends BaseRecyclerAdapter<RecommendationServiceBean> {
    private Context mContext;
    private ImageTopView image;
    public RecommendationServiceAdapter(Context context){
        mContext = context;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_recommendation_service;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, RecommendationServiceBean item) {
        image = holder.findViewById(R.id.RS_item);
        if (item!=null){
            image.setUrl(item.getUrl());
            image.setText(item.getName());
        }
    }
}
