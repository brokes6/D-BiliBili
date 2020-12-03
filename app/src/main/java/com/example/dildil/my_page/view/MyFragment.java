package com.example.dildil.my_page.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.databinding.FragmentMyBinding;
import com.example.dildil.login_page.bean.UserBean;

public class MyFragment extends BaseFragment {
    private FragmentMyBinding binding;
    private UserBean Userbean;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        binding.MSetting.setOnClickListener(this);
        binding.MUserImg.setOnClickListener(this);
        binding.MyHistory.setOnClickListener(this);
        binding.RightMore.setOnClickListener(this);
        binding.MVideoCover.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        showDialog();
        MyApplication.getDatabase(getContext()).userDao().getAll()
                .observe(this, new Observer<UserBean>() {

                    @Override
                    public void onChanged(UserBean userBean) {
                        Userbean = userBean;
                        Glide.with(getContext()).load(userBean.getData().getImg()).into(binding.MUserImg);
                        binding.MUserName.setText(userBean.getData().getUsername());
                        binding.MBCurrency.setText("B币: 5.0");
                        binding.MCoin.setText("硬币: " + userBean.getData().getCoinNum());
                        binding.dynamic.setTop_Text(0 + "");
                        binding.follow.setTop_Text(userBean.getData().getFollowNum() + "");
                        binding.fans.setTop_Text(userBean.getData().getFansNum() + "");
                        if (true) {
                            binding.member.setText("年度大会员");
                            binding.MMember.setText("年度大会员");
                        } else {
                            binding.member.setText("普通会员");
                            binding.MMember.setText("普通会员");
                            binding.MMember.setBackground(getResources().getDrawable(R.drawable.skeleton_circular_grey));
                        }
                        hideDialog();
                    }
                });
    }

    @Override
    protected void initLocalData() {

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.M_setting) {
            ActivityUtils.startActivity(SettingActivity.class);
        } else if (id == R.id.M_user_img) {
            Intent intent = new Intent(getContext(), PersonalActivity.class);
            intent.putExtra("uid", Userbean.getData().getId());
            getContext().startActivity(intent);
        } else if (id == R.id.MyHistory) {
            ActivityUtils.startActivity(HistoryActivity.class);
        } else if (id == R.id.Right_more) {
            Intent intent = new Intent(getContext(), PersonalActivity.class);
            intent.putExtra("uid", Userbean.getData().getId());
            getContext().startActivity(intent);
        } else if (id == R.id.M_video_cover) {
            ActivityUtils.startActivity(VipActivity.class);
        }
    }
}
