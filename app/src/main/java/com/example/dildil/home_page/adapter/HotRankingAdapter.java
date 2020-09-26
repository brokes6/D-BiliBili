package com.example.dildil.home_page.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.android.liuzhuang.rcimageview.RoundCornerImageView;
import com.bumptech.glide.Glide;
import com.example.dildil.R;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

public class HotRankingAdapter extends BaseRecyclerAdapter<RecommendVideoBean.BeanData>{
    private static final String TAG = "HotRankingAdapter";
    private Context mContext;
    private RoundCornerImageView video_cover;
    private RelativeLayout hot_video_cover;
    private ItemOnClickListener listener;
    public HotRankingAdapter(Context context){
        mContext = context;
    }
    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_hot_ranking_list;
    }


    public void setListener(ItemOnClickListener listener) {
        this.listener = listener;
        Log.e(TAG, "onClick: 当前的listener为"+listener );
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, RecommendVideoBean.BeanData item) {
        video_cover = holder.findViewById(R.id.Ranking_video_img);
        hot_video_cover = holder.findViewById(R.id.Hot_video_cover);
        hot_video_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(getSelectPosition(),item.getId());
                }
            }
        });
        if (item != null) {
            Glide.with(mContext).load(item.getCover()).into(video_cover);
            holder.text(R.id.Ranking_video_Title,item.getTitle());
            holder.text(R.id.Ranking_video_up,item.getUpName());
            holder.text(R.id.Ranking_video_time,"2.25");
            holder.text(R.id.Ranking_video_play_num,""+item.getPlayNum());
        }
    }

    public interface ItemOnClickListener {

        void onClick(int position,int vid);

    }
}
