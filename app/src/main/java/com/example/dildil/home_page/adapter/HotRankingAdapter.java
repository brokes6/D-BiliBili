package com.example.dildil.home_page.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.liuzhuang.rcimageview.RoundCornerImageView;
import com.bumptech.glide.Glide;
import com.example.dildil.R;
import com.example.dildil.home_page.bean.HotRankingBean;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

public class HotRankingAdapter extends BaseRecyclerAdapter<HotRankingBean> {
    private Context mContext;
    private RoundCornerImageView video_cover;
    public HotRankingAdapter(Context context){
        mContext = context;
    }
    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_hot_ranking_list;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, HotRankingBean item) {
        video_cover = holder.findViewById(R.id.Ranking_video_img);
        if (item != null) {
            Glide.with(mContext).load(item.getVideo_cover()).into(video_cover);
            holder.text(R.id.Ranking_video_Title,item.getVideo_title());
            holder.text(R.id.Ranking_video_up,item.getVideo_up());
            holder.text(R.id.Ranking_video_time,item.getVideo_time());
            holder.text(R.id.Ranking_video_play_num,item.getVideo_play_num());
        }
    }
}
