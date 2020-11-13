package com.example.dildil.dynamic_page.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customcontrollibs.BaseAdapter;
import com.example.dildil.R;
import com.example.dildil.channel_page.adapter.BeInterestedChannerAdapter;
import com.example.dildil.dynamic_page.bean.TypeBean;

public class TypeAdapter extends BaseAdapter<TypeBean.TypeData, RecyclerView.ViewHolder> {
    private final Context mContext;
    private BeInterestedChannerAdapter adapter;
    private final int RECENT_VISIT = 1;
    private final int PURSUE_VISIT = 2;
    private final int TITLE = 3;
    private PursueAdapter adapters;

    public TypeAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case RECENT_VISIT:
                return new RecentVisit(inflateView(parent, R.layout.item_recent_visit));
            case PURSUE_VISIT:
                return new PursueVisit(inflateView(parent, R.layout.item_pursue_visit));
            default:
                return new TitleVisit(inflateView(parent, R.layout.item_title_visit));
        }
    }

    @Override
    protected void bindData(@NonNull RecyclerView.ViewHolder holder, int position, TypeBean.TypeData item) {
        if (getItemViewType(position) == RECENT_VISIT) {
            Log.e("why", ": RECENT_VISIT数据为" + item.getTopicBean());
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            if (adapter == null) {
                adapter = new BeInterestedChannerAdapter(mContext);
            }
            ((RecentVisit) holder).Recycler.setLayoutManager(layoutManager);
            ((RecentVisit) holder).Recycler.setAdapter(adapter);
            adapter.loadMore(item.getTopicBean());
        } else if (getItemViewType(position) == PURSUE_VISIT) {
            Log.e("why", ": PURSUE_VISIT数据为" + item.getPursueBean());
            LinearLayoutManager layoutManagerSecond = new LinearLayoutManager(mContext);
            layoutManagerSecond.setOrientation(LinearLayoutManager.HORIZONTAL);
            if (adapters == null) {
                adapters = new PursueAdapter(mContext);
            }
            ((PursueVisit) holder).Recycler.setLayoutManager(layoutManagerSecond);
            ((PursueVisit) holder).Recycler.setAdapter(adapters);
            adapters.loadMore(item.getPursueBean());
        } else if (getItemViewType(position) == TITLE) {
            ((TitleVisit) holder).textView.setText(item.getTitle());
        }
    }


    @Override
    public int getItemViewType(int position) {
        switch (getData().get(position).getType()) {
            case 1:
                return RECENT_VISIT;
            case 2:
                return PURSUE_VISIT;
            default:
                return TITLE;
        }
    }

    public static class RecentVisit extends RecyclerView.ViewHolder {
        private RecyclerView Recycler;

        public RecentVisit(@NonNull View itemView) {
            super(itemView);
            Recycler = itemView.findViewById(R.id.Recycler);
        }
    }

    public static class PursueVisit extends RecyclerView.ViewHolder {
        private RecyclerView Recycler;

        public PursueVisit(@NonNull View itemView) {
            super(itemView);
            Recycler = itemView.findViewById(R.id.Recycler);
        }
    }

    public static class TitleVisit extends RecyclerView.ViewHolder {
        private TextView textView;

        public TitleVisit(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_1);
        }
    }


}

