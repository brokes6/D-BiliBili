package com.example.dildil.channel_page.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customcontrollibs.BaseAdapter;
import com.example.customcontrollibs.ImageTopView;
import com.example.dildil.R;
import com.example.dildil.dynamic_page.bean.AttentionDetailsBean;

public class BeInterestedChannerAdapter extends BaseAdapter<AttentionDetailsBean.Data, BeInterestedChannerAdapter.BeViewHolder> {
    private final Context mContext;

    public BeInterestedChannerAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    protected BeViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BeViewHolder(inflateView(parent, R.layout.item_be_interested));
    }

    @Override
    protected void bindData(@NonNull BeViewHolder holder, int position, AttentionDetailsBean.Data item) {
        holder.mainImage.setUrl(item.getImg());
        holder.mainImage.setText(item.getUsername());
    }

    public static class BeViewHolder extends RecyclerView.ViewHolder {
        private ImageTopView mainImage;

        public BeViewHolder(@NonNull View itemView) {
            super(itemView);
            mainImage = itemView.findViewById(R.id.BI_Main);
        }
    }
}
