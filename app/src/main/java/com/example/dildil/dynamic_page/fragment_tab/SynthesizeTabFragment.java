package com.example.dildil.dynamic_page.fragment_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.FragmentTabSynthesizeBinding;
import com.example.dildil.dynamic_page.adapter.DynamicAdapter;
import com.example.dildil.dynamic_page.adapter.TopicAdapter;
import com.example.dildil.dynamic_page.bean.DynamicBean;
import com.example.dildil.dynamic_page.contract.DynamicContract;
import com.example.dildil.dynamic_page.presenter.DynamicPresenter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import javax.inject.Inject;

public class SynthesizeTabFragment extends BaseFragment implements DynamicContract.View {
    FragmentTabSynthesizeBinding binding;
    private TopicAdapter adapter_topic;
    private DynamicAdapter adapter_dynamic;
    private boolean isFirst = true;

    @Inject
    DynamicPresenter mPresenter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_synthesize, container, false);
        DaggerActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .activityModule(new ActivityModule(getActivity()))
                .build().inject(this);
        mPresenter.attachView(this);

        return binding.getRoot();
    }

    @Override
    protected void initView() {
        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2);
        adapter_topic = new TopicAdapter(getContext());
        binding.RecyTopic.setLayoutManager(layoutManager1);
        binding.RecyTopic.setAdapter(adapter_topic);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter_dynamic = new DynamicAdapter(getContext());
        binding.RecyDynamic.setLayoutManager(layoutManager);
        binding.RecyDynamic.setAdapter(adapter_dynamic);
        binding.swipe.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (isFirst){
                    mPresenter.getDynamic(1,8,1);
                    isFirst = false;
                    ResourcesData resourcesData = new ResourcesData();
                    resourcesData.initTopicData();
                    adapter_topic.loadMore(resourcesData.getTopicData());
                }else{

                }

            }
        });
    }

    @Override
    protected void initData() {
        binding.swipe.autoRefresh();//自动刷新
    }

    @Override
    protected void initLocalData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetDynamicSuccess(DynamicBean dynamicBean) {
        binding.swipe.finishRefresh(true);
    }

    @Override
    public void onGetDynamicFail(String e) {
        binding.swipe.finishRefresh(true);
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
