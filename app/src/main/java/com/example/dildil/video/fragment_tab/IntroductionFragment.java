package com.example.dildil.video.fragment_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.databinding.FragmentIntroductionBinding;
import com.example.dildil.home_page.adapter.HotRankingAdapter;
import com.example.dildil.video.view.VideoActivity;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

public class IntroductionFragment extends BaseFragment {
    FragmentIntroductionBinding binding;
    HotRankingAdapter adapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_introduction, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new HotRankingAdapter(getContext());
        adapter.setListener(listener);
        binding.InRecyclerView.setLayoutManager(layoutManager);
        binding.InRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        initDatas();
    }

//    @Override
//    public BasePresenter onCreatePresenter() {
//        return null;
//    }

    @Override
    public void onClick(View v) {

    }

    HotRankingAdapter.ItemOnClickListener listener = new HotRankingAdapter.ItemOnClickListener() {
        @Override
        public void onClick(int position) {
            VideoActivity videoActivity = (VideoActivity) getActivity();
            videoActivity.getPlayPosition();
            GSYVideoManager gsyVideoManager = GSYVideoManager.instance();
            gsyVideoManager.onPause();
            ActivityUtils.startActivity(VideoActivity.class);
        }
    };

    private void initDatas() {
        Glide.with(this).load("https://i0.hdslb.com/bfs/face/416560828f5aa755eae21b2bd7a9fa7de105fb46.jpg@45w_45h_1c_100q.webp").into(binding.InUserImg);
        binding.InUserName.setText("是仙仙醬啦");
        binding.InFans.setText(608 + "粉丝");

        ResourcesData resourcesData = new ResourcesData();
        resourcesData.initHotRanking();
        adapter.loadMore(resourcesData.getHotRanking());
    }
}
