package com.example.dildil.dynamic_page.adapter;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.liuzhuang.rcimageview.CircleImageView;
import com.android.liuzhuang.rcimageview.RoundCornerImageView;
import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.example.dildil.R;
import com.example.dildil.dynamic_page.bean.DynamicBean;
import com.example.dildil.video.view.VideoActivity;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

import cn.lemon.multi.MultiView;

public class DynamicAdapter extends BaseRecyclerAdapter<DynamicBean.Datas> implements View.OnClickListener{
    private static final String TAG = "DynamicAdapter";
    private Context mContext;
    private CircleImageView userimg;
    private TextView comment_num,thmbus_num;
    private RoundCornerImageView video_cover;
    private RelativeLayout video_introduce,main;
    private MultiView multi_view;

    public DynamicAdapter(Context context){
        mContext = context;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_dynamic;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, DynamicBean.Datas item) {
        userimg = holder.findViewById(R.id.Dy_user_img);
        multi_view = holder.findViewById(R.id.multi_view);
        video_cover = holder.findViewById(R.id.Dy_video_cover);
        video_introduce = holder.findViewById(R.id.video_introduce);
        comment_num = holder.findViewById(R.id.comment_number);
        main = holder.findViewById(R.id.Dy_main);
        thmbus_num = holder.findViewById(R.id.thumbs_number);

        video_cover.setOnClickListener(this);
        main.setOnClickListener(this);
        if (item != null) {
            initData(holder, item);
        }
    }

    private void initData(@NonNull RecyclerViewHolder holder, DynamicBean.Datas item) {
        Glide.with(mContext).load(item.getUpImg()).into(userimg);
        holder.text(R.id.Dy_user_name,item.getUpName());
        //holder.text(R.id.Dy_date,item.getRelease_date());
        holder.text(R.id.Dy_text,item.getTitle());
        //comment_num.setText(item.getComment_num()+"");
        //thmbus_num.setText(item.getThumbs_num()+"");
//        switch (item.getPictureStatus()){
//            case 1:
//                video_cover.setVisibility(View.GONE);
//                video_introduce.setVisibility(View.GONE);
//                break;
//            case 2:
//                Glide.with(mContext).load(item.getVideo_cover()).into(video_cover);
//                holder.text(R.id.V_time,item.getVideo_time());
//                holder.text(R.id.V_Playback_volume,item.getVideo_Playback_volume()+"播放量");
//                holder.text(R.id.V_bullet_chat,item.getVideo_bullet_chat()+"弹幕");
//                break;
//            case 3:
//                multi_view.setVisibility(View.VISIBLE);
//                video_cover.setVisibility(View.GONE);
//                video_introduce.setVisibility(View.GONE);
//                multi_view.setImages(item.getShowPictures());
//                break;
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Dy_main:

                break;
            case R.id.Dy_video_cover:
                ActivityUtils.startActivity(VideoActivity.class);
                break;
        }
    }
}
