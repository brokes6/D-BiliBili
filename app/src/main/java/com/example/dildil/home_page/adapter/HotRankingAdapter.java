package com.example.dildil.home_page.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.liuzhuang.rcimageview.RoundCornerImageView;
import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.example.customcontrollibs.BaseAdapter;
import com.example.dildil.R;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.home_page.view.RankingLstActivity;
import com.example.dildil.util.DateUtils;

public class HotRankingAdapter extends BaseAdapter<RecommendVideoBean.BeanData, RecyclerView.ViewHolder> {
    private final Context mContext;
    private int position;
    private ItemOnClickListener listener;

    public HotRankingAdapter(Context context) {
        mContext = context;
    }

    public void setListener(ItemOnClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    protected RecyclerView.ViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new HotHeaderViewHolder(inflateView(parent, R.layout.item_hot_header_list));
        } else {
            return new HotViewHolder(inflateView(parent, R.layout.item_hot_ranking_list));
        }
    }

    @Override
    protected void bindData(@NonNull RecyclerView.ViewHolder holder, int position, RecommendVideoBean.BeanData item) {
        if (getItemViewType(position) == TYPE_HEADER) {
            ((HotHeaderViewHolder) holder).rankingList.setTag(position);
        } else {
            Glide.with(mContext)
                    .load(item.getCover())
                    .placeholder(R.drawable.skeleton_circular_grey)
                    .into(((HotViewHolder) holder).video_cover);
            ((HotViewHolder) holder).title.setText(item.getTitle());
            ((HotViewHolder) holder).up.setText(item.getUpName());
            ((HotViewHolder) holder).time.setText(DateUtils.timeParse(item.getLength()));
            ((HotViewHolder) holder).playNum.setText(String.valueOf(item.getPlayNum()));
            ((HotViewHolder) holder).hot_video_cover.setTag(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    public class HotViewHolder extends RecyclerView.ViewHolder {
        private final RoundCornerImageView video_cover;
        private final RelativeLayout hot_video_cover;
        private final TextView time,title,up;
        private final TextView playNum;

        public HotViewHolder(@NonNull View itemView) {
            super(itemView);
            video_cover = itemView.findViewById(R.id.Ranking_video_img);
            hot_video_cover = itemView.findViewById(R.id.Hot_video_cover);
            time = itemView.findViewById(R.id.Ranking_video_time);
            title = itemView.findViewById(R.id.Ranking_video_Title);
            up = itemView.findViewById(R.id.Ranking_video_up);
            playNum = itemView.findViewById(R.id.Ranking_video_play_num);
            hot_video_cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        position = (int) v.getTag();
                        listener.onClick(position, getData().get(position).getId());
                    }
                }
            });
        }
    }

    public class HotHeaderViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout rankingList;

        public HotHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            rankingList = itemView.findViewById(R.id.Ranking_List);
            rankingList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position = (int) v.getTag();
                    ActivityUtils.startActivity(RankingLstActivity.class);
                }
            });
        }
    }

    public interface ItemOnClickListener {

        void onClick(int position, int vid);

    }
}
