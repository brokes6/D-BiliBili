package com.example.dildil.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.android.liuzhuang.rcimageview.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.dildil.R;
import com.example.dildil.util.LoadingsDialog;
import com.example.dildil.util.LocaleManageUtil;
import com.gyf.immersionbar.ImmersionBar;


/**
 * Author:fuxinbo
 * Activity Base类
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "BaseActivity";

    protected LoadingsDialog mDialogs;

    public Context mContext;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleManageUtil.setLocal(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将导航栏颜色改为白色，按键设为灰色
        ImmersionBar.with(this)
                .navigationBarColor(R.color.White)
                .statusBarDarkFont(true)
                .navigationBarDarkIcon(true)
                .init();
        mContext = this;
        onCreateView(savedInstanceState);
        goDialog();
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }

    /**
     * 对Loading进行初始化
     */
    public void goDialog() {
        if (mDialogs == null) {
            mDialogs = LoadingsDialog.getInstance(this);
        } else {
            return;
        }
    }

    protected abstract void onCreateView(Bundle savedInstanceState);

    protected abstract void initView();

    protected abstract void initData();


    public void startActivity(Class<? extends AppCompatActivity> target, Bundle bundle, boolean finish) {
        Intent intent = new Intent();
        intent.setClass(this, target);
        if (bundle != null)
            intent.putExtra(getPackageName(), bundle);
        startActivity(intent);
        if (finish)
            finish();
    }

    public Bundle getBundle() {
        if (getIntent() != null && getIntent().hasExtra(getPackageName()))
            return getIntent().getBundleExtra(getPackageName());
        else
            return null;
    }

    public void openSearch(String usrImg) {
        LinearLayout Title_Search = findViewById(R.id.Title_Search);
        Title_Search.setVisibility(View.VISIBLE);
        CircleImageView ivUserImg = findViewById(R.id.user_img);
        Glide.with(mContext).load(usrImg).transition(new DrawableTransitionOptions().crossFade()).into(ivUserImg);
    }


    /**
     * 对标题栏的返回图标修颜色
     *
     * @param color
     */
    public void setBackBtn(String color) {
        ImageView backBtn = findViewById(R.id.iv_back);
        backBtn.setVisibility(View.VISIBLE);
        VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(getResources(), R.drawable.shape_back, getTheme());
        //你需要改变的颜色
        vectorDrawableCompat.setTint(Color.parseColor(color));
        backBtn.setImageDrawable(vectorDrawableCompat);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.gc();
                onBackPressed();
            }
        });
    }

    /**
     * 设置标题栏的文字
     *
     * @param resId
     */
    public void setLeftTitleText(int resId) {
        TextView leftTitle = findViewById(R.id.tv_left_title);
        leftTitle.setVisibility(View.VISIBLE);
        leftTitle.setText(resId);
    }

    /**
     * 设置标题栏背景颜色
     *
     * @param resId
     */
    public void setTitleBG(String resId) {
        RelativeLayout title = findViewById(R.id.title);
        title.setBackgroundColor(Color.parseColor(resId));
    }

    /**
     * 设置标题栏的文字和颜色
     *
     * @param titleText
     * @param textColor
     */
    public void setLeftTitleText(String titleText, String textColor) {
        TextView leftTitle = findViewById(R.id.tv_left_title);
        leftTitle.setVisibility(View.VISIBLE);
        leftTitle.setText(titleText);
        leftTitle.setTextColor(Color.parseColor(textColor));
    }

    /**
     * 设置标题栏的文字是否隐藏
     */
    public void setLeftTitleTextGone() {
        TextView leftTitle = findViewById(R.id.tv_left_title);
        leftTitle.setVisibility(View.GONE);
    }

    /**
     * 将标题栏的文字设置位默认白色
     */
    public void setLeftTitleTextColorWhite() {
        TextView leftTitle = findViewById(R.id.tv_left_title);
        leftTitle.setTextColor(Color.parseColor("#ffffff"));
    }

    /**
     * 设置标题栏文字的透明度
     *
     * @param alpha
     */
    public void setLeftTitleAlpha(float alpha) {
        TextView leftTitle = findViewById(R.id.tv_left_title);
        leftTitle.setVisibility(View.VISIBLE);
        leftTitle.setAlpha(alpha);
    }

    /**
     * 将标题栏的样式改为 音乐样式
     * 设置音乐名称
     * 设置音乐作者
     *
     * @param songName
     * @param singerName
     */
    public void setSongInfo(String songName, String singerName) {
        RelativeLayout rlSong = findViewById(R.id.rl_song_info);
        rlSong.setVisibility(View.VISIBLE);
        TextView tvSongName = findViewById(R.id.tv_songname);
        TextView tvSingerName = findViewById(R.id.tv_singername);
        tvSongName.setText(songName);
        tvSingerName.setText(singerName);
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int height = 0;
        int resourceId = context.getApplicationContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = context.getApplicationContext().getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    /**
     * 用来防止状态栏和控件重叠在一起（设置控件的Margins值）
     *
     * @param v 传递进来最上方的控件
     * @param l 左
     * @param t 上
     * @param r 右
     * @param b 下
     */
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public void showDialog() {
        mDialogs.show();
    }

    public void hideDialog() {
        mDialogs.dismiss();
    }
}
