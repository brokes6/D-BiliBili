package com.example.dildil.home_page.dialog;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.dildil.R;
import com.example.dildil.util.XToastUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class VideoChoiceDialog extends BottomSheetDialog implements View.OnClickListener {
    private Context mContext;
    private TextView UP_name;
    private String upName;
    private OnFeedbackClickListener onFeedbackClickListener;
    private int position;
    private int type;

    public VideoChoiceDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public void setData(String name, int position, int value) {
        this.upName = name;
        this.position = position;
        type = value;
        UP_name.setText("UP主:" + upName);
    }

    public void setOnFeedbackClickListener(OnFeedbackClickListener onFeedbackClickListeners) {
        this.onFeedbackClickListener = onFeedbackClickListeners;
    }

    private void initView() {
        ContextThemeWrapper ctx = new ContextThemeWrapper(mContext, R.style.AppThemes);
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_video_choice_dialog, null);
        TextView addLater = view.findViewById(R.id.AddLater);
        TextView pornographyVulgarity = view.findViewById(R.id.pornographyVulgarity);
        TextView bloodyAwful = view.findViewById(R.id.bloodyAwful);
        TextView coverDisgusting = view.findViewById(R.id.coverDisgusting);
        TextView titleParty = view.findViewById(R.id.TitleParty);
        UP_name = view.findViewById(R.id.UP_name);
        TextView classification = view.findViewById(R.id.classification);
        TextView cancel = view.findViewById(R.id.cancel);
        addLater.setOnClickListener(this);
        pornographyVulgarity.setOnClickListener(this);
        bloodyAwful.setOnClickListener(this);
        coverDisgusting.setOnClickListener(this);
        titleParty.setOnClickListener(this);
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
        int id = v.getId();
        if (id == R.id.AddLater) {

        } else if (id == R.id.pornographyVulgarity || id == R.id.coverDisgusting || id == R.id.TitleParty || id == R.id.UP_name || id == R.id.classification || id == R.id.Channel || id == R.id.notInterested || id == R.id.bloodyAwful) {
            XToastUtils.toast("感谢反馈，将减少该类型的推送");
            if (onFeedbackClickListener != null) {
                onFeedbackClickListener.update(position, type);
            }
            dismiss();
        } else if (id == R.id.cancel) {
            dismiss();
        }
    }

    public interface OnFeedbackClickListener {
        void update(int position, int type);
    }
}
