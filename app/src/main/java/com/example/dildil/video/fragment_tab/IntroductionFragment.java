package com.example.dildil.video.fragment_tab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.customcontrollibs.AnnularProgressButton;
import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.FragmentIntroductionBinding;
import com.example.dildil.dynamic_page.bean.AttentionBean;
import com.example.dildil.dynamic_page.bean.params;
import com.example.dildil.home_page.adapter.HotRankingAdapter;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.login_page.bean.UserBean;
import com.example.dildil.my_page.view.PersonalActivity;
import com.example.dildil.util.XToastUtils;
import com.example.dildil.video.adapter.RelevantVideoAdapter;
import com.example.dildil.video.bean.CoinBean;
import com.example.dildil.video.bean.CollectionBean;
import com.example.dildil.video.bean.CommentBean;
import com.example.dildil.video.bean.CommentDetailBean;
import com.example.dildil.video.bean.DanmuBean;
import com.example.dildil.video.bean.SeadDanmuBean;
import com.example.dildil.video.bean.ThumbsUpBean;
import com.example.dildil.video.bean.VideoDaoBean;
import com.example.dildil.video.bean.VideoDetailsBean;
import com.example.dildil.video.bean.dto;
import com.example.dildil.video.contract.VideoDetailsContract;
import com.example.dildil.video.dialog.CoinDialog;
import com.example.dildil.video.presenter.VideoDetailsPresenter;
import com.example.dildil.video.view.VideoActivity;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import javax.inject.Inject;

public class IntroductionFragment extends BaseFragment implements VideoDetailsContract.View {
    private FragmentIntroductionBinding binding;
    private RelevantVideoAdapter adapter;
    private TextView mTime, mDanmu, mPlayNum, mPraise, mCoin, CollectionNum, It_video_id;
    private ImageView like_img, Collection, coinImg;
    private boolean isOpen = false;
    private AnnularProgressButton coin_circleView, Collection_circleView;
    private boolean mIsPraise = false;
    private int mCoinNum = 0;
    private boolean isCollection = false;
    private boolean isSanLian = true;
    private UserBean UserBean;
    private int uid, vid;
    private int praiseNum;

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.InRecyclerView.setLayoutManager(layoutManager);
        binding.InUserImg.setOnClickListener(this);
        adapter = new RelevantVideoAdapter(getContext());
        binding.InRecyclerView.setAdapter(adapter);
        binding.InAttention.setOnClickListener(this);

