package com.example.dildil.home_page.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.example.dildil.R;
import com.example.dildil.home_page.bean.VideoBean;
import com.example.dildil.util.XToastUtils;
import com.example.dildil.video.view.VideoActivity;
import com.gcssloop.widget.RCRelativeLayout;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

public class RecommendedVideoAdapter extends BaseRecyclerAdapter<VideoBean> implements View.OnClickListener{
    private ImageView cover,more;
    private TextView play_volume,barrage_volume,title;
    private Context mContext;
    private RCRelativeLayout Re_video;
    public RecommendedVideoAdapter(Context context){
        mContext = context;
    }
    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_recommendedvideo;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, VideoBean item) {
        cover = holder.findViewById(R.id.Re_cover);
        play_volume = holder.findViewById(R.id.Re_play_volume);
        barrage_volume = holder.findViewById(R.id.Re_barrage_volume);
        Re_video = holder.findViewById(R.id.Re_video_cover);
        title = holder.findViewById(R.id.Re_title);
        more = holder.findViewById(R.id.Re_more);
        Re_video.setOnClickListener(this);
        more.setOnClickListener(this);
        if (item!=null){
            initData(position,item);
        }
    }

    private void initData( int position, VideoBean item){
        Glide.with(mContext)
                .load(item.getImgurl())
                .into(cover);
        play_volume.setText(item.getPlay_volume()+"");
        barrage_volume.setText(item.getBarrage_volume()+"");
        title.setText(item.getTitle());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Re_video_cover:
                ActivityUtils.startActivity(VideoActivity.class);
                break;
            case R.id.Re_more:
                XToastUtils.info("暂未开发");
                break;
        }
    }
}
