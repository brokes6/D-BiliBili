package com.example.dildil.video.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import com.example.dildil.rewriting_view.RecyclingPagerAdapter;

/**
 * Created by wujian on 2016/3/23.
 * RecyclingPagerAdapter是Jake WhartonAndroid大神封装的可用于复用的PagerAdapter。
 */
public class TubatuAdapter extends RecyclingPagerAdapter {

    private List<Integer> mList;
    private Context mContext;
    private List<String> strList;
    private OnItemListener listener;

    public TubatuAdapter(Context context,List<String> strList) {
        mList = new ArrayList<>();
        mContext = context;
        this.strList = strList;

    }

    public void addAll(List<Integer> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void setListener(OnItemListener listener) {
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup container) {
        ImageView imageView = null;
        if (convertView == null) {
            imageView = new ImageView(mContext);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setTag(position);
        imageView.setImageResource(mList.get(position));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onLikeClick(position);
                }
//                Toast.makeText(mContext, strList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        return imageView;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    public interface OnItemListener {
        void onLikeClick(int position);
    }
}