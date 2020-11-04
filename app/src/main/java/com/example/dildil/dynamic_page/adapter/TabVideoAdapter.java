package com.example.dildil.dynamic_page.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.liuzhuang.rcimageview.CircleImageView;
import com.bumptech.glide.Glide;
import com.example.customcontrollibs.BaseAdapter;
import com.example.dildil.R;
import com.example.dildil.dynamic_page.bean.DynamicBean;
import com.example.dildil.dynamic_page.view.DynamicDetailsActivity;
import com.example.dildil.dynamic_page.view.SwitchVideo;
import com.example.dildil.util.DensityUtil;
import com.example.dildil.video.view.VideoActivity;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;

import cn.lemon.multi.MultiView;

public class TabVideoAdapter extends BaseAdapter<DynamicBean.Datas, RecyclerView.ViewHolder> {
    public static final String TAG = "VideoNewsAdapter";
    private Context mContext;
    private static final int IMAGE_TEXT = 1;
    private static final int VIDEO_TEXT = 2;
    private Intent intent;

    public TabVideoAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case IMAGE_TEXT:
                return new TabImageHolder(inflateView(parent, R.layout.item_tab_imagetext));
            case VIDEO_TEXT:
                return new TabVideoHolder(inflateView(parent, R.layout.item_tab_videotext));
        }
        return null;
    }

    @Override
    protected void bindData(@NonNull RecyclerView.ViewHolder holder, int position, DynamicBean.Datas item) {
        switch (getItemViewType(position)) {
            case IMAGE_TEXT:
                Glide.with(mContext)
                        .load(item.getUpImg())
                        .placeholder(R.drawable.skeleton_circular_grey)
                        .into(((TabImageHolder) holder).VNImage);
                ((TabImageHolder) holder).VN_userName.setText(item.getUpName());
                ((TabImageHolder) holder).VN_Title.setText(item.getContent());
                ((TabImageHolder) holder).VN_date.setText(item.getCreateTime().substring(5, 10));
                ((TabImageHolder) holder).multiView.setLayoutParams(new LinearLayout.LayoutParams(900, ViewGroup.LayoutParams.WRAP_CONTENT));
                ((TabImageHolder) holder).multiView.setImages(item.getImgs().split(","));
                ((TabImageHolder) holder).VN_main.setTag(position);
                break;
            case VIDEO_TEXT:
                ((TabVideoHolder) holder).VN_main.setTag(position);
                ((TabVideoHolder) holder).VNideo.setPlayTag(TAG);
                ((TabVideoHolder) holder).VNideo.setPlayPosition(position);
                String[] urlList = item.getObject().getUrls().split(",");
                ((TabVideoHolder) holder).VNideo.setUp(urlList[0], true, null, null, item.getObject().getTitle());

                ((TabVideoHolder) holder).imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(mContext)
                        .load(item.getObject().getPreviewUrl())
                        .placeholder(R.drawable.skeleton_circular_grey)
                        .into(((TabVideoHolder) holder).imageView);
                if (((TabVideoHolder) holder).imageView.getParent() != null) {
                    ViewGroup viewGroup = (ViewGroup) ((TabVideoHolder) holder).imageView.getParent();
                    viewGroup.removeView(((TabVideoHolder) holder).imageView);
                }

                ((TabVideoHolder) holder).VNideo.setThumbImageView(((TabVideoHolder) holder).imageView);
                ((TabVideoHolder) holder).VNideo.setNeedShowWifiTip(false);
                if (GSYVideoManager.instance().getPlayTag().equals(VideoNewsAdapter.TAG)
                        && (position == GSYVideoManager.instance().getPlayPosition())) {
                    ((TabVideoHolder) holder).VNideo.getThumbImageViewLayout().setVisibility(View.GONE);
                } else {
                    ((TabVideoHolder) holder).VNideo.getThumbImageViewLayout().setVisibility(View.VISIBLE);
                }
                Glide.with(mContext)
                        .load(item.getUpImg())
                        .into(((TabVideoHolder) holder).VNImage);
                ((TabVideoHolder) holder).VN_userName.setText(item.getUpName());

                ((TabVideoHolder) holder).videoTime.setText(DensityUtil.timeParse(item.getObject().getLength()));
                ((TabVideoHolder) holder).videoDanmu.setText(item.getObject().getDanmuNum() + "弹幕");
                ((TabVideoHolder) holder).videoPlayer.setText(item.getObject().getPlayNum() + "播放量");
                ((TabVideoHolder) holder).VN_videoTitle.setText(item.getObject().getTitle());
                ((TabVideoHolder) holder).VN_Title.setText(item.getContent());
                ((TabVideoHolder) holder).VN_date.setText(item.getCreateTime().substring(5, 10));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (getData().get(position).getType()) {
            case "VIDEO":
                return VIDEO_TEXT;

            case "STANDARD":
                return IMAGE_TEXT;
        }
        return 0;
    }

    public class TabVideoHolder extends RecyclerView.ViewHolder {
        private SwitchVideo VNideo;
        private CircleImageView VNImage;
        private TextView VN_userName, VN_Title, VN_date, VN_videoTitle, videoTime, videoPlayer, videoDanmu;
        private ImageView VN_more, imageView;
        private RelativeLayout VN_main;

        public TabVideoHolder(@NonNull View itemView) {
            super(itemView);
            VN_main = itemView.findViewById(R.id.IT_main);
            VNideo = itemView.findViewById(R.id.video_item_player);
            VNImage = itemView.findViewById(R.id.VN_user_img);
            VN_userName = itemView.findViewById(R.id.VN_user_name);
            VN_Title = itemView.findViewById(R.id.VN_Title);
            VN_date = itemView.findViewById(R.id.VN_date);
            VN_videoTitle = itemView.findViewById(R.id.VN_videoTitle);
            videoTime = itemView.findViewById(R.id.videoTime);
            videoPlayer = itemView.findViewById(R.id.videoPlayer);
            videoDanmu = itemView.findViewById(R.id.videoDanmu);
            imageView = new ImageView(mContext);
            VNideo.setVideoAllCallBack(new GSYSampleCallBack() {
                @Override
                public void onStartPrepared(String url, Object... objects) {
                    super.onStartPrepared(url, objects);
                    videoTime.setVisibility(View.GONE);
                    videoPlayer.setVisibility(View.GONE);
                    videoDanmu.setVisibility(View.GONE);
                }
            });
            VN_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GSYVideoManager.onPause();
                    int playtime = VNideo.getVideoTime();
                    intent = new Intent(mContext, VideoActivity.class);
                    intent.putExtra("id", getData().get((int) v.getTag()).getObject().getId());
                    intent.putExtra("uid", getData().get((int) v.getTag()).getObject().getUid());
                    intent.putExtra("playtime", playtime);
                    mContext.startActivity(intent);
                    intent = null;
                }
            });
        }
    }

    public class TabImageHolder extends RecyclerView.ViewHolder {
        private CircleImageView VNImage;
        private TextView VN_userName, VN_Title, VN_date;
        private ImageView VN_more;
        private RelativeLayout VN_main;
        private MultiView multiView;

        public TabImageHolder(@NonNull View itemView) {
            super(itemView);
            VN_main = itemView.findViewById(R.id.IT_main);
            multiView = itemView.findViewById(R.id.VN_multi);
            VNImage = itemView.findViewById(R.id.VN_user_img);
            VN_userName = itemView.findViewById(R.id.VN_user_name);
            VN_Title = itemView.findViewById(R.id.VN_Title);
            VN_date = itemView.findViewById(R.id.VN_date);
            VN_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(mContext, DynamicDetailsActivity.class);
                    intent.putExtra("id",getData().get((int) v.getTag()).getId());
                    intent.putExtra("uid",getData().get((int) v.getTag()).getUid());
                    mContext.startActivity(intent);
                    intent = null;
                }
            });
        }
    }
}
