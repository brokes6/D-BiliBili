package com.example.dildil.channel_page.fragment_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.channel_page.adapter.PartitionAdapter;
import com.example.dildil.channel_page.bean.PartitionBean;
import com.example.dildil.channel_page.contract.PartitionContract;
import com.example.dildil.channel_page.presenter.PartitionPresenter;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.FragmentTabPartitionBinding;

import javax.inject.Inject;

public class PartitionTabFragment extends BaseFragment implements PartitionContract.View {
    private FragmentTabPartitionBinding binding;
    private PartitionAdapter adapter;

    @Inject
    PartitionPresenter mPresenter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_partition, container, false);

        DaggerActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .activityModule(new ActivityModule(getActivity()))
                .build().inject(this);
        mPresenter.attachView(this);

        return binding.getRoot();
    }

    @Override
    protected void initView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        binding.ChannelRecyclerView.setLayoutManager(layoutManager);
        adapter = new PartitionAdapter(getContext());
        binding.ChannelRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        mPresenter.getCategory();
    }

    @Override
    protected void initLocalData() {

    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetCategorySuccess(PartitionBean partitionBean) {
        adapter.loadMore(partitionBean.getData());
    }

    @Override
    public void onGetCategoryFail(String e) {
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
