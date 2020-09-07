package com.example.dildil.home_page.fragment.fragment_tab;

import android.graphics.Outline;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.FragmentRecommendedBinding;
import com.example.dildil.home_page.adapter.RecommendedVideoAdapter;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.home_page.contract.RecommendContract;
import com.example.dildil.home_page.other.GlideImageLoader;
import com.example.dildil.home_page.presenter.RecommendPresenter;
import com.example.dildil.util.XToastUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.BannerConfig;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RecommendedFragment extends BaseFragment implements RecommendContract.View {
    private static final String TAG = "RecommendedFragment";
    FragmentRecommendedBinding binding;
    private List<URL> bannerImageList = new ArrayList<>();
    private List<RecommendVideoBean.BeanData> list = new ArrayList<>();
    private RecommendedVideoAdapter adapter;

    @Inject
    RecommendPresenter mPresenter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recommended, container, false);

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
        //网格模式(并不是瀑布流模式，瀑布流模式和NestedScrollView一起使用会起冲突)
        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2);
        adapter = new RecommendedVideoAdapter(getContext());
        binding.ReRecy.setLayoutManager(layoutManager1);
        binding.ReRecy.setAdapter(adapter);

        binding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset >= 0) {
                    binding.swipe.setEnabled(true);
                } else {
                    binding.swipe.setEnabled(false);
                }
            }
        });
        //设置 Header式
        binding.swipe.setRefreshHeader(new MaterialHeader(getContext()));
        //取消Footer
        binding.swipe.setEnableLoadMore(false);
        binding.swipe.setDisableContentWhenRefresh(true);

        binding.swipe.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Log.e(TAG, "onRefresh: 开始刷新");
                mPresenter.getRefreshRecommendVideo();
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.getRandomRecommendation();
        ResourcesData resourcesData = new ResourcesData();
        resourcesData.initBanner();
        initBanner(resourcesData.getBeannerUrl());
    }

    private void initDates() {
        ResourcesData resourcesData = new ResourcesData();
        resourcesData.initBanner();
        initBanner(resourcesData.getBeannerUrl());

    }

    private void initBanner(List<?> imageUrls) {
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

//    @Override
//    public BasePresenter onCreatePresenter() {
//        return null;
//    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetRecommendVideoSuccess(RecommendVideoBean videoBean) {
        Log.e(TAG, "RecommendVideoBean有：" + videoBean.getData());
        adapter.setData(videoBean);
        adapter.loadMore(videoBean.getData());
    }

    @Override
    public void onGetRecommendVideoFail(String e) {

    }

    @Override
    public void onGetRefreshRecommendVideoSuccess(RecommendVideoBean videoBean) {
        Log.e(TAG, "onGetRefreshRecommendVideoSuccess: 刷新成功" );
        binding.ReBanner.setVisibility(View.GONE);
        adapter.refresh(videoBean.getData());
        adapter.setData(videoBean);
        binding.swipe.finishRefresh(true);
    }

    @Override
    public void onGetRefreshRecommendVideoFail(String e) {
        XToastUtils.error("出现错误:"+e);
        binding.swipe.finishRefresh(true);
    }
}
