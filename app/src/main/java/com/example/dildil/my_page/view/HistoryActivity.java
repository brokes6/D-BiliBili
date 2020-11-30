package com.example.dildil.my_page.view;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.dildil.R;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.databinding.ActivityHistoryBinding;
import com.gyf.immersionbar.ImmersionBar;

public class HistoryActivity extends BaseActivity {
    private ActivityHistoryBinding binding;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_history);

        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(false)
                .statusBarColor(R.color.Pink)
                .init();
    }

    @Override
    protected void initView() {
        setLeftTitleText("播放历史");
        setBackBtn(getResources().getColor(R.color.While, null));
        setTitleBG(getResources().getColor(R.color.Pink, null));
        setLeftTitleTextColorWhite();
        setMargins(binding.Title, 0, getStatusBarHeight(this), 0, 0);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}