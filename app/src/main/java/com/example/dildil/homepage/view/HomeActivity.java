package com.example.dildil.homepage.view;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.dildil.R;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.databinding.ActivityHomeBinding;
import com.example.dildil.homepage.fragment.ChannelFragment;
import com.example.dildil.homepage.fragment.DynamicFragment;
import com.example.dildil.homepage.fragment.HomePageFragment;
import com.example.dildil.homepage.fragment.MyFragment;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {
    private static final String TAG = "HomeActivity";
    ActivityHomeBinding binding;
    private String[] tabText = {"首页", "频道", "动态", "我的"};
    private int[] normalIcon = {R.mipmap.home, R.mipmap.channel, R.mipmap.dynamic, R.mipmap.my};
    private int[] selectIcon = {R.mipmap.home_select, R.mipmap.channel_select, R.mipmap.dynamic_select, R.mipmap.my_select};
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarColor(R.color.white)
                .init();
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void initData() {
        binding.navigationBar.defaultSetting()
                .titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragmentList)
                .fragmentManager(getSupportFragmentManager())
                .smoothScroll(true)
                .build();

    }

    @Override
    protected void initView() {
        fragmentList.add(new HomePageFragment());
        fragmentList.add(new ChannelFragment());
        fragmentList.add(new DynamicFragment());
        fragmentList.add(new MyFragment());
        Log.e(TAG, "initView: ???????????"+getStatusBarHeight(this));
        setMargins(binding.navigationBar,0,getStatusBarHeight(this)*2,0,0);
    }

    @Override
    public void onClick(View view) {

    }

}