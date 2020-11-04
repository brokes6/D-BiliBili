package com.example.dildil.dynamic_page.fragment_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.example.dildil.R;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.databinding.FragmentDetailscommentBinding;

public class DetailsCommentFragment extends BaseFragment {
    private FragmentDetailscommentBinding binding;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detailscomment,container,false);
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
}
