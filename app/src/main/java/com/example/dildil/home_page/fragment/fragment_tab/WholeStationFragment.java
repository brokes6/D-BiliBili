package com.example.dildil.home_page.fragment.fragment_tab;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.FragmentWholeStationBinding;
import com.example.dildil.home_page.adapter.WholeStationAdapter;
import com.example.dildil.home_page.bean.WholeStationBean;
import com.example.dildil.home_page.contract.WholeStationContract;
import com.example.dildil.home_page.presenter.WholeStationPresenter;

import javax.inject.Inject;

public class WholeStationFragment extends BaseFragment implements WholeStationContract.View {
    private FragmentWholeStationBinding binding;
    private WholeStationAdapter adapter;

    @Inject
    WholeStationPresenter mPresenter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_whole_station, container, false);

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.WSRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new WholeStationAdapter(getContext());
        binding.WSRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        mPresenter.getWholeStation(1,1);
    }

    @Override
    protected void initLocalData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetWholeStationSuccess(WholeStationBean bean) {
        adapter.loadMore(bean.getData());
    }

    @Override
    public void onGetWholeStationFail(String e) {
        Log.e("why", "onGetWholeStationFail: 出现错误"+e );
    }
}
