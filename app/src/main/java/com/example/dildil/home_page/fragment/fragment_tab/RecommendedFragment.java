package com.example.dildil.home_page.fragment.fragment_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.dildil.home_page.dialog.VideoChoiceDialog;
import com.example.dildil.home_page.presenter.RecommendPresenter;
import com.example.dildil.home_page.view.HomeActivity;
import com.example.dildil.util.XToastUtils;
import com.next.easynavigation.view.EasyNavigationBar;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import javax.inject.Inject;

public class RecommendedFragment extends BaseFragment implements RecommendContract.View {
    private FragmentRecommendedBinding binding;
    private RecommendedVideoAdapter adapter;
    private boolean isFirst = true;
    private RecommendVideoBean.BeanData mBeanData;

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

        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2);
        binding.ReRecy.setLayoutManager(layoutManager1);
        binding.ReRecy.setHasFixedSize(true);
        adapter = new RecommendedVideoAdapter(getContext(), videoChoiceDialog, 1);
        binding.ReRecy.setAdapter(adapter);

        binding.swipe.setEnableLoadMore(true);
        binding.swipe.setRefreshFooter(new BallPulseFooter(getContext()));
        binding.swipe.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (isFirst) {
                    mPresenter.getRandomRecommendation();
                    isFirst = false;
                } else {
                    mPresenter.getRefreshRecommendVideo();
                }
            }
        });
        binding.swipe.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.LoadVideo();
            }
        });
        ((HomeActivity) getActivity()).setOnTabClickListener(onTabClickListener);
    }

    @Override
    protected void initData() {
        adapter.setHeaderView(LayoutInflater.from(getContext()).inflate(R.layout.item_recommended_header, binding.ReRecy, false));

        binding.swipe.autoRefresh();//自动刷新
        setScanScroll(false);
        ResourcesData mResourcesData = new ResourcesData();
        mResourcesData.initBanner();
        mBeanData = new RecommendVideoBean.BeanData();
        mBeanData.setBanner(true);
        mBeanData.setBannerUrl(mResourcesData.getBeannerUrl());
    }

    @Override
    protected void initLocalData() {
        binding.swipe.setVisibility(View.GONE);
    }

    private final VideoChoiceDialog.OnFeedbackClickListener onFeedbackClickListener = new VideoChoiceDialog.OnFeedbackClickListener() {
        @Override
        public void update(int position, int type) {
            adapter.removeItem(position);
        }
    };

    @Override
    public void onClick(View v) {

    }

    EasyNavigationBar.OnTabClickListener onTabClickListener = new EasyNavigationBar.OnTabClickListener() {
        @Override
        public boolean onTabSelectEvent(View view, int position) {
            return false;
        }

        @Override
        public boolean onTabReSelectEvent(View view, int position) {
            binding.swipe.autoRefresh();
            return false;
        }
    };

    @Override
    public void onGetRecommendVideoSuccess(RecommendVideoBean videoBean) {
        binding.swipe.setVisibility(View.VISIBLE);
        videoBean.addData(mBeanData);
        for (RecommendVideoBean.BeanData datum : videoBean.getData()) {
            adapter.add(datum);
        }
        binding.swipe.finishRefresh(true);
        setScanScroll(true);
    }

    @Override
    public void onGetRecommendVideoFail(String e) {
        binding.swipe.setVisibility(View.GONE);
        setScanScroll(true);
    }

    @Override
    public void onGetRefreshRecommendVideoSuccess(RecommendVideoBean videoBean) {
        for (RecommendVideoBean.BeanData datum : videoBean.getData()) {
            adapter.add(0, datum);
        }
        binding.ReRecy.scrollToPosition(0);
        binding.swipe.finishRefresh(true);
    }

    @Override
    public void onGetRefreshRecommendVideoFail(String e) {
        XToastUtils.error(R.string.errorOccurred + e);
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
        mPresenter.detachView();
        super.onDestroy();
    }
}
