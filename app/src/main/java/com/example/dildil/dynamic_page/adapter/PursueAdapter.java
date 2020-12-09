package com.example.dildil.dynamic_page.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.liuzhuang.rcimageview.RoundCornerImageView;
import com.bumptech.glide.Glide;
import com.example.customcontrollibs.BaseAdapter;
import com.example.dildil.R;
import com.example.dildil.dynamic_page.bean.PursueBean;
import com.example.dildil.home_page.view.BangumiDetailsActivity;

public class PursueAdapter extends BaseAdapter<PursueBean, PursueAdapter.PursueViewHolder> {
    private Context mContext;

    public PursueAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    protected PursueViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PursueViewHolder(inflateView(parent, R.layout.item_pursue));
    }

    @Override
    protected void bindData(@NonNull PursueViewHolder holder, int position, PursueBean item) {
        Glide.with(mContext).load(item.getPursueImage()).placeholder(R.drawable.skeleton_circular_grey).into(holder.image);
        holder.image_text.setText("更新至第" + item.getToUpdate_word() + "话");
        holder.title.setText(item.getPursueName());
        holder.image.setTag(position);
    }

    public class PursueViewHolder extends RecyclerView.ViewHolder {
        private RoundCornerImageView image;
        private TextView image_text, title;

        public PursueViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.IP_image);
            image_text = itemView.findViewById(R.id.IP_image_text);
            title = itemView.findViewById(R.id.IP_title);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BangumiDetailsActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
