package com.example.dildil.home_page.fragment.fragment_tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
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
import com.example.dildil.video.view.VideoActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import javax.inject.Inject;

public class HotFragment extends BaseFragment implements RecommendContract.View{
    FragmentHotBinding binding;
    private HotRankingAdapter adapter;
    private SkeletonScreen mSkeletonScreen;
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
        binding.HotRecy.setAdapter(adapter);
        mSkeletonScreen = Skeleton.bind(binding.HotRecy)
                .adapter(adapter)//设置实际adapter
                .shimmer(true)//是否开启动画
                .angle(30)//shimmer的倾斜角度
                .frozen(false)//true则表示显示骨架屏时，RecyclerView不可滑动，否则可以滑动
                .duration(1200)//动画时间，以毫秒为单位
                .count(6)//显示骨架屏时item的个数
                .load(R.layout.item_hot_ranking_list_skleton)//骨架屏UI
                .show();

        binding.swipe.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (isFirst) {
                    mPresenter.getRandomRecommendation();
                }else{
                    mPresenter.getRefreshRecommendVideo();
                }
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

    }

    @Override
    public void onClick(View v) {

    }

    HotRankingAdapter.ItemOnClickListener listener = new HotRankingAdapter.ItemOnClickListener() {
        @Override
        public void onClick(int position, int vid) {
            Intent intent = new Intent(getContext(),VideoActivity.class);
            intent.putExtra("id",vid);
            intent.putExtra("uid",1);
            getContext().startActivity(intent);
        }

    };


    @Override
    public void onGetRecommendVideoSuccess(RecommendVideoBean videoBean) {
        adapter.loadMore(videoBean.getData());
        binding.swipe.finishRefresh(true);
        mSkeletonScreen.hide();
    }

    @Override
    public void onGetRecommendVideoFail(String e) {
        mSkeletonScreen.hide();
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
}
