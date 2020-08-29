package com.example.dildil.channel_page.fragment_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.channel_page.adapter.BeInterestedChannerAdapter;
import com.example.dildil.channel_page.adapter.HaveViewedAdapter;
import com.example.dildil.databinding.FragmentTabChannelBinding;

public class ChannelTabFragment extends BaseFragment {
    FragmentTabChannelBinding binding;
    BeInterestedChannerAdapter adapter;
    HaveViewedAdapter HV_adapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_channel,container,false);
        return binding.getRoot();
    }

    @Override
    protected void initView() {

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

        initDatas();

    }

//    @Override
//    public BasePresenter onCreatePresenter() {
//        return null;
//    }

    @Override
    public void onClick(View v) {

    }

    private void initDatas(){
        ResourcesData resourcesData = new ResourcesData();
        resourcesData.initBeInterestedData();
        resourcesData.initHaveViewedData();

        adapter.loadMore(resourcesData.getBeInterestedBeans());
        HV_adapter.loadMore(resourcesData.getHaveViewedBeans());
    }
}
