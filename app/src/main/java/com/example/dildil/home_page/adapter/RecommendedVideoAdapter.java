package com.example.dildil.home_page.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.dildil.R;
import com.example.dildil.home_page.bean.VideoBean;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

public class RecommendedVideoAdapter extends BaseRecyclerAdapter<VideoBean> {
    private ImageView cover;
    private TextView play_volume,barrage_volume,title;
    private Context mContext;
    public RecommendedVideoAdapter(Context context){
        mContext = context;
    }
    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_recommendedvideo;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, VideoBean item) {
        cover = holder.findViewById(R.id.cover);
        play_volume = holder.findViewById(R.id.R_play_volume);
        barrage_volume = holder.findViewById(R.id.R_barrage_volume);
        title = holder.findViewById(R.id.R_title);
        if (item!=null){
            Glide.with(mContext).load(item.getImgurl()).into(cover);
            play_volume.setText(item.getPlay_volume()+"");
            barrage_volume.setText(item.getBarrage_volume()+"");
            title.setText(item.getTitle());
        }
    }
}
