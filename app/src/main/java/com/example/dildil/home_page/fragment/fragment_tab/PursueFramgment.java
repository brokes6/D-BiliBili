 package com.example.dildil.home_page.fragment.fragment_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.databinding.FramgmentPursueBinding;
import com.example.dildil.home_page.adapter.FanRecommendationAdapter;
import com.example.dildil.home_page.adapter.MyPursuitAdapter;
import com.example.dildil.home_page.bean.BannerBean;
import com.example.dildil.home_page.bean.FanRecommendationBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

public class PursueFramgment extends BaseFragment {
    FramgmentPursueBinding binding;
    private MyPursuitAdapter adapter;
    private FanRecommendationAdapter fanRecommendationAdapter;
    private List<FanRecommendationBean> fanRecommendationBeans = new ArrayList<>();
    private boolean isFirst = true;
    private ResourcesData resourcesData;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.framgment_pursue, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapter = new MyPursuitAdapter(getContext());
        binding.PuMyPursuit.setLayoutManager(layoutManager);
        binding.PuMyPursuit.setAdapter(adapter);

        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2);
        fanRecommendationAdapter = new FanRecommendationAdapter(getContext());
        binding.PuFanOperaRecommendation.setLayoutManager(layoutManager1);
        binding.PuFanOperaRecommendation.setAdapter(fanRecommendationAdapter);

        binding.swipe.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initDates();
            }
        });
    }

    @Override
    protected void initData() {
        binding.swipe.autoRefresh();//自动刷新
    }

    @Override
    protected void initLocalData() {
        binding.main.setVisibility(View.GONE);
    }

    private void initDates() {
        if (isFirst) {
            resourcesData = new ResourcesData();
            resourcesData.initBanner();
            resourcesData.initMyPursue();
            resourcesData.initFanRecommendation();
            initBanner(resourcesData.getBeannerUrl());
            adapter.loadMore(resourcesData.getMyPursuitBean());
            fanRecommendationAdapter.loadMore(resourcesData.getFanRecommendationBeans());
            isFirst = false;
        } else {
            adapter.refresh(resourcesData.getMyPursuitBean());
            fanRecommendationAdapter.refresh(resourcesData.getFanRecommendationBeans());
        }
        binding.swipe.finishRefresh(true);
        binding.main.setVisibility(View.VISIBLE);
    }

    private void initBanner(List<BannerBean> imageUrls) {
        binding.PuBanner.setIndicatorGravity(IndicatorConfig.Direction.RIGHT);
        binding.PuBanner.setBannerRound(15);
        binding.PuBanner.setClipToOutline(true);
        binding.PuBanner.start();
        binding.PuBanner.setAdapter(new BannerImageAdapter<BannerBean>(imageUrls) {

            @Override
            public void onBindView(BannerImageHolder holder, BannerBean data, int position, int size) {
                //图片加载自己实现
                Glide.with(holder.itemView)
                        .load(data.getImageUrl())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.imageView);
            }
        })
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(getContext()));
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        if (fanRecommendationBeans!=null){
            fanRecommendationBeans.clear();
            fanRecommendationBeans = null;
        }
        super.onDestroy();
    }
}
