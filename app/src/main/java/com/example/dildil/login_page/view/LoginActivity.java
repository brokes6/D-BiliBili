package com.example.dildil.login_page.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.dildil.R;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.databinding.ActivityLoginBinding;
import com.example.dildil.home_page.view.HomeActivity;
import com.example.dildil.util.InputUtil;
import com.example.dildil.util.SharePreferenceUtil;
import com.gyf.immersionbar.ImmersionBar;

/**
 * 登录界面
 * 只能手机号登录
 */
public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    ActivityLoginBinding binding;
    String phoneNumber;
    String password;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }

    @Override
    protected void initView() {
        binding.LoLogin.setOnClickListener(this);
        setMargins(binding.top1,0,getStatusBarHeight(this),0,0);
//        SettingSPUtils spUtils = new SettingSPUtils(LoginActivity.this);
//        if (!spUtils.isAgreePrivacy()) {
//            PrivacyUtils.showPrivacyDialog(this, (dialog, which) -> {
//                dialog.dismiss();
//                spUtils.setIsAgreePrivacy(true);
//            });
//        }
    }

    @Override
    protected void initData() {
        if (!TextUtils.isEmpty(SharePreferenceUtil.getInstance(this).getAccountNum())) {
            phoneNumber = SharePreferenceUtil.getInstance(this).getAccountNum();
            binding.LoUserAccount.setText(phoneNumber);
        }
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        //设置状态栏为白底黑字
        ImmersionBar.with(LoginActivity.this)
                .statusBarColor(R.color.White)
                .statusBarDarkFont(true);
    }

    @Override
    public void onClick(View v) {
//        if (ClickUtil.isFastClick(1000, v)) {
//            return;
//        }
        switch (v.getId()) {
            case R.id.Lo_login:
                phoneNumber = binding.LoUserAccount.getText().toString().trim();
                password = binding.LoUserPassword.getText().toString().trim();
                if (InputUtil.checkMobileLegal(phoneNumber) && InputUtil.checkPasswordLegal(password)) {
                    showDialog();
                    Log.d(TAG, "login账号密码 : " + phoneNumber + " ," + password);
//                    mPresenter.login(phoneNumber, password);
                    ActivityUtils.startActivity(HomeActivity.class);
                }
                break;
//            case R.id.register:
//            case R.id.forget_pwd:
//                XToastUtils.info(R.string.in_developing);
//                break;
        }
    }
}
