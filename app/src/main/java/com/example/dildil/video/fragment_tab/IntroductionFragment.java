package com.example.dildil.video.fragment_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.FragmentIntroductionBinding;
import com.example.dildil.home_page.adapter.HotRankingAdapter;
import com.example.dildil.util.SharedPreferencesUtil;
import com.example.dildil.util.XToastUtils;
import com.example.dildil.video.bean.CoinBean;
import com.example.dildil.video.bean.VideoDetailsBean;
import com.example.dildil.video.contract.VideoDetailsContract;
import com.example.dildil.video.dialog.CoinDialog;
import com.example.dildil.video.presenter.VideoDetailsPresenter;
import com.example.dildil.video.view.VideoActivity;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import javax.inject.Inject;

public class IntroductionFragment extends BaseFragment implements VideoDetailsContract.View {
    private static final String TAG = "IntroductionFragment";
    FragmentIntroductionBinding binding;
    HotRankingAdapter adapter;
    private int id;
    private int uid;
    private TextView mTime,mDanmu,mPlayNum,mPraise,mCoin;
    private ImageView mOpen;
    private boolean isOpen = false;
    private LinearLayout mMainCoin;

    @Inject
    VideoDetailsPresenter mPresenter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_introduction, container, false);

        DaggerActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .activityModule(new ActivityModule(getActivity()))
                .build().inject(this);
        mPresenter.attachView(this);
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        mTime = binding.InVideoData.findViewById(R.id.It_video_time);
        mDanmu = binding.InVideoData.findViewById(R.id.It_barrage_num);
        mPlayNum = binding.InVideoData.findViewById(R.id.It_play_num);
        mOpen = binding.InVideoData.findViewById(R.id.open_warning);
        mPraise = binding.Sanlian.findViewById(R.id.praise);
        mCoin = binding.Sanlian.findViewById(R.id.coin);
        mMainCoin = binding.Sanlian.findViewById(R.id.main_coin);

        mOpen.setOnClickListener(this);
        mMainCoin.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new HotRankingAdapter(getContext());
        adapter.setListener(listener);
        binding.InRecyclerView.setLayoutManager(layoutManager);
        binding.InRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        showDialog();
        id = (int) SharedPreferencesUtil.getData("id",0);
        uid = (int) SharedPreferencesUtil.getData("uid",0);
        mPresenter.getVideoDetails(id,uid);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.open_warning:
                if (!isOpen){
                    binding.InNotInterested.setVisibility(View.VISIBLE);
                    mOpen.setImageResource(R.drawable.arrow_up);
                    isOpen = true;
                }else{
                    binding.InNotInterested.setVisibility(View.GONE);
                    mOpen.setImageResource(R.drawable.arrow_down);
                    isOpen = false;
                }
                break;
            case R.id.main_coin:
                CoinDialog coinDialog = new CoinDialog(getContext());
                coinDialog.show();
                break;
        }
    }

    HotRankingAdapter.ItemOnClickListener listener = new HotRankingAdapter.ItemOnClickListener() {
        @Override
        public void onClick(int position) {
            VideoActivity videoActivity = (VideoActivity) getActivity();
            videoActivity.getPlayPosition();
            GSYVideoManager gsyVideoManager = GSYVideoManager.instance();
            gsyVideoManager.onPause();
            ActivityUtils.startActivity(VideoActivity.class);
        }
    };

    @Override
    public void onGetVideoDetailsSuccess(VideoDetailsBean.BeanData videoDetailsBean) {
        binding.InTitle.setText(videoDetailsBean.getTitle());
        Glide.with(this).load(videoDetailsBean.getUpImg()).into(binding.InUserImg);
        binding.InUserName.setText(videoDetailsBean.getUpName());
        binding.InWarning.setText(videoDetailsBean.getDescription());
        mPlayNum.setText(videoDetailsBean.getPlayNum()+"");
        mDanmu.setText(videoDetailsBean.getDanmuNum()+"");
        mPraise.setText(videoDetailsBean.getPlayNum()+"");
        mCoin.setText(videoDetailsBean.getCoinNum()+"");
        String times = videoDetailsBean.getUpdateTime();
        mTime.setText(times.substring(5,10));
        initDatas();
        hideDialog();
    }

    private void initDatas() {
        binding.InFans.setText(608 + "粉丝");

        ResourcesData resourcesData = new ResourcesData();
        resourcesData.initHotRanking();
        adapter.loadMore(resourcesData.getHotRanking());
    }

    @Override
    public void onGetVideoDetailsFail(String e) {
        XToastUtils.error("网络出现错误");
    }

    @Override
    public void onGetCoinOperatedSuccess(CoinBean coinBean) {

    }

    @Override
    public void onGetCoinOperatedFail(String e) {

    }
}
