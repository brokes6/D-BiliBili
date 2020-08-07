package com.example.dildil.home_page.fragment.fragment_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.databinding.FragmentHotBinding;
import com.example.dildil.home_page.adapter.HotRankingAdapter;
import com.example.dildil.video.view.VideoActivity;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

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
        adapter.setListener(listener);
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

    HotRankingAdapter.ItemOnClickListener listener = new HotRankingAdapter.ItemOnClickListener() {
        @Override
        public void onClick(int position) {
            GSYVideoManager gsyVideoManager = GSYVideoManager.instance();
            gsyVideoManager.onPause();
            ActivityUtils.startActivity(VideoActivity.class);
        }
    };

    private void initDatas(){
        ResourcesData resourcesData = new ResourcesData();
        resourcesData.initHotRanking();

        adapter.loadMore(resourcesData.getHotRanking());
        hideDialog();
    }
}
