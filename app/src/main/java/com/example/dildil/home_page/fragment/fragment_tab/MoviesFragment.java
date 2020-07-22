package com.example.dildil.home_page.fragment.fragment_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.example.dildil.R;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.databinding.FragmentMoviesBinding;

public class MoviesFragment extends BaseFragment {
    FragmentMoviesBinding binding;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }
}
