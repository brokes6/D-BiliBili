package com.example.dildil.login_page.view;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.dildil.R;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.databinding.ActivitySplashBinding;
import com.example.dildil.home_page.view.HomeActivity;
import com.example.dildil.util.SharedPreferencesUtil;
import com.gyf.immersionbar.ImmersionBar;

import java.util.HashSet;

/**
 * 启动页
 */
public class SplashActivity extends BaseActivity {
    private ActivitySplashBinding binding;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        ImmersionBar.with(this).transparentBar();
    }

    @Override
    protected void initView() {
        binding.Logo.setImageResource(R.mipmap.bilibili_logo);
        binding.LogoBottom.setImageResource(R.mipmap.bilibilis);
    }

    @Override
    protected void initData() {
        countDownTimer = new CountDownTimer(2500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished <= 1000) {
                    binding.Logo.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFinish() {
                HashSet<String> authTokens = SharedPreferencesUtil.getCookies("cookie");
                if (authTokens == null) {
                    ActivityUtils.startActivity(LoginActivity.class);
                } else {
                    ActivityUtils.startActivity(HomeActivity.class);
                }
                SplashActivity.this.finish();
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onStop() {
        if (isFinishing()){
            if (countDownTimer != null) {
                countDownTimer.cancel();
                countDownTimer = null;
            }
        }
        super.onStop();
    }
}
