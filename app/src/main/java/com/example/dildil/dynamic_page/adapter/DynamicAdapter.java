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

public class DynamicAdapter extends BaseRecyclerAdapter<DynamicBean.Datas> implements View.OnClickListener {
    private Context mContext;
    private CircleImageView userimg;
    private TextView comment_num, thmbus_num;
    private RoundCornerImageView video_cover;
    private RelativeLayout video_introduce, main;
//    private MultiView multi_view;

    public DynamicAdapter(Context context) {
        mContext = context;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_dynamic;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, DynamicBean.Datas item) {
        userimg = holder.findViewById(R.id.Dy_user_img);
        //multi_view = holder.findViewById(R.id.multi_view);
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
        Glide.with(mContext).load(item.getUpImg()).placeholder(R.drawable.skeleton_circular_grey).into(userimg);
        holder.text(R.id.Dy_user_name, item.getUpName());
        holder.text(R.id.Dy_text, item.getContent());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Dy_main:

                break;
            case R.id.Dy_video_cover:
                ActivityUtils.startActivity(VideoActivity.class);
                break;
        }
    }
}
