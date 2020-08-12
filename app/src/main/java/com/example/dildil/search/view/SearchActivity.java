package com.example.dildil.search.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.databinding.ActivitySearchBinding;
import com.example.dildil.search.adapter.HistoryFlowTagAdapter;
import com.example.dildil.search.adapter.HotSearchAdapter;
import com.example.dildil.util.XToastUtils;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

public class SearchActivity extends BaseActivity {
    ActivitySearchBinding binding;
    private TextView mSearchButton;
    private HistoryFlowTagAdapter adapter;
    private HotSearchAdapter hadapter;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void initView() {
        mSearchButton = findViewById(R.id.search_button);
        mSearchButton.setOnClickListener(this);
        setMargins(binding.SeSearch, 0, getStatusBarHeight(this), 0, 0);
    }

    @Override
    protected void initData() {
        GridLayoutManager layoutManager1 = new GridLayoutManager(this,2);
        hadapter = new HotSearchAdapter(this);
        binding.SeHotSearch.setLayoutManager(layoutManager1);
        binding.SeHotSearch.setAdapter(hadapter);

        adapter = new HistoryFlowTagAdapter(this);
        binding.SeNormalSelect.setAdapter(adapter);
        binding.SeNormalSelect.setOnTagClickListener(new FlowTagLayout.OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                XToastUtils.info("点击了：" + parent.getAdapter().getItem(position));
            }
        });
        initDatas();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_button:
                finish();
                break;
        }
    }
    private void initDatas(){
        ResourcesData resourcesData = new ResourcesData();
        resourcesData.initHotSearch();
        adapter.addTags(resourcesData.getSearchTag());
        hadapter.loadMore(resourcesData.getHotSearchBeans());
    }
}
