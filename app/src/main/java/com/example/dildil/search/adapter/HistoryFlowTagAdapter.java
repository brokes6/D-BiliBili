package com.example.dildil.search.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.dildil.R;
import com.xuexiang.xui.widget.flowlayout.BaseTagAdapter;

public class HistoryFlowTagAdapter extends BaseTagAdapter<String,TextView> {

    public HistoryFlowTagAdapter(Context context) {
        super(context);
    }

    @Override
    protected TextView newViewHolder(View convertView) {
        return (TextView) convertView.findViewById(R.id.tv_tag);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.adapter_item_tag;
    }

    @Override
    protected void convert(TextView holder, String item, int position) {
        holder.setText(item);
    }
}
