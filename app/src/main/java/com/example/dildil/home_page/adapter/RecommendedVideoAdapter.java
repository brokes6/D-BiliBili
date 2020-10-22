package com.example.dildil.home_page.adapter;

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
import com.example.customcontrollibs.BaseAdapter;
import com.example.dildil.R;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.home_page.dialog.VideoChoiceDialog;
import com.example.dildil.video.view.VideoActivity;

public class RecommendedVideoAdapter extends BaseAdapter<RecommendVideoBean.BeanData, RecommendedVideoAdapter.RecomViewHolder> {
    private Context mContext;
    private VideoChoiceDialog videoChoiceDialog;
    private int positions;
    private int type = 0;

    public RecommendedVideoAdapter(Context context, VideoChoiceDialog videoChoiceDialog, int value) {
        this.videoChoiceDialog = videoChoiceDialog;
        mContext = context;
        type = value;
    }

    @NonNull
    @Override
    protected RecomViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecomViewHolder(inflateView(parent, R.layout.item_recommendedvideo));
    }

    @Override
    protected void bindData(@NonNull RecomViewHolder holder, int position, RecommendVideoBean.BeanData item) {
        Glide.with(mContext)
                .load(item.getCover())
                .placeholder(R.drawable.skeleton_circular_grey)
                .into(holder.cover);
        holder.play_volume.setText(item.getPlayNum() + "");
        holder.barrage_volume.setText(item.getDanmunum() + "");
        holder.title.setText(item.getTitle());
        holder.Re_video.setTag(position);
        holder.more.setTag(position);
    }

    public class RecomViewHolder extends RecyclerView.ViewHolder {
        private ImageView cover, more;
        private TextView play_volume, barrage_volume, title;
        private CardView Re_video;

        public RecomViewHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.Re_cover);
            play_volume = itemView.findViewById(R.id.Re_play_volume);
            barrage_volume = itemView.findViewById(R.id.Re_barrage_volume);
            Re_video = itemView.findViewById(R.id.Re_video_cover);
            title = itemView.findViewById(R.id.Re_title);
            more = itemView.findViewById(R.id.Re_more);

            Re_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positions = (int) v.getTag();
                    Intent intent = new Intent(mContext, VideoActivity.class);
                    intent.putExtra("id", getData().get(positions).getId());
                    intent.putExtra("uid", getData().get(positions).getUid());
                    mContext.startActivity(intent);
                }
            });
            Re_video.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    positions = (int) v.getTag();
                    if (videoChoiceDialog != null) {
                        videoChoiceDialog.setData("fuxinbo", positions, type);
                        videoChoiceDialog.show();
                    }
                    return false;
                }
            });
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positions = (int) v.getTag();
                    if (videoChoiceDialog != null) {
                        videoChoiceDialog.setData("fuxinbo", positions, type);
                        videoChoiceDialog.show();
                    }
                }
            });
        }
    }
}
