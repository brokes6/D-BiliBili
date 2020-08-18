package com.example.dildil.dynamic_page.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.liuzhuang.rcimageview.CircleImageView;
import com.bumptech.glide.Glide;
import com.example.dildil.R;
import com.example.dildil.dynamic_page.bean.VideoNewsBean;
import com.example.dildil.dynamic_page.view.SwitchVideo;
import com.example.dildil.video.view.VideoActivity;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

public class VideoNewsAdapter extends BaseRecyclerAdapter<VideoNewsBean> implements View.OnClickListener {
    public static final String TAG = "VideoNewsAdapter";
    private Context mContext;
    private SwitchVideo VNideo;
    private CircleImageView VNImage;
    private TextView VN_userName, VN_Title, VN_date;
    private ImageView VN_more, imageView;
    private RelativeLayout IT_main;

    public VideoNewsAdapter(Context context) {
        mContext = context;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_video_news;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, VideoNewsBean item) {
        IT_main = holder.findViewById(R.id.IT_main);
        VNideo = holder.findViewById(R.id.video_item_player);
        VNImage = holder.findViewById(R.id.VN_user_img);
        VN_userName = holder.findViewById(R.id.VN_user_name);
        VN_Title = holder.findViewById(R.id.VN_Title);
        VN_date = holder.findViewById(R.id.VN_date);
        imageView = new ImageView(mContext);

        IT_main.setOnClickListener(this);
        if (item != null) {
            initData(position, item);
        }
    }


    private void initData(int position, VideoNewsBean item) {
        VNideo.setPlayTag(TAG);
        VNideo.setPlayPosition(position);
        VNideo.setUp(item.getVideo_Url(), true, null, null, "这是title");

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(mContext).load(item.getVideo_Cover()).into(imageView);
        if (imageView.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) imageView.getParent();
            viewGroup.removeView(imageView);
        }
        VNideo.setThumbImageView(imageView);
        if (GSYVideoManager.instance().getPlayTag().equals(VideoNewsAdapter.TAG)
                && (position == GSYVideoManager.instance().getPlayPosition())) {
            VNideo.getThumbImageViewLayout().setVisibility(View.GONE);
        } else {
            VNideo.getThumbImageViewLayout().setVisibility(View.VISIBLE);
        }

        Glide.with(mContext).load(item.getVideo_UserImage()).into(VNImage);
        VN_userName.setText(item.getVideo_UserName());
        VN_Title.setText(item.getVideo_Title());
        VN_date.setText(item.getVideo_Time());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IT_main:
                GSYVideoManager gsyVideoManager = GSYVideoManager.instance();
                gsyVideoManager.onPause();
                int playtime = VNideo.getCurrentPositionWhenPlaying();
                Intent intent = new Intent(mContext, VideoActivity.class);
                intent.putExtra("playtime", playtime);
                mContext.startActivity(intent);
                Log.e(TAG, "onClick: ???????????????"+playtime);
                break;
        }
    }
}
