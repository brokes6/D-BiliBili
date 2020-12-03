package com.example.dildil.my_page.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.customcontrollibs.BaseAdapter;
import com.example.dildil.R;
import com.example.dildil.home_page.bean.RecommendVideoBean;

public class UserHomePagerContributeAdapter extends BaseAdapter<RecommendVideoBean.BeanData, UserHomePagerContributeAdapter.ItemViewHolder> {
    private Context mContext;

    public UserHomePagerContributeAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    protected ItemViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflateView(parent, R.layout.item_video_card));
    }

    @Override
    protected void bindData(@NonNull ItemViewHolder holder, int position, RecommendVideoBean.BeanData item) {
        Glide.with(mContext)
                .load(item.getCover())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_small_tv_play)
                .dontAnimate()
                .into(holder.mVideoPic);

        holder.mVideoTitle.setText(item.getTitle());
        holder.mVideoPlayNum.setText(String.valueOf(item.getPlayNum()));
        holder.mVideoReviewNum.setText(String.valueOf(item.getDanmunum()));
    }

    @Override
    public int getItemCount() {
        if (getData().size() == 0) {
            return 0;
        } else if (getData().size() == 1) {
            return 1;
        } else {
            return 2;
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView mVideoPic;
        TextView mVideoTitle;
        TextView mVideoPlayNum;
        TextView mVideoReviewNum;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mVideoPic = itemView.findViewById(R.id.item_img);
            mVideoTitle = itemView.findViewById(R.id.item_title);
            mVideoPlayNum = itemView.findViewById(R.id.item_play);
            mVideoReviewNum = itemView.findViewById(R.id.item_review);
        }
    }
}
