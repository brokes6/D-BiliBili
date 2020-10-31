package com.example.dildil.home_page.view;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.channel_page.view.ChannelFragment;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.ActivityHomeBinding;
import com.example.dildil.dynamic_page.view.DynamicFragment;
import com.example.dildil.home_page.bean.DynamicNumBean;
import com.example.dildil.home_page.bean.VersionBean;
import com.example.dildil.home_page.contract.HomeContract;
import com.example.dildil.home_page.dialog.AppUpdateDialog;
import com.example.dildil.home_page.fragment.HomePageFragment;
import com.example.dildil.home_page.presenter.HomePresenter;
import com.example.dildil.my_page.view.MyFragment;
import com.example.dildil.util.AppDownloadManager;
import com.gyf.immersionbar.ImmersionBar;
import com.next.easynavigation.view.EasyNavigationBar;
import com.xuexiang.xui.utils.SnackbarUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Author:fuxinbo
 * 主页
 */
public class HomeActivity extends BaseActivity implements HomeContract.View {
    private static final String TAG = "HomeActivity";
    ActivityHomeBinding binding;
    private final String[] tabText = {"首页", "频道", "动态", "我的"};
    private final int[] normalIcon = {R.mipmap.home, R.mipmap.channel, R.mipmap.dynamic, R.mipmap.my};
    private final int[] selectIcon = {R.mipmap.home_select, R.mipmap.channel_select, R.mipmap.dynamic_select, R.mipmap.my_select};
    private List<Fragment> fragmentList = new ArrayList<>();
    private long firstTime = 0;
    private AppDownloadManager mDownloadManager;

    @Inject
    HomePresenter mPresenter;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarColor(R.color.White)
                .init();

        DaggerActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build().inject(this);
        mPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        fragmentList.add(new HomePageFragment());
        fragmentList.add(new ChannelFragment());
        fragmentList.add(new DynamicFragment());
        fragmentList.add(new MyFragment());
        setMargins(binding.navigationBar, 0, getStatusBarHeight(this), 0, 0);
    }

    @Override
    protected void initData() {
        binding.navigationBar.defaultSetting()
                .titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragmentList)
                .iconSize(18)
                .navigationHeight(48)
                .normalTextColor(Color.parseColor("#a2a2a2"))
                .selectTextColor(Color.parseColor("#fa7298"))
                .fragmentManager(getSupportFragmentManager())
                .smoothScroll(false)
                .setOnTabLoadListener(new EasyNavigationBar.OnTabLoadListener() { //Tab加载完毕回调
                    @Override
                    public void onTabLoadCompleteEvent() {
//                        binding.navigationBar.setMsgPointCount(2, 3);
                    }
                })
                .build();
        mPresenter.getVersion("http://116.196.105.203:8183/apk");
        mPresenter.getDynamicNum(1);
        showDialog();
    }

    @Override
    public void onClick(View view) {

    }

    private void downloadApk(final VersionBean updateInfo) {
        mDownloadManager = new AppDownloadManager(this);
        AppUpdateDialog dialog = new AppUpdateDialog(this, updateInfo);
        dialog.setOnUpdateClickListener(new AppUpdateDialog.OnUpdateClickListener() {
            @Override
            public void update(final AppUpdateDialog updateDialog) {
                mDownloadManager.setUpdateListener(new AppDownloadManager.OnUpdateListener() {
                    @Override
                    public void update(int currentByte, int totalByte) {
                        updateDialog.setProgress(currentByte, totalByte);
                        if ((currentByte == totalByte) && totalByte != 0) {
                            updateDialog.dismiss();
                        }

                    }
                });
                mDownloadManager.downloadApk(updateInfo.getData().getUrl());
            }

        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 切换Tab
     *
     * @param index
     */
    public void SwitchPages(int index) {
        binding.navigationBar.selectTab(index, true);
    }

    /**
     * 设置Tab红点数量
     *
     * @param index
     * @param value
     */
    public void SetRedDot(int index, int value) {
        binding.navigationBar.setMsgPointCount(index, value);
    }

    /**
     * 清除Tab红点
     *
     * @param index
     */
    public void ClearRedDot(int index) {
        binding.navigationBar.clearMsgPoint(index);
    }

    public void setOnTabClickListener(EasyNavigationBar.OnTabClickListener onTabClick){
        binding.navigationBar.setOnTabClickListener(onTabClick);
    }

    @Override
    public void onGetDynamicNumSuccess(DynamicNumBean dynamicNumBean) {
        SetRedDot(3,dynamicNumBean.getData());
        hideDialog();
    }

    @Override
    public void onGetDynamicNumFail(String e) {
        hideDialog();
    }

       @Override
    public void onGetVersionSuccess(VersionBean versionBean) {
        if (versionBean.getData().getVersion().compareTo(getVersionCode()) > 0) {
            downloadApk(versionBean);
        }
    }

    @Override
    public void onGetVersionFail(String e) {
        Log.e(TAG, "获取版本号出现错误:" + e);
    }

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            SnackbarUtils.Short(binding.main, "再按一次退出").info().show();
            firstTime = secondTime;
        } else {
            System.exit(0);// 完全退出应用
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        fragmentList.clear();
        fragmentList = null;
        super.onDestroy();
    }
}