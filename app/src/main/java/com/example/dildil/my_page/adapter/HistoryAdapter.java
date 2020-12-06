package com.example.dildil.my_page.adapter;

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
import com.example.dildil.my_page.bean.HistoryBean;
import com.example.dildil.util.DateUtils;
import com.example.dildil.video.view.VideoActivity;

public class HistoryAdapter extends BaseAdapter<HistoryBean, RecyclerView.ViewHolder> {
    private Context mContext;
    private boolean isOne, isSecond, isThree;
    private final int NORMAL = 1;
    private final int TIMETITLE = 2;

    public HistoryAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TIMETITLE;
        } else if (DateUtils.getCurrentTimestamp() - getData().get(position).getCurrentTime() > 86400000 && DateUtils.getCurrentTimestamp() - getData().get(position).getCurrentTime() <= 172800000 && isOne) {
            isOne = false;
            return TIMETITLE;
        } else if (DateUtils.getCurrentTimestamp() - getData().get(position).getCurrentTime() > 172800000 && isSecond) {
            isSecond = false;
            return TIMETITLE;
        } else {
            return NORMAL;
        }
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case NORMAL:
                return new HistoryViewHolder(inflateView(parent, R.layout.item_history));
            case TIMETITLE:
                return new HistoryTimeViewHolder(inflateView(parent, R.layout.item_history_time));
        }
        return null;
    }

    @Override
    protected void bindData(@NonNull RecyclerView.ViewHolder holder, int position, HistoryBean item) {
        switch (getItemViewType(position)) {
            case NORMAL:
                Glide.with(mContext).load(item.getVImg()).apply(((HistoryViewHolder) holder).requestOptions).into(((HistoryViewHolder) holder).image);
                ((HistoryViewHolder) holder).title.setText(item.getVTitle());
                ((HistoryViewHolder) holder).main.setTag(position);
                //((HistoryViewHolder) holder).hTimeDetail.setText(item.getVTime());
                if (item.getPlayTime() == 0) {
                    ((HistoryViewHolder) holder).time.setText("未播放~");
                } else {
                    ((HistoryViewHolder) holder).time.setText(DateUtils.timeParse(item.getPlayTime() / 1000) + " / " + DateUtils.timeParse(item.getTotalDuration() / 1000));
                }
                if (DateUtils.getCurrentTimestamp() - getData().get(position).getCurrentTime() <= 86400000) {
                    ((HistoryViewHolder) holder).hTime.setText("今天");
                } else if (DateUtils.getCurrentTimestamp() - getData().get(position).getCurrentTime() > 86400000 && DateUtils.getCurrentTimestamp() - getData().get(position).getCurrentTime() <= 172800000) {
                    ((HistoryViewHolder) holder).hTime.setText("昨天");
                } else {
                    ((HistoryViewHolder) holder).hTime.setText("更早");
                }
                break;
            case TIMETITLE:
                if (DateUtils.getCurrentTimestamp() - getData().get(position + 1).getCurrentTime() <= 86400000) {
                    ((HistoryTimeViewHolder) holder).HTimeTitle.setText("今天");
                } else if (DateUtils.getCurrentTimestamp() - getData().get(position + 1).getCurrentTime() > 86400000 && DateUtils.getCurrentTimestamp() - getData().get(position + 1).getCurrentTime() <= 172800000) {
                    ((HistoryTimeViewHolder) holder).HTimeTitle.setText("昨天");
                } else {
                    ((HistoryTimeViewHolder) holder).HTimeTitle.setText("更早");
                }
                break;
        }
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.skeleton_circular_grey);
        private final RoundCornerImageView image;
        private final TextView time, title, hTime, hTimeDetail;
        private RelativeLayout main;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.H_video_img);
            time = itemView.findViewById(R.id.H_video_time);
            title = itemView.findViewById(R.id.H_video_Title);
            hTime = itemView.findViewById(R.id.HTime);
            hTimeDetail = itemView.findViewById(R.id.HTimeDetail);
            main = itemView.findViewById(R.id.H_video_cover);
            main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, VideoActivity.class);
                    intent.putExtra("id", getData().get((Integer) v.getTag()).getVid());
                    intent.putExtra("playtime", getData().get((Integer) v.getTag()).getPlayTime());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public class HistoryTimeViewHolder extends RecyclerView.ViewHolder {
        private TextView HTimeTitle;

        public HistoryTimeViewHolder(@NonNull View itemView) {
            super(itemView);
            HTimeTitle = itemView.findViewById(R.id.HTimeTitle);
        }
    }
}
