package com.example.dildil.home_page.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.liuzhuang.rcimageview.RoundCornerImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.customcontrollibs.BaseAdapter;
import com.example.dildil.R;
import com.example.dildil.home_page.bean.MyPursuitBean;
import com.example.dildil.home_page.view.BangumiDetailsActivity;

public class MyPursuitAdapter extends BaseAdapter<MyPursuitBean, MyPursuitAdapter.PursuitViewHolder> {
    private Context mContext;

    public MyPursuitAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    protected PursuitViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PursuitViewHolder(inflateView(parent, R.layout.item_mypursuit));
    }

    @Override
    protected void bindData(@NonNull PursuitViewHolder holder, int position, MyPursuitBean item) {
        Glide.with(mContext).load(item.getMyPursueImage()).apply(holder.requestOptions).into(holder.image);
        holder.image_text.setText("更新至第" + item.getToUpdate_word() + "话");
        holder.title.setText(item.getMyPursueName());
        if (!item.getWatch_Situation().equals("尚未观看")) {
            holder.watch.setTextColor(mContext.getResources().getColor(R.color.Pink));
        }
        holder.watch.setText(item.getWatch_Situation());
        holder.main.setTag(position);
    }

    public class PursuitViewHolder extends RecyclerView.ViewHolder {
        private final RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.skeleton_circular_grey);
        private final RoundCornerImageView image;
        private final TextView image_text;
        private final TextView title;
        private final TextView watch;
        private RelativeLayout main;

        public PursuitViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.MP_image);
            image_text = itemView.findViewById(R.id.MP_image_text);
            title = itemView.findViewById(R.id.MP_title);
            watch = itemView.findViewById(R.id.MP_watch);
            main = itemView.findViewById(R.id.main);
            main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BangumiDetailsActivity.class);
                    //intent.putExtra("uid", getData().get((Integer) v.getTag()).get);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
