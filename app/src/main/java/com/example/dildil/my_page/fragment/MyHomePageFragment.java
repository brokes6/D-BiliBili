package com.example.dildil.my_page.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.FragmentMyhomeBinding;
import com.example.dildil.home_page.adapter.RecommendedVideoAdapter;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.home_page.contract.RecommendContract;
import com.example.dildil.home_page.presenter.RecommendPresenter;

import javax.inject.Inject;

public class MyHomePageFragment extends BaseFragment implements RecommendContract.View{
    private FragmentMyhomeBinding binding;
    private RecommendedVideoAdapter adapter;

    @Inject
    RecommendPresenter mPresenter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_myhome, container, false);

        DaggerActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .activityModule(new ActivityModule(getActivity()))
                .build()
                .inject(this);
        mPresenter.attachView(this);

        return binding.getRoot();
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initLocalData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetRecommendVideoSuccess(RecommendVideoBean videoBean) {
    }

    @Override
    public void onGetRecommendVideoFail(String e) {

    }

    @Override
    public void onGetRefreshRecommendVideoSuccess(RecommendVideoBean videoBean) {

    }

    @Override
    public void onGetRefreshRecommendVideoFail(String e) {

    }

    @Override
    public void onGetVideoLoadSuccess(RecommendVideoBean videoBean) {

    }

    @Override
    public void onGetVideoLoadFail(String e) {

    }
}
