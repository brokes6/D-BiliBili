package com.example.dildil.home_page.fragment.fragment_tab;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.databinding.FramgmentPursueBinding;
import com.example.dildil.home_page.adapter.MyPursuitAdapter;
import com.example.dildil.home_page.bean.BannerBean;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;

public class PursueFramgment extends BaseFragment {
    private static final String TAG = "PursueFramgment";
    FramgmentPursueBinding binding;
    private MyPursuitAdapter adapter;

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

        //设置 Header式
        binding.swipe.setRefreshHeader(new MaterialHeader(getContext()));
        //取消Footer
        binding.swipe.setEnableLoadMore(false);
        binding.swipe.setDisableContentWhenRefresh(true);

        binding.swipe.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Log.e(TAG, "onRefresh: 开始刷新");
                binding.swipe.finishRefresh(true);
            }
        });
    }

    @Override
    protected void initData() {
        ResourcesData resourcesData = new ResourcesData();
        resourcesData.initBanner();
        resourcesData.initMyPursue();
        initBanner(resourcesData.getBeannerUrl());
        adapter.loadMore(resourcesData.getMyPursuitBean());
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
