package com.example.dildil.home_page.fragment.fragment_tab;

import android.graphics.Outline;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.databinding.FragmentRecommendedBinding;
import com.example.dildil.home_page.adapter.RecommendedVideoAdapter;
import com.example.dildil.home_page.other.GlideImageLoader;
import com.youth.banner.BannerConfig;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecommendedFragment extends BaseFragment {
    private static final String TAG = "RecommendedFragment";
    FragmentRecommendedBinding binding;
    private List<URL> bannerImageList = new ArrayList<>();
    private RecommendedVideoAdapter adapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recommended, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        //网格模式(并不是瀑布流模式，瀑布流模式和NestedScrollView一起使用会起冲突)
        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(),2);
        adapter = new RecommendedVideoAdapter(getContext());
        binding.ReRecy.setLayoutManager(layoutManager1);
        binding.ReRecy.setAdapter(adapter);
        initDates();
    }

    private void initDates(){
        ResourcesData resourcesData = new ResourcesData();
        resourcesData.initBanner();
        initBanner(resourcesData.getBeannerUrl());
        adapter.loadMore(resourcesData.getData());
    }

    private void initBanner(List<?> imageUrls){
        binding.ReBanner.setImageLoader(new GlideImageLoader());
        binding.ReBanner.setImages(imageUrls);
        binding.ReBanner.setDelayTime(4000);
        binding.ReBanner.setIndicatorGravity(BannerConfig.CENTER);
        binding.ReBanner.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 15);
            }
        });
        binding.ReBanner.setClipToOutline(true);
        binding.ReBanner.start();
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }
}
