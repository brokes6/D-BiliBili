package com.example.dildil.video.view;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.customcontrollibs.Selector;
import com.example.customcontrollibs.SelectorGroup;
import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.AppDatabase;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.ActivityVideoBinding;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.util.DensityUtil;
import com.example.dildil.util.ViewWrapper;
import com.example.dildil.util.XToastUtils;
import com.example.dildil.video.bean.CoinBean;
import com.example.dildil.video.bean.CollectionBean;
import com.example.dildil.video.bean.CommentDetailBean;
import com.example.dildil.video.bean.DanmuBean;
import com.example.dildil.video.bean.SeadDanmuBean;
import com.example.dildil.video.bean.SwitchVideoBean;
import com.example.dildil.video.bean.ThumbsUpBean;
import com.example.dildil.video.bean.VideoDaoBean;
import com.example.dildil.video.bean.VideoDetailsBean;
import com.example.dildil.video.bean.danmu;
import com.example.dildil.video.contract.VideoDetailsContract;
import com.example.dildil.video.fragment_tab.CommentFragment;
import com.example.dildil.video.fragment_tab.IntroductionFragment;
import com.example.dildil.video.presenter.VideoDetailsPresenter;
import com.example.dildil.video.rewriting.DanmakuVideoPlayer;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED;
import static com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL;
import static com.shuyu.gsyvideoplayer.utils.GSYVideoType.SCREEN_TYPE_CUSTOM;
import static com.shuyu.gsyvideoplayer.video.base.GSYVideoView.CURRENT_STATE_AUTO_COMPLETE;
import static com.shuyu.gsyvideoplayer.video.base.GSYVideoView.CURRENT_STATE_NORMAL;
import static com.shuyu.gsyvideoplayer.video.base.GSYVideoView.CURRENT_STATE_PAUSE;

