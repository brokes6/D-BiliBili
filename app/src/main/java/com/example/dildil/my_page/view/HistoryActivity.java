package com.example.dildil.my_page.view;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.databinding.ActivityHistoryBinding;
import com.example.dildil.my_page.adapter.HistoryAdapter;
import com.example.dildil.my_page.bean.HistoryBean;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

public class HistoryActivity extends BaseActivity {
    private ActivityHistoryBinding binding;
    private HistoryAdapter adapter;
    private boolean isFirst = true;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history);

        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(false)
                .statusBarColor(R.color.Pink)
                .init();
    }

    @Override
    protected void initView() {
        setLeftTitleText("播放历史");
        setBackBtn(getResources().getColor(R.color.While, null));
        setTitleBG(getResources().getColor(R.color.Pink, null));
        setLeftTitleTextColorWhite();
        setDelete();

        setMargins(binding.Title, 0, getStatusBarHeight(this), 0, 0);
    }

    @Override
    protected void initData() {
        binding.HRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryAdapter(this);
        binding.HRecyclerView.setAdapter(adapter);
        MyApplication.getDatabase(this).historyDao().getAll().observe(this, new Observer<List<HistoryBean>>() {
            @Override
            public void onChanged(List<HistoryBean> historyBean) {
                if (isFirst) {
                    historyBean.add(0, new HistoryBean());
                    adapter.loadMore(historyBean);
                    isFirst = false;
                } else {
                    adapter.refresh(historyBean);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}