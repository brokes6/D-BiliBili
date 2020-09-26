package com.example.dildil.video.rewriting;

import android.view.View;
import android.widget.TextView;

import com.example.dildil.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomCommentViewHolder {
    public CircleImageView ico;
    public TextView userName,prizes,comment;

    public CustomCommentViewHolder(View view) {
        userName=view.findViewById(R.id.user);
        prizes=view.findViewById(R.id.prizes);
        comment=view.findViewById(R.id.data);
        ico=view.findViewById(R.id.ico);
    }
}