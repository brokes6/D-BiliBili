package com.example.dildil.video.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dildil.R;
import com.example.dildil.video.bean.SwitchVideoBean;

import java.util.List;

public class SwitchVideoTypeDialog extends Dialog {

    private Context mContext;

    private ListView listView = null;

    private ArrayAdapter<SwitchVideoBean> adapter = null;

    private OnListItemClickListener onItemClickListener;

    private List<SwitchVideoBean> data;

    public interface OnListItemClickListener {
        void onItemClick(int position);
    }

    public SwitchVideoTypeDialog(Context context) {
        super(context, R.style.dialog_FadeIn);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initList(List<SwitchVideoBean> data, OnListItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.data = data;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_switch_video, null);
        listView = (ListView) view.findViewById(R.id.switch_dialog_list);
        setContentView(view);
        adapter = new ArrayAdapter<>(mContext, R.layout.item_switch_video_dialog, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener());

        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.RIGHT);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        //设置弹窗宽度
        lp.width = 500;
        dialogWindow.setAttributes(lp);
    }

    private class OnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            onItemClickListener.onItemClick(position);
            dismiss();
        }
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
