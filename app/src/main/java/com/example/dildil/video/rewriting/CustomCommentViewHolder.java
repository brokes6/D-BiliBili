package com.example.dildil.video.rewriting;

import android.view.View;
import android.widget.TextView;

import com.example.dildil.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomCommentViewHolder {
    public CircleImageView ico;
    public TextView userName,prizes,comment,userData;

    public CustomCommentViewHolder(View view) {
        userName=view.findViewById(R.id.userName);
        prizes=view.findViewById(R.id.comment_item_like_num);
        comment=view.findViewById(R.id.userContent);
        userData=view.findViewById(R.id.userData);
        ico=view.findViewById(R.id.ico);
    }
}