package com.example.dildil.video.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.dildil.R;
import com.example.dildil.video.rewriting.DanmakuVideoPlayer;

public class DoubleSpeedDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private Activity Context;
    private View view;
    private TextView x2, x1_5, x1_25, x1, x0_75, x0_5;

    public DoubleSpeedDialog(@NonNull Context context) {
        super(context, R.style.dialog_FadeIn);
        mContext = context;
        init();
    }

    /**
     * 初始化控件，初始化高度，宽度
     */
    private void init() {
        Context = (Activity) mContext;
        view = Context.getLayoutInflater().inflate(R.layout.dialog_double_speed, null);
        x2 = view.findViewById(R.id.x2);
        x1_5 = view.findViewById(R.id.x1_5);
        x1_25 = view.findViewById(R.id.x1_25);
        x1 = view.findViewById(R.id.x1);
        x0_75 = view.findViewById(R.id.x0_75);
        x0_5 = view.findViewById(R.id.x0_5);
        setContentView(view);
        x2.setOnClickListener(this);
        x1_5.setOnClickListener(this);
        x1_25.setOnClickListener(this);
        x1.setOnClickListener(this);
        x0_75.setOnClickListener(this);
        x0_5.setOnClickListener(this);

        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.RIGHT);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        WindowManager windowManager = Context.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        //设置弹窗宽度
        lp.width = 500;
        //为弹窗绑定效果
        dialogWindow.setAttributes(lp);
    }

    /**
     * 重写show方法，将高度设置为全屏高度，取消谈起虚拟按键
     */
    public void show() {
        if (this.getWindow() != null) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            super.show();
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        }
    }

    @Override
    public void onClick(View v) {
        DanmakuVideoPlayer danmakuVideoPlayer = new DanmakuVideoPlayer(mContext);
        Toast toast;
        switch (v.getId()) {
            case R.id.x2:
                danmakuVideoPlayer.setSpeed(2f);
                toast = Toast.makeText(getContext(), "当前倍速为:" + 2f, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.LEFT|Gravity.BOTTOM,0,220);
                toast.show();
                dismiss();
                break;
            case R.id.x1_5:
                danmakuVideoPlayer.setSpeed(1.5f);
                toast = Toast.makeText(getContext(), "当前倍速为:" + 1.5f, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.LEFT|Gravity.BOTTOM,0,220);
                toast.show();
                dismiss();
                break;
            case R.id.x1_25:
                danmakuVideoPlayer.setSpeed(1.25f);
                toast = Toast.makeText(getContext(), "当前倍速为:" + 1.25f, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.LEFT|Gravity.BOTTOM,0,220);
                toast.show();
                dismiss();
                break;
            case R.id.x1:
                danmakuVideoPlayer.setSpeed(1f);
                toast = Toast.makeText(getContext(), "当前倍速正常" , Toast.LENGTH_LONG);
                toast.setGravity(Gravity.LEFT|Gravity.BOTTOM,0,220);
                toast.show();
                dismiss();
                break;
            case R.id.x0_75:
                danmakuVideoPlayer.setSpeed(0.75f);
                toast = Toast.makeText(getContext(), "当前倍速为:" + 0.75f, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.LEFT|Gravity.BOTTOM,0,220);
                toast.show();
                dismiss();
                break;
            case R.id.x0_5:
                danmakuVideoPlayer.setSpeed(0.5f);
                toast = Toast.makeText(getContext(), "当前倍速为:" + 0.5f, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.LEFT|Gravity.BOTTOM,0,220);
                toast.show();
                dismiss();
                break;
        }
    }
}
