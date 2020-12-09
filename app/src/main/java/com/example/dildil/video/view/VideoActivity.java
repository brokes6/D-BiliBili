package com.example.dildil.video.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.example.customcontrollibs.Selector;
import com.example.customcontrollibs.SelectorGroup;
import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.base.UserDaoOperation;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.ActivityVideoBinding;
import com.example.dildil.dynamic_page.bean.AttentionBean;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.login_page.bean.UserBean;
import com.example.dildil.my_page.bean.HistoryBean;
import com.example.dildil.util.DateUtils;
import com.example.dildil.util.DensityUtil;
import com.example.dildil.util.NetUtil;
import com.example.dildil.util.XToastUtils;
import com.example.dildil.video.bean.CoinBean;
import com.example.dildil.video.bean.CollectionBean;
import com.example.dildil.video.bean.CommentBean;
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
import com.shuyu.gsyvideoplayer.cache.CacheFactory;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;
import tv.danmaku.ijk.media.exo2.ExoPlayerCacheManager;

import static com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED;
import static com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL;
import static com.shuyu.gsyvideoplayer.video.base.GSYVideoView.CURRENT_STATE_AUTO_COMPLETE;
import static com.shuyu.gsyvideoplayer.video.base.GSYVideoView.CURRENT_STATE_NORMAL;
import static com.shuyu.gsyvideoplayer.video.base.GSYVideoView.CURRENT_STATE_PAUSE;

public class VideoActivity extends BaseActivity implements VideoDetailsContract.View, Selector.OnSelectorStateListener {
    private static final String TAG = "VideoActivity";
    private ActivityVideoBinding binding;
    private String[] TabTitle = {};
    private ArrayList<Fragment> mFragments;
    private OrientationUtils orientationUtils;
    private int mWhenPlaying;
    private CollapsingToolbarLayoutState state;
    private int id;
    private int uid;
    private int timing = 0;
    private final List<SwitchVideoBean> urls = new ArrayList<>();
    private final SelectorGroup selectorGroup = new SelectorGroup();
    private int textSize;
    private boolean isFunction = true;
    private final String[] definition = {"360p", "480p", "720p", "1080p"};
    private Handler handler;
    private String imageUrl, Title;
    private int playtime;

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
        ifGO();
    }

    private void ifGO() {
        Intent intent = getIntent();
        playtime = intent.getIntExtra("playtime", 0);
        id = intent.getIntExtra("id", 0);
        UserDaoOperation operation = UserDaoOperation.getDatabase(this);
        operation.UpVideoDetail(new VideoDaoBean(1, id));
    }

    @Override
    protected void initView() {
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
        //关闭自动旋转
        binding.detailPlayer.setRotateViewAuto(false);
        binding.detailPlayer.setNeedAutoAdaptation(true);
        binding.detailPlayer.setShowFullAnimation(false);
        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_16_9);
        binding.detailPlayer.setNeedLockFull(true);
        binding.detailPlayer.setListener(listener);

        PlayerFactory.setPlayManager(Exo2PlayerManager.class);
        CacheFactory.setCacheManager(ExoPlayerCacheManager.class);

