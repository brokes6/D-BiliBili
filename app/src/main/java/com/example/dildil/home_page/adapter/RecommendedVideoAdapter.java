package com.example.dildil.home_page.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.dildil.R;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.home_page.dialog.VideoChoiceDialog;
import com.example.dildil.video.view.VideoActivity;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

public class RecommendedVideoAdapter extends BaseRecyclerAdapter<RecommendVideoBean.BeanData> {
    private ImageView cover, more;
    private TextView play_volume, barrage_volume, title;
    private Context mContext;
    private CardView Re_video;
    private RecommendVideoBean videoBean;

    public RecommendedVideoAdapter(Context context) {
        mContext = context;
    }

    public void setData(RecommendVideoBean bean) {
        videoBean = bean;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_recommendedvideo;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, RecommendVideoBean.BeanData item) {
        cover = holder.findViewById(R.id.Re_cover);
        play_volume = holder.findViewById(R.id.Re_play_volume);
        barrage_volume = holder.findViewById(R.id.Re_barrage_volume);
        Re_video = holder.findViewById(R.id.Re_video_cover);
        title = holder.findViewById(R.id.Re_title);
        more = holder.findViewById(R.id.Re_more);
        Re_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, VideoActivity.class);
                intent.putExtra("id", videoBean.getData().get(position).getId());
                intent.putExtra("uid", videoBean.getData().get(position).getUid());
                mContext.startActivity(intent);
            }
        });
        Re_video.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                VideoChoiceDialog videoChoiceDialog = new VideoChoiceDialog(mContext, "fuxinbo");
                videoChoiceDialog.show();
                return false;
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoChoiceDialog videoChoiceDialog = new VideoChoiceDialog(mContext, "fuxinbo");
                videoChoiceDialog.show();
            }
        });
        if (item != null) {
            initData(item);
        }
    }

    private void initData(RecommendVideoBean.BeanData item) {
        Glide.with(mContext)
                .load(item.getCover())
                .into(cover);
        play_volume.setText(item.getPlayNum() + "");
        barrage_volume.setText(item.getDanmunum() + "");
        title.setText(item.getTitle());
    }
}
