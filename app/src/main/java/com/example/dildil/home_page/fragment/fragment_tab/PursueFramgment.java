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
import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.FramgmentPursueBinding;
import com.example.dildil.home_page.adapter.FanRecommendationAdapter;
import com.example.dildil.home_page.adapter.MyPursuitAdapter;
import com.example.dildil.home_page.bean.BannerBean;
import com.example.dildil.home_page.bean.FanRecommendationBean;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.home_page.contract.RecommendContract;
import com.example.dildil.home_page.presenter.RecommendPresenter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PursueFramgment extends BaseFragment implements RecommendContract.View {
    private FramgmentPursueBinding binding;
    private MyPursuitAdapter adapter;
    private FanRecommendationAdapter fanRecommendationAdapter;
    private List<FanRecommendationBean> fanRecommendationBeans = new ArrayList<>();
    private boolean isFirst = true;
    private ResourcesData resourcesData;

    @Inject
    RecommendPresenter mPresenter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.framgment_pursue, container, false);

        DaggerActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .activityModule(new ActivityModule(getActivity()))
                .build()
                .inject(this);
        mPresenter.attachView(this);

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
        mPresenter.findBanner();
        binding.swipe.autoRefresh();//自动刷新
    }

    @Override
    protected void initLocalData() {
        binding.main.setVisibility(View.GONE);
    }

    @Override
    protected void onRefresh() {
        binding.swipe.autoRefresh();
    }

    private void initDates() {
        if (isFirst) {
            resourcesData = new ResourcesData();
            resourcesData.initMyPursue();
            resourcesData.initFanRecommendation();
            adapter.loadMore(resourcesData.getMyPursuitBean());
            fanRecommendationAdapter.loadMore(resourcesData.getFanRecommendationBeans());
            isFirst = false;
        } else {
            //adapter.refresh(resourcesData.getMyPursuitBean());
            fanRecommendationAdapter.refresh(resourcesData.getFanRecommendationBeans());
        }
        binding.swipe.finishRefresh(true);
        binding.main.setVisibility(View.VISIBLE);
    }

    private void initBanner(List<BannerBean.BannerList> imageUrls) {
        binding.PuBanner.setIndicatorGravity(IndicatorConfig.Direction.RIGHT);
        binding.PuBanner.setBannerRound2(15);
        binding.PuBanner.setClipToOutline(true);
        binding.PuBanner.setAdapter(new BannerImageAdapter<BannerBean.BannerList>(imageUrls) {

            @Override
            public void onBindView(BannerImageHolder holder, BannerBean.BannerList data, int position, int size) {
                //图片加载自己实现
                Glide.with(holder.itemView)
                        .load(data.getImage())
                        .into(holder.imageView);
            }
        }).setIndicator(new CircleIndicator(getContext()));
        binding.PuBanner.start();
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        binding.PuBanner.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        //停止轮播
        binding.PuBanner.stop();
    }


    @Override
    public void onGetRecommendVideoSuccess(RecommendVideoBean videoBean) {

    }

    @Override
    public void onGetRecommendVideoFail(String e) {

    }

    @Override
    public void onGetRefreshRecommendVideoSuccess(RecommendVideoBean videoBean) {

    }

    @Override
    public void onGetRefreshRecommendVideoFail(String e) {

    }

    @Override
    public void onGetVideoLoadSuccess(RecommendVideoBean videoBean) {

    }

    @Override
    public void onGetVideoLoadFail(String e) {

    }

    @Override
    public void onGetBannerSuccess(BannerBean bannerBean) {
        initBanner(bannerBean.getData());
    }

    @Override
    public void onGetBannerFail(String e) {

    }

    @Override
    public void onDestroy() {
        binding.PuBanner.destroy();
        if (fanRecommendationBeans != null) {
            fanRecommendationBeans.clear();
            fanRecommendationBeans = null;
        }
        super.onDestroy();
    }
}
