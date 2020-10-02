package com.example.dildil.home_page.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.dildil.R;
import com.example.dildil.home_page.bean.VersionBean;

public class AppUpdateDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private Activity mContext;
    private VersionBean mAppUpdateInfo;
    private OnUpdateClickListener mOnUpdateListener;
    private ProgressBar mProgressBar;
    private View view;
    private TextView mVersion, mMainBody, mUpdate, mValueText;
    private ImageView close;

    public AppUpdateDialog(@NonNull Context context, VersionBean mAppUpdateInfo) {
        super(context, R.style.BottomDialog);
        this.mAppUpdateInfo = mAppUpdateInfo;
        this.context = context;
        initView();
    }

    public void setProgress(int progress, int maxValue) {
        if (maxValue == 0) {
            return;
        }
        close.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mValueText.setVisibility(View.VISIBLE);
        mProgressBar.setMax(maxValue);
        mProgressBar.setProgress(progress);
        mValueText.setText((int) (progress * 1.0f / maxValue * 100) + "%");
    }

    public void setOnUpdateClickListener(OnUpdateClickListener mOnUpdateListener) {
        this.mOnUpdateListener = mOnUpdateListener;
    }

    private void initView() {
        mContext = (Activity) context;
        view = mContext.getLayoutInflater().inflate(R.layout.item_update_content, null);
        setCanceledOnTouchOutside(false);
        close = view.findViewById(R.id.close);
        mVersion = view.findViewById(R.id.Version);
        mMainBody = view.findViewById(R.id.maiBody);
        mProgressBar = view.findViewById(R.id.app_update_progress);
        mValueText = view.findViewById(R.id.app_update_current_percent);
        mUpdate = view.findViewById(R.id.update);
        mVersion.setText(mAppUpdateInfo.getData().getVersion() + " 更新内容");
        mMainBody.setText(mAppUpdateInfo.getData().getDescription());
        close.setOnClickListener(this);
        mMainBody.setOnClickListener(this);
        mUpdate.setOnClickListener(this);

        setContentView(view);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置弹窗宽度
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        //为弹窗绑定效果
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                dismiss();
                break;
            case R.id.update:
                mValueText.setVisibility(View.VISIBLE);
                mUpdate.setVisibility(View.GONE);
                mOnUpdateListener.update(this);
                break;
        }
    }

    public interface OnUpdateClickListener {
        void update(AppUpdateDialog dialog);
    }
}