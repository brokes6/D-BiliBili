package com.example.dildil.home_page.fragment.fragment_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.databinding.FragmentHotBinding;
import com.example.dildil.home_page.adapter.HotRankingAdapter;
import com.example.dildil.video.view.VideoActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

public class HotFragment extends BaseFragment {
    FragmentHotBinding binding;
    private HotRankingAdapter adapter;
    private SkeletonScreen mSkeletonScreen;
    private boolean isFirst = true;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hot, container, false);
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

    }

    @Override
    public void onClick(View v) {

    }

    HotRankingAdapter.ItemOnClickListener listener = new HotRankingAdapter.ItemOnClickListener() {
        @Override
        public void onClick(int position) {
            GSYVideoManager gsyVideoManager = GSYVideoManager.instance();
            gsyVideoManager.onPause();
            ActivityUtils.startActivity(VideoActivity.class);
        }
    };

    private void initDatas() {
        ResourcesData resourcesData = new ResourcesData();
        resourcesData.initHotRanking();
        if (isFirst) {
            adapter.loadMore(resourcesData.getHotRanking());
        } else {
            adapter.refresh(resourcesData.getHotRanking());
        }
        binding.swipe.finishRefresh(true);
        mSkeletonScreen.hide();
    }
}
