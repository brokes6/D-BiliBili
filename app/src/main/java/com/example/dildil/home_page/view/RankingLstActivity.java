package com.example.dildil.home_page.view;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.dildil.R;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.databinding.ActivityRankingBinding;

public class RankingLstActivity extends BaseActivity {
    private ActivityRankingBinding binding;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ranking);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
