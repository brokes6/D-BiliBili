package com.example.dildil.home_page.fragment.fragment_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.databinding.FragmentHotBinding;
import com.example.dildil.home_page.adapter.HotRankingAdapter;

public class HotFragment extends BaseFragment {
    FragmentHotBinding binding;
    HotRankingAdapter adapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hot, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        showDialog();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new HotRankingAdapter(getContext());
        binding.HotRecy.setLayoutManager(layoutManager);
        binding.HotRecy.setAdapter(adapter);

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
        resourcesData.initHotRanking();

        hideDialog();
        adapter.loadMore(resourcesData.getHotRanking());
    }
}
