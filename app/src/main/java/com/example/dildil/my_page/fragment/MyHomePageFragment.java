package com.example.dildil.my_page.fragment;

import android.os.Bundle;
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
import com.example.dildil.home_page.adapter.RecommendedVideoAdapter;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.home_page.contract.RecommendContract;
import com.example.dildil.home_page.dialog.VideoChoiceDialog;
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
        VideoChoiceDialog videoChoiceDialog = new VideoChoiceDialog(getContext());
        videoChoiceDialog.setOnFeedbackClickListener(onFeedbackClickListener);

        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2);
        adapter = new RecommendedVideoAdapter(getContext(), videoChoiceDialog, 1);
        binding.ReTopRecy.setLayoutManager(layoutManager1);
        binding.ReTopRecy.setHasFixedSize(true);
        binding.ReTopRecy.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        mPresenter.getRandomRecommendation();
    }

    @Override
    protected void initLocalData() {

    }

    private VideoChoiceDialog.OnFeedbackClickListener onFeedbackClickListener = new VideoChoiceDialog.OnFeedbackClickListener() {
        @Override
        public void update(int position, int type) {
            switch (type) {
                case 1:
                    adapter.removeItem(position);
                    break;
                case 2:
                    break;
            }

        }
    };

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetRecommendVideoSuccess(RecommendVideoBean videoBean) {
        adapter.loadMore(videoBean.getData());
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
