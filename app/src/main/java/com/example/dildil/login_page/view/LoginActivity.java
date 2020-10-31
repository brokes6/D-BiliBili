package com.example.dildil.login_page.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.ActivityLoginBinding;
import com.example.dildil.home_page.view.HomeActivity;
import com.example.dildil.login_page.bean.LoginBean;
import com.example.dildil.login_page.bean.inputDto;
import com.example.dildil.login_page.contract.LoginContract;
import com.example.dildil.login_page.presenter.LoginPresenter;
import com.example.dildil.util.InputUtil;
import com.example.dildil.util.SharePreferenceUtil;
import com.example.dildil.util.XToastUtils;
import com.gyf.immersionbar.ImmersionBar;

import javax.inject.Inject;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {
    private static final String TAG = "LoginActivity";
    ActivityLoginBinding binding;
    private String account;
    private String password;

    @Inject
    LoginPresenter mPresenter;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        //设置状态栏为白底黑字
        ImmersionBar.with(LoginActivity.this)
                .statusBarColor(R.color.White)
                .statusBarDarkFont(true);

        DaggerActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build().inject(this);
        mPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        binding.LoLogin.setOnClickListener(this);
        setMargins(binding.top1,0,getStatusBarHeight(this),0,0);
    }

    @Override
    protected void initData() {
        if (!TextUtils.isEmpty(SharePreferenceUtil.getInstance(this).getAccountNum())) {
            account = SharePreferenceUtil.getInstance(this).getAccountNum();
            binding.LoUserAccount.setText(account);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Lo_login:
                account = binding.LoUserAccount.getText().toString().trim();
                password = binding.LoUserPassword.getText().toString().trim();
                if (InputUtil.checkMobileLegal(account) && InputUtil.checkPasswordLegal(password)) {
                    showDialog();
                    inputDto inputDto = new inputDto(account,password);
                    mPresenter.userLogin(inputDto);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        AppUtils.exitApp();
    }

    @Override
    public void onGetLoginSuccess(LoginBean loginBean) {
        SharePreferenceUtil.getInstance(this).saveUserInfo(loginBean, account);
        hideDialog();
        XToastUtils.success("登录成功!");
        ActivityUtils.startActivity(HomeActivity.class);
        this.finish();
    }

    @Override
    public void onGetLoginFail(String e) {
        hideDialog();
        if (e.equals("HTTP 500")) {
            XToastUtils.error(R.string.enter_correct_password);
        } else {
            XToastUtils.error(e);
            Log.e(TAG, R.string.errorOccurred+e );
        }
    }
}
