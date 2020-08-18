package com.example.dildil.dynamic_page.fragment_tab;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.databinding.FragmentTabSynthesizeBinding;
import com.example.dildil.dynamic_page.adapter.DynamicAdapter;
import com.example.dildil.dynamic_page.adapter.TopicAdapter;

public class SynthesizeTabFragment extends BaseFragment {
    private static final String TAG = "SynthesizeTabFragment";
    FragmentTabSynthesizeBinding binding;
    private TopicAdapter adapter_topic;
    private DynamicAdapter adapter_dynamic;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_synthesize,container,false);
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        Log.e(TAG, "SynthesizeTabFragment: 初始化View已运行" );
    }

    @Override
    protected void initData() {
        Log.e(TAG, "VideoTabFragment: 初始化Data已运行" );
        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(),2);
        adapter_topic = new TopicAdapter(getContext());
        binding.RecyTopic.setLayoutManager(layoutManager1);
        binding.RecyTopic.setAdapter(adapter_topic);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter_dynamic = new DynamicAdapter(getContext());
        binding.RecyDynamic.setLayoutManager(layoutManager);
        binding.RecyDynamic.setAdapter(adapter_dynamic);
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
        resourcesData.initTopicData();
        resourcesData.initDynamic();

        adapter_topic.loadMore(resourcesData.getTopicData());
        adapter_dynamic.loadMore(resourcesData.getDynamicBeans());
    }
}
