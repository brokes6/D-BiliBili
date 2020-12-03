package com.example.dildil.home_page.view;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.dildil.R;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.databinding.ActivityRankingBinding;
import com.example.dildil.home_page.fragment.fragment_tab.WholeStationFragment;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

public class RankingLstActivity extends BaseActivity {
    private ActivityRankingBinding binding;
    private final String[] TabTitle = {"全站", "番剧", "国创", "国创相关", "纪录片", "动画", "音乐"};
    private ArrayList<Fragment> mFragments;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ranking);
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(false)
                .statusBarColor(R.color.Pink)
                .init();
    }

    @Override
    protected void initView() {
        setLeftTitleText("全区排行榜");
        setBackBtn(getResources().getColor(R.color.While, null));
        setTitleBG(getResources().getColor(R.color.Pink, null));
        setLeftTitleTextColorWhite();

        mFragments = new ArrayList<>();
        mFragments.add(new WholeStationFragment());

        binding.RLTab.setViewPager(binding.RLViewPager, TabTitle, this, mFragments);

        setMargins(binding.RLMain, 0, getStatusBarHeight(this), 0, 0);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
