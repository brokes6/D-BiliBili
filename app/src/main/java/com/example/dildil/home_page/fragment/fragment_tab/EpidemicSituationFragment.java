package com.example.dildil.home_page.fragment.fragment_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.example.dildil.R;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.databinding.FragmentEpidemicsituationBinding;

public class EpidemicSituationFragment extends BaseFragment {
    FragmentEpidemicsituationBinding binding;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_epidemicsituation, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initLocalData() {

    }

    @Override
    protected void initView() {

    }


    @Override
    public void onClick(View v) {

    }
}
