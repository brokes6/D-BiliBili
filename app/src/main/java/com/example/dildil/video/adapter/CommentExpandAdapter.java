package com.example.dildil.video.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dildil.R;
import com.example.dildil.login_page.bean.LoginBean;
import com.example.dildil.util.GsonUtil;
import com.example.dildil.util.SharePreferenceUtil;
import com.example.dildil.util.TimeDateUtils;
import com.example.dildil.video.bean.CommentDetailBean;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.dildil.util.TimeDateUtils.FORMAT_TYPE_2;

public class CommentExpandAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "CommentExpandAdapter";
    private Context mContext;
    private List<CommentDetailBean.CommentData> commentBeanList;
    private LoginBean loginBean;

    public CommentExpandAdapter(Context context, CommentDetailBean list) {
        this.mContext = context;
        this.commentBeanList = list.getData();
    }

    @Override
    public int getGroupCount() {
        return commentBeanList.size();
    }

    @Override
    public int getChildrenCount(int position) {
        //判断此评论是否右回复
        if (commentBeanList.get(position).getReplyList() == null) {
            return 0;
        } else {
            //如果回复不为空，则取最大值（getReplyList().size()和0）
            return Math.max(commentBeanList.get(position).getReplyList().size(), 0);
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        //获取有几组
        return commentBeanList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        //获取有几个子回复
        return commentBeanList.get(childPosition).getReplyList().size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    boolean isLike = false;


    /**
     * 设置主评论的数据
     *
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupHolder groupHolder;
        //判断是否绑定了View
        if (convertView == null) {
            //绑定View
            convertView = LayoutInflater.from(mContext).inflate(R.layout.comment_item_layout, parent, false);
            groupHolder = new GroupHolder(convertView);
            //类似于给View绑定ViewHolder
            convertView.setTag(groupHolder);
        } else {
            //否则就返回一个当前的View实例
            groupHolder = (GroupHolder) convertView.getTag();
        }
        loginBean = GsonUtil.fromJSON(SharePreferenceUtil.getInstance(mContext).getUserInfo(""), LoginBean.class);
        //将数据展示设置上去
        if (commentBeanList.get(groupPosition).getUsername().equals(loginBean.getData().getUsername())) {
            groupHolder.comment_follow.setVisibility(View.GONE);
        }
        Glide.with(mContext).load(commentBeanList.get(groupPosition).getImg()).into(groupHolder.logo);
        groupHolder.tv_name.setText(commentBeanList.get(groupPosition).getUsername());
        String data = TimeDateUtils.long2String(commentBeanList.get(groupPosition).getCreateTime(), FORMAT_TYPE_2);
        groupHolder.tv_time.setText(data);
        groupHolder.tv_content.setText(commentBeanList.get(groupPosition).getContent());
        groupHolder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLike) {
                    isLike = false;
                    groupHolder.iv_like.setColorFilter(Color.parseColor("#aaaaaa"));
                } else {
                    isLike = true;
                    groupHolder.iv_like.setColorFilter(Color.parseColor("#FF5C5C"));
                }
            }
        });

        return convertView;
    }

    /**
     * 设置子评论的数据
     *
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildHolder childHolder;
        if (convertView == null) {
            //也是一样的给子评论绑定View
            convertView = LayoutInflater.from(mContext).inflate(R.layout.comment_reply_item_layout, parent, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
            //绑定ViewHolder
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        if (childPosition == 0) {
            childHolder.secondLevel.setBackgroundResource(R.drawable.file_background_radius_top_grey);
            childHolder.secondLevel.setPadding(30, 25, 0, 0);
        } else if (isLastChild) {
            childHolder.secondLevel.setBackgroundResource(R.drawable.file_background_radius_bottom_grey);
            childHolder.secondLevel.setPadding(30, 0, 0, 25);
        } else {
            childHolder.secondLevel.setBackgroundColor(mContext.getResources().getColor(R.color.White_ash));
            childHolder.secondLevel.setPadding(30, 15, 0, 15);
        }
        if (childPosition > 3) {
            childHolder.tv_name.setText("查看更多评论 >");
            childHolder.tv_content.setVisibility(View.GONE);
        } else {
            //设置数据
            String replyUser = commentBeanList.get(groupPosition).getReplyList().get(childPosition).getUsername();
            if (!TextUtils.isEmpty(replyUser)) {
                childHolder.tv_name.setText(replyUser + ":");
            }
            childHolder.tv_content.setText(commentBeanList.get(groupPosition).getReplyList().get(childPosition).getContent());
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    /**
     * 主评论的ViewHolder
     */
    private class GroupHolder {
        private CircleImageView logo;
        private TextView tv_name, tv_content, tv_time, comment_follow;
        private ImageView iv_like;

        public GroupHolder(View view) {
            logo = view.findViewById(R.id.comment_item_logo);
            tv_content = view.findViewById(R.id.comment_item_content);
            tv_name = view.findViewById(R.id.comment_item_userName);
            tv_time = view.findViewById(R.id.comment_item_time);
            iv_like = view.findViewById(R.id.comment_item_like);
            comment_follow = view.findViewById(R.id.comment_follow);
        }
    }


    /**
     * 二级评论的ViewHolder
     */
    private class ChildHolder {
        private TextView tv_name, tv_content;
        private LinearLayout secondLevel;

        public ChildHolder(View view) {
            tv_name = view.findViewById(R.id.reply_item_user);
            tv_content = view.findViewById(R.id.reply_item_content);
            secondLevel = view.findViewById(R.id.secondLevel);
        }
    }

    /**
     * func:评论成功后插入一条数据
     *
     * @param commentDetailBean 新的评论数据
     */
    public void addTheCommentData(CommentDetailBean.CommentData commentDetailBean) {
        if (commentDetailBean != null) {
            //每次评论成功之后，将当前的评论传递进来
            commentBeanList.add(commentDetailBean);
            //通知适配器进行刷新
            notifyDataSetChanged();
        } else {
            //如果传递进来的评论为空，则提示
            throw new IllegalArgumentException("评论数据为空!");
        }

    }

    /**
     * func:回复成功后插入一条数据
     *
     * @param replyDetailBean 新的回复数据
     */
    public void addTheReplyData(CommentDetailBean.CommentData.replyData replyDetailBean, int groupPosition) {
        //每次回复成功之后，都要将回复的评论传递进来
        if (replyDetailBean != null) {
            Log.e(TAG, "addTheReplyData: >>>>该刷新回复列表了:" + replyDetailBean.toString());
            //首先判断此主评论是否有二级评论
            if (commentBeanList.get(groupPosition).getReplyList() != null) {
                //如果有，则将当前的回复添加进去
                commentBeanList.get(groupPosition).getReplyList().add(replyDetailBean);
            } else {
                //如果没有，则新建二级评论，并添加
                List<CommentDetailBean.CommentData.replyData> replyList = new ArrayList<>();
                replyList.add(replyDetailBean);
                commentBeanList.get(groupPosition).setReplyList(replyList);
            }
            //通知适配器进行刷新
            notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("回复数据为空!");
        }

    }

    /**
     * by moos on 2018/04/20
     * func:添加和展示所有回复
     *
     * @param replyBeanList 所有回复数据
     * @param groupPosition 当前的评论
     */
    private void addReplyList(List<CommentDetailBean.CommentData.replyData> replyBeanList, int groupPosition) {
        //首先判断二级评论是否存在
        if (commentBeanList.get(groupPosition).getReplyList() != null) {
            //清空当前的二级评论
            commentBeanList.get(groupPosition).getReplyList().clear();
            //重新将现在的二级评论添加进来
            commentBeanList.get(groupPosition).getReplyList().addAll(replyBeanList);
        } else {
            //如果二级评论不存在，则直接设置二级评论
            commentBeanList.get(groupPosition).setReplyList(replyBeanList);
        }
        //通知适配器进行刷新
        notifyDataSetChanged();
    }


}
