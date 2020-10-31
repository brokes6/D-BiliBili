package com.example.dildil.dynamic_page.fragment_tab;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.channel_page.adapter.BeInterestedChannerAdapter;
import com.example.dildil.databinding.FragmentTabVideoBinding;
import com.example.dildil.dynamic_page.adapter.PursueAdapter;
import com.example.dildil.dynamic_page.adapter.VideoNewsAdapter;
import com.example.dildil.util.ScrollCalculatorHelper;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;

public class VideoTabFragment extends BaseFragment {
    private FragmentTabVideoBinding binding;
    private BeInterestedChannerAdapter adapter;
    private PursueAdapter mPAdapter;
    private VideoNewsAdapter mVAdapter;
    private LinearLayoutManager layoutManager2;
    private static ScrollCalculatorHelper scrollCalculatorHelper;
    private CountDownTimer countDownTimer;
    private boolean mFull = false;
    private boolean isFirst = true;
    private ResourcesData resourcesData;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_video, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapter = new BeInterestedChannerAdapter(getContext());
        binding.VTRecentVisit.setLayoutManager(layoutManager);
        binding.VTRecentVisit.setAdapter(adapter);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mPAdapter = new PursueAdapter(getContext());
        binding.VTChasingFans.setLayoutManager(layoutManager1);
        binding.VTChasingFans.setAdapter(mPAdapter);

        layoutManager2 = new LinearLayoutManager(getContext());
        mVAdapter = new VideoNewsAdapter(getContext());
        binding.VTVideo.setLayoutManager(layoutManager2);
        binding.VTVideo.setAdapter(mVAdapter);

        binding.swipe.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initDatas();
            }
        });

        //限定范围为屏幕一半的上下偏移180
        int playTop = CommonUtil.getScreenHeight(getContext()) / 2 - CommonUtil.dip2px(getContext(), 180);
        int playBottom = CommonUtil.getScreenHeight(getContext()) / 2 + CommonUtil.dip2px(getContext(), 180);

        //自定播放帮助类
        scrollCalculatorHelper = new ScrollCalculatorHelper(R.id.video_item_player, playTop, playBottom);

        binding.VTVideo.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                scrollCalculatorHelper.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = layoutManager2.findFirstVisibleItemPosition();
                lastVisibleItem = layoutManager2.findLastVisibleItemPosition();

                //这是滑动自动播放的代码
                if (!mFull) {
                    scrollCalculatorHelper.onScroll(recyclerView, firstVisibleItem, lastVisibleItem, lastVisibleItem - firstVisibleItem);
                }
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

    private void initDatas() {
        if (isFirst) {
            resourcesData = new ResourcesData();
            resourcesData.initBeInterestedData();
            resourcesData.initPursue();
            resourcesData.initVideoList();
            adapter.loadMore(resourcesData.getBeInterestedBeans());
            mPAdapter.loadMore(resourcesData.getPursueBeans());
            mVAdapter.loadMore(resourcesData.getVideoNewsBeans());
            isFirst = false;
        } else {
            adapter.refresh(resourcesData.getBeInterestedBeans());
            mPAdapter.refresh(resourcesData.getPursueBeans());
            mVAdapter.refresh(resourcesData.getVideoNewsBeans());
        }
        binding.swipe.finishRefresh(true);
        countDownTimer = new CountDownTimer(500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                binding.VTRecentVisit.setVisibility(View.VISIBLE);
            }
        };
        countDownTimer.start();
    }

    public static void VideoSuspend() {
        GSYVideoManager gsyVideoManager = GSYVideoManager.instance();
        if (gsyVideoManager.isPlaying()) {
            gsyVideoManager.onPause();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (newConfig.orientation != ActivityInfo.SCREEN_ORIENTATION_USER) {
            mFull = false;
        } else {
            mFull = true;
        }
    }

    @Override
    public void onDestroy() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        resourcesData = null;
        super.onDestroy();
    }
}
