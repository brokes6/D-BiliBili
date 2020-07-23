package com.example.dildil.dynamic_page.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.dildil.R;
import com.example.dildil.dynamic_page.bean.TopicBean;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

public class TopicAdapter extends BaseRecyclerAdapter<TopicBean> {
    private Context mContext;
    public TopicAdapter(Context context){
        mContext = context;
    }
    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_topic;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, TopicBean item) {
        if (item!=null){
            holder.text(R.id.topic_type,item.getTopicType());
            holder.text(R.id.topic_name,item.getTopicName());
        }
    }
}
