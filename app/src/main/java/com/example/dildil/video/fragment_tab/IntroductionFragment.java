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
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
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
import com.example.dildil.video.bean.CollectionBean;
import com.example.dildil.video.bean.CommentDetailBean;
import com.example.dildil.video.bean.DanmuBean;
import com.example.dildil.video.bean.SeadDanmuBean;
import com.example.dildil.video.bean.ThumbsUpBean;
import com.example.dildil.video.bean.VideoDetailsBean;
import com.example.dildil.video.bean.dto;
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
    private TextView mTime, mDanmu, mPlayNum, mPraise, mCoin, CollectionNum;
    private ImageView like_img, Collection, coinImg;
    private boolean isOpen = false;
    private LinearLayout mMainCoin, thumbsUp, CollectionMain, ForwardMain;
    private boolean isLoad = false;
    private SkeletonScreen mSkeletonScreen;
    private SkeletonScreen mSkeletonScreen2;
    private boolean mIsPraise = false;
    private int mCoinNum = 0;
    private boolean isCollection = false;

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
        getIncludeView();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new HotRankingAdapter(getContext());
        adapter.setListener(listener);
        binding.InRecyclerView.setLayoutManager(layoutManager);
        binding.InRecyclerView.setAdapter(adapter);
        mSkeletonScreen2 = Skeleton.bind(binding.InRecyclerView)
                .adapter(adapter)//设置实际adapter
                .shimmer(true)//是否开启动画
                .angle(30)//shimmer的倾斜角度
                .frozen(true)//true则表示显示骨架屏时，RecyclerView不可滑动，否则可以滑动
                .duration(1200)//动画时间，以毫秒为单位
                .count(4)//显示骨架屏时item的个数
                .load(R.layout.item_hot_ranking_list_skleton)//骨架屏UI
                .show();

        mSkeletonScreen = Skeleton.bind(binding.top)
                .load(R.layout.item_fragment_introduction_skeleton)//骨架屏UI
                .duration(1000)//动画时间，以毫秒为单位
                .shimmer(true)//是否开启动画
                .color(R.color.shimmer_color)//shimmer的颜色
                .angle(30)//shimmer的倾斜角度
                .show();

    }

    private void getIncludeView() {
        mTime = binding.InVideoData.findViewById(R.id.It_video_time);
        mDanmu = binding.InVideoData.findViewById(R.id.It_barrage_num);
        mPlayNum = binding.InVideoData.findViewById(R.id.It_play_num);
        mPraise = binding.Sanlian.findViewById(R.id.praise);
        mCoin = binding.Sanlian.findViewById(R.id.coin);
        mMainCoin = binding.Sanlian.findViewById(R.id.main_coin);
        thumbsUp = binding.Sanlian.findViewById(R.id.thumbsUp);
        like_img = binding.Sanlian.findViewById(R.id.like_img);
        CollectionMain = binding.Sanlian.findViewById(R.id.CollectionMain);
        Collection = binding.Sanlian.findViewById(R.id.Collection);
        CollectionNum = binding.Sanlian.findViewById(R.id.CollectionNum);
        ForwardMain = binding.Sanlian.findViewById(R.id.ForwardMain);
        coinImg = binding.Sanlian.findViewById(R.id.coinImg);

        binding.function1.setOnClickListener(this);
        thumbsUp.setOnClickListener(this);
        mMainCoin.setOnClickListener(this);
        CollectionMain.setOnClickListener(this);
        ForwardMain.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        showDialog();
        id = (int) SharedPreferencesUtil.getData("id", 0);
        uid = (int) SharedPreferencesUtil.getData("uid", 0);
        mPresenter.getVideoDetails(id, uid);
    }

    @Override
    protected void initLocalData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.function1:
                if (!isOpen) {
                    binding.InTitle.setMaxLines(2);
                    binding.InNotInterested.setVisibility(View.VISIBLE);
                    binding.openWarning.setImageResource(R.drawable.arrow_up);
                    isOpen = true;
                } else {
                    binding.InTitle.setMaxLines(1);
                    binding.InNotInterested.setVisibility(View.GONE);
                    binding.openWarning.setImageResource(R.drawable.arrow_down);
                    isOpen = false;
                }
                break;
            case R.id.main_coin:
                if (mCoinNum == 2) {
                    XToastUtils.toast("已经投过币拉~");
                    return;
                }
                CoinDialog coinDialog = new CoinDialog(getContext(), id);
                coinDialog.setListener(resultListener);
                coinDialog.show();
                break;
            case R.id.thumbsUp:
                if (mIsPraise) {
                    XToastUtils.toast("已经点过赞拉~");
                    return;
                }
                dto str = new dto(id);
                mPresenter.getThumbsUp("http://116.196.105.203/videoservice/video/dynamic_like", str);
                break;
            case R.id.CollectionMain:
                if (isCollection) {
                    XToastUtils.toast("已经收藏过拉~");
                    return;
                }
                dto str1 = new dto(id);
                mPresenter.CollectionVideo(str1);
                break;
        }
    }

    /**
     * 投币外部监听
     */
    CoinDialog.throwCoinResultListener resultListener = new CoinDialog.throwCoinResultListener() {
        @Override
        public void throwCoinSuccess(CoinBean coinBean) {
            XToastUtils.success(coinBean.getMessage());
            showDialog();
            mPresenter.getVideoDetails(id, uid);
        }

        @Override
        public void throwCoinFail(String e) {
            XToastUtils.error("操作失败：" + e);
        }

        @Override
        public void ThumbsUpSuccess(ThumbsUpBean bean) {

        }

        @Override
        public void ThumbsUpFail(String e) {
        }
    };

    /**
     * 适配器item点击外部监听
     */
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
        mPlayNum.setText(videoDetailsBean.getPlayNum() + "");
        mDanmu.setText(videoDetailsBean.getDanmuNum() + "");
        mPraise.setText(videoDetailsBean.getPraiseNum() + "");
        mCoin.setText(videoDetailsBean.getCoinNum() + "");
        String times = videoDetailsBean.getUpdateTime();
        mTime.setText(times.substring(5, 10));
        if (videoDetailsBean.getLog() != null) {
            mIsPraise = videoDetailsBean.getLog().isPraise();
            mCoinNum = videoDetailsBean.getLog().getCoinNum();
            isCollection = videoDetailsBean.getLog().isCollection();
            if (videoDetailsBean.getLog().isPraise()) like_img.setImageResource(R.drawable.thumb_up_24);
            if (videoDetailsBean.getLog().isCollection()) Collection.setImageResource(R.mipmap.collect_on);
            if (videoDetailsBean.getLog().getCoinNum() == 2) coinImg.setImageResource(R.mipmap.coin_on);
        }
        if (!isLoad) initDatas();
        isLoad = true;
        mSkeletonScreen.hide();
        mSkeletonScreen2.hide();
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
        hideDialog();
        mSkeletonScreen.hide();
        mSkeletonScreen2.hide();
        XToastUtils.error("网络出现错误");
    }

    @Override
    public void onGetCoinOperatedSuccess(CoinBean coinBean) {

    }

    @Override
    public void onGetCoinOperatedFail(String e) {

    }

    @Override
    public void onGetThumbsUpSuccess(ThumbsUpBean thumbsUpBean) {
        mIsPraise = true;
        XToastUtils.success("操纵成功！");
        XToastUtils.success(thumbsUpBean.getMessage());
        like_img.setImageResource(R.drawable.thumb_up_24);
        mPresenter.getVideoDetails(id, uid);
        showDialog();
    }

    @Override
    public void onGetThumbsUpFail(String e) {
        XToastUtils.error("发生错误:" + e);
    }

    @Override
    public void onGetCollectionVideoSuccess(CollectionBean collectionBean) {
        isCollection = true;
        Collection.setImageResource(R.mipmap.collect_on);
        XToastUtils.success("收藏成功！");
    }

    @Override
    public void onGetCollectionVideoFail(String e) {
        XToastUtils.error("发生错误:" + e);
    }

    @Override
    public void onGetVideoCommentSuccess(CommentDetailBean commentDetailBean) {

    }

    @Override
    public void onGetVideoCommentFail(String e) {

    }

    @Override
    public void onGetDanMuSuccess(DanmuBean danmuBean) {

    }

    @Override
    public void onGetDanMuFail(String e) {

    }

    @Override
    public void onGetSeadDanMuSuccess(SeadDanmuBean seadDanmuBean) {

    }

    @Override
    public void onGetSedaDanMuFail(String e) {

    }
}
