package com.example.dildil.video.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.liuzhuang.rcimageview.RoundCornerImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.customcontrollibs.BaseAdapter;
import com.example.dildil.R;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.util.DateUtils;
import com.example.dildil.video.view.VideoActivity;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

public class RelevantVideoAdapter extends BaseAdapter<RecommendVideoBean.BeanData, RelevantVideoAdapter.RelevantVideoViewHolder> {
    private final Context mContext;

    public RelevantVideoAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    protected RelevantVideoViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RelevantVideoViewHolder(inflateView(parent, R.layout.item_relevant_video));
    }

    @Override
    protected void bindData(@NonNull RelevantVideoViewHolder holder, int position, RecommendVideoBean.BeanData item) {
        Glide.with(mContext)
                .load(item.getCover())
                .apply(holder.requestOptions)
                .into(holder.video_cover);
        holder.title.setText(item.getTitle());
        holder.danmu_num.setText(String.valueOf(item.getDanmunum()));
        holder.up.setText(item.getUpName());
        holder.time.setText(DateUtils.timeParse(item.getLength()));
        holder.playNum.setText(String.valueOf(item.getPlayNum()));
        holder.hot_video_cover.setTag(position);
    }

    public class RelevantVideoViewHolder extends RecyclerView.ViewHolder {
        private final RequestOptions requestOptions= new RequestOptions().placeholder(R.drawable.skeleton_circular_grey);
        private final RoundCornerImageView video_cover;
        private final RelativeLayout hot_video_cover;
        private final TextView time, title, up;
        private final TextView playNum, danmu_num;

        public RelevantVideoViewHolder(@NonNull View itemView) {
            super(itemView);
            video_cover = itemView.findViewById(R.id.Ranking_video_img);
            hot_video_cover = itemView.findViewById(R.id.Hot_video_cover);
            time = itemView.findViewById(R.id.Ranking_video_time);
            title = itemView.findViewById(R.id.Ranking_video_Title);
            up = itemView.findViewById(R.id.Ranking_video_up);
            playNum = itemView.findViewById(R.id.Ranking_video_play_num);
            danmu_num = itemView.findViewById(R.id.play_danmu_img_num);
            hot_video_cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    Intent intent = new Intent(mContext, VideoActivity.class);
                    intent.putExtra("id", getData().get(pos).getId());
                    VideoActivity videoActivity = (VideoActivity) mContext;
                    videoActivity.getPlayPosition();
                    GSYVideoManager.onPause();
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
