package com.example.dildil.video.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.ActivityVideoBinding;
import com.example.dildil.util.SharedPreferencesUtil;
import com.example.dildil.util.XToastUtils;
import com.example.dildil.video.bean.CoinBean;
import com.example.dildil.video.bean.SwitchVideoBean;
import com.example.dildil.video.bean.VideoDetailsBean;
import com.example.dildil.video.contract.VideoDetailsContract;
import com.example.dildil.video.fragment_tab.CommentFragment;
import com.example.dildil.video.fragment_tab.IntroductionFragment;
import com.example.dildil.video.presenter.VideoDetailsPresenter;
import com.example.dildil.video.rewriting.DanmakuVideoPlayer;
import com.google.android.material.appbar.AppBarLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.shuyu.gsyvideoplayer.video.base.GSYVideoView.CURRENT_STATE_PAUSE;

public class VideoActivity extends BaseActivity implements VideoDetailsContract.View {
    private static final String TAG = "VideoActivity";
    ActivityVideoBinding binding;
    private String[] TabTitle = {"简介", "评论"};
    private ArrayList<Fragment> mFragments;
    private OrientationUtils orientationUtils;
    boolean isPlay;
    boolean isPause;
    boolean isDestory;
    private int mWhenPlaying;
    private CollapsingToolbarLayoutState state;
    private ImageView imageView;
    private int id,uid;
    private List<SwitchVideoBean> urls = new ArrayList<>();
    String[] definition = {"360p","480p","720p","1080p"};

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
        int playtime = intent.getIntExtra("playtime", 0);
        id = intent.getIntExtra("id",0);
        uid = intent.getIntExtra("uid",0);
        SharedPreferencesUtil.putData("id",id);
        SharedPreferencesUtil.putData("uid",uid);
        if (playtime != 0) {
            mWhenPlaying = playtime;
            binding.detailPlayer.setSeekOnStart(mWhenPlaying);
            binding.detailPlayer.startPlayLogic();
            XToastUtils.info("已为您保存播放进度");
        }
    }

    @Override
    protected void initView() {
        showDialog();
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

        binding.detailPlayer.setIsTouchWiget(true);
        //关闭自动旋转
        binding.detailPlayer.setRotateViewAuto(false);
        binding.detailPlayer.setLockLand(false);
        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_16_9);
        binding.detailPlayer.setShowFullAnimation(false);
        binding.detailPlayer.setNeedLockFull(true);
        binding.detailPlayer.setListener(listener);

        //detailPlayer.setOpenPreView(true);
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
                getDanmu();
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
                if (orientationUtils != null) {
                    orientationUtils.backToProtVideo();
                }
            }

            @Override
            public void onClickStartIcon(String url, Object... objects) {
                super.onClickStartIcon(url, objects);
                banAppBarScroll(false);
            }

            @Override
            public void onClickStop(String url, Object... objects) {
                super.onClickStop(url, objects);
                banAppBarScroll(true);
            }

            @Override
            public void onClickResume(String url, Object... objects) {
                super.onClickResume(url, objects);
                binding.playButton.setVisibility(View.GONE);
                binding.appbar.setExpanded(true);
                banAppBarScroll(false);
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
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                            binding.playButton.setVisibility(View.GONE);//由折叠变为中间状态时隐藏播放按钮
                        }
                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                    }
                }
            }
        });
        setMargins(binding.toolbar, 0, getStatusBarHeight(this), 0, 0);
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
        binding.VTab.setViewPager(binding.VViewPager, TabTitle, this, mFragments);
        binding.detailPlayer.setShrinkImageRes(R.drawable.crop_free_24);
        binding.detailPlayer.setEnlargeImageRes(R.drawable.crop_free_24);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        mPresenter.getVideoDetails(id,uid);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.V_DanmakuShow:
                DanmakuShow();
                break;
            case R.id.V_definition_text:
                binding.detailPlayer.addDanmaku(true);
                break;
            case R.id.playButton:
                binding.detailPlayer.onVideoResume();
                binding.appbar.setExpanded(true);
                banAppBarScroll(false);
                break;
            case R.id.keyboard:
                finish();
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

    //获取弹幕
    private void getDanmu() {
        DanmakuVideoPlayer currentPlayer = (DanmakuVideoPlayer) binding.detailPlayer.getCurrentPlayer();
        File file = new File("text");
        currentPlayer.setDanmaKuStream(file);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        if (isPlay) {
            getCurPlay().release();
        }
        //GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
        isDestory = true;
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
            mAppBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
            mAppBarChildAt.setLayoutParams(mAppBarParams);
        } else {
            mAppBarParams.setScrollFlags(0);
        }
    }

    @Override
    public void onGetVideoDetailsSuccess(VideoDetailsBean.BeanData videoDetailsBean) {
        Glide.with(mContext).load(videoDetailsBean.getCover()).into(imageView);
        binding.detailPlayer.setThumbImageView(imageView);
        String[] urlList=videoDetailsBean.getUrls().split(",");
        for (int i = 0; i < urlList.length; i++) {
            SwitchVideoBean switchVideoBean = new SwitchVideoBean(definition[i],urlList[i]);
            urls.add(switchVideoBean);
        }
        binding.detailPlayer.setUp(urls, true, null, videoDetailsBean.getTitle());
        hideDialog();
    }

    @Override
    public void onGetVideoDetailsFail(String e) {
        XToastUtils.error(e);
    }

    @Override
    public void onGetCoinOperatedSuccess(CoinBean coinBean) {

    }

    @Override
    public void onGetCoinOperatedFail(String e) {

    }
}