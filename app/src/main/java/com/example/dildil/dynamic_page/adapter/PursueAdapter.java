package com.example.dildil.dynamic_page.adapter;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.liuzhuang.rcimageview.RoundCornerImageView;
import com.bumptech.glide.Glide;
import com.example.dildil.R;
import com.example.dildil.dynamic_page.bean.PursueBean;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

public class PursueAdapter extends BaseRecyclerAdapter<PursueBean> {
    private Context mContext;
    private RoundCornerImageView image;
    private TextView image_text, title;

    public PursueAdapter(Context context) {
        mContext = context;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_pursue;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, PursueBean item) {
        image = holder.findViewById(R.id.IP_image);
        image_text = holder.findViewById(R.id.IP_image_text);
        title = holder.findViewById(R.id.IP_title);
        if (item != null) {
            initData(item);
        }
    }

    private void initData(PursueBean item) {
        Glide.with(mContext).load(item.getPursueImage()).into(image);
        image_text.setText("更新至第" + item.getToUpdate_word() + "话");
        title.setText(item.getPursueName());
    }
}
