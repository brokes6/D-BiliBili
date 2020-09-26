package com.example.dildil.my_page.view;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.ActivitySettingBinding;
import com.example.dildil.login_page.view.LoginActivity;
import com.example.dildil.my_page.bean.LogoutBean;
import com.example.dildil.my_page.contract.MyContract;
import com.example.dildil.my_page.presenter.MyPresenter;
import com.example.dildil.util.SharePreferenceUtil;
import com.example.dildil.util.XToastUtils;
import com.gyf.immersionbar.ImmersionBar;

import javax.inject.Inject;

public class SettingActivity extends BaseActivity implements MyContract.View {
    ActivitySettingBinding binding;

    @Inject
    MyPresenter mPresenter;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_setting);

        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarColor(R.color.White)
                .init();

        DaggerActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
        mPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        binding.logOut.setOnClickListener(this);
        setMargins(binding.Title,0,getStatusBarHeight(this),0,0);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logOut:
                showDialog();
                mPresenter.Logout();
                break;
        }
    }

    @Override
    public void onGetLogoutSuccess(LogoutBean logoutBean) {
        hideDialog();
        XToastUtils.success("退出成功！");
        SharePreferenceUtil.getInstance(this).remove("cookie");
        ActivityUtils.startActivity(LoginActivity.class);
        this.finish();
    }

    @Override
    public void onGetLogoutFail(String e) {
        hideDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}