package com.example.dildil.video.fragment_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.dildil.R;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.databinding.FragmentIntroductionBinding;

public class IntroductionFragment extends BaseFragment {
    FragmentIntroductionBinding binding;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_introduction,container,false);
        return binding.getRoot();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        initDatas();
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }

    private void initDatas(){
        Glide.with(this).load("https://i0.hdslb.com/bfs/face/416560828f5aa755eae21b2bd7a9fa7de105fb46.jpg@45w_45h_1c_100q.webp").into(binding.InUserImg);
        binding.InUserName.setText("是仙仙醬啦");
        binding.InFans.setText(608+"粉丝");
    }
}
