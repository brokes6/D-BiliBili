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

import androidx.annotation.NonNull;

import com.example.dildil.R;

public class DoubleSpeedDialog extends Dialog {
    private Context mContext;
    private Activity Context;
    private View view;
    private TextView x2,x1_5,x1_25,x1,x0_75,x0_5;

    public DoubleSpeedDialog(@NonNull Context context) {
        super(context, R.style.dialog_FadeIn);
        mContext = context;
        init();
    }

    /**
     * 初始化控件，初始化高度，宽度
     */
    private void init(){
        Context = (Activity) mContext;
        view = Context.getLayoutInflater().inflate(R.layout.dialog_double_speed, null);
        x2 = view.findViewById(R.id.x2);
        x1_5 = view.findViewById(R.id.x1_5);
        x1_25 = view.findViewById(R.id.x1_25);
        x1 = view.findViewById(R.id.x1);
        x0_75 = view.findViewById(R.id.x0_75);
        x0_5 = view.findViewById(R.id.x0_5);
        setContentView(view);
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
}
