package com.example.dildil.my_page.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.databinding.FragmentMyBinding;
import com.example.dildil.my_page.bean.MyDataBean;

public class MyFragment extends BaseFragment {
    private static final String TAG = "MyFragment";
    FragmentMyBinding binding;
    private MyDataBean MyDataBean;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        binding.MSetting.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        showDialog();
        ResourcesData resourcesData = new ResourcesData();
        resourcesData.initMyData();
        MyDataBean = resourcesData.getMyDataBeans();

        Glide.with(getContext()).load(MyDataBean.getUserImg()).into(binding.MUserImg);
        binding.MUserName.setText(MyDataBean.getUsername());
        binding.dynamic.setTop_Text(MyDataBean.getDynamic()+"");
        binding.follow.setTop_Text(MyDataBean.getFollow()+"");
        binding.fans.setTop_Text(MyDataBean.getFans()+"");
        if (MyDataBean.isMember()){
            binding.member.setText("年度大会员");
        }else{
            binding.member.setText("普通会员");
        }
        hideDialog();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.M_setting:
                ActivityUtils.startActivity(SettingActivity.class);
                break;
        }
    }
}
