package com.example.dildil.home_page.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.customcontrollibs.BaseAdapter;
import com.example.customcontrollibs.RoundRelativeLayout;
import com.example.dildil.R;
import com.example.dildil.home_page.bean.BannerBean;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.home_page.dialog.VideoChoiceDialog;
import com.example.dildil.util.BannerImageAdapter;
import com.example.dildil.util.DensityUtil;
import com.example.dildil.video.view.VideoActivity;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

public class RecommendedVideoAdapter extends BaseAdapter<RecommendVideoBean.BeanData, RecyclerView.ViewHolder> {
    private final Context mContext;
    private final VideoChoiceDialog videoChoiceDialog;
    private boolean isLoad = false;
    private int positions;
    private int type = 0;

    public RecommendedVideoAdapter(Context context, VideoChoiceDialog videoChoiceDialog, int value) {
        this.videoChoiceDialog = videoChoiceDialog;
        mContext = context;
        type = value;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new RecHeaderHolder(inflateView(parent, R.layout.item_recommended_header));
        } else {
            return new RecViewHolder(inflateView(parent, R.layout.item_recommendedvideo));
        }
    }

    @Override
    protected void bindData(@NonNull RecyclerView.ViewHolder holder, int position, RecommendVideoBean.BeanData item) {
        if (getItemViewType(position) == TYPE_HEADER) {
            if (!isLoad) {
                ((RecHeaderHolder) holder).banner.setIndicatorGravity(IndicatorConfig.Direction.RIGHT);
                ((RecHeaderHolder) holder).banner.setBannerRound(15);
                ((RecHeaderHolder) holder).banner.setClipToOutline(true);
                ((RecHeaderHolder) holder).banner.start();
                ((RecHeaderHolder) holder).banner.setAdapter(new BannerImageAdapter<BannerBean>(getData().get(position).getBannerUrl()) {

                    @Override
                    public void onBindView(BannerImageHolder holder, BannerBean data, int position, int size) {
                        //图片加载自己实现
                        Glide.with(holder.itemView)
                                .load(data.getImageUrl())
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                                .placeholder(R.drawable.skeleton_circular_grey)
                                .into(holder.imageView);
                    }
                }, true)
                        .addBannerLifecycleObserver((LifecycleOwner) mContext)//添加生命周期观察者
                        .setIndicator(new CircleIndicator(mContext));
                isLoad = true;
            }
        } else {
            Glide.with(mContext)
                    .load(item.getCover())
                    .placeholder(R.drawable.skeleton_circular_grey)
                    .into(((RecViewHolder) holder).cover);
            ((RecViewHolder) holder).play_volume.setText(String.valueOf(item.getPlayNum()));
            ((RecViewHolder) holder).time.setText(DensityUtil.timeParse(item.getLength()));
            ((RecViewHolder) holder).barrage_volume.setText(String.valueOf(item.getDanmunum()));
            ((RecViewHolder) holder).title.setText(item.getTitle());
            ((RecViewHolder) holder).Re_video.setTag(position);
            ((RecViewHolder) holder).more.setTag(position);
        }
    }

    /**
     * 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view *
     */
    @Override
    public int getItemViewType(int position) {
        if (getData().get(position).isBanner()) {
            return TYPE_HEADER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {   // 布局是GridLayoutManager所管理
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果是Header、Footer的对象则占据spanCount的位置，否则就只占用1个位置
                    return (getData().get(position).isBanner()) ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    public class RecViewHolder extends RecyclerView.ViewHolder {
        private final ImageView more, cover;
        private final TextView play_volume, barrage_volume, title, time;
        private final RoundRelativeLayout Re_video;

        public RecViewHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.Re_cover);
            play_volume = itemView.findViewById(R.id.Re_play_volume);
            barrage_volume = itemView.findViewById(R.id.Re_barrage_volume);
            Re_video = itemView.findViewById(R.id.Re_video_cover);
            title = itemView.findViewById(R.id.Re_title);
            more = itemView.findViewById(R.id.Re_more);
            time = itemView.findViewById(R.id.Re_video_time);

            Re_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positions = (int) v.getTag();
                    Intent intent = new Intent(mContext, VideoActivity.class);
                    intent.putExtra("id", getData().get(positions).getId());
                    intent.putExtra("uid", getData().get(positions).getUid());
                    mContext.startActivity(intent);
                }
            });
            Re_video.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    positions = (int) v.getTag();
                    if (videoChoiceDialog != null) {
                        videoChoiceDialog.setData(getData().get(positions).getUpName(), positions, type);
                        videoChoiceDialog.show();
                    }
                    return false;
                }
            });
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positions = (int) v.getTag();
                    if (videoChoiceDialog != null) {
                        videoChoiceDialog.setData(getData().get(positions).getUpName(), positions, type);
                        videoChoiceDialog.show();
                    }
                }
            });
        }
    }

    public static class RecHeaderHolder extends RecyclerView.ViewHolder {
        private final Banner banner;

        public RecHeaderHolder(@NonNull View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.Re_banner);
        }
    }
}
