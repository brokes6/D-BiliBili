package com.example.dildil.dynamic_page.fragment_tab;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.FragmentTabSynthesizeBinding;
import com.example.dildil.dynamic_page.adapter.TabVideoAdapter;
import com.example.dildil.dynamic_page.adapter.TopicAdapter;
import com.example.dildil.dynamic_page.bean.AttentionDetailsBean;
import com.example.dildil.dynamic_page.bean.DynamicBean;
import com.example.dildil.dynamic_page.bean.DynamicDetailsBean;
import com.example.dildil.dynamic_page.contract.DynamicContract;
import com.example.dildil.dynamic_page.presenter.DynamicPresenter;
import com.example.dildil.login_page.bean.UserBean;
import com.example.dildil.util.ScrollCalculatorHelper;
import com.example.dildil.video.bean.CommentBean;
import com.example.dildil.video.bean.CommentDetailBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;

import javax.inject.Inject;

public class SynthesizeTabFragment extends BaseFragment implements DynamicContract.View {
    private FragmentTabSynthesizeBinding binding;
    private TopicAdapter adapter_topic;
    private TabVideoAdapter adapter_dynamic;
    private static ScrollCalculatorHelper scrollCalculatorHelper;
    private boolean isFirst = true;
    private boolean mFull = false;
    private ResourcesData resourcesData;

    @Inject
    DynamicPresenter mPresenter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_synthesize, container, false);
        DaggerActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .activityModule(new ActivityModule(getActivity()))
                .build().inject(this);
        mPresenter.attachView(this);

        return binding.getRoot();
    }

    @Override
    protected void initView() {
        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2);
        adapter_topic = new TopicAdapter(getContext());
        binding.RecyTopic.setLayoutManager(layoutManager1);
        binding.RecyTopic.setAdapter(adapter_topic);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter_dynamic = new TabVideoAdapter(getContext());
        binding.RecyDynamic.setLayoutManager(layoutManager);
        binding.RecyDynamic.setAdapter(adapter_dynamic);

        //限定范围为屏幕一半的上下偏移180
        int playTop = CommonUtil.getScreenHeight(getContext()) / 2 - CommonUtil.dip2px(getContext(), 180);
        int playBottom = CommonUtil.getScreenHeight(getContext()) / 2 + CommonUtil.dip2px(getContext(), 180);

        //自定播放帮助类
        scrollCalculatorHelper = new ScrollCalculatorHelper(R.id.video_item_player, playTop, playBottom);

        binding.RecyDynamic.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                scrollCalculatorHelper.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                //这是滑动自动播放的代码
                if (!mFull) {
                    scrollCalculatorHelper.onScroll(recyclerView, firstVisibleItem, lastVisibleItem, lastVisibleItem - firstVisibleItem);
                }
            }
        });

        binding.swipe.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                MyApplication.getDatabase(getContext()).userDao().getAll()
                        .observe(SynthesizeTabFragment.this, new Observer<UserBean>() {

                            @Override
                            public void onChanged(UserBean userBean) {
                                if (isFirst) {
                                    mPresenter.getDynamic(1, 8, userBean.getData().getId());
                                    resourcesData = new ResourcesData();
                                    resourcesData.initTopicData();
                                    adapter_topic.loadMore(resourcesData.getTopicData());
                                    isFirst = false;
                                } else {
                                    mPresenter.getDynamic(1, 8, userBean.getData().getId());
                                    adapter_topic.refresh(resourcesData.getTopicData());
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void initData() {
        binding.swipe.autoRefresh();//自动刷新
    }

    @Override
    protected void initLocalData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetDynamicSuccess(DynamicBean dynamicBean) {
        binding.swipe.finishRefresh(true);
        if (isFirst) {
            for (DynamicBean.Datas datum : dynamicBean.getData()) {
                adapter_dynamic.add(datum);
            }
        } else {
            adapter_dynamic.refresh(dynamicBean.getData());
        }
    }

    @Override
    public void onGetDynamicFail(String e) {
        Log.e("why", "动态出现错误" + e);
        binding.swipe.finishRefresh(true);
    }

    @Override
    public void onGetVideoDynamicSuccess(DynamicBean dynamicBean) {

    }

    @Override
    public void onGetVideoDynamicFail(String e) {

    }

    @Override
    public void onGetAddDynamicCommentSuccess(CommentBean commentBean) {

    }

    @Override
    public void onGetAddDynamicCommentFail(String e) {

    }

    @Override
    public void onGetDynamicCommentSuccess(CommentDetailBean commentDetailBean) {

    }

    @Override
    public void onGetDynamicCommentFail(String e) {

    }

    @Override
    public void onGetDynamicDetailsSuccess(DynamicDetailsBean dynamicDetailsBean) {

    }

    @Override
    public void onGetDynamicDetailsFail(String e) {

    }

    @Override
    public void onGetAttentionDetailsSuccess(AttentionDetailsBean attentionDetailsBean) {

    }

    @Override
    public void onGetAttentionDetailsFail(String e) {

    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        resourcesData = null;
        super.onDestroy();
    }
}
