package com.example.dildil.dynamic_page.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.liuzhuang.rcimageview.CircleImageView;
import com.bumptech.glide.Glide;
import com.example.dildil.R;
import com.example.dildil.dynamic_page.bean.DynamicBean;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

public class DynamicAdapter extends BaseRecyclerAdapter<DynamicBean> {
    private static final String TAG = "DynamicAdapter";
    private Context mContext;
    private CircleImageView userimg;
    private TextView comment_num,thmbus_num;
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
        comment_num = holder.findViewById(R.id.comment_number);
        thmbus_num = holder.findViewById(R.id.thumbs_number);
        if (item != null) {
            Glide.with(mContext).load(item.getUser_img()).into(userimg);
            holder.text(R.id.Dy_user_name,item.getUser_name());
            holder.text(R.id.Dy_date,item.getRelease_date());
            holder.text(R.id.Dy_text,item.getText());
            comment_num.setText(item.getComment_num()+"");
            thmbus_num.setText(item.getThumbs_num()+"");
        }
    }
}
