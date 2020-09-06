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
import com.example.dildil.util.XToastUtils;
import com.example.dildil.video.view.VideoActivity;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

public class RecommendedVideoAdapter extends BaseRecyclerAdapter<RecommendVideoBean.BeanData>{
    private static final String TAG = "RecommendedVideoAdapter";
    private ImageView cover,more;
    private TextView play_volume,barrage_volume,title;
    private Context mContext;
    private CardView Re_video;
    private RecommendVideoBean videoBean;
    private int id,uid;
    public RecommendedVideoAdapter(Context context){
        mContext = context;
    }

    public void setData(RecommendVideoBean bean){
        videoBean = bean;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_recommendedvideo;
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
                Intent intent = new Intent(mContext,VideoActivity.class);
                intent.putExtra("id",videoBean.getData().get(position).getId());
                intent.putExtra("uid",videoBean.getData().get(position).getUid());
                mContext.startActivity(intent);
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XToastUtils.info("暂未开发");
            }
        });
        if (item!=null){
            initData(position, item);
        }
    }

    private void initData( int position, RecommendVideoBean.BeanData item){
        Glide.with(mContext)
                .load(item.getCover())
                .into(cover);
        play_volume.setText(item.getPlayNum()+"");
        barrage_volume.setText(item.getDanmunum()+"");
        title.setText(item.getTitle());
    }


}
