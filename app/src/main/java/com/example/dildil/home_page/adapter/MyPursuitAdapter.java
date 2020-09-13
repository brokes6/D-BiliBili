package com.example.dildil.home_page.adapter;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.liuzhuang.rcimageview.RoundCornerImageView;
import com.bumptech.glide.Glide;
import com.example.dildil.R;
import com.example.dildil.home_page.bean.MyPursuitBean;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

public class MyPursuitAdapter extends BaseRecyclerAdapter<MyPursuitBean> {
    private Context mContext;
    private RoundCornerImageView image;
    private TextView image_text, title,watch;

    public MyPursuitAdapter(Context context) {
        mContext = context;
    }
    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_mypursuit;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, MyPursuitBean item) {
        image = holder.findViewById(R.id.MP_image);
        image_text = holder.findViewById(R.id.MP_image_text);
        title = holder.findViewById(R.id.MP_title);
        watch = holder.findViewById(R.id.MP_watch);
        if (item != null) {
            initData(item);
        }
    }

    private void initData(MyPursuitBean item) {
        Glide.with(mContext).load(item.getMyPursueImage()).into(image);
        image_text.setText("更新至第" + item.getToUpdate_word() + "话");
        title.setText(item.getMyPursueName());
        if (!item.getWatch_Situation().equals("尚未观看")) {
            watch.setTextColor(mContext.getResources().getColor(R.color.Pink));
        }
        watch.setText(item.getWatch_Situation());
    }
}
