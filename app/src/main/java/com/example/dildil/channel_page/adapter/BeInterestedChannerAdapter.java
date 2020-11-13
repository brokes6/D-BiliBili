package com.example.dildil.channel_page.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.customcontrollibs.ImageTopView;
import com.example.dildil.R;
import com.example.dildil.channel_page.bean.BeInterestedBean;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

public class BeInterestedChannerAdapter extends BaseRecyclerAdapter<BeInterestedBean> {
    private final Context mContext;
    private ImageTopView mainImage;

    public BeInterestedChannerAdapter(Context context) {
        mContext = context;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_be_interested;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, BeInterestedBean item) {
        mainImage = holder.findViewById(R.id.BI_Main);
        mainImage.setUrl(item.getBeInterestedImage());
        mainImage.setText(item.getBeInterestedTitle());
    }
}
