package com.example.dildil.video.fragment_tab;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.customcontrollibs.AnnularProgressButton;
import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.FragmentIntroductionBinding;
import com.example.dildil.home_page.adapter.HotRankingAdapter;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.login_page.bean.LoginBean;
import com.example.dildil.my_page.view.PersonalActivity;
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
    private FragmentIntroductionBinding binding;
    private HotRankingAdapter adapter;
    private int id;
    private int uid;
    private TextView mTime, mDanmu, mPlayNum, mPraise, mCoin, CollectionNum;
    private ImageView like_img, Collection, coinImg;
    private boolean isOpen = false;
    private LinearLayout ForwardMain;
    private AnnularProgressButton coin_circleView, Collection_circleView;
    private boolean isLoad = false;
    private boolean mIsPraise = false;
    private int mCoinNum = 0;
    private boolean isCollection = false;
    private boolean isSanLian = true;

    @Inject
    VideoDetailsPresenter mPresenter;
    private LoginBean loginBean;

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
        adapter = new HotRankingAdapter(getContext());
        adapter.setListener(listener);
        binding.InRecyclerView.setAdapter(adapter);

        getIncludeView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void getIncludeView() {
        mTime = binding.InVideoData.findViewById(R.id.It_video_time);
        mDanmu = binding.InVideoData.findViewById(R.id.It_barrage_num);
        mPlayNum = binding.InVideoData.findViewById(R.id.It_play_num);
        mPraise = binding.Sanlian.findViewById(R.id.praise);
        mCoin = binding.Sanlian.findViewById(R.id.coin);
        like_img = binding.Sanlian.findViewById(R.id.like_img);
        Collection = binding.Sanlian.findViewById(R.id.Collection);
        CollectionNum = binding.Sanlian.findViewById(R.id.CollectionNum);
        ForwardMain = binding.Sanlian.findViewById(R.id.ForwardMain);
        coinImg = binding.Sanlian.findViewById(R.id.coinImg);
        coin_circleView = binding.Sanlian.findViewById(R.id.coin_circleView);
        Collection_circleView = binding.Sanlian.findViewById(R.id.Collection_circleView);

        binding.function1.setOnClickListener(this);
        like_img.setOnClickListener(this);
        coinImg.setOnClickListener(this);
        Collection.setOnClickListener(this);
        ForwardMain.setOnClickListener(this);
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

        /**
         * 这里是因为setOnTouchListener最好在自定义View来覆写
         * 但是我为了达到统一的效果，只能写在这里
         * 上面已经加了@SuppressLint("ClickableViewAccessibility")用来取消警告
         */

//        like_img.setOnTouchListener(new View.OnTouchListener() {
//            private long downTime;
//            private long upTime;
//            private boolean mIsLongPressed = false;
//            private float mLastMotionX, mLastMotionY;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        downTime = System.currentTimeMillis();
//                        Log.e(TAG, "onTouch: 按下" + downTime);
//                        mLastMotionX = event.getX();
//                        mLastMotionY = event.getX();
//                        if (mIsPraise) {
//                            XToastUtils.toast("已经点过赞拉~");
//                        } else {
//                            dto str = new dto(id);
//                            mPresenter.getThumbsUp("http://116.196.105.203/videoservice/video/dynamic_like", str);
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        Log.e(TAG, "onTouch: 抬起");
//                        if (coin_circleView.mProgress >= 300) {
//                            //完成长按
//                            like_img.setImageResource(R.drawable.thumb_up_24);
//                            Collection.setImageResource(R.mipmap.collect_on);
//                            coinImg.setImageResource(R.mipmap.coin_on);
//                            /**
//                             * 这里进行三连操作
//                             */
//                            return false;
//                        }
//                        //取消长按
//                        coin_circleView.stopAnimationProgress(coin_circleView.mProgress);
//                        Collection_circleView.stopAnimationProgress(Collection_circleView.mProgress);
//
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        Log.e(TAG, "onTouch: 移动");
//                        upTime = System.currentTimeMillis();
//                        if (!mIsLongPressed) {
//                            mIsLongPressed = isLongPressed(mLastMotionX, mLastMotionY, event.getX(), event.getY(), downTime, upTime, 500);
//                        }
//                        if (mIsLongPressed) {
//                            //长按模式所做的事
//                            coin_circleView.startAnimationProgress(300);
//                            Collection_circleView.startAnimationProgress(300);
//
//                        } else {
//                            //移动模式所做的事
//                        }
//                        break;
//                }
//                return true;
//            }
//        });
    }

