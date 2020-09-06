package com.example.dildil.login_page.view;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.dildil.R;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.home_page.view.HomeActivity;
import com.example.dildil.util.ScreenUtils;
import com.example.dildil.util.SharePreferenceUtil;

/**
 * 启动页
 */
public class SplashActivity extends BaseActivity {
    private static final String TAG = "SplashActivity";
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        setContentView(R.layout.activity_splash);
    }


    @Override
    protected void onResume() {
        super.onResume();
        ScreenUtils.setStatusBarColor(this, Color.parseColor("#FFFFFF"));
    }

    @Override
    protected void initData() {
        startCountDownTime();
    }

    @Override
    protected void initView() {

    }

    private void startCountDownTime() {
        countDownTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                String authToken = SharePreferenceUtil.getInstance(SplashActivity.this).getAuthToken("");
                if (TextUtils.isEmpty(authToken)) {
                    ActivityUtils.startActivity(LoginActivity.class);
//                    ActivityUtils.startActivity(HomeActivity.class);
                } else {
                    ActivityUtils.startActivity(HomeActivity.class);
                }
                SplashActivity.this.finish();
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    @Override
    public void onClick(View v) {

    }
}
