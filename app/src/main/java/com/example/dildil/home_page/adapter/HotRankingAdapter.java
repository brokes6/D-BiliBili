package com.example.dildil.home_page.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.android.liuzhuang.rcimageview.RoundCornerImageView;
import com.bumptech.glide.Glide;
import com.example.dildil.R;
import com.example.dildil.home_page.bean.HotRankingBean;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.adapter.recyclerview.XRecyclerAdapter;

import java.util.Collection;

public class HotRankingAdapter extends BaseRecyclerAdapter<HotRankingBean> implements View.OnClickListener{
    private static final String TAG = "HotRankingAdapter";
    private Context mContext;
    private RoundCornerImageView video_cover;
    private RelativeLayout hot_video_cover;
    private ItemOnClickListener listener;
    public HotRankingAdapter(Context context){
        mContext = context;
    }
    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_hot_ranking_list;
    }


    public void setListener(ItemOnClickListener listener) {
        this.listener = listener;
        Log.e(TAG, "onClick: 当前的listener为"+listener );
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, HotRankingBean item) {
        video_cover = holder.findViewById(R.id.Ranking_video_img);
        hot_video_cover = holder.findViewById(R.id.Hot_video_cover);
        hot_video_cover.setOnClickListener(this);
        if (item != null) {
            Glide.with(mContext).load(item.getVideo_cover()).into(video_cover);
            holder.text(R.id.Ranking_video_Title,item.getVideo_title());
            holder.text(R.id.Ranking_video_up,item.getVideo_up());
            holder.text(R.id.Ranking_video_time,item.getVideo_time());
            holder.text(R.id.Ranking_video_play_num,item.getVideo_play_num());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Hot_video_cover:
                Log.e(TAG, "onClick: 当前的listener为"+listener );
                if (listener != null) {
                    listener.onClick(getSelectPosition());
                }
                break;
        }
    }

    public interface ItemOnClickListener {

        void onClick(int position);

    }

    @Override
    public XRecyclerAdapter loadMore(Collection<HotRankingBean> collection) {

        return super.loadMore(collection);
    }
}
