package com.example.dildil.video.fragment_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.databinding.FragmentIntroductionBinding;
import com.example.dildil.home_page.adapter.HotRankingAdapter;

public class IntroductionFragment extends BaseFragment {
    FragmentIntroductionBinding binding;
    HotRankingAdapter adapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_introduction,container,false);
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new HotRankingAdapter(getContext());
        binding.InRecyclerView.setLayoutManager(layoutManager);
        binding.InRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
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
        Glide.with(this).load("https://i0.hdslb.com/bfs/face/416560828f5aa755eae21b2bd7a9fa7de105fb46.jpg@45w_45h_1c_100q.webp").into(binding.InUserImg);
        binding.InUserName.setText("是仙仙醬啦");
        binding.InFans.setText(608+"粉丝");

        ResourcesData resourcesData = new ResourcesData();
        resourcesData.initHotRanking();
        adapter.loadMore(resourcesData.getHotRanking());
    }
}
