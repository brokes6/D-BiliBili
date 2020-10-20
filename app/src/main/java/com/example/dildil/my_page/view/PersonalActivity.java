package com.example.dildil.my_page.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.ActivityPersonalBinding;
import com.example.dildil.login_page.bean.LoginBean;
import com.example.dildil.my_page.contract.PersonalContract;
import com.example.dildil.my_page.fragment.MyCollectionFragment;
import com.example.dildil.my_page.fragment.MyDynamicFragment;
import com.example.dildil.my_page.fragment.MyHomePageFragment;
import com.example.dildil.my_page.fragment.MyTrackingFragment;
import com.example.dildil.my_page.presenter.PersonalPresenter;
import com.example.dildil.util.GsonUtil;
import com.example.dildil.util.SharePreferenceUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

import javax.inject.Inject;

public class PersonalActivity extends BaseActivity implements PersonalContract.View {
    private ActivityPersonalBinding binding;
    private final String[] TabTitle = {"主页", "动态", "收藏", "追番"};
    private ArrayList<Fragment> mFragments;
    private LoginBean loginBean;
    private final int While = 0xFFFFFF, grey = 0x999999;
    private ImageView backBackGround, backImage, searchBackGround, searchImage, moreBackGround, moreImage;

    @Inject
    PersonalPresenter mPresenter;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personal);

        ImmersionBar.with(this)
                .transparentStatusBar()
                .autoDarkModeEnable(true)
                .init();

        DaggerActivityComponent.builder().appComponent(MyApplication.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build().inject(this);
        mPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        mFragments = new ArrayList<>();
        mFragments.add(new MyHomePageFragment());
        mFragments.add(new MyDynamicFragment());
        mFragments.add(new MyCollectionFragment());
        mFragments.add(new MyTrackingFragment());
        binding.tab.setViewPager(binding.viewPager, TabTitle, this, mFragments);
        binding.tab.setCurrentTab(0);
        binding.PBack.setOnClickListener(this);

        backBackGround = binding.PBack.findViewById(R.id.B_background);
        backImage = binding.PBack.findViewById(R.id.B_back);
        searchBackGround = binding.PSearch.findViewById(R.id.S_backGround);
        searchImage = binding.PSearch.findViewById(R.id.S_image);
        moreBackGround = binding.PMore.findViewById(R.id.M_backGround);
        moreImage = binding.PMore.findViewById(R.id.M_image);

        binding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset == 0) {
                    backBackGround.setBackgroundResource(R.drawable.skeleton_circulars_grey);
                    backImage.setImageResource(R.drawable.keyboard_backspace_24);

                    searchBackGround.setBackgroundResource(R.drawable.skeleton_circulars_grey);
                    searchImage.setImageResource(R.drawable.search_24_while);

                    moreBackGround.setBackgroundResource(R.drawable.skeleton_circulars_grey);
                    moreImage.setImageResource(R.drawable.more_vert_while_24);

                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    backBackGround.setBackgroundColor(While);
                    backImage.setImageResource(R.drawable.keyboard_backspace_24_gray);

                    searchBackGround.setBackgroundColor(While);
                    searchImage.setImageResource(R.drawable.search_24_gray);

                    moreBackGround.setBackgroundColor(While);
                    moreImage.setImageResource(R.drawable.more_vert_24_gray);
                }
            }
        });

        setMargins(binding.toolbar, 0, getStatusBarHeight(this), 0, 0);
    }

    @Override
    protected void initData() {
        loginBean = GsonUtil.fromJSON(SharePreferenceUtil.getInstance(this).getUserInfo(""), LoginBean.class);
        Glide.with(this).load(loginBean.getData().getImg()).into(binding.PUserImg);
        binding.PUserName.setText(loginBean.getData().getUsername());
        binding.PDynamic.setTop_Text(0 + "");
        binding.PFollow.setTop_Text(loginBean.getData().getFollowNum() + "");
        binding.PFans.setTop_Text(loginBean.getData().getFansNum() + "");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.P_back) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}