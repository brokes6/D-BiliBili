package com.example.dildil.my_page.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.customcontrollibs.BaseAdapter;
import com.example.dildil.R;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.video.view.VideoActivity;

public class UserHomePagerContributeAdapter extends BaseAdapter<RecommendVideoBean.BeanData, UserHomePagerContributeAdapter.ItemViewHolder> {
    private final Context mContext;

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
                .dontAnimate()
                .into(holder.mVideoPic);

        holder.mVideoTitle.setText(item.getTitle());
        holder.mVideoPlayNum.setText(String.valueOf(item.getPlayNum()));
        holder.mVideoReviewNum.setText(String.valueOf(item.getDanmunum()));
        holder.cardView.setTag(position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView mVideoPic;
        TextView mVideoTitle;
        TextView mVideoPlayNum;
        TextView mVideoReviewNum;

        public ItemViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            mVideoPic = itemView.findViewById(R.id.item_img);
            mVideoTitle = itemView.findViewById(R.id.item_title);
            mVideoPlayNum = itemView.findViewById(R.id.item_play);
            mVideoReviewNum = itemView.findViewById(R.id.item_review);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, VideoActivity.class);
                    intent.putExtra("id", getData().get((Integer) v.getTag()).getId());
                    intent.putExtra("uid", getData().get((Integer) v.getTag()).getUid());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
