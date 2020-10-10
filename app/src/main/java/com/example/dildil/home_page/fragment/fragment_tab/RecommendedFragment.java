package com.example.dildil.home_page.fragment.fragment_tab;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.FragmentRecommendedBinding;
import com.example.dildil.home_page.adapter.RecommendedVideoAdapter;
import com.example.dildil.home_page.bean.BannerBean;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.home_page.contract.RecommendContract;
import com.example.dildil.home_page.dialog.VideoChoiceDialog;
import com.example.dildil.home_page.presenter.RecommendPresenter;
import com.example.dildil.util.GsonUtil;
import com.example.dildil.util.XToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;

import javax.inject.Inject;

public class RecommendedFragment extends BaseFragment implements RecommendContract.View {
    private static final String TAG = "RecommendedFragment";
    FragmentRecommendedBinding binding;
    private RecommendedVideoAdapter adapter;
    private RecommendedVideoAdapter adapter2;
    private SkeletonScreen mSkeletonScreen;
    private SkeletonScreen mSkeletonScreeView;
    private boolean isFirst = true;

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
        VideoChoiceDialog videoChoiceDialog = new VideoChoiceDialog(getContext());
        videoChoiceDialog.setOnFeedbackClickListener(onFeedbackClickListener);

        //网格模式(并不是瀑布流模式，瀑布流模式和NestedScrollView一起使用会起冲突)
        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2);
        adapter = new RecommendedVideoAdapter(getContext(), videoChoiceDialog, 1);
        binding.ReRecy.setLayoutManager(layoutManager1);
        binding.ReRecy.setHasFixedSize(true);
        binding.ReRecy.setAdapter(adapter);

        GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 2);
        adapter2 = new RecommendedVideoAdapter(getContext(), videoChoiceDialog, 2);
        binding.ReTopRecy.setLayoutManager(layoutManager2);
        binding.ReTopRecy.setHasFixedSize(true);
        binding.ReTopRecy.setAdapter(adapter2);

        mSkeletonScreen = Skeleton.bind(binding.ReRecy)
                .adapter(adapter)//设置实际adapter
                .shimmer(true)//是否开启动画
                .angle(30)//shimmer的倾斜角度
                .frozen(false)//true则表示显示骨架屏时，RecyclerView不可滑动，否则可以滑动
                .duration(1200)//动画时间，以毫秒为单位
                .count(6)//显示骨架屏时item的个数
                .load(R.layout.item_recommendedvideo_skleton)//骨架屏UI
                .show();

        mSkeletonScreeView = Skeleton.bind(binding.ReBanner)
                .load(R.layout.item_banner_skeleton)//骨架屏UI
                .duration(1000)//动画时间，以毫秒为单位
                .shimmer(true)//是否开启动画
                .color(R.color.shimmer_color)//shimmer的颜色
                .angle(30)//shimmer的倾斜角度
                .show();

        binding.swipe.setEnableLoadMore(true);
        binding.swipe.setRefreshFooter(new BallPulseFooter(getContext()));
        binding.swipe.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Log.e(TAG, "onRefresh: 开始刷新");
                if (isFirst) {
                    mPresenter.getRandomRecommendation();
                    ResourcesData resourcesData = new ResourcesData();
                    resourcesData.initBanner();
                    initBanner(resourcesData.getBeannerUrl());
                } else {
                    mPresenter.getRefreshRecommendVideo();
                }
                isFirst = false;
            }
        });
        binding.swipe.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.LoadVideo();
            }
        });
    }

    @Override
    protected void initData() {
        binding.swipe.autoRefresh();//自动刷新
    }

    @Override
    protected void initLocalData() {
        RecommendVideoBean recommendVideoBean = GsonUtil.fromJSON(load("OfflineData"), RecommendVideoBean.class);
        adapter.loadMore(recommendVideoBean.getData());
        mSkeletonScreen.hide();
    }

    private VideoChoiceDialog.OnFeedbackClickListener onFeedbackClickListener = new VideoChoiceDialog.OnFeedbackClickListener() {
        @Override
        public void update(int position, int type) {
            switch (type) {
                case 1:
                    adapter.removeItem(position);
                    break;
                case 2:
                    adapter2.removeItem(position);
                    break;
            }

        }
    };

    private void initBanner(List<BannerBean> imageUrls) {
        binding.ReBanner.setIndicatorGravity(IndicatorConfig.Direction.RIGHT);
        binding.ReBanner.setBannerRound(15);
        binding.ReBanner.setClipToOutline(true);
        binding.ReBanner.start();
        binding.ReBanner.setAdapter(new BannerImageAdapter<BannerBean>(imageUrls) {

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
        mSkeletonScreeView.hide();
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetRecommendVideoSuccess(RecommendVideoBean videoBean) {
        adapter.loadMore(videoBean.getData());
        mSkeletonScreen.hide();
        save(GsonUtil.toJson(videoBean), "OfflineData");
        binding.swipe.finishRefresh(true);
    }

    @Override
    public void onGetRecommendVideoFail(String e) {
        mSkeletonScreen.hide();
    }

    @Override
    public void onGetRefreshRecommendVideoSuccess(RecommendVideoBean videoBean) {
        for (RecommendVideoBean.BeanData datum : videoBean.getData()) {
            adapter2.add(0, datum);
        }
        binding.ReTopRecy.scrollToPosition(0);
        binding.swipe.finishRefresh(true);
    }

    @Override
    public void onGetRefreshRecommendVideoFail(String e) {
        XToastUtils.error("出现错误:" + e);
        binding.swipe.finishRefresh(true);
    }

    @Override
    public void onGetVideoLoadSuccess(RecommendVideoBean videoBean) {
        binding.swipe.finishLoadMore(true);
        for (RecommendVideoBean.BeanData datum : videoBean.getData()) {
            adapter.add(datum);
        }
    }

    @Override
    public void onGetVideoLoadFail(String e) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