        getIncludeView();
    }

    private void getIncludeView() {
        mTime = binding.InVideoData.findViewById(R.id.It_video_time);
        mDanmu = binding.InVideoData.findViewById(R.id.It_barrage_num);
        mPlayNum = binding.InVideoData.findViewById(R.id.It_play_num);
        It_video_id = binding.InVideoData.findViewById(R.id.It_video_id);
        mPraise = binding.Sanlian.findViewById(R.id.praise);
        mCoin = binding.Sanlian.findViewById(R.id.coin);
        like_img = binding.Sanlian.findViewById(R.id.like_img);
        Collection = binding.Sanlian.findViewById(R.id.Collection);
        CollectionNum = binding.Sanlian.findViewById(R.id.CollectionNum);
        RelativeLayout forwardMain = binding.Sanlian.findViewById(R.id.ForwardMain);
        coinImg = binding.Sanlian.findViewById(R.id.coinImg);
        coin_circleView = binding.Sanlian.findViewById(R.id.coin_circleView);
        Collection_circleView = binding.Sanlian.findViewById(R.id.Collection_circleView);

        binding.function1.setOnClickListener(this);
        like_img.setOnClickListener(this);
        coinImg.setOnClickListener(this);
        Collection.setOnClickListener(this);
        forwardMain.setOnClickListener(this);
        coin_circleView.setListener(new AnnularProgressButton.ProgressButtonFinishCallback() {
            @Override
            public void onFinish() {

            }

            @Override
            public void onCancel() {

            }
        });
        Collection_circleView.setListener(new AnnularProgressButton.ProgressButtonFinishCallback() {
            @Override
            public void onFinish() {
                if (coin_circleView.mProgress >= 300) {
                    like_img.setImageResource(R.drawable.thumb_up_24);
                    Collection.setImageResource(R.mipmap.collect_on);
                    coinImg.setImageResource(R.mipmap.coin_on);
                }
            }

            @Override
            public void onCancel() {

            }
        });
        like_img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (isSanLian) {
                    coin_circleView.startAnimationProgress(300);
                    Collection_circleView.startAnimationProgress(300);

                    isSanLian = false;
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        MyApplication.getDatabase(getContext()).videoDao().getAll()
                .observe(this, new Observer<VideoDaoBean>() {

                    @Override
                    public void onChanged(VideoDaoBean c) {
                        vid = c.getVideoId();
                        MyApplication.getDatabase(getContext()).userDao().getAll()
                                .observe(IntroductionFragment.this, new Observer<UserBean>() {

                                    @Override
                                    public void onChanged(UserBean userBean) {
                                        if (UserBean == null) {
                                            UserBean = userBean;
                                            mPresenter.getVideoDetails(vid, userBean.getData().getId());
                                            mPresenter.getRelatedVideos();
                                        }
                                    }
                                });
                    }
                });


    }

    @Override
    protected void initLocalData() {
        binding.top.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        if (vId == R.id.function1) {
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
        } else if (vId == R.id.coinImg) {
            if (mCoinNum == 2) {
                XToastUtils.toast("已经投过币拉~");
                return;
            }
            CoinDialog coinDialog = new CoinDialog(getContext(), vid, UserBean.getData().getCoinNum());
            coinDialog.setListener(resultListener);
            coinDialog.show();
        } else if (vId == R.id.like_img) {
            if (mIsPraise) {
                XToastUtils.toast("已经点过赞拉~");
                return;
            }
            dto str = new dto(vid);
            mPresenter.getThumbsUp("http://116.196.105.203:6380/videoservice/video/dynamic_like", str);
        } else if (vId == R.id.Collection) {
            if (isCollection) {
                XToastUtils.toast("已经收藏过拉~");
                return;
            }
            dto str1 = new dto(vid);
            mPresenter.CollectionVideo(str1, UserBean.getData().getId());
        } else if (vId == R.id.In_user_img) {
            Intent intent = new Intent(getContext(), PersonalActivity.class);
            intent.putExtra("uid", uid);
            getContext().startActivity(intent);
        } else if (vId == R.id.In_Attention) {
            mPresenter.Attention(new params(vid), uid);
        }
    }

    @Override
    protected void onRefresh() {

    }

    /**
     * 投币外部监听
     */
    CoinDialog.throwCoinResultListener resultListener = new CoinDialog.throwCoinResultListener() {
        @Override
        public void throwCoinSuccess(CoinBean coinBean) {
//            showDialog();
            XToastUtils.success(coinBean.getMessage());
            mPresenter.getVideoDetails(vid, UserBean.getData().getId());
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
            Log.e("why", "ThumbsUpFail: 出错了" + e);
            XToastUtils.error("操作失败：" + e);
        }
    };

    /**
     * 适配器item点击外部监听
     */
    HotRankingAdapter.ItemOnClickListener listener = new HotRankingAdapter.ItemOnClickListener() {
        @Override
        public void onClick(int position, int vid) {
            Intent intent = new Intent(getContext(), VideoActivity.class);
            intent.putExtra("id", vid);
            VideoActivity videoActivity = (VideoActivity) getActivity();
            videoActivity.getPlayPosition();
            GSYVideoManager.onPause();
            getContext().startActivity(intent);
        }
    };

    @Override
    public void onGetVideoDetailsSuccess(VideoDetailsBean.BeanData videoDetailsBean) {
        Glide.with(this)
                .load(videoDetailsBean.getUpImg())
                .placeholder(R.drawable.skeleton_circular_grey)
                .into(binding.InUserImg);
        praiseNum = videoDetailsBean.getPraiseNum();
        uid = videoDetailsBean.getUid();
        binding.InTitle.setText(videoDetailsBean.getTitle());
        binding.InFans.setText("0粉丝");
        binding.InUserName.setText(videoDetailsBean.getUpName());
        binding.InIntroduce.setText(videoDetailsBean.getDescription());
        mPlayNum.setText(String.valueOf(videoDetailsBean.getPlayNum()));
        mDanmu.setText(String.valueOf(videoDetailsBean.getDanmuNum()));
        mPraise.setText(String.valueOf(videoDetailsBean.getPraiseNum()));
        mCoin.setText(String.valueOf(videoDetailsBean.getCoinNum()));
        It_video_id.setText("BV" + videoDetailsBean.getId());
        String times = videoDetailsBean.getUpdateTime();
        mTime.setText(times.substring(5, 10));
        if (videoDetailsBean.getLog() != null) {
            mIsPraise = videoDetailsBean.getLog().isPraise();
            mCoinNum = videoDetailsBean.getLog().getCoinNum();
            isCollection = videoDetailsBean.getLog().isCollection();
            if (videoDetailsBean.getLog().isPraise())
                like_img.setImageResource(R.drawable.thumb_up_24);
            if (videoDetailsBean.getLog().isCollection())
                Collection.setImageResource(R.mipmap.collect_on);
            if (videoDetailsBean.getLog().getCoinNum() == 2)
                coinImg.setImageResource(R.mipmap.coin_on);
        }
        if (videoDetailsBean.isFollow()) {
            binding.InAttention.setText("已关注");
            binding.InAttention.setBackgroundResource(R.drawable.file_background_follow_gray);
        }
    }

    @Override
    public void onGetVideoDetailsFail(String e) {
        Log.e("why", "错误为" + e);
        //hideDialog();
        XToastUtils.error(R.string.networkError);
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
        mPraise.setText(String.valueOf(praiseNum + 1));
        //mPresenter.getVideoDetails(id, getUserId());
        //showDialog();
    }

    @Override
    public void onGetThumbsUpFail(String e) {
        XToastUtils.error(R.string.errorOccurred + e);
    }

    @Override
    public void onGetCollectionVideoSuccess(CollectionBean collectionBean) {
        isCollection = true;
        Collection.setImageResource(R.mipmap.collect_on);
        /**
         * 这里的收藏数量有问题，需改进
         */
        CollectionNum.setText(String.valueOf(praiseNum + 1));
        XToastUtils.success("收藏成功！");
    }

    @Override
    public void onGetCollectionVideoFail(String e) {
        XToastUtils.error(R.string.errorOccurred + e);
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

    @Override
    public void onGetRelatedVideosSuccess(RecommendVideoBean recommendVideoBean) {
        binding.top.setVisibility(View.VISIBLE);
        adapter.loadMore(recommendVideoBean.getData());
    }

    @Override
    public void onGetRelatedVideosFail(String e) {
        Log.e("why", "错误为---" + e);
        XToastUtils.error(R.string.networkError);
    }

    @Override
    public void onGetAddCommentSuccess(CommentBean commentBean) {

    }

    @Override
    public void onGetAddCommentFail(String e) {

    }

    @Override
    public void onAttentionSuccess(AttentionBean attentionBean) {
        binding.InAttention.setText("已关注");
        binding.InAttention.setBackgroundResource(R.drawable.file_background_follow_gray);
    }

    @Override
    public void onAttentionFail(String e) {
        Log.e("why", "错误为---" + e);
        XToastUtils.error(R.string.networkError);
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
