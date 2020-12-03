package com.example.dildil.dynamic_page.fragment_tab;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.AppDatabase;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.FragmentTabVideoBinding;
import com.example.dildil.dynamic_page.adapter.TabVideoAdapter;
import com.example.dildil.dynamic_page.adapter.TypeAdapter;
import com.example.dildil.dynamic_page.bean.DynamicBean;
import com.example.dildil.dynamic_page.bean.TypeBean;
import com.example.dildil.dynamic_page.contract.DynamicContract;
import com.example.dildil.dynamic_page.presenter.DynamicPresenter;
import com.example.dildil.login_page.bean.UserBean;
import com.example.dildil.util.ScrollCalculatorHelper;
import com.example.dildil.util.XToastUtils;
import com.example.dildil.video.bean.CommentBean;
import com.example.dildil.video.bean.CommentDetailBean;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;

import javax.inject.Inject;

public class VideoTabFragment extends BaseFragment implements DynamicContract.View {
    private final String url = "{\"date\":[{\"mFull\":false,\"title\":\"最近访问\",\"type\":3},{\"mFull\":false,\"topicBean\":[{\"beInterestedImage\":\"https://i0.hdslb.com/bfs/tag/a92c19dbc4335fd553e50e187d759b2fafee9a64.jpg@115w_115h_1c_100q.webp\",\"beInterestedTitle\":\"COSPLAY\"},{\"beInterestedImage\":\"https://i0.hdslb.com/bfs/tag/c47710d730162f1e1c49d23e68aa0bf42b83b0e9.jpg@115w_115h_1c_100q.webp\",\"beInterestedTitle\":\"游戏集锦\"},{\"beInterestedImage\":\"https://i0.hdslb.com/bfs/archive/4afb90b88597f226d22fdaed28a5c4769b372fdc.png@115w_115h_1c_100q.webp\",\"beInterestedTitle\":\"搞笑\"},{\"beInterestedImage\":\"https://i0.hdslb.com/bfs/tag/db30d74add4aafeaf4faa1d2ddca120d1d89c52a.jpg@115w_115h_1c_100q.webp\",\"beInterestedTitle\":\"风暴英雄\"},{\"beInterestedImage\":\"https://i0.hdslb.com/bfs/tag/0f7b6c8c3d38382e677c1d137986a11fed8075ac.jpg@115w_115h_1c_100q.webp\",\"beInterestedTitle\":\"星际争霸2\"}],\"type\":1},{\"mFull\":false,\"title\":\"我的追番：追剧\",\"type\":3},{\"mFull\":false,\"pursueBean\":[{\"PursueImage\":\"https://i0.hdslb.com/bfs/archive/0fe50fefc0def68b88a32b2be67f3b63791419a3.jpg@120w_75h.webp\",\"PursueName\":\"刀剑神域 爱丽丝篇 异界战争 -终章-\",\"toUpdate_word\":18},{\"PursueImage\":\"https://i0.hdslb.com/bfs/archive/5894df7f7e8ccea3acbc33f124b0cbc9a24f1f22.jpg@120w_75h.webp\",\"PursueName\":\"某科学的超电磁炮T\",\"toUpdate_word\":19},{\"PursueImage\":\"https://i0.hdslb.com/bfs/archive/89c839e9be005ed074a8efb2afeb1e0fb53c7850.jpg@120w_75h.webp\",\"PursueName\":\"关于我转生变成史莱姆这档事\",\"toUpdate_word\":28},{\"PursueImage\":\"https://i0.hdslb.com/bfs/bangumi/3883837b7458fe93799591a59175d3fb26497b06.png@120w_75h.webp\",\"PursueName\":\"我的脑内选项正在全力妨碍学园恋爱喜剧\",\"toUpdate_word\":10}],\"type\":2}]}";
    private FragmentTabVideoBinding binding;
    private TabVideoAdapter mVAdapter;
    private TypeAdapter typeAdapter;
    private LinearLayoutManager layoutManager, layoutManager1;
    private static ScrollCalculatorHelper scrollCalculatorHelper;
    private CountDownTimer countDownTimer;
    private boolean mFull = false;
    private boolean isFirst = true;
    private TypeBean typeBean;
    private AppDatabase db;

    @Inject
    DynamicPresenter mPresenter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_video, container, false);
        DaggerActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .activityModule(new ActivityModule(getActivity()))
                .build().inject(this);
        mPresenter.attachView(this);
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        db = MyApplication.getDatabase(getContext());
        layoutManager = new LinearLayoutManager(getContext());
        mVAdapter = new TabVideoAdapter(getContext());
        binding.VTVideo.setLayoutManager(layoutManager);
        binding.VTVideo.setAdapter(mVAdapter);

        layoutManager1 = new LinearLayoutManager(getContext());
        typeAdapter = new TypeAdapter(getContext());

        binding.swipe.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                MyApplication.getDatabase(getContext()).userDao().getAll()
                        .observe(VideoTabFragment.this, new Observer<UserBean>() {

                            @Override
                            public void onChanged(UserBean userBean) {
                                mPresenter.getVideoDynamic(1, 8, userBean.getData().getId());
                            }
                        });
            }
        });

        //限定范围为屏幕一半的上下偏移180
        int playTop = CommonUtil.getScreenHeight(getContext()) / 2 - CommonUtil.dip2px(getContext(), 180);
        int playBottom = CommonUtil.getScreenHeight(getContext()) / 2 + CommonUtil.dip2px(getContext(), 180);

        //自定播放帮助类
        scrollCalculatorHelper = new ScrollCalculatorHelper(R.id.video_item_player, playTop, playBottom);

        binding.VTVideo.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    public static void VideoSuspend() {
        GSYVideoManager gsyVideoManager = GSYVideoManager.instance();
        if (gsyVideoManager.isPlaying()) {
            GSYVideoManager.onPause();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (newConfig.orientation != ActivityInfo.SCREEN_ORIENTATION_USER) {
            mFull = false;
        } else {
            mFull = true;
        }
    }

    @Override
    public void onGetDynamicSuccess(DynamicBean dynamicBean) {

    }

    @Override
    public void onGetDynamicFail(String e) {

    }

    @Override
    public void onGetVideoDynamicSuccess(DynamicBean dynamicBean) {
        if (isFirst) {
            isFirst = false;
            Gson gson = new Gson();
            typeBean = new TypeBean();
            typeBean = gson.fromJson(url, TypeBean.class);
            typeAdapter.loadMore(typeBean.getDate());
            countDownTimer = new CountDownTimer(500, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    View ry = binding.TopRecycler.getViewStub().inflate();
                    RecyclerView recyclerView = ry.findViewById(R.id.Recycler);
                    recyclerView.setLayoutManager(layoutManager1);
                    recyclerView.setAdapter(typeAdapter);
                }
            };
            countDownTimer.start();
            mVAdapter.loadMore(dynamicBean.getData());
        } else {
            for (DynamicBean.Datas datum : dynamicBean.getData()) {
                mVAdapter.add(datum);
            }
        }
        binding.swipe.finishRefresh(true);
    }

    @Override
    public void onGetVideoDynamicFail(String e) {
        binding.swipe.finishRefresh(true);
        XToastUtils.error(R.string.networkError + e);
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
    public void onResume() {
        super.onResume();
        if (isFirst) {
            binding.swipe.autoRefresh();
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        super.onDestroy();
    }
}