//    private static boolean isLongPressed(float lastX, float lastY, float thisX,
//                                         float thisY, long lastDownTime, long thisEventTime,
//                                         long longPressTime) {
//        float offsetX = Math.abs(thisX - lastX);
//        float offsetY = Math.abs(thisY - lastY);
//        long intervalTime = thisEventTime - lastDownTime;
//        if (offsetX <= 10 && offsetY <= 10 && intervalTime >= longPressTime) {
//            return true;
//        }
//        return false;
//    }

    @Override
    protected void initData() {
        loginBean = getUserData();
        id = (int) SharedPreferencesUtil.getData("id", 0);
        uid = (int) SharedPreferencesUtil.getData("uid", 0);
        mPresenter.getVideoDetails(id, uid);
        mPresenter.getRelatedVideos();
    }

    @Override
    protected void initLocalData() {
        //binding.top.setVisibility(View.VISIBLE);
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
            CoinDialog coinDialog = new CoinDialog(getContext(), id, loginBean.getData().getCoinNum());
            coinDialog.setListener(resultListener);
            coinDialog.show();
        } else if (vId == R.id.like_img) {
            if (mIsPraise) {
                XToastUtils.toast("已经点过赞拉~");
                return;
            }
            dto str = new dto(id);
            mPresenter.getThumbsUp("http://116.196.105.203/videoservice/video/dynamic_like", str);
        } else if (vId == R.id.Collection) {
            if (isCollection) {
                XToastUtils.toast("已经收藏过拉~");
                return;
            }
            dto str1 = new dto(id);
            mPresenter.CollectionVideo(str1);
        } else if (vId == R.id.In_user_img) {
            ActivityUtils.startActivity(PersonalActivity.class);
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
        public void onClick(int position, int vid) {
            Intent intent = new Intent(getContext(), VideoActivity.class);
            intent.putExtra("id", vid);
            intent.putExtra("uid", 1);
            VideoActivity videoActivity = (VideoActivity) getActivity();
            videoActivity.getPlayPosition();
            GSYVideoManager.onPause();
            getContext().startActivity(intent);
        }
    };

    @Override
    public void onGetVideoDetailsSuccess(VideoDetailsBean.BeanData videoDetailsBean) {
        binding.InTitle.setText(videoDetailsBean.getTitle());
        Glide.with(this).load(videoDetailsBean.getUpImg()).into(binding.InUserImg);
        binding.InUserName.setText(videoDetailsBean.getUpName());
        binding.InWarning.setText(videoDetailsBean.getDescription());
        mPlayNum.setText(String.valueOf(videoDetailsBean.getPlayNum()));
        mDanmu.setText(String.valueOf(videoDetailsBean.getDanmuNum()));
        mPraise.setText(String.valueOf(videoDetailsBean.getPraiseNum()));
        mCoin.setText(String.valueOf(videoDetailsBean.getCoinNum()));
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
        isLoad = true;
    }

    @Override
    public void onGetVideoDetailsFail(String e) {
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
        mPresenter.getVideoDetails(id, uid);
        showDialog();
    }

    @Override
    public void onGetThumbsUpFail(String e) {
        XToastUtils.error(R.string.errorOccurred + e);
    }

    @Override
    public void onGetCollectionVideoSuccess(CollectionBean collectionBean) {
        isCollection = true;
        Collection.setImageResource(R.mipmap.collect_on);
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
        XToastUtils.error(R.string.networkError);
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
