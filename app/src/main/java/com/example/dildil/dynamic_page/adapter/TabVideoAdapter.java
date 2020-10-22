package com.example.dildil.dynamic_page.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.dildil.dynamic_page.bean.VideoNewsBean;
import com.example.dildil.dynamic_page.view.SwitchVideo;
import com.example.dildil.video.view.VideoActivity;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import cn.lemon.multi.MultiView;

public class TabVideoAdapter extends BaseAdapter<VideoNewsBean, RecyclerView.ViewHolder> {
    public static final String TAG = "VideoNewsAdapter";
    private Context mContext;
    private static final int IMAGE_TEXT = 1;
    private static final int VIDEO_TEXT = 2;

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
    protected void bindData(@NonNull RecyclerView.ViewHolder holder, int position, VideoNewsBean item) {
        switch (getItemViewType(position)) {
            case IMAGE_TEXT:
                Glide.with(mContext).load(item.getVideo_UserImage()).into(((TabImageHolder) holder).VNImage);
                ((TabImageHolder) holder).VN_userName.setText(item.getVideo_UserName());
                ((TabImageHolder) holder).VN_Title.setText(item.getVideo_Title());
                ((TabImageHolder) holder).VN_date.setText(item.getVideo_Time());
                ((TabImageHolder) holder).multiView.setLayoutParams(new LinearLayout.LayoutParams(900, ViewGroup.LayoutParams.WRAP_CONTENT));
                ((TabImageHolder) holder).multiView.setImages(item.getImages());
                break;
            case VIDEO_TEXT:
                ((TabVideoHolder) holder).VNideo.setPlayTag(TAG);
                ((TabVideoHolder) holder).VNideo.setPlayPosition(position);
                ((TabVideoHolder) holder).VNideo.setUp(item.getVideo_Url(), true, null, null, "这是title");

                ((TabVideoHolder) holder).imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(mContext).load(item.getVideo_Cover()).into(((TabVideoHolder) holder).imageView);
                if (((TabVideoHolder) holder).imageView.getParent() != null) {
                    ViewGroup viewGroup = (ViewGroup) ((TabVideoHolder) holder).imageView.getParent();
                    viewGroup.removeView(((TabVideoHolder) holder).imageView);
                }
                ((TabVideoHolder) holder).VNideo.setThumbImageView(((TabVideoHolder) holder).imageView);
                if (GSYVideoManager.instance().getPlayTag().equals(VideoNewsAdapter.TAG)
                        && (position == GSYVideoManager.instance().getPlayPosition())) {
                    ((TabVideoHolder) holder).VNideo.getThumbImageViewLayout().setVisibility(View.GONE);
                } else {
                    ((TabVideoHolder) holder).VNideo.getThumbImageViewLayout().setVisibility(View.VISIBLE);
                }

                Glide.with(mContext).load(item.getVideo_UserImage()).into(((TabVideoHolder) holder).VNImage);
                ((TabVideoHolder) holder).VN_userName.setText(item.getVideo_UserName());
                ((TabVideoHolder) holder).VN_Title.setText(item.getVideo_Title());
                ((TabVideoHolder) holder).VN_date.setText(item.getVideo_Time());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getData().get(position).getType();
    }

    public class TabVideoHolder extends RecyclerView.ViewHolder {
        private SwitchVideo VNideo;
        private CircleImageView VNImage;
        private TextView VN_userName, VN_Title, VN_date;
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
            imageView = new ImageView(mContext);
            VN_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GSYVideoManager.onPause();
                    int playtime = VNideo.getVideoTime();
                    Intent intent = new Intent(mContext, VideoActivity.class);
                    intent.putExtra("playtime", playtime);
                    Log.e(TAG, "onClick: 能否获取到当前播放的进度?" + playtime);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public class TabImageHolder extends RecyclerView.ViewHolder {
        private CircleImageView VNImage;
        private TextView VN_userName, VN_Title, VN_date;
        private ImageView VN_more, imageView;
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
            imageView = new ImageView(mContext);
        }
    }
}
