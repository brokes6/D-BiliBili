package com.example.dildil.channel_page.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.customcontrollibs.CustomChannelView;
import com.example.dildil.R;
import com.example.dildil.channel_page.bean.HaveViewedBean;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;

public class HaveViewedAdapter extends BaseRecyclerAdapter<HaveViewedBean> {
    private Context mContext;
    private TextView title,time;
    private ImageView topImage;
    private CircleImageView middleImage;
    private CustomChannelView CCView;

    public HaveViewedAdapter(Context context){
        mContext = context;
    }
    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_have_viewed;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, HaveViewedBean item) {
        CCView = holder.findViewById(R.id.HV_Image);
        CCView.onWindowFocusChanged(true);
        if (item != null) {
            initData(item);
        }
    }

    private void initData(HaveViewedBean item) {
        if (item.getTop_Image() != null) {
            CCView.setTopUrl(item.getTop_Image());
        }
        CCView.setMiddleUrl(item.getMiddle_Image());
        CCView.setThemeText(item.getTitle());
        CCView.setThemeTime(item.getTitle_time());
    }
}
