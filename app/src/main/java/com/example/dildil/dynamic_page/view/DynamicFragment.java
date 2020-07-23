package com.example.dildil.dynamic_page.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.dildil.R;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.channel_page.fragment_tab.ChannelTabFragment;
import com.example.dildil.channel_page.fragment_tab.PartitionTabFragment;
import com.example.dildil.databinding.FragmentDynamicBinding;
import com.example.dildil.dynamic_page.fragment_tab.SynthesizeTabFragment;
import com.example.dildil.dynamic_page.fragment_tab.VideoTabFragment;
import com.example.dildil.home_page.view.HomeActivity;

import java.util.ArrayList;

public class DynamicFragment extends BaseFragment {
    FragmentDynamicBinding binding;
    private String[] TabTitle = {"视频", "综合"};
    private ArrayList<Fragment> mFragments;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dynamic, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        mFragments = new ArrayList<>();
        mFragments.add(new VideoTabFragment());
        mFragments.add(new SynthesizeTabFragment());
    }

    @Override
    protected void initData() {
        binding.DyTab.setViewPager(binding.DyViewPager,TabTitle,getActivity(),mFragments);
        binding.DyTab.setCurrentTab(1);
        ((HomeActivity)getActivity()).ClearRedDot(2);
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {

    }
}
