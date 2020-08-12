package com.example.dildil.search.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.dildil.R;
import com.example.dildil.search.bean.HotSearchBean;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

public class HotSearchAdapter extends BaseRecyclerAdapter<HotSearchBean> {
    private Context mContext;
    private TextView num,title;
    private ImageView img;

    public HotSearchAdapter(Context context){
        mContext = context;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_hot_search;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, HotSearchBean item) {
        img = holder.findViewById(R.id.HS_degree);
        num = holder.findViewById(R.id.HS_num);
        if (item != null) {
            holder.text(R.id.HS_num,(position+1)+"");
            holder.text(R.id.HS_Title,item.getHotSearchTitle());
            if (position>3){
                num.setTextColor(R.color.White_ash);
            }
            switch (item.getDegree()){
                case 0:
                    img.setVisibility(View.GONE);
                    break;
                case 1:
                    //hot
                    img.setImageResource(R.mipmap.hot);
                    break;
                case 2:
                    //new
                    img.setImageResource(R.mipmap.snew);
                    break;
            }

        }
    }
}
