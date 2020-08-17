package com.example.dildil.dynamic_page.fragment_tab;

import android.os.Bundle;
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
import com.example.dildil.base.BasePresenter;
import com.example.dildil.channel_page.adapter.BeInterestedChannerAdapter;
import com.example.dildil.databinding.FragmentTabVideoBinding;
import com.example.dildil.dynamic_page.adapter.PursueAdapter;
import com.example.dildil.dynamic_page.adapter.VideoNewsAdapter;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

public class VideoTabFragment extends BaseFragment {
    private static final String TAG = "VideoTabFragment";
    FragmentTabVideoBinding binding;
    BeInterestedChannerAdapter adapter;
    PursueAdapter mPAdapter;
    VideoNewsAdapter mVAdapter;
    private LinearLayoutManager layoutManager2;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_video,container,false);
        return binding.getRoot();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
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

        binding.VTVideo.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem   = layoutManager2.findFirstVisibleItemPosition();
                lastVisibleItem = layoutManager2.findLastVisibleItemPosition();
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(VideoNewsAdapter.TAG)
                            && (position < firstVisibleItem || position > lastVisibleItem)) {

                        //如果滑出去了上面和下面就是否，和今日头条一样
                        //是否全屏
                        if(!GSYVideoManager.isFullState(getActivity())) {
                            GSYVideoManager.releaseAllVideos();
                            mVAdapter.notifyItemChanged(position);
                        }
                    }
                }
            }
        });

        initDatas();
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }

    private void initDatas(){
        ResourcesData resourcesData = new ResourcesData();
        resourcesData.initBeInterestedData();
        resourcesData.initPursue();
        resourcesData.initVideoList();
        adapter.loadMore(resourcesData.getBeInterestedBeans());
        mPAdapter.loadMore(resourcesData.getPursueBeans());
        mVAdapter.loadMore(resourcesData.getVideoNewsBeans());
    }

}
