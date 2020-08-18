package com.example.dildil.home_page.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.dildil.R;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.channel_page.view.ChannelFragment;
import com.example.dildil.databinding.ActivityHomeBinding;
import com.example.dildil.dynamic_page.view.DynamicFragment;
import com.example.dildil.home_page.fragment.HomePageFragment;
import com.example.dildil.my_page.view.MyFragment;
import com.gyf.immersionbar.ImmersionBar;
import com.next.easynavigation.view.EasyNavigationBar;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {
    private static final String TAG = "HomeActivity";
    ActivityHomeBinding binding;
    private String[] tabText = {"首页", "频道", "动态", "我的"};
    private int[] normalIcon = {R.mipmap.home, R.mipmap.channel, R.mipmap.dynamic, R.mipmap.my};
    private int[] selectIcon = {R.mipmap.home_select, R.mipmap.channel_select, R.mipmap.dynamic_select, R.mipmap.my_select};
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarColor(R.color.White)
                .init();
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void initView() {
        fragmentList.add(new HomePageFragment());
        fragmentList.add(new ChannelFragment());
        fragmentList.add(new DynamicFragment());
        fragmentList.add(new MyFragment());
        Log.e(TAG, "当前流海高度为:" + getStatusBarHeight(this));
        setMargins(binding.navigationBar, 0, getStatusBarHeight(this), 0, 0);
    }

    @Override
    protected void initData() {
        binding.navigationBar.defaultSetting()
                .titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragmentList)
                .fragmentManager(getSupportFragmentManager())
                .smoothScroll(false)
                .setOnTabLoadListener(new EasyNavigationBar.OnTabLoadListener() { //Tab加载完毕回调
                    @Override
                    public void onTabLoadCompleteEvent() {
                        binding.navigationBar.setMsgPointCount(2, 3);
                    }
                })
                .build();
    }

    @Override
    public void onClick(View view) {

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

}