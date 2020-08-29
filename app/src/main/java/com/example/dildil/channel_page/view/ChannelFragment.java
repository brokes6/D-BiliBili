package com.example.dildil.channel_page.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.dildil.R;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.channel_page.fragment_tab.ChannelTabFragment;
import com.example.dildil.channel_page.fragment_tab.PartitionTabFragment;
import com.example.dildil.databinding.FragmentChannelBinding;

import java.util.ArrayList;

public class ChannelFragment extends BaseFragment {
    FragmentChannelBinding binding;
    private String[] TabTitle = {"频道", "分区"};
    private ArrayList<Fragment> mFragments;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_channel, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        mFragments = new ArrayList<>();
        mFragments.add(new ChannelTabFragment());
        mFragments.add(new PartitionTabFragment());
    }

    @Override
    protected void initData() {
        binding.ChTab.setViewPager(binding.ChViewPager, TabTitle, getActivity(), mFragments);
    }


//    @Override
//    public BasePresenter onCreatePresenter() {
//        return null;
//    }

    @Override
    public void onClick(View view) {

    }
}
