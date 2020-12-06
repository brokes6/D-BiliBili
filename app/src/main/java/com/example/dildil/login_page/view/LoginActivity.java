package com.example.dildil.login_page.view;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.base.UserDaoOperation;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.ActivityLoginBinding;
import com.example.dildil.home_page.view.HomeActivity;
import com.example.dildil.login_page.bean.UserBean;
import com.example.dildil.login_page.bean.inputDto;
import com.example.dildil.login_page.contract.LoginContract;
import com.example.dildil.login_page.presenter.LoginPresenter;
import com.example.dildil.util.InputUtil;
import com.example.dildil.util.SharedPreferencesUtil;
import com.example.dildil.util.XToastUtils;
import com.example.dildil.video.bean.VideoDaoBean;
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
    private UserDaoOperation operation;

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
        binding.LoUserAccount.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        binding.LoUserPassword.setImeOptions(EditorInfo.IME_ACTION_DONE);
        setMargins(binding.top1, 0, getStatusBarHeight(this), 0, 0);
    }

    @Override
    protected void initData() {
        operation = UserDaoOperation.getDatabase(this);
        if (!TextUtils.isEmpty(SharedPreferencesUtil.getAccountNum())) {
            account = SharedPreferencesUtil.getAccountNum();
            binding.LoUserAccount.setText(account);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Lo_login:
                account = binding.LoUserAccount.getText().toString().trim();
                password = binding.LoUserPassword.getText().toString().trim();
                if (InputUtil.checkPasswordLegal(password)) {
                    showDialog();
                    inputDto inputDto = new inputDto(account, password);
                    mPresenter.userLogin(inputDto);
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onBackPressed() {
        AppUtils.exitApp();
    }

    @Override
    public void onGetLoginSuccess(UserBean userBean) {
        hideDialog();
        operation.setUserDetail(userBean);
        operation.setVideoDetail(new VideoDaoBean(1, 1));
        XToastUtils.success("登录成功!");
        ActivityUtils.startActivity(HomeActivity.class);
        finish();
    }

    @Override
    public void onGetLoginFail(String e) {
        hideDialog();
        if (e.equals("HTTP 500")) {
            XToastUtils.error(R.string.enter_correct_password);
        } else {
            XToastUtils.error(R.string.errorOccurred);
            Log.e(TAG, e);
        }
    }
}
