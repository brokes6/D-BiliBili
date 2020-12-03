package com.example.dildil.home_page.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;

import androidx.databinding.DataBindingUtil;

import com.example.dildil.R;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.databinding.ActivityBannerBinding;
import com.gyf.immersionbar.ImmersionBar;

public class BannerActivity extends BaseActivity {
    private ActivityBannerBinding binding;
    private int BannerType = 9;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_banner);
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(false)
                .statusBarColor(R.color.Pink)
                .init();

        Intent intent = getIntent();
        BannerType = intent.getIntExtra("type", 9);
    }

    @Override
    protected void initView() {
        setLeftTitleText("返回");
        setBackBtn(getResources().getColor(R.color.While, null));
        setTitleBG(getResources().getColor(R.color.Pink, null));
        setLeftTitleTextColorWhite();

        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setSupportZoom(true);

        setMargins(binding.Title, 0, getStatusBarHeight(this), 0, 0);
    }

    @Override
    protected void initData() {
        switch (BannerType) {
            case 0:
                binding.webView.loadUrl("https://www.bilibili.com/bangumi/play/ep368799?spm_id_from=333.851.b_7265706f7274466972737431.1");
                break;
            case 1:
                binding.webView.loadUrl("https://www.bilibili.com/bangumi/play/ep367623?spm_id_from=333.851.b_7265706f7274466972737431.2");
                break;
            case 2:
                binding.webView.loadUrl("https://www.bilibili.com/blackboard/activity-rctWymdEm.html?spm_id_from=333.851.b_7265706f7274466972737431.4");
                break;
        }
    }

    @Override
    public void onClick(View v) {

    }
}