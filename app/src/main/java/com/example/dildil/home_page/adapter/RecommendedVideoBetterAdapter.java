package com.example.dildil.home_page.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dildil.R;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.home_page.dialog.VideoChoiceDialog;
import com.example.dildil.video.view.VideoActivity;

import java.util.ArrayList;
import java.util.List;

public class RecommendedVideoBetterAdapter extends RecyclerView.Adapter<RecommendedVideoBetterAdapter.RecomViewHolder> {
    private Context mContext;
    private int type = 0;
    private int positions;
    private VideoChoiceDialog videoChoiceDialog;
    private List<RecommendVideoBean.BeanData> listData = new ArrayList<>();

    public RecommendedVideoBetterAdapter(Context context, VideoChoiceDialog videoChoiceDialog, int value) {
        this.videoChoiceDialog = videoChoiceDialog;
        mContext = context;
        type = value;
    }

    @NonNull
    @Override
    public RecomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_recommendedvideo, parent, false);
        return new RecomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecomViewHolder holder, int position) {
        Glide.with(mContext)
                .load(listData.get(position).getCover())
                .into(holder.cover);
        holder.play_volume.setText(String.valueOf(listData.get(position).getPlayNum()));
        holder.barrage_volume.setText(String.valueOf(listData.get(position).getDanmunum()));
        holder.title.setText(listData.get(position).getTitle());
        holder.Re_video.setTag(position);
        holder.more.setTag(position);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    public void loadMore(List<RecommendVideoBean.BeanData> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public void add(int pos, RecommendVideoBean.BeanData beanData) {
        listData.add(pos, beanData);
        notifyItemInserted(pos);
    }

    public void add(RecommendVideoBean.BeanData beanData) {
        listData.add(beanData);
        notifyItemInserted(listData.size() - 1);
    }

    public void removeItem(int position) {
        listData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
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
                    intent.putExtra("id", listData.get(positions).getId());
                    intent.putExtra("uid", listData.get(positions).getUid());
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
