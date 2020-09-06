package com.example.dildil.login_page.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.ActivityUtils;
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
            account = SharePreferenceUtil.getInstance(this).getAccountNum();
            binding.LoUserAccount.setText(account);
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
                account = binding.LoUserAccount.getText().toString().trim();
                password = binding.LoUserPassword.getText().toString().trim();
                if (InputUtil.checkMobileLegal(account) && InputUtil.checkPasswordLegal(password)) {
                    showDialog();
                    Log.d(TAG, "login账号密码 : " + account + " ," + password);
                    inputDto inputDto = new inputDto(account,password);
                    mPresenter.userLogin(inputDto);
                }
                break;
//            case R.id.register:
//            case R.id.forget_pwd:
//                XToastUtils.info(R.string.in_developing);
//                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
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
        if (e.equals("HTTP 500 ")) {
            XToastUtils.error(R.string.enter_correct_password);
        } else {
            XToastUtils.error(e);
        }
    }
}
