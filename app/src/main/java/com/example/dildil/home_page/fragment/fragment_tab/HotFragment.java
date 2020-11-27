package com.example.dildil.home_page.fragment.fragment_tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.FragmentHotBinding;
import com.example.dildil.home_page.adapter.HotRankingAdapter;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.home_page.contract.RecommendContract;
import com.example.dildil.home_page.presenter.RecommendPresenter;
import com.example.dildil.home_page.view.RankingLstActivity;
import com.example.dildil.video.view.VideoActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import javax.inject.Inject;

public class HotFragment extends BaseFragment implements RecommendContract.View {
    private FragmentHotBinding binding;
    private HotRankingAdapter adapter;
    private boolean isFirst = true;

    @Inject
    RecommendPresenter mPresenter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hot, container, false);
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
        adapter = new HotRankingAdapter(getContext());
        adapter.setListener(listener);
        binding.HotRecy.setLayoutManager(layoutManager);
        binding.HotRecy.setHasFixedSize(true);
        binding.HotRecy.setAdapter(adapter);

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
    }

    @Override
    protected void initData() {
        adapter.setHeaderView(LayoutInflater.from(getContext()).inflate(R.layout.item_hot_header_list, binding.HotRecy, false));

        binding.swipe.autoRefresh();//自动刷新
        setScanScroll(false);
    }

    @Override
    protected void initLocalData() {
        binding.swipe.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.Ranking_List) {
            ActivityUtils.startActivity(RankingLstActivity.class);
        }
    }

    HotRankingAdapter.ItemOnClickListener listener = new HotRankingAdapter.ItemOnClickListener() {
        @Override
        public void onClick(int position, int vid) {
            Intent intent = new Intent(getContext(), VideoActivity.class);
            intent.putExtra("id", vid);
            intent.putExtra("uid", 1);
            getContext().startActivity(intent);
        }
    };

    @Override
    public void onGetRecommendVideoSuccess(RecommendVideoBean videoBean) {
        binding.swipe.setVisibility(View.VISIBLE);
        videoBean.getData().add(0,new RecommendVideoBean.BeanData());
        adapter.loadMore(videoBean.getData());
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
        binding.swipe.finishRefresh(true);
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
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
