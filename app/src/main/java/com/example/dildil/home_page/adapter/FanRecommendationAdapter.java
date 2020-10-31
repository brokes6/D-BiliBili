package com.example.dildil.home_page.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.liuzhuang.rcimageview.RoundCornerImageView;
import com.bumptech.glide.Glide;
import com.example.dildil.R;
import com.example.dildil.home_page.bean.FanRecommendationBean;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

public class FanRecommendationAdapter extends BaseRecyclerAdapter<FanRecommendationBean> {
    private Context mContext;
    private RoundCornerImageView cover;
    private TextView mExclusive, mSecondaryTitle, mTitle, mWatch;

    public FanRecommendationAdapter(Context context) {
        mContext = context;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_fan_recommendation;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, FanRecommendationBean item) {
        cover = holder.findViewById(R.id.FR_image);
        mExclusive = holder.findViewById(R.id.FR_Exclusive_members);
        mSecondaryTitle = holder.findViewById(R.id.FR_secondaryTitle);
        mTitle = holder.findViewById(R.id.FR_Title);
        mWatch = holder.findViewById(R.id.FR_watch);
        if (item != null) {
            initData(item);
        }
    }

    private void initData(FanRecommendationBean item) {
        Glide.with(mContext).load(item.getCover()).placeholder(R.drawable.skeleton_circular_grey).into(cover);
        if (item.isExclusive()) mExclusive.setVisibility(View.VISIBLE);
        if (!item.getWatch().equals("尚未观看")) {
            mWatch.setTextColor(mContext.getResources().getColor(R.color.Pink));
        }
        mSecondaryTitle.setText(item.getSecondaryTitle() + "追番");
        mTitle.setText(item.getTitle());
        mWatch.setText(item.getWatch());
    }
}
