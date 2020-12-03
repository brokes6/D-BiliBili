package com.example.dildil.my_page.view;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.dildil.R;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.databinding.ActivityVipBinding;
import com.gyf.immersionbar.ImmersionBar;

public class VipActivity extends BaseActivity {
    private ActivityVipBinding binding;
    public static final String VIP_URL = "http://vip.bilibili.com/site/vip-faq-h5.html#yv1";

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vip);
        //设置状态栏为白底黑字
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true);
    }

    @Override
    protected void initView() {
        binding.back.setOnClickListener(this);
        setMargins(binding.toolbar, 0, getStatusBarHeight(this), 0, 0);
    }

    @Override
    protected void initData() {
        binding.webView.loadUrl(VIP_URL);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        }
    }
}