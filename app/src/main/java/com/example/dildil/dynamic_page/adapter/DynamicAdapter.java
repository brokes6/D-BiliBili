package com.example.dildil.dynamic_page.adapter;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.liuzhuang.rcimageview.CircleImageView;
import com.android.liuzhuang.rcimageview.RoundCornerImageView;
import com.bumptech.glide.Glide;
import com.example.dildil.R;
import com.example.dildil.dynamic_page.bean.DynamicBean;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

public class DynamicAdapter extends BaseRecyclerAdapter<DynamicBean> implements View.OnClickListener{
    private static final String TAG = "DynamicAdapter";
    private Context mContext;
    private CircleImageView userimg;
    private TextView comment_num,thmbus_num;
    private RoundCornerImageView video_cover;
    private RelativeLayout video_introduce,main;
    public DynamicAdapter(Context context){
        mContext = context;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_dynamic;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, DynamicBean item) {
        userimg = holder.findViewById(R.id.Dy_user_img);
        video_cover = holder.findViewById(R.id.Dy_video_cover);
        video_introduce = holder.findViewById(R.id.video_introduce);
        comment_num = holder.findViewById(R.id.comment_number);
        main = holder.findViewById(R.id.Dy_main);
        thmbus_num = holder.findViewById(R.id.thumbs_number);

        main.setOnClickListener(this);
        if (item != null) {
            Glide.with(mContext).load(item.getUser_img()).into(userimg);
            if (item.getVideo_cover()==null){
                video_cover.setVisibility(View.GONE);
                video_introduce.setVisibility(View.GONE);
            }else{
                Glide.with(mContext).load(item.getVideo_cover()).into(video_cover);
                holder.text(R.id.V_time,item.getVideo_time());
                holder.text(R.id.V_Playback_volume,item.getVideo_Playback_volume()+"播放量");
                holder.text(R.id.V_bullet_chat,item.getVideo_bullet_chat()+"弹幕");
            }
            holder.text(R.id.Dy_user_name,item.getUser_name());
            holder.text(R.id.Dy_date,item.getRelease_date());
            holder.text(R.id.Dy_text,item.getText());
            comment_num.setText(item.getComment_num()+"");
            thmbus_num.setText(item.getThumbs_num()+"");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Dy_main:

                break;
        }
    }
}
