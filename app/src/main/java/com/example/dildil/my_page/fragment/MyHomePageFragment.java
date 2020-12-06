package com.example.dildil.my_page.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.FragmentMyhomeBinding;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.login_page.bean.UserBean;
import com.example.dildil.my_page.adapter.UserHomePagerContributeAdapter;
import com.example.dildil.my_page.contract.PersonalContract;
import com.example.dildil.my_page.presenter.PersonalPresenter;

import javax.inject.Inject;

public class MyHomePageFragment extends BaseFragment implements PersonalContract.View {
    private FragmentMyhomeBinding binding;
    private UserHomePagerContributeAdapter adapter,adapter2;
    private final int uid;

    @Inject
    PersonalPresenter mPresenter;

    public MyHomePageFragment(int uid) {
        this.uid = uid;
    }

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
        binding.contributeRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter2 = new UserHomePagerContributeAdapter(getContext());
        binding.contributeRecycler.setAdapter(adapter2);

        binding.coinsRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new UserHomePagerContributeAdapter(getContext());
        binding.coinsRecycler.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        Log.e("why", "initData: 当前用户id为===>"+uid );
        mPresenter.findPublishVideo(1, 2, uid);
        mPresenter.findHasCoinVideo(1, 2, uid);
    }

    @Override
    protected void initLocalData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetFindUserDetailsSuccess(UserBean userBean) {
    }

    @Override
    public void onGetFindUserDetailsFail(String e) {

    }

    @Override
    public void onGetFindHasCoinVideoSuccess(RecommendVideoBean recommendVideoBean) {
        adapter.loadMore(recommendVideoBean.getData());
    }

    @Override
    public void onGetFindHasCoinVideoFail(String e) {
    }

    @Override
    public void onGetPublishVideoSuccess(RecommendVideoBean recommendVideoBean) {
        adapter2.loadMore(recommendVideoBean.getData());
    }

    @Override
    public void onGetPublishVideoFail(String e) {
    }
}
