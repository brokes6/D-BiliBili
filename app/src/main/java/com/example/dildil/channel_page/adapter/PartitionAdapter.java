package com.example.dildil.channel_page.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.customcontrollibs.BaseAdapter;
import com.example.dildil.R;
import com.example.dildil.channel_page.bean.PartitionBean;

public class PartitionAdapter extends BaseAdapter<PartitionBean.Data, PartitionAdapter.PartitionViewHolder> {
    private final Context mContext;

    public PartitionAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    protected PartitionViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PartitionViewHolder(inflateView(parent, R.layout.item_partition));
    }

    @Override
    protected void bindData(@NonNull PartitionViewHolder holder, int position, PartitionBean.Data item) {
        Glide.with(mContext).load(item.getLogo()).into(holder.image);
        holder.title.setText(item.getName());
    }

    public static class PartitionViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView title;

        public PartitionViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.Partition_image);
            title = itemView.findViewById(R.id.Partition_Title);
        }
    }
}
