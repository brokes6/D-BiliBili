package com.example.dildil.channel_page.fragment_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.channel_page.adapter.BeInterestedChannerAdapter;
import com.example.dildil.channel_page.adapter.HaveViewedAdapter;
import com.example.dildil.databinding.FragmentTabChannelBinding;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class ChannelTabFragment extends BaseFragment {
    private FragmentTabChannelBinding binding;
    private BeInterestedChannerAdapter adapter;
    private HaveViewedAdapter HV_adapter;
    private boolean isFirst = true;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_channel,container,false);
        return binding.getRoot();
    }

    @Override
    protected void initView() {
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
        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(),5);
        adapter = new BeInterestedChannerAdapter(getContext());
        binding.ChBeInterested.setLayoutManager(layoutManager1);
        binding.ChBeInterested.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        HV_adapter = new HaveViewedAdapter(getContext());
        binding.CHHaveViewed.setLayoutManager(layoutManager);
        binding.CHHaveViewed.setAdapter(HV_adapter);

        binding.swipe.autoRefresh();//自动刷新

    }

    @Override
    protected void initLocalData() {

    }

    @Override
    public void onClick(View v) {

    }

    private void initDatas(){
        ResourcesData resourcesData = new ResourcesData();
        resourcesData.initBeInterestedData();
        resourcesData.initHaveViewedData();
        if (isFirst){
            adapter.loadMore(resourcesData.getBeInterestedBeans());
            HV_adapter.loadMore(resourcesData.getHaveViewedBeans());
        }else{
            adapter.refresh(resourcesData.getBeInterestedBeans());
            HV_adapter.refresh(resourcesData.getHaveViewedBeans());
        }
        binding.swipe.finishRefresh(true);
    }
}
