package com.example.dildil.dynamic_page.fragment_tab;

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
import com.example.dildil.databinding.FragmentTabSynthesizeBinding;
import com.example.dildil.dynamic_page.adapter.DynamicAdapter;
import com.example.dildil.dynamic_page.adapter.TopicAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class SynthesizeTabFragment extends BaseFragment {
    FragmentTabSynthesizeBinding binding;
    private TopicAdapter adapter_topic;
    private DynamicAdapter adapter_dynamic;
    private boolean isFirst = true;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_synthesize, container, false);
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
        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2);
        adapter_topic = new TopicAdapter(getContext());
        binding.RecyTopic.setLayoutManager(layoutManager1);
        binding.RecyTopic.setAdapter(adapter_topic);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter_dynamic = new DynamicAdapter(getContext());
        binding.RecyDynamic.setLayoutManager(layoutManager);
        binding.RecyDynamic.setAdapter(adapter_dynamic);
        binding.swipe.autoRefresh();//自动刷新
    }

    @Override
    protected void initLocalData() {

    }

    @Override
    public void onClick(View v) {

    }

    private void initDatas() {
        ResourcesData resourcesData = new ResourcesData();
        resourcesData.initTopicData();
        resourcesData.initDynamic();
        if (isFirst) {
            adapter_topic.loadMore(resourcesData.getTopicData());
            adapter_dynamic.loadMore(resourcesData.getDynamicBeans());
        } else {
            adapter_topic.refresh(resourcesData.getTopicData());
            adapter_dynamic.refresh(resourcesData.getDynamicBeans());
        }
        binding.swipe.finishRefresh(true);

    }
}