public class VideoActivity extends BaseActivity implements VideoDetailsContract.View, Selector.OnSelectorStateListener {
    private static final String TAG = "VideoActivity";
    private ActivityVideoBinding binding;
    private final String[] TabTitle = {"简介", "评论"};
    private ArrayList<Fragment> mFragments;
    private OrientationUtils orientationUtils;
    boolean isPlay;
    boolean isDestory;
    private int mWhenPlaying;
    private CollapsingToolbarLayoutState state;
    private ImageView imageView;
    private int id, uid;
    private List<SwitchVideoBean> urls = new ArrayList<>();
    private BottomSheetDialog dialog;
    private final SelectorGroup selectorGroup = new SelectorGroup();
    private int textSize;
    private boolean isFunction = true;
    private final String[] definition = {"360p", "480p", "720p", "1080p"};
    private DrawableCrossFadeFactory factory;
    private AppDatabase dp;

    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    @Inject
    VideoDetailsPresenter mPresenter;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video);
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(false)
                .statusBarColor(R.color.Black)
                .init();
        DaggerActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
        mPresenter.attachView(this);
        dp = MyApplication.getDatabase();
        ifGO();
    }

    private void ifGO() {
        Intent intent = getIntent();
        int playtime = intent.getIntExtra("playtime", 0);
        id = intent.getIntExtra("id", 0);
        if (dp.videoDao().getAll() != null) {
            dp.videoDao().updateVideo(new VideoDaoBean(1,id));
        } else {
            dp.videoDao().insertAll(new VideoDaoBean(1,id));
        }
        if (playtime != 0) {
            mWhenPlaying = playtime;
            binding.detailPlayer.setSeekOnStart(mWhenPlaying);
            binding.detailPlayer.startPlayLogic();
            XToastUtils.info(R.string.saveProgress);
        }
    }

    @Override
    protected void initView() {
        //showDialog();
        imageView = new ImageView(mContext);
        mFragments = new ArrayList<>();
        mFragments.add(new IntroductionFragment());
        mFragments.add(new CommentFragment());

        binding.playButton.setOnClickListener(this);
        binding.VDanmakuShow.setOnClickListener(this);
        binding.VDefinitionText.setOnClickListener(this);
        binding.keyboard.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        //使用自定义的全屏切换图片，!!!注意xml布局中也需要设置为一样的(已取消)
        //必须在setUp之前设置(已取消)
        resolveNormalVideoUI();
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, binding.detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        binding.detailPlayer.setSeekRatio(1.5f);

        binding.detailPlayer.setIsTouchWiget(true);
        binding.detailPlayer.setVideoDetails(uid, id);
        //关闭自动旋转
        binding.detailPlayer.setRotateViewAuto(false);
        binding.detailPlayer.setNeedAutoAdaptation(true);
        binding.detailPlayer.setLockLand(false);
        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_16_9);
        binding.detailPlayer.setShowFullAnimation(false);
        binding.detailPlayer.setNeedLockFull(true);
        binding.detailPlayer.setListener(listener);

        binding.detailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                binding.detailPlayer.startWindowFullscreen(VideoActivity.this, true, true);
            }
        });

        binding.detailPlayer.setVideoAllCallBack(new GSYSampleCallBack() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                //开始播放了才能旋转和全屏
                orientationUtils.setEnable(true);
                isPlay = true;
                DanmakuVideoPlayer currentPlayer = (DanmakuVideoPlayer) binding.detailPlayer.getCurrentPlayer();
                currentPlayer.setDanmaKuStream();
            }

            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);
            }

            @Override
            public void onClickStartError(String url, Object... objects) {
                super.onClickStartError(url, objects);
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
                if (binding.detailPlayer.getCurrentState() != CURRENT_STATE_AUTO_COMPLETE && binding.detailPlayer.getCurrentState() != CURRENT_STATE_NORMAL && binding.detailPlayer.getVideoType() == 1) {
                    banAppBarScroll(false);
                }
                if (orientationUtils != null) {
                    orientationUtils.backToProtVideo();
                }
            }

            @Override
            public void onClickStartIcon(String url, Object... objects) {
                super.onClickStartIcon(url, objects);
                binding.keyboard.setVisibility(View.GONE);
                binding.more.setVisibility(View.GONE);
                if (binding.detailPlayer.getVideoType() == 1) {
                    banAppBarScroll(false);
                }
            }

            @Override
            public void onClickStop(String url, Object... objects) {
                super.onClickStop(url, objects);
                binding.keyboard.setVisibility(View.VISIBLE);
                binding.more.setVisibility(View.VISIBLE);
                if (binding.detailPlayer.getVideoType() == 1) {
                    banAppBarScroll(true);
                }
            }

            @Override
            public void onClickResume(String url, Object... objects) {
                super.onClickResume(url, objects);
                binding.keyboard.setVisibility(View.GONE);
                binding.more.setVisibility(View.GONE);
                binding.playButton.setVisibility(View.GONE);
                binding.appbar.setExpanded(true);
                if (binding.detailPlayer.getVideoType() == 1) {
                    banAppBarScroll(false);
                }
            }
        });


        binding.detailPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        });
        binding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                        binding.playButton.setVisibility(View.GONE);//由折叠变为中间状态时隐藏播放按钮
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        binding.coll.setContentScrimColor(getResources().getColor(R.color.Pink));
                        binding.playButton.setVisibility(View.VISIBLE);//隐藏播放按钮
                        state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                        if (binding.detailPlayer.getVideoType() == 2) {
                            GSYVideoManager.onPause();
                        }
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                            binding.playButton.setVisibility(View.GONE);//由折叠变为中间状态时隐藏播放按钮
                            if (binding.detailPlayer.getVideoType() == 2) {
                                GSYVideoManager.onResume();
                            }
                        }
                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                    }
                }
            }
        });
        setMargins(binding.main, 0, getStatusBarHeight(this), 0, 0);
    }

    DanmakuVideoPlayer.FullScreenStatusMonitoring listener = new DanmakuVideoPlayer.FullScreenStatusMonitoring() {
        @Override
        public void StateChange(boolean isFullScreen) {
            if (isFullScreen) {
                binding.VDanmakuShow.setImageResource(R.mipmap.definition_off);
            } else {
                binding.VDanmakuShow.setImageResource(R.mipmap.definition);
            }
        }
    };

    @Override
    protected void initData() {
        factory = new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
        binding.VTab.setViewPager(binding.VViewPager, TabTitle, this, mFragments);
        binding.detailPlayer.setShrinkImageRes(R.drawable.crop_free_24);
        binding.detailPlayer.setEnlargeImageRes(R.drawable.crop_free_24);
        imageView.setScaleType(ImageView.ScaleType.CENTER);

        mPresenter.getDanMu(0, id);
        mPresenter.getVideoDetails(id, uid);
    }


    @Override
    public void onClick(View v) {
        int vId = v.getId();
        if (vId == R.id.V_DanmakuShow) {
            DanmakuShow();
        } else if (vId == R.id.V_definition_text) {
            sendOutDanMu();
        } else if (vId == R.id.playButton) {
            binding.detailPlayer.onVideoResume();
            binding.appbar.setExpanded(true);
            binding.more.setVisibility(View.GONE);
            binding.keyboard.setVisibility(View.GONE);
            banAppBarScroll(false);
        } else if (vId == R.id.keyboard) {
            finish();
        }
    }

    private void sendOutDanMu() {
        dialog = new BottomSheetDialog(this, R.style.BottomSheetEdit);
        View commentView = LayoutInflater.from(this).inflate(R.layout.danmu_comment_out, null);
        final EditText commentText = commentView.findViewById(R.id.DM_dialog_comment_et);
        final ImageView send = commentView.findViewById(R.id.DM_send);
        final ImageView rotation = commentView.findViewById(R.id.DM_text_rotation);
        final RelativeLayout function = commentView.findViewById(R.id.function);
        final Selector teenageSelector = commentView.findViewById(R.id.text_up);
        final Selector manSelector = commentView.findViewById(R.id.text_no);
        teenageSelector.setOnSelectorStateListener(this).setSelectorGroup(selectorGroup);
        manSelector.setOnSelectorStateListener(this).setSelectorGroup(selectorGroup);
        commentText.setFocusable(true);
        commentText.setFocusableInTouchMode(true);
        commentText.requestFocus();
        dialog.setContentView(commentView);
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0, 0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());
        rotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFunction) {
                    function.setVisibility(View.VISIBLE);
                    isFunction = true;
                } else {
                    function.setVisibility(View.GONE);
                    isFunction = false;
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(replyContent)) {
                    dialog.dismiss();
                    binding.detailPlayer.addDanmaku(true, replyContent, textSize);
                    danmu danmu = new danmu(replyContent, binding.detailPlayer.getCurrentPositionWhenPlaying(), uid, id);
                    mPresenter.seadDanMu(danmu, id);
                } else {
                    XToastUtils.toast("弹幕内容不能为空");
                }
            }
        });
        dialog.show();

    }

    @Override
    public void onStateChange(Selector selector, boolean isSelect) {
        String tag = selector.getTag();
        int FINEPRINT = 2;
        int LARGEFONT = 1;
        switch (tag) {
            case "text_up":
                textSize = LARGEFONT;
                break;
            case "text_no":
                textSize = FINEPRINT;
                break;
        }
    }

    private void DanmakuShow() {
        if (binding.detailPlayer.getDanmaKuShow()) {
            binding.VDanmakuShow.setImageResource(R.mipmap.definition_off);
            binding.detailPlayer.offDanmaku();
        } else {
            binding.VDanmakuShow.setImageResource(R.mipmap.definition);
            binding.detailPlayer.openDanmaku();
        }
    }

    private void resolveNormalVideoUI() {
        //增加title
        binding.detailPlayer.getTitleTextView().setVisibility(View.GONE);
        binding.detailPlayer.getBackButton().setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int play = binding.detailPlayer.getCurrentState();
        if (play == CURRENT_STATE_PAUSE) {
            binding.detailPlayer.setSeekOnStart(mWhenPlaying);
            binding.detailPlayer.startPlayLogic();
        }
    }

    public void getPlayPosition() {
        mWhenPlaying = binding.detailPlayer.getCurrentPositionWhenPlaying();
        Log.e(TAG, "当前播放位置为:" + mWhenPlaying);
    }

    public GSYVideoPlayer getCurPlay() {
        if (binding.detailPlayer.getFullWindowPlayer() != null) {
            return binding.detailPlayer.getFullWindowPlayer();
        }
        return binding.detailPlayer;
    }

    /**
     * 控制appbar的滑动
     *
     * @param isScroll true 允许滑动 false 禁止滑动
     */
    private void banAppBarScroll(boolean isScroll) {
        View mAppBarChildAt = binding.appbar.getChildAt(0);
        AppBarLayout.LayoutParams mAppBarParams = (AppBarLayout.LayoutParams) mAppBarChildAt.getLayoutParams();
        if (isScroll) {
            mAppBarParams.setScrollFlags(SCROLL_FLAG_SCROLL | SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
            mAppBarChildAt.setLayoutParams(mAppBarParams);
        } else {
            mAppBarParams.setScrollFlags(0);
        }
    }

    @Override
    public void onGetVideoDetailsSuccess(VideoDetailsBean.BeanData videoDetailsBean) {
        JudgeVideoType(videoDetailsBean.getScreenType());
        Glide.with(mContext).load(videoDetailsBean.getCover()).transition(withCrossFade(factory)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.skeleton_circular_grey).into(imageView);
        binding.detailPlayer.setThumbImageView(imageView);
        String[] urlList = videoDetailsBean.getUrls().split(",");
        for (int i = 0; i < urlList.length; i++) {
            SwitchVideoBean switchVideoBean = new SwitchVideoBean(definition[i], urlList[i]);
            urls.add(switchVideoBean);
        }
        binding.detailPlayer.setUPData(videoDetailsBean.getUpImg(), videoDetailsBean.getUpName());
        binding.detailPlayer.setUp(urls, true, null, videoDetailsBean.getTitle());
    }

    private void JudgeVideoType(String valueType) {
        if (valueType.equals("PORTRAIT")) {
            binding.detailPlayer.setAutoFullWithSize(true);
            binding.videoDanmu.setPadding(60, 0, 60, 0);
            GSYVideoType.setScreenScaleRatio(16f / 9f);
            GSYVideoType.setShowType(SCREEN_TYPE_CUSTOM);
            binding.detailPlayer.setVideoType(2);
            ViewWrapper wrapper = new ViewWrapper(binding.videoDanmu);
            ObjectAnimator animator = ObjectAnimator.ofInt(wrapper, "height", DensityUtil.getScreenRelatedInformation(this) * 3 / 4);
            animator.setDuration(500);
            animator.start();
        } else {
            binding.detailPlayer.setVideoType(1);
        }
    }

    @Override
    public void onGetVideoDetailsFail(String e) {
        XToastUtils.error(R.string.errorOccurred + e);
        //hideDialog();
    }

    @Override
    public void onGetCoinOperatedSuccess(CoinBean coinBean) {

    }

    @Override
    public void onGetCoinOperatedFail(String e) {

    }

    @Override
    public void onGetThumbsUpSuccess(ThumbsUpBean thumbsUpBean) {

    }

    @Override
    public void onGetThumbsUpFail(String e) {

    }

    @Override
    public void onGetCollectionVideoSuccess(CollectionBean collectionBean) {

    }

    @Override
    public void onGetCollectionVideoFail(String e) {

    }

    @Override
    public void onGetVideoCommentSuccess(CommentDetailBean commentDetailBean) {

    }

    @Override
    public void onGetVideoCommentFail(String e) {

    }

    @Override
    public void onGetDanMuSuccess(DanmuBean danmuBean) {
        binding.detailPlayer.setDanmuData(danmuBean.getData());
    }

    @Override
    public void onGetDanMuFail(String e) {
        XToastUtils.error("获取弹幕出错：" + e);
    }

    @Override
    public void onGetSeadDanMuSuccess(SeadDanmuBean seadDanmuBean) {
        XToastUtils.success("发送弹幕成功!");
    }

    @Override
    public void onGetSedaDanMuFail(String e) {
        XToastUtils.error("弹幕发送失败:" + e);
    }

    @Override
    public void onGetRelatedVideosSuccess(RecommendVideoBean recommendVideoBean) {

    }

    @Override
    public void onGetRelatedVideosFail(String e) {

    }

    @Override
    public void onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        dialog = null;
        mFragments.clear();
        mFragments = null;
        urls.clear();
        urls = null;
        if (isPlay) {
            getCurPlay().release();
        }
        if (orientationUtils != null)
            orientationUtils.releaseListener();
        isDestory = true;
        GSYVideoManager.releaseAllVideos();
        super.onDestroy();
    }
}