//        List<VideoOptionModel> list = new ArrayList<>();
//        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-hevc", 1));
//        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_transport", "tcp"));
//        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "enable-accurate-seek", 1));
//        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "reconnect", 5));
//        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "dns_cache_clear", 1));
//        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "dns_cache_timeout", -1));
//        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "max-buffer-size", 100 * 1024));
//        IjkPlayerManager.setLogLevel(IjkMediaPlayer.IJK_LOG_SILENT);
//        GSYVideoManager.instance().setOptionModelList(list);


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
                handler.postDelayed(runnable, 10000);
            }

            @Override
            public void onClickStop(String url, Object... objects) {
                super.onClickStop(url, objects);
                binding.keyboard.setVisibility(View.VISIBLE);
                binding.more.setVisibility(View.VISIBLE);
                if (binding.detailPlayer.getVideoType() == 1) {
                    banAppBarScroll(true);
                }
                handler.removeCallbacks(runnable);
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
                handler.postDelayed(runnable, 10000);
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
                binding.VDanmakuShow.setImageResource(R.drawable.ic_definition);
            }
        }
    };

    @Override
    protected void initData() {
        handler = new Handler();
        binding.detailPlayer.setShrinkImageRes(R.drawable.crop_free_24);
        binding.detailPlayer.setEnlargeImageRes(R.drawable.crop_free_24);
        MyApplication.getDatabase(this).userDao().getAll()
                .observe(this, new Observer<UserBean>() {

                    @Override
                    public void onChanged(UserBean userBean) {
                        Log.e(TAG, "onChanged: 第一次加载呢");
                        uid = userBean.getData().getId();
                        binding.detailPlayer.setVideoDetails(id, uid);
                        mPresenter.getDanMu(0, id);
                        mPresenter.getVideoDetails(id, uid);
                    }
                });
    }


    @Override
    public void onClick(View v) {
        int vId = v.getId();
        if (vId == R.id.V_DanmakuShow) {
            DanmakuShow();
        } else if (vId == R.id.V_definition_text) {
            sendOutDanMu();
        } else if (vId == R.id.playButton) {
            if (binding.detailPlayer.getCurrentState() == 1) {
                binding.detailPlayer.startAfterPrepared();
            } else {
                binding.detailPlayer.onVideoResume();
                binding.appbar.setExpanded(true);
                binding.more.setVisibility(View.GONE);
                binding.keyboard.setVisibility(View.GONE);
            }
            banAppBarScroll(false);
        } else if (vId == R.id.keyboard) {
            finish();
        }
    }

    private void sendOutDanMu() {
        BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.BottomSheetEdit);
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
                    binding.detailPlayer.addDanmaku(false, replyContent, textSize);
                    mPresenter.seadDanMu(new danmu(replyContent, binding.detailPlayer.getCurrentPositionWhenPlaying(), uid, id), id);
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
            binding.VDanmakuShow.setImageResource(R.drawable.ic_definition);
            binding.detailPlayer.openDanmaku();
        }
    }

    private void resolveNormalVideoUI() {
        //增加title
        binding.detailPlayer.getTitleTextView().setVisibility(View.GONE);
        binding.detailPlayer.getBackButton().setVisibility(View.GONE);
    }

    public void getPlayPosition() {
        mWhenPlaying = binding.detailPlayer.getCurrentPositionWhenPlaying();
        Log.e(TAG, "当前播放位置为:" + mWhenPlaying);
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
        imageUrl = videoDetailsBean.getCover();
        Title = videoDetailsBean.getTitle();
        TabTitle = new String[]{"简介", "评论" + videoDetailsBean.getCommentNum()};
        String[] urlList = videoDetailsBean.getUrls().split(",");
        for (int i = 0; i < urlList.length; i++) {
            SwitchVideoBean switchVideoBean = new SwitchVideoBean(definition[i], urlList[i]);
            urls.add(switchVideoBean);
        }
        binding.detailPlayer.setUPData(videoDetailsBean.getUpImg(), videoDetailsBean.getUpName());
        binding.detailPlayer.setUp(urls, false, videoDetailsBean.getTitle());
        JudgeVideoType(videoDetailsBean.getScreenType());
    }

    private void JudgeVideoType(String valueType) {
        if (valueType.equals("PORTRAIT")) {
            binding.detailPlayer.setAutoFullWithSize(true);
            binding.detailPlayer.setPadding(60, 0, 60, 0);
            binding.detailPlayer.setVideoType(2);
            binding.videoDanmu.getLayoutParams().height = DensityUtil.getScreenRelatedInformation(this) * 3 / 4;
        } else {
            binding.detailPlayer.setVideoType(1);
        }
        View vg = Objects.requireNonNull(binding.VViewPager.getViewStub()).inflate();
        ViewPager viewGroup = vg.findViewById(R.id.viewPager);
        binding.VTab.setViewPager(viewGroup, TabTitle, this, mFragments);

        if (playtime != 0) {
            mWhenPlaying = playtime;
            binding.detailPlayer.setSeekOnStart(mWhenPlaying);
            binding.detailPlayer.startPlayLogic();
            banAppBarScroll(false);
            XToastUtils.info(R.string.saveProgress);
        } else if (NetUtil.isWifi()) {
            binding.detailPlayer.startPlayLogic();
            banAppBarScroll(false);
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
        binding.detailPlayer.addDanmaKuExternal(danmuBean.getData());
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            timing += 10000;
            mPresenter.getDanMu(timing, id);
            handler.postDelayed(this, 10000);
        }
    };


    @Override
    public void onGetDanMuFail(String e) {
        handler.removeCallbacks(runnable);
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
    public void onGetAddCommentSuccess(CommentBean commentBean) {

    }

    @Override
    public void onGetAddCommentFail(String e) {

    }

    @Override
    public void onAttentionSuccess(AttentionBean attentionBean) {

    }

    @Override
    public void onAttentionFail(String e) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //GSYVideoManager.onResume();
        Log.e(TAG, "onResume: 当前url为"+urls.toString() );
        int play = binding.detailPlayer.getCurrentState();
        if (play == CURRENT_STATE_PAUSE) {
            binding.detailPlayer.setSeekOnStart(mWhenPlaying);
            binding.detailPlayer.startPlayLogic();
        }
    }

    @Override
    public void onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        UserDaoOperation.getDatabase(this).insertHistory(new HistoryBean(id,
                imageUrl, Title,
                DateUtils.getCurrentTimestamp(),
                binding.detailPlayer.getCurrentPositionWhenPlaying(),
                binding.detailPlayer.getDuration()));
        if (isFinishing()) {
            handler.removeCallbacks(runnable);
            mPresenter.detachView();
            GSYVideoManager.releaseAllVideos();
            if (orientationUtils != null) orientationUtils.releaseListener();
        } else {
            GSYVideoManager.onPause();
            getPlayPosition();
        }
    }
}