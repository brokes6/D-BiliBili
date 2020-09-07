package com.example.dildil.video.rewriting;


import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dildil.R;
import com.example.dildil.util.BiliDanmukuParser;
import com.example.dildil.util.SharedPreferencesUtil;
import com.example.dildil.util.XToastUtils;
import com.example.dildil.video.adapter.DanamakuAdapter;
import com.example.dildil.video.bean.SwitchVideoBean;
import com.example.dildil.video.dialog.DoubleSpeedDialog;
import com.example.dildil.video.dialog.SwitchVideoTypeDialog;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;

public class DanmakuVideoPlayer extends StandardGSYVideoPlayer {
    private static final String TAG = "DanmakuVideoPlayer";
    private BaseDanmakuParser mParser;//解析器对象
    private IDanmakuView mDanmakuView;//弹幕view
    private DanmakuContext mDanmakuContext;

    private TextView mSendDanmaku, DoubleSpeed, mResolvingPower;

    private long mDanmakuStartSeekPosition = -1;

    private boolean mDanmaKuShow = true;

    private File mDumakuFile;

    private LinearLayout Bottom_controller;

    private ImageView Video_play, mToogleDanmaku;

    //滑动小图预览
    private RelativeLayout mPreviewLayout;

    private ImageView mPreView, mSeekBar_play, mCoverImage;

    String mCoverOriginUrl;
    int mDefaultRes;
    int mCoverOriginId = 0;

    //是否因为用户点击
    private boolean mIsFromUser;

    //是否打开滑动预览
    private boolean mOpenPreView = true;

    private int mPreProgress = -2;

    private List<SwitchVideoBean> mUrlList = new ArrayList<>();

    //数据源
    private int mSourcePosition = 0;

    private String mTypeText = "自动";

    private boolean isFirstload = true;

    protected boolean byStartedClick;
    private BottomVideoDialog dialog;

    private FullScreenStatusMonitoring listener;

    public DanmakuVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public DanmakuVideoPlayer(Context context) {
        super(context);
    }

