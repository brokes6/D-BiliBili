package com.example.dildil.dynamic_page.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.liuzhuang.rcimageview.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.customcontrollibs.BaseAdapter;
import com.example.customcontrollibs.viewground.ShowComment;
import com.example.dildil.R;
import com.example.dildil.dynamic_page.bean.DynamicBean;
import com.example.dildil.dynamic_page.view.DynamicDetailsActivity;
import com.example.dildil.dynamic_page.view.SwitchVideo;
import com.example.dildil.my_page.view.PersonalActivity;
import com.example.dildil.util.DateUtils;
import com.example.dildil.video.view.VideoActivity;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class TabVideoAdapter extends BaseAdapter<DynamicBean.Datas, RecyclerView.ViewHolder> implements BGANinePhotoLayout.Delegate {
    public static final String TAG = "TabVideoAdapter";
    private final Context mContext;
    private static final int IMAGE_TEXT = 1;
    private static final int VIDEO_TEXT = 2;
    private BGANinePhotoLayout mCurrentClickNpl;

    public TabVideoAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case IMAGE_TEXT:
                return new TabImageHolder(inflateView(parent, R.layout.item_tab_imagetext));
            case VIDEO_TEXT:
                return new TabVideoHolder(inflateView(parent, R.layout.item_tab_videotext));
        }
        return null;
    }

    @Override
    protected void bindData(@NonNull RecyclerView.ViewHolder holder, int position, DynamicBean.Datas item) {
        switch (getItemViewType(position)) {
            case IMAGE_TEXT:
                Glide.with(mContext)
                        .load(item.getUpImg())
                        .apply(((TabImageHolder)holder).requestOptions)
                        .into(((TabImageHolder) holder).VNImage);
                ((TabImageHolder) holder).VN_userName.setText(item.getUpName());
                ((TabImageHolder) holder).VN_Title.setText(item.getContent());
                ((TabImageHolder) holder).VN_date.setText(item.getCreateTime().substring(5, 10));
                ((TabImageHolder) holder).multiView.setData(new ArrayList<>(Arrays.asList(item.getImgs().split(","))));
                ((TabImageHolder) holder).multiView.setDelegate(this);
                ((TabImageHolder) holder).VN_main.setTag(position);
                ((TabImageHolder) holder).VNImage.setTag(position);
                ((TabImageHolder) holder).comment.setUserName("fuxinbo:");
                ((TabImageHolder) holder).comment.setUserComment("热门评论");
                break;
            case VIDEO_TEXT:
                ((TabVideoHolder) holder).VN_main.setTag(position);
                ((TabVideoHolder) holder).VNideo.setPlayTag(TAG);
                ((TabVideoHolder) holder).VNideo.setPlayPosition(position);
                String[] urlList = item.getObject().getUrls().split(",");
                ((TabVideoHolder) holder).VNideo.setUp(urlList[0], true, null, null, item.getObject().getTitle());

                ((TabVideoHolder) holder).imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(mContext)
                        .load(item.getObject().getPreviewUrl())
                        .apply(((TabVideoHolder)holder).requestOptions)
                        .into(((TabVideoHolder) holder).imageView);
                if (((TabVideoHolder) holder).imageView.getParent() != null) {
                    ViewGroup viewGroup = (ViewGroup) ((TabVideoHolder) holder).imageView.getParent();
                    viewGroup.removeView(((TabVideoHolder) holder).imageView);
                }

                ((TabVideoHolder) holder).VNideo.setThumbImageView(((TabVideoHolder) holder).imageView);
                ((TabVideoHolder) holder).VNideo.setNeedShowWifiTip(false);
                if (GSYVideoManager.instance().getPlayTag().equals(TAG)
                        && (position == GSYVideoManager.instance().getPlayPosition())) {
                    ((TabVideoHolder) holder).VNideo.getThumbImageViewLayout().setVisibility(View.GONE);
                } else {
                    ((TabVideoHolder) holder).VNideo.getThumbImageViewLayout().setVisibility(View.VISIBLE);
                }
                Glide.with(mContext)
                        .load(item.getUpImg())
                        .into(((TabVideoHolder) holder).VNImage);
                ((TabVideoHolder) holder).VN_userName.setText(item.getUpName());
                ((TabVideoHolder) holder).VNImage.setTag(position);

                ((TabVideoHolder) holder).videoTime.setText(DateUtils.timeParse(item.getObject().getLength()));
                ((TabVideoHolder) holder).videoDanmu.setText(item.getObject().getDanmuNum() + "弹幕");
                ((TabVideoHolder) holder).videoPlayer.setText(item.getObject().getPlayNum() + "播放量");
                ((TabVideoHolder) holder).VN_videoTitle.setText(item.getObject().getTitle());
                ((TabVideoHolder) holder).VN_Title.setText(item.getContent());
                ((TabVideoHolder) holder).VN_date.setText(item.getCreateTime().substring(5, 10));
                ((TabVideoHolder) holder).comment.setUserName("fuxinbo:");
                ((TabVideoHolder) holder).comment.setUserComment("热门评论");
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (getData().get(position).getType()) {
            case "VIDEO":
                return VIDEO_TEXT;

            case "STANDARD":
                return IMAGE_TEXT;
        }
        return 0;
    }

    @Override
    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
        mCurrentClickNpl = ninePhotoLayout;
        photoPreviewWrapper();
    }

    @Override
    public void onClickExpand(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {

    }

    @AfterPermissionGranted(1)
    private void photoPreviewWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(mContext, perms)) {
            File downloadDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerDownload");
            BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(mContext)
                    .saveImgDir(downloadDir); // 保存图片的目录，如果传 null，则没有保存图片功能

            if (mCurrentClickNpl.getItemCount() == 1) {
                // 预览单张图片
                photoPreviewIntentBuilder.previewPhoto(mCurrentClickNpl.getCurrentClickItem());
            } else if (mCurrentClickNpl.getItemCount() > 1) {
                // 预览多张图片
                photoPreviewIntentBuilder.previewPhotos(mCurrentClickNpl.getData())
                        .currentPosition(mCurrentClickNpl.getCurrentClickItemPosition()); // 当前预览图片的索引
            }
            mContext.startActivity(photoPreviewIntentBuilder.build());
        } else {
            EasyPermissions.requestPermissions((Activity) mContext, "图片预览需要以下权限:\n\n1.访问设备上的照片", 1, perms);
        }
    }

    public class TabVideoHolder extends RecyclerView.ViewHolder {
        private final RequestOptions requestOptions= new RequestOptions().placeholder(R.drawable.skeleton_circular_grey);
        private final SwitchVideo VNideo;
        private final CircleImageView VNImage;
        private final TextView VN_userName, VN_Title, VN_date, VN_videoTitle, videoTime, videoPlayer, videoDanmu;
        private ImageView VN_more, imageView;
        private final RelativeLayout VN_main;
        private final ShowComment comment;

        public TabVideoHolder(@NonNull View itemView) {
            super(itemView);
            VN_main = itemView.findViewById(R.id.IT_main);
            VNideo = itemView.findViewById(R.id.video_item_player);
            VNImage = itemView.findViewById(R.id.VN_user_img);
            VN_userName = itemView.findViewById(R.id.VN_user_name);
            VN_Title = itemView.findViewById(R.id.VN_Title);
            VN_date = itemView.findViewById(R.id.VN_date);
            VN_videoTitle = itemView.findViewById(R.id.VN_videoTitle);
            videoTime = itemView.findViewById(R.id.videoTime);
            videoPlayer = itemView.findViewById(R.id.videoPlayer);
            videoDanmu = itemView.findViewById(R.id.videoDanmu);
            comment = itemView.findViewById(R.id.comment);
            imageView = new ImageView(mContext);
            VNideo.setVideoAllCallBack(new GSYSampleCallBack() {
                @Override
                public void onStartPrepared(String url, Object... objects) {
                    super.onStartPrepared(url, objects);
                    videoTime.setVisibility(View.GONE);
                    videoPlayer.setVisibility(View.GONE);
                    videoDanmu.setVisibility(View.GONE);
                }
            });
            VN_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GSYVideoManager.onPause();
                    int playtime = VNideo.getVideoTime();
                    Intent intent = new Intent(mContext, VideoActivity.class);
                    intent.putExtra("id", getData().get((int) v.getTag()).getObject().getId());
                    intent.putExtra("uid", getData().get((int) v.getTag()).getObject().getUid());
                    intent.putExtra("playtime", playtime);
                    mContext.startActivity(intent);
                }
            });
            VNImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PersonalActivity.class);
                    intent.putExtra("uid", getData().get((int) v.getTag()).getUid());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public class TabImageHolder extends RecyclerView.ViewHolder {
        private final RequestOptions requestOptions= new RequestOptions().placeholder(R.drawable.skeleton_circular_grey);
        private final CircleImageView VNImage;
        private final TextView VN_userName, VN_Title, VN_date;
        private ImageView VN_more;
        private final RelativeLayout VN_main;
        private final BGANinePhotoLayout multiView;
        private final ShowComment comment;

        public TabImageHolder(@NonNull View itemView) {
            super(itemView);
            VN_main = itemView.findViewById(R.id.IT_main);
            multiView = itemView.findViewById(R.id.VN_multi);
            VNImage = itemView.findViewById(R.id.VN_user_img);
            VN_userName = itemView.findViewById(R.id.VN_user_name);
            VN_Title = itemView.findViewById(R.id.VN_Title);
            comment = itemView.findViewById(R.id.comment);
            VN_date = itemView.findViewById(R.id.VN_date);
            VN_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DynamicDetailsActivity.class);
                    intent.putExtra("id", getData().get((int) v.getTag()).getId());
                    mContext.startActivity(intent);
                }
            });
            VNImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PersonalActivity.class);
                    intent.putExtra("uid", getData().get((int) v.getTag()).getUid());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
