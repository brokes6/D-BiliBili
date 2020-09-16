package com.example.dildil.home_page.fragment.fragment_tab;

import android.os.Bundle;
import android.util.Log;
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
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.databinding.FramgmentPursueBinding;
import com.example.dildil.home_page.adapter.FanRecommendationAdapter;
import com.example.dildil.home_page.adapter.MyPursuitAdapter;
import com.example.dildil.home_page.bean.BannerBean;
import com.example.dildil.home_page.bean.FanRecommendationBean;
import com.example.dildil.util.GsonUtil;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

public class PursueFramgment extends BaseFragment {
    private static final String TAG = "PursueFramgment";
    FramgmentPursueBinding binding;
    private MyPursuitAdapter adapter;
    private FanRecommendationAdapter fanRecommendationAdapter;
    private SkeletonScreen mSkeletonScreen;
    private SkeletonScreen mSkeletonScreen2;
    private List<FanRecommendationBean> fanRecommendationBeans = new ArrayList<>();
    private boolean isFirst = true;

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
        mSkeletonScreen2 = Skeleton.bind(binding.PuMyPursuit)
                .adapter(adapter)//设置实际adapter
                .shimmer(true)//是否开启动画
                .angle(30)//shimmer的倾斜角度
                .frozen(false)//true则表示显示骨架屏时，RecyclerView不可滑动，否则可以滑动
                .duration(1200)//动画时间，以毫秒为单位
                .count(4)//显示骨架屏时item的个数
                .load(R.layout.item_mypursuit_skleton)//骨架屏UI
                .show();

        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2);
        fanRecommendationAdapter = new FanRecommendationAdapter(getContext());
        binding.PuFanOperaRecommendation.setLayoutManager(layoutManager1);
        binding.PuFanOperaRecommendation.setAdapter(fanRecommendationAdapter);
        mSkeletonScreen = Skeleton.bind(binding.PuFanOperaRecommendation)
                .adapter(fanRecommendationAdapter)//设置实际adapter
                .shimmer(true)//是否开启动画
                .angle(30)//shimmer的倾斜角度
                .frozen(false)//true则表示显示骨架屏时，RecyclerView不可滑动，否则可以滑动
                .duration(1200)//动画时间，以毫秒为单位
                .count(4)//显示骨架屏时item的个数
                .load(R.layout.item_fan_recommendation_skeleton)//骨架屏UI
                .show();

        binding.swipe.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Log.e(TAG, "onRefresh: 开始刷新");
                initDatas();
                isFirst = false;
            }
        });
    }

    @Override
    protected void initData() {
        binding.swipe.autoRefresh();//自动刷新
    }

    @Override
    protected void initLocalData() {
        fanRecommendationBeans.clear();
        List<FanRecommendationBean> fanRecommendationBean = GsonUtil.fromJSON(load("LocalHua"), new TypeToken<List<FanRecommendationBean>>() {}.getType());
        fanRecommendationAdapter.loadMore(fanRecommendationBean);
        mSkeletonScreen2.hide();
        mSkeletonScreen.hide();
    }

    private void initDatas() {
        ResourcesData resourcesData = new ResourcesData();
        resourcesData.initBanner();
        resourcesData.initMyPursue();
        resourcesData.initFanRecommendation();
        if (isFirst) {
            initBanner(resourcesData.getBeannerUrl());
            adapter.loadMore(resourcesData.getMyPursuitBean());
            fanRecommendationAdapter.loadMore(resourcesData.getFanRecommendationBeans());
        } else {
            adapter.refresh(resourcesData.getMyPursuitBean());
            fanRecommendationAdapter.refresh(resourcesData.getFanRecommendationBeans());
        }
        save(GsonUtil.toJson(resourcesData.getFanRecommendationBeans()), "LocalHua");
        binding.swipe.finishRefresh(true);
        mSkeletonScreen2.hide();
        mSkeletonScreen.hide();

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
}
