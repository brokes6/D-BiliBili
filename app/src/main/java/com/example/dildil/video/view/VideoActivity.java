package com.example.dildil.video.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.databinding.ActivityVideoBinding;
import com.example.dildil.video.fragment_tab.CommentFragment;
import com.example.dildil.video.fragment_tab.IntroductionFragment;
import com.example.dildil.video.rewriting.DanmakuVideoPlayer;
import com.gyf.immersionbar.ImmersionBar;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import java.io.File;
import java.util.ArrayList;

public class VideoActivity extends BaseActivity {
    private static final String TAG = "VideoActivity";
    ActivityVideoBinding binding;
    private String[] TabTitle = {"简介", "评论"};
    private ArrayList<Fragment> mFragments;
    //假地址
    private String path = "http://vjs.zencdn.net/v/oceans.mp4";
    private String url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
    private OrientationUtils orientationUtils;
    boolean isPlay;
    boolean isPause;
    boolean isDestory;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video);
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(false)
                .statusBarColor(R.color.Black)
                .init();
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void initView() {
        showDialog();
        mFragments = new ArrayList<>();
        mFragments.add(new IntroductionFragment());
        mFragments.add(new CommentFragment());

        binding.VDanmakuShow.setOnClickListener(this);
        binding.VDefinitionText.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        //使用自定义的全屏切换图片，!!!注意xml布局中也需要设置为一样的
        //必须在setUp之前设置
        binding.detailPlayer.setShrinkImageRes(R.drawable.custom_shrink);
        binding.detailPlayer.setEnlargeImageRes(R.drawable.custom_enlarge);
        resolveNormalVideoUI();
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, binding.detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        binding.detailPlayer.setIsTouchWiget(true);
        //关闭自动旋转
        binding.detailPlayer.setRotateViewAuto(false);
        binding.detailPlayer.setLockLand(false);
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
        setMargins(binding.detailPlayer, 0, getStatusBarHeight(this), 0, 0);
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
        ResourcesData resourcesData = new ResourcesData();
        resourcesData.initVideo();
        binding.VTab.setViewPager(binding.VViewPager, TabTitle, this, mFragments);
        binding.detailPlayer.setShrinkImageRes(R.drawable.crop_free_24);
        binding.detailPlayer.setEnlargeImageRes(R.drawable.crop_free_24);
        binding.detailPlayer.setUp(resourcesData.getVideoData(), true, null, "测试视频");
        hideDialog();
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
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            getCurPlay().release();
        }
        //GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
        isDestory = true;
    }

    private GSYVideoPlayer getCurPlay() {
        if (binding.detailPlayer.getFullWindowPlayer() != null) {
            return binding.detailPlayer.getFullWindowPlayer();
        }
        return binding.detailPlayer;
    }
}