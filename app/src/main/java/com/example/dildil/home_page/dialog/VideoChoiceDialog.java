package com.example.dildil.home_page.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.dildil.R;
import com.example.dildil.util.XToastUtils;

public class VideoChoiceDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private Activity mContext;
    private View view;
    private TextView AddLater, pornographyVulgarity, bloodyAwful, coverDisgusting, TitleParty,
            UP_name, classification, cancel;
    private String upName;
    private OnFeedbackClickListener onFeedbackClickListener;
    private int position;
    private int type;

    public VideoChoiceDialog(@NonNull Context context) {
        super(context, R.style.BottomDialog);
        this.context = context;
        initView();
    }

    public void setData(String name, int position, int value) {
        this.upName = name;
        this.position = position;
        type = value;
    }

    public void setOnFeedbackClickListener(OnFeedbackClickListener onFeedbackClickListeners) {
        this.onFeedbackClickListener = onFeedbackClickListeners;
    }

    private void initView() {
        mContext = (Activity) context;
        view = mContext.getLayoutInflater().inflate(R.layout.item_video_choice_dialog, null);
        AddLater = view.findViewById(R.id.AddLater);
        pornographyVulgarity = view.findViewById(R.id.pornographyVulgarity);
        bloodyAwful = view.findViewById(R.id.bloodyAwful);
        coverDisgusting = view.findViewById(R.id.coverDisgusting);
        TitleParty = view.findViewById(R.id.TitleParty);
        UP_name = view.findViewById(R.id.UP_name);
        classification = view.findViewById(R.id.classification);
        cancel = view.findViewById(R.id.cancel);
        AddLater.setOnClickListener(this);
        pornographyVulgarity.setOnClickListener(this);
        bloodyAwful.setOnClickListener(this);
        coverDisgusting.setOnClickListener(this);
        TitleParty.setOnClickListener(this);
        UP_name.setOnClickListener(this);
        classification.setOnClickListener(this);
        cancel.setOnClickListener(this);
        UP_name.setText("UP主:" + upName);


        setContentView(view);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置弹窗宽度
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //为弹窗绑定效果
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.AddLater:

                break;
            case R.id.pornographyVulgarity:
            case R.id.coverDisgusting:
            case R.id.TitleParty:
            case R.id.UP_name:
            case R.id.classification:
            case R.id.Channel:
            case R.id.notInterested:
            case R.id.bloodyAwful:
                XToastUtils.toast("感谢反馈，将减少该类型的推送");
                if (onFeedbackClickListener != null) {
                    onFeedbackClickListener.update(position,type);
                }
                dismiss();
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }

    public interface OnFeedbackClickListener {
        void update(int position,int type);
    }
}