    public DanmakuVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.danmaku_layout;
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        initView();
        //初始化弹幕显示
        initDanmaku();
    }

    private void initView() {
        mCoverImage = findViewById(R.id.thumbImage);
        mDanmakuView = findViewById(R.id.danmaku_view);
        mSendDanmaku = findViewById(R.id.send_danmaku);
        mToogleDanmaku = findViewById(R.id.definition_off);
        Bottom_controller = findViewById(R.id.Bottom_controller);
        Video_play = findViewById(R.id.Video_play);
        mPreviewLayout = findViewById(R.id.preview_layout);
        mPreView = findViewById(R.id.preview_image);
        DoubleSpeed = findViewById(R.id.Double_speed);
        mResolvingPower = findViewById(R.id.definition);
        mSeekBar_play = findViewById(R.id.Video_SeekBar_play);

        mSeekBar_play.setOnClickListener(this);
        mResolvingPower.setOnClickListener(this);
        DoubleSpeed.setOnClickListener(this);
        mSendDanmaku.setOnClickListener(this);
        mToogleDanmaku.setOnClickListener(this);
        Video_play.setOnClickListener(this);
        resolveTypeUI();
    }

    /**
     * 恢复暂停状态
     */
    @Override
    public void onVideoResume() {
        onVideoResume(true);
        mSeekBar_play.setImageResource(R.mipmap.pause);
    }

    @Override
    public void onPrepared() {
        super.onPrepared();
        if (isFirstload) {
            Bottom_controller.setVisibility(View.GONE);
            mSeekBar_play.setVisibility(View.VISIBLE);
        }
        startDownFrame(mOriginUrl);
    }

    @Override
    public void onVideoPause() {
        super.onVideoPause();
        danmakuOnPause();
    }

    @Override
    public void onVideoResume(boolean isResume) {
        super.onVideoResume(isResume);
        danmakuOnResume();
    }

    @Override
    protected void clickStartIcon() {
        super.clickStartIcon();
        if (mCurrentState == CURRENT_STATE_PLAYING) {
            danmakuOnResume();
            Video_play.setImageResource(R.mipmap.pause);
            mSeekBar_play.setImageResource(R.mipmap.pause);
            Log.e(TAG, "恢复播放");
        } else if (mCurrentState == CURRENT_STATE_PAUSE) {
            danmakuOnPause();
            Video_play.setImageResource(R.mipmap.play);
            mSeekBar_play.setImageResource(R.mipmap.play);
            Log.e(TAG, "暂停视频");
        }
    }

    @Override
    public void onCompletion() {
        releaseDanmaku(this);
    }

    @Override
    public void onSeekComplete() {
        super.onSeekComplete();
        int time = mProgressBar.getProgress() * getDuration() / 100;
        //如果已经初始化过的，直接seek到对于位置
        if (mHadPlay && getDanmakuView() != null && getDanmakuView().isPrepared()) {
            resolveDanmakuSeek(this, time);
        } else if (mHadPlay && getDanmakuView() != null && !getDanmakuView().isPrepared()) {
            //如果没有初始化过的，记录位置等待
            setDanmakuStartSeekPosition(time);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.send_danmaku:
                sendOutDanMu();
                break;
            case R.id.definition_off:
                mDanmaKuShow = !mDanmaKuShow;
                resolveDanmakuShow();
                break;
            case R.id.Video_play:
            case R.id.Video_SeekBar_play:
                clickStartIcon();
                break;
            case R.id.Double_speed:
                DoubleSpeedDialog doubleSpeedDialog = new DoubleSpeedDialog(mContext);
                doubleSpeedDialog.show();
                break;
            case R.id.definition:
                isFirstload = false;
                showSwitchDialog();
                break;
        }
    }

    private void sendOutDanMu() {
        onVideoPause();
        dialog = new BottomVideoDialog(mContext, R.style.BottomSheetEdit);
        View commentView = LayoutInflater.from(mContext).inflate(R.layout.danmu_comment, null);
        final EditText commentText = commentView.findViewById(R.id.DM_dialog_comment_et);
        final ImageView send = commentView.findViewById(R.id.DM_send);
        final ImageView rotation = commentView.findViewById(R.id.DM_text_rotation);
        commentText.setFocusable(true);
        commentText.setFocusableInTouchMode(true);
        commentText.requestFocus();
        dialog.setContentView(commentView);
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0, 0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(replyContent)) {
                    dialog.dismiss();
//                    onVideoResume();
                    getGSYVideoManager().start();
                    danmakuOnResume();
                    addDanmaku(true, replyContent,0);
                    setStateAndUi(CURRENT_STATE_PLAYING);
                    Video_play.setImageResource(R.mipmap.pause);
                } else {
                    XToastUtils.toast("弹幕内容不能为空");
                }
            }
        });
        dialog.show();
    }

    @Override
    protected void cloneParams(GSYBaseVideoPlayer from, GSYBaseVideoPlayer to) {
        super.cloneParams(from, to);
        ((DanmakuVideoPlayer) to).mDumakuFile = ((DanmakuVideoPlayer) from).mDumakuFile;
        ((DanmakuVideoPlayer) to).mSourcePosition = ((DanmakuVideoPlayer) from).mSourcePosition;
        ((DanmakuVideoPlayer) to).isFirstload = ((DanmakuVideoPlayer) from).isFirstload;
        ((DanmakuVideoPlayer) to).mTypeText = ((DanmakuVideoPlayer) from).mTypeText;
    }


    private void resolveTypeUI() {
        if (getCurrentState() == CURRENT_STATE_PREPAREING) {
            mSeekBar_play.setImageResource(R.mipmap.play);
            Video_play.setImageResource(R.mipmap.play);
        } else {
            mSeekBar_play.setImageResource(R.mipmap.pause);
            Video_play.setImageResource(R.mipmap.pause);
        }
        mResolvingPower.setText(mTypeText);
    }

    /**
     * 设置播放URL
     *
     * @param url           播放url
     * @param cacheWithPlay 是否边播边缓存
     * @param title         title
     * @return
     */
    public boolean setUp(List<SwitchVideoBean> url, boolean cacheWithPlay, String title) {
        mUrlList = url;
        return setUp(url.get(mSourcePosition).getUrl(), cacheWithPlay, title);
    }

    /**
     * 设置播放URL
     *
     * @param url           播放url
     * @param cacheWithPlay 是否边播边缓存
     * @param cachePath     缓存路径，如果是M3U8或者HLS，请设置为false
     * @param title         title
     * @return
     */
    public boolean setUp(List<SwitchVideoBean> url, boolean cacheWithPlay, File cachePath, String title) {
        mUrlList = url;
//        mSourcePosition = (int) SharedPreferencesUtil.getData("index",0);
        mSourcePosition = mUrlList.size()-1;
        mTypeText = mUrlList.get(mSourcePosition).getName();
        mResolvingPower.setText(mTypeText);
        return setUp(url.get(mSourcePosition).getUrl(), cacheWithPlay, cachePath, title);
    }

    /**
     * 处理播放器在全屏切换时，弹幕显示的逻辑
     * 需要格外注意的是，因为全屏和小屏，是切换了播放器，所以需要同步之间的弹幕状态
     */
    @Override
    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
        GSYBaseVideoPlayer gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar);
        if (gsyBaseVideoPlayer != null) {
            DanmakuVideoPlayer gsyVideoPlayer = (DanmakuVideoPlayer) gsyBaseVideoPlayer;
            if (mCoverOriginUrl != null) {
                gsyVideoPlayer.loadCoverImage(mCoverOriginUrl, mDefaultRes);
            } else if (mCoverOriginId != 0) {
                gsyVideoPlayer.loadCoverImageBy(mCoverOriginId, mDefaultRes);
            }
            gsyVideoPlayer.mOpenPreView = mOpenPreView;
            gsyVideoPlayer.mUrlList = mUrlList;
            gsyVideoPlayer.mSourcePosition = mSourcePosition;
            gsyVideoPlayer.mTypeText = mTypeText;
            gsyVideoPlayer.resolveTypeUI();
            //对弹幕设置偏移记录
            gsyVideoPlayer.setDanmakuStartSeekPosition(getCurrentPositionWhenPlaying());
            gsyVideoPlayer.setDanmaKuShow(getDanmaKuShow());
            onPrepareDanmaku(gsyVideoPlayer);
        }
        return gsyBaseVideoPlayer;
    }


    /**
     * 处理播放器在退出全屏时，弹幕显示的逻辑
     * 需要格外注意的是，因为全屏和小屏，是切换了播放器，所以需要同步之间的弹幕状态
     */
    @Override
    protected void resolveNormalVideoShow(View oldF, ViewGroup vp, GSYVideoPlayer gsyVideoPlayer) {
        super.resolveNormalVideoShow(oldF, vp, gsyVideoPlayer);
        listener.StateChange(mDanmaKuShow);
        if (gsyVideoPlayer != null) {
            DanmakuVideoPlayer gsyDanmaVideoPlayer = (DanmakuVideoPlayer) gsyVideoPlayer;
            gsyDanmaVideoPlayer.mOpenPreView = mOpenPreView;
            gsyDanmaVideoPlayer.mSourcePosition = mSourcePosition;
            gsyDanmaVideoPlayer.mTypeText = mTypeText;
            setDanmaKuShow(gsyDanmaVideoPlayer.getDanmaKuShow());
            if (gsyDanmaVideoPlayer.getDanmakuView() != null && gsyDanmaVideoPlayer.getDanmakuView().isPrepared()) {
                resolveDanmakuSeek(this, gsyDanmaVideoPlayer.getCurrentPositionWhenPlaying());
                resolveDanmakuShow();
                releaseDanmaku(gsyDanmaVideoPlayer);
            }
        }
        resolveTypeUI();
    }

    public void loadCoverImage(String url, int res) {
        mCoverOriginUrl = url;
        mDefaultRes = res;
        Glide.with(getContext().getApplicationContext())
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(1000000)
                                .centerCrop()
                                .error(res)
                                .placeholder(res))
                .load(url)
                .into(mCoverImage);
    }

    public void loadCoverImageBy(int id, int res) {
        mCoverOriginId = id;
        mDefaultRes = res;
        mCoverImage.setImageResource(id);
    }

    protected void danmakuOnPause() {
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    protected void danmakuOnResume() {
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }

    public void setDanmaKuStream(File is) {
        mDumakuFile = is;
        if (!getDanmakuView().isPrepared()) {
            onPrepareDanmaku((DanmakuVideoPlayer) getCurrentPlayer());
            Video_play.setImageResource(R.mipmap.pause);
        }
    }


    private void initDanmaku() {
        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<Integer, Integer>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5); // 滚动弹幕最大显示5行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);

        DanamakuAdapter danamakuAdapter = new DanamakuAdapter(mDanmakuView);
        mDanmakuContext = DanmakuContext.create();
        mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3).setDuplicateMergingEnabled(false).setScrollSpeedFactor(1.2f).setScaleTextSize(1.2f)
                .setCacheStuffer(new SpannedCacheStuffer(), danamakuAdapter) // 图文混排使用SpannedCacheStuffer
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair);
        if (mDanmakuView != null) {
            if (mDumakuFile != null) {
                mParser = createParser(getIsStream(mDumakuFile));
            }

            //todo 这是为了demo效果，实际上需要去掉这个，外部传输文件进来
            mParser = createParser(this.getResources().openRawResource(R.raw.comments));

            mDanmakuView.setCallback(new master.flame.danmaku.controller.DrawHandler.Callback() {
                @Override
                public void updateTimer(DanmakuTimer timer) {
                }

                @Override
                public void drawingFinished() {

                }

                @Override
                public void danmakuShown(BaseDanmaku danmaku) {
                }

                @Override
                public void prepared() {
                    if (getDanmakuView() != null) {
                        getDanmakuView().start();
                        if (getDanmakuStartSeekPosition() != -1) {
                            resolveDanmakuSeek(DanmakuVideoPlayer.this, getDanmakuStartSeekPosition());
                            setDanmakuStartSeekPosition(-1);
                        }
                        resolveDanmakuShow();
                    }
                }
            });
            mDanmakuView.enableDanmakuDrawingCache(true);
        }
    }

    private InputStream getIsStream(File file) {
        try {
            InputStream instream = new FileInputStream(file);
            InputStreamReader inputreader = new InputStreamReader(instream);
            BufferedReader buffreader = new BufferedReader(inputreader);
            String line;
            StringBuilder sb1 = new StringBuilder();
            sb1.append("<i>");
            //分行读取
            while ((line = buffreader.readLine()) != null) {
                sb1.append(line);
            }
            sb1.append("</i>");
            Log.e("3333333", sb1.toString());
            instream.close();
            return new ByteArrayInputStream(sb1.toString().getBytes());
        } catch (java.io.FileNotFoundException e) {
//            Log.d("TestFile", "The File doesn't not exist.");
        } catch (IOException e) {
//            Log.d("TestFile", e.getMessage());
        }
        return null;
    }

    /**
     * 弹幕的显示与关闭
     */
    private void resolveDanmakuShow() {
        post(new Runnable() {
            @Override
            public void run() {
                if (mDanmaKuShow) {
                    if (!getDanmakuView().isShown())
                        getDanmakuView().show();
                    mToogleDanmaku.setImageResource(R.mipmap.definition);
                } else {
                    if (getDanmakuView().isShown()) {
                        getDanmakuView().hide();
                    }
                    mToogleDanmaku.setImageResource(R.mipmap.definition_off);
                }
            }
        });
    }

    public void offDanmaku() {
        mDanmaKuShow = false;
        resolveDanmakuShow();
    }

    public void openDanmaku() {
        mDanmaKuShow = true;
        resolveDanmakuShow();
    }

    /**
     * 开始播放弹幕
     */
    private void onPrepareDanmaku(DanmakuVideoPlayer gsyVideoPlayer) {
        if (gsyVideoPlayer.getDanmakuView() != null && !gsyVideoPlayer.getDanmakuView().isPrepared() && gsyVideoPlayer.getParser() != null) {
            gsyVideoPlayer.getDanmakuView().prepare(gsyVideoPlayer.getParser(),
                    gsyVideoPlayer.getDanmakuContext());
        }
    }

    /**
     * 弹幕偏移
     */
    private void resolveDanmakuSeek(DanmakuVideoPlayer gsyVideoPlayer, long time) {
        if (mHadPlay && gsyVideoPlayer.getDanmakuView() != null && gsyVideoPlayer.getDanmakuView().isPrepared()) {
            gsyVideoPlayer.getDanmakuView().seekTo(time);
        }
    }

    /**
     * 创建解析器对象，解析输入流
     *
     * @param stream
     * @return
     */
    private BaseDanmakuParser createParser(InputStream stream) {

        if (stream == null) {
            return new BaseDanmakuParser() {

                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
        }

        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);

        try {
            loader.load(stream);
        } catch (IllegalDataException e) {
            e.printStackTrace();
        }
        BaseDanmakuParser parser = new BiliDanmukuParser();
        IDataSource<?> dataSource = loader.getDataSource();
        parser.load(dataSource);
        return parser;

    }

    /**
     * 释放弹幕控件
     */
    private void releaseDanmaku(DanmakuVideoPlayer danmakuVideoPlayer) {
        if (danmakuVideoPlayer != null && danmakuVideoPlayer.getDanmakuView() != null) {
            Debuger.printfError("release Danmaku!");
            danmakuVideoPlayer.getDanmakuView().release();
        }
    }

    public BaseDanmakuParser getParser() {
        if (mParser == null) {
            if (mDumakuFile != null) {
                mParser = createParser(getIsStream(mDumakuFile));
            }
        }
        return mParser;
    }

    public DanmakuContext getDanmakuContext() {
        return mDanmakuContext;
    }

    public IDanmakuView getDanmakuView() {
        return mDanmakuView;
    }

    public long getDanmakuStartSeekPosition() {
        return mDanmakuStartSeekPosition;
    }

    public void setDanmakuStartSeekPosition(long danmakuStartSeekPosition) {
        this.mDanmakuStartSeekPosition = danmakuStartSeekPosition;
    }

    public void setDanmaKuShow(boolean danmaKuShow) {
        mDanmaKuShow = danmaKuShow;
    }

    public void setResolvingPower(int value) {
        mSourcePosition = value;
    }

    public boolean getDanmaKuShow() {
        return mDanmaKuShow;
    }

    public void setListener(FullScreenStatusMonitoring listener) {
        this.listener = listener;
    }

    public interface FullScreenStatusMonitoring {

        void StateChange(boolean isFullScreen);

    }

    /******************* 下方重载方法，在播放开始不显示底部进度和按键，不需要可屏蔽 ********************/

    @Override
    protected void onClickUiToggle() {
        if (mIfCurrentIsFullscreen && mLockCurScreen && mNeedLockFull) {
            setViewShowState(mLockScreen, VISIBLE);
            return;
        }
        byStartedClick = true;
        super.onClickUiToggle();

    }

    @Override
    protected void changeUiToNormal() {
        super.changeUiToNormal();
        byStartedClick = false;
    }

    @Override
    protected void changeUiToPreparingShow() {
        super.changeUiToPreparingShow();
        Debuger.printfLog("Sample changeUiToPreparingShow");
        setViewShowState(mBottomContainer, INVISIBLE);
        setViewShowState(mStartButton, INVISIBLE);
    }

    @Override
    protected void changeUiToPlayingBufferingShow() {
        super.changeUiToPlayingBufferingShow();
        Debuger.printfLog("Sample changeUiToPlayingBufferingShow");
        if (!byStartedClick) {
            setViewShowState(mBottomContainer, INVISIBLE);
            setViewShowState(mStartButton, INVISIBLE);
        }
    }

    @Override
    protected void changeUiToPlayingShow() {
        super.changeUiToPlayingShow();
        Debuger.printfLog("Sample changeUiToPlayingShow");
        if (!byStartedClick) {
            setViewShowState(mBottomContainer, INVISIBLE);
            setViewShowState(mStartButton, INVISIBLE);
        }
    }

    @Override
    public void startAfterPrepared() {
        super.startAfterPrepared();
        Debuger.printfLog("Sample startAfterPrepared");
        setViewShowState(mBottomContainer, INVISIBLE);
        setViewShowState(mStartButton, INVISIBLE);
        setViewShowState(mBottomProgressBar, VISIBLE);
    }


    /**
     * 初始化预览图的参数
     *
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        super.onProgressChanged(seekBar, progress, fromUser);
        if (fromUser && mOpenPreView) {
            int width = seekBar.getWidth();
            int time = progress * getDuration() / 100;
            int offset = (int) (width - (getResources().getDimension(R.dimen.seek_bar_image) / 2)) / 100 * progress;
            Debuger.printfError("***************** " + progress);
            Debuger.printfError("***************** " + time);
            showPreView(mOriginUrl, time);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mPreviewLayout.getLayoutParams();
            layoutParams.leftMargin = offset;
            //设置帧预览图的显示位置
            mPreviewLayout.setLayoutParams(layoutParams);
            if (mHadPlay && mOpenPreView) {
                mPreProgress = progress;
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        super.onStartTrackingTouch(seekBar);
        byStartedClick = true;
        if (mOpenPreView) {
            mIsFromUser = true;
            mPreviewLayout.setVisibility(VISIBLE);
            mPreProgress = -2;
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mOpenPreView) {
            if (mPreProgress >= 0) {
                seekBar.setProgress(mPreProgress);
            }
            super.onStopTrackingTouch(seekBar);
            mIsFromUser = false;
            mPreviewLayout.setVisibility(GONE);
        } else {
            super.onStopTrackingTouch(seekBar);
        }
    }

    @Override
    protected void setTextAndProgress(int secProgress) {
        if (mIsFromUser) {
            return;
        }
        super.setTextAndProgress(secProgress);
    }

    public boolean isOpenPreView() {
        return mOpenPreView;
    }

    /**
     * 如果是需要进度条预览的设置打开，默认关闭
     */
    public void setOpenPreView(boolean localFile) {
        this.mOpenPreView = localFile;
    }

    private void showPreView(String url, long time) {
        int width = CommonUtil.dip2px(getContext(), 150);
        int height = CommonUtil.dip2px(getContext(), 100);
        Glide.with(getContext())
                .setDefaultRequestOptions(
                        new RequestOptions()
                                //这里限制了只从缓存读取
                                .onlyRetrieveFromCache(true)
                                .frame(1000 * time)
                                .override(width, height)
                                .dontAnimate()
                                .centerCrop())
                .load(url)
                .into(mPreView);
    }

    private void startDownFrame(String url) {
        for (int i = 1; i <= 100; i++) {
            int time = i * getDuration() / 100;
            int width = CommonUtil.dip2px(getContext(), 150);
            int height = CommonUtil.dip2px(getContext(), 100);
            Glide.with(getContext().getApplicationContext())
                    .setDefaultRequestOptions(
                            new RequestOptions()
                                    .frame(1000 * time)
                                    .override(width, height)
                                    .centerCrop())
                    .load(url).preload(width, height);

        }
    }

    /**
     * 弹出切换清晰度
     */
    private void showSwitchDialog() {
        if (!mHadPlay) {
            return;
        }
        SwitchVideoTypeDialog switchVideoTypeDialog = new SwitchVideoTypeDialog(getContext());
        switchVideoTypeDialog.initList(mUrlList, new SwitchVideoTypeDialog.OnListItemClickListener() {
            @Override
            public void onItemClick(int position) {
                final String name = mUrlList.get(position).getName();
                if (mSourcePosition != position) {
                    if ((mCurrentState == GSYVideoPlayer.CURRENT_STATE_PLAYING
                            || mCurrentState == GSYVideoPlayer.CURRENT_STATE_PAUSE)) {
                        final String url = mUrlList.get(position).getUrl();
                        onVideoPause();
                        final long currentPosition = mCurrentPosition;
                        getGSYVideoManager().releaseMediaPlayer();
                        cancelProgressTimer();
//                        hideAllWidget();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setUp(url, mCache, mCachePath, mTitle);
                                setSeekOnStart(currentPosition);
                                startPlayLogic();
                                cancelProgressTimer();
//                                hideAllWidget();
                            }
                        }, 500);
                        mTypeText = name;
                        mResolvingPower.setText(mTypeText);
                        mSourcePosition = position;
                        SharedPreferencesUtil.putData("index",position);
                        Toast toast = Toast.makeText(getContext(), "已切换到" + mTypeText, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.LEFT | Gravity.BOTTOM, 0, 220);
                        toast.show();
                        Log.e(TAG, "当前分辨率改变,为：" + mTypeText);
                    }
                } else {
                    Toast.makeText(getContext(), "已经是 " + name, Toast.LENGTH_LONG).show();
                }
            }
        });
        switchVideoTypeDialog.show();
    }

    /**
     * 模拟添加弹幕数据
     */
    public void addDanmaku(boolean islive, String content,int textSize) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || mDanmakuView == null) {
            return;
        }
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.priority = 8;  // 可能会被各种过滤器过滤并隐藏显示，所以提高等级
        danmaku.isLive = islive;
        danmaku.setTime(mDanmakuView.getCurrentTime() + 500);
        if (textSize == 1){
            danmaku.textSize = 25f * (mParser.getDisplayer().getDensity() - 0.6f);
        }else if (textSize == 0){
            danmaku.textSize = 20f * (mParser.getDisplayer().getDensity() - 0.6f);
        }else{
            danmaku.textSize = 15f * (mParser.getDisplayer().getDensity() - 0.6f);
        }
        danmaku.textColor = Color.RED;
//        danmaku.textShadowColor = Color.WHITE;
        danmaku.underlineColor = Color.GREEN;
        mDanmakuView.addDanmaku(danmaku);
    }

}


