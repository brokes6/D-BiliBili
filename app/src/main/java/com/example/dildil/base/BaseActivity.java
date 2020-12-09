package com.example.dildil.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.android.liuzhuang.rcimageview.CircleImageView;
import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.dildil.R;
import com.example.dildil.api.ApiEngine;
import com.example.dildil.api.ApiService;
import com.example.dildil.login_page.view.LoginActivity;
import com.example.dildil.my_page.bean.LogoutBean;
import com.example.dildil.util.LoadingsDialog;
import com.example.dildil.util.SharedPreferencesUtil;
import com.example.dildil.util.XToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Author:fuxinbo
 * Activity Base类
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements View.OnClickListener {
    protected LoadingsDialog mDialogs;
    public Context mContext;
    private JudgeLoginReceiver judgeLoginReceiver;
    public static String signInAction = "LOGIN.DilDil.ACTION";

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
        initBroadcastReceiver();
    }

    private void initBroadcastReceiver() {
        if (judgeLoginReceiver != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(signInAction);
            registerReceiver(judgeLoginReceiver, intentFilter);
        } else {
            judgeLoginReceiver = new JudgeLoginReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(signInAction);
            registerReceiver(judgeLoginReceiver, intentFilter);
        }
    }

    private static class JudgeLoginReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            new MaterialDialog.Builder(context)
                    .title(R.string.loginWarning)
                    .content(R.string.thisAccountHasSigned)
                    .positiveText(R.string.determine)
                    .cancelable(false)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            ApiService apiService = ApiEngine.getInstance().getApiService();
                            apiService.Logout()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<LogoutBean>() {
                                        @Override
                                        public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                                        }

                                        @Override
                                        public void onNext(@io.reactivex.annotations.NonNull LogoutBean logoutBean) {
                                            SharedPreferencesUtil.remove("cookie");
                                            ActivityUtils.startActivity(LoginActivity.class);
                                        }

                                        @Override
                                        public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                                            XToastUtils.error(R.string.networkError);
                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                        }
                    })
                    .show();
        }
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
        if (judgeLoginReceiver != null) {
            unregisterReceiver(judgeLoginReceiver);
        }
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
    public void setBackBtn(@ColorInt int color) {
        ImageView backBtn = findViewById(R.id.iv_back);
        backBtn.setVisibility(View.VISIBLE);
        VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(getResources(), R.drawable.shape_back, getTheme());
        //你需要改变的颜色
        vectorDrawableCompat.setTint(color);
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
    public void setLeftTitleText(String resId) {
        TextView leftTitle = findViewById(R.id.tv_left_title);
        leftTitle.setVisibility(View.VISIBLE);
        leftTitle.setText(resId);
    }

    /**
     * 设置标题栏背景颜色
     *
     * @param color
     */
    public void setTitleBG(@ColorInt int color) {
        LinearLayout title = findViewById(R.id.back_BackGround);
        title.setBackgroundColor(color);
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
     * 在标题栏添加删除按钮
     */
    public void setDelete() {
        ImageView delete = findViewById(R.id.delete);
        delete.setVisibility(View.VISIBLE);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDaoOperation.getDatabase(mContext).dellHistory();
                XToastUtils.success("成功清空~");
            }
        });
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

    /**
     * 获取版本号
     *
     * @return
     */
    public String getVersionCode() {
        // 包管理器 可以获取清单文件信息
        PackageManager packageManager = getPackageManager();
        try {
            // 获取包信息
            // 参1 包名 参2 获取额外信息的flag 不需要的话 写0
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void showDialog() {
        mDialogs.show();
    }

    public void hideDialog() {
        mDialogs.dismiss();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.fontScale != 1) { //fontScale不为1，需要强制设置为1
            getResources();
        }
    }

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if (resources.getConfiguration().fontScale != 1) { //fontScale不为1，需要强制设置为1
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置成默认值，即fontScale为1
            resources.updateConfiguration(newConfig, resources.getDisplayMetrics());
        }
        return resources;
    }
}
