package com.example.dildil.dynamic_page.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.dildil.R;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.databinding.FragmentDynamicBinding;
import com.example.dildil.dynamic_page.fragment_tab.SynthesizeTabFragment;
import com.example.dildil.dynamic_page.fragment_tab.VideoTabFragment;
import com.example.dildil.home_page.view.HomeActivity;

import java.util.ArrayList;

public class DynamicFragment extends BaseFragment {
    private FragmentDynamicBinding binding;
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
        binding.DyTab.setViewPager(binding.DyViewPager, TabTitle, getActivity(), mFragments);
        binding.DyTab.setCurrentTab(0);
        ((HomeActivity) getActivity()).ClearRedDot(2);
        binding.DyViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                SwitchVideo switchVideo = new SwitchVideo(getContext());
                switchVideo.onVideoPause();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initLocalData() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser){
            VideoTabFragment.VideoSuspend();
        }
    }
}
