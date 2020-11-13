package com.example.dildil.home_page.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.liuzhuang.rcimageview.CircleImageView;
import com.bumptech.glide.Glide;
import com.example.customcontrollibs.BaseAdapter;
import com.example.dildil.R;
import com.example.dildil.home_page.bean.WholeStationBean;

public class WholeStationAdapter extends BaseAdapter<WholeStationBean.Detail, WholeStationAdapter.WholeViewHolder> {
    private Context mContext;

    public WholeStationAdapter(Context context){
        mContext = context;
    }

    @NonNull
    @Override
    protected WholeViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WholeViewHolder(inflateView(parent,R.layout.item_whole_station));
    }

    @Override
    protected void bindData(@NonNull WholeViewHolder holder, int position, WholeStationBean.Detail item) {
        Glide.with(mContext).load(item.getCover()).into(holder.image);
        holder.title.setText(item.getTitle());
        holder.upName.setText(item.getUpName());
//        Glide.with(mContext).load(item.getData().get(position).)
        holder.danmu.setText(String.valueOf(item.getDanmuNum()));
    }

    public static class WholeViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView title,score,danmu,upName,follow;
        private CircleImageView upImage;

        public WholeViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.videoImage);
            title = itemView.findViewById(R.id.title);
            score = itemView.findViewById(R.id.score);
            danmu = itemView.findViewById(R.id.danmu);
            upName = itemView.findViewById(R.id.upName);
            follow = itemView.findViewById(R.id.follow);
            upImage = itemView.findViewById(R.id.upImage);
        }
    }
}
