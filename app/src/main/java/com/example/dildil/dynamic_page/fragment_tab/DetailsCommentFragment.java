package com.example.dildil.dynamic_page.fragment_tab;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.FragmentDetailscommentBinding;
import com.example.dildil.dynamic_page.bean.DynamicBean;
import com.example.dildil.dynamic_page.contract.DynamicContract;
import com.example.dildil.dynamic_page.presenter.DynamicPresenter;
import com.example.dildil.login_page.bean.UserBean;
import com.example.dildil.my_page.view.PersonalActivity;
import com.example.dildil.util.XToastUtils;
import com.example.dildil.video.bean.CommentBean;
import com.example.dildil.video.bean.CommentDetailBean;
import com.example.dildil.video.rewriting.CustomCommentViewHolder;
import com.example.dildil.video.rewriting.CustomReplyViewHolder;
import com.example.dildil.video.rewriting.CustomViewStyleConfigurator;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jidcoo.android.widget.commentview.callback.CustomCommentItemCallback;
import com.jidcoo.android.widget.commentview.callback.CustomReplyItemCallback;
import com.jidcoo.android.widget.commentview.callback.OnItemClickCallback;
import com.jidcoo.android.widget.commentview.callback.OnPullRefreshCallback;
import com.jidcoo.android.widget.commentview.callback.OnReplyLoadMoreCallback;

import javax.inject.Inject;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class DetailsCommentFragment extends BaseFragment implements DynamicContract.View {
    private FragmentDetailscommentBinding binding;
    private CustomCommentViewHolder holder = null;
    private CustomReplyViewHolder holders = null;
    private BottomSheetDialog dialog;
    private EmojIconActions emojIcon;
    private int id;
    private UserBean userBean;

    @Inject
    DynamicPresenter mPresenter;

    public DetailsCommentFragment(int id) {
        this.id = id;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detailscomment, container, false);
        DaggerActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .activityModule(new ActivityModule(getActivity()))
                .build().inject(this);
        mPresenter.attachView(this);
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        initCommentList();
    }

    @Override
    protected void initData() {
        MyApplication.getDatabase(getContext()).userDao().getAll()
                .observe(DetailsCommentFragment.this, new Observer<UserBean>() {

                    @Override
                    public void onChanged(UserBean userBeans) {
                        userBean = userBeans;
                        mPresenter.getDynamicComment(id, 1, 10, userBeans.getData().getId());
                    }
                });
    }

    @Override
    protected void initLocalData() {

    }

    @Override
    public void onClick(View v) {

    }

    public void getComment() {
        mPresenter.getDynamicComment(id, 1, 10, userBean.getData().getId());
    }


    /**
     * 初始化评论和回复列表
     */
    private void initCommentList() {
        binding.commentView.setViewStyleConfigurator(new CustomViewStyleConfigurator(getContext()));
        binding.commentView.setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.item_comment_empty, null));
        binding.commentView.setErrorView(LayoutInflater.from(getContext()).inflate(R.layout.item_comment_error, null));
        binding.commentView.callbackBuilder()
                //自定义评论布局(必须使用ViewHolder机制)--CustomCommentItemCallback<C> 泛型C为自定义评论数据类
                .customCommentItem(new CustomCommentItemCallback<CommentDetailBean.CommentData>() {
                    @Override
                    public View buildCommentItem(int groupPosition, CommentDetailBean.CommentData item, LayoutInflater inflater, View convertView, ViewGroup parent) {
                        //使用方法就像adapter里面的getView()方法一样
                        if (convertView == null) {
                            //使用自定义布局
                            convertView = inflater.inflate(R.layout.comment_item_layout, parent, false);
                            holder = new CustomCommentViewHolder(convertView);
                            //必须使用ViewHolder机制
                            convertView.setTag(holder);
                        } else {
                            holder = (CustomCommentViewHolder) convertView.getTag();
                        }
                        holder.ico.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), PersonalActivity.class);
                                intent.putExtra("uid", item.getUid());
                                startActivity(intent);
                            }
                        });
                        holder.prizes.setText("0");
                        Glide.with(getContext()).load(item.getImg()).into(holder.ico);
                        holder.userName.setText(item.getUsername());
                        holder.userData.setText("10-15");
                        holder.comment.setText(item.getContent());
                        return convertView;
                    }
                })
                //自定义评论布局(必须使用ViewHolder机制）
                // 并且自定义ViewHolder类必须继承自com.jidcoo.android.widget.commentview.view.ViewHolder
                // --CustomReplyItemCallback<R> 泛型R为自定义回复数据类
                .customReplyItem(new CustomReplyItemCallback<CommentDetailBean.CommentData.replyData>() {
                    @Override
                    public View buildReplyItem(int groupPosition, int childPosition, boolean isLastReply, CommentDetailBean.CommentData.replyData item, LayoutInflater inflater, View convertView, ViewGroup parent) {
                        //使用方法就像adapter里面的getView()方法一样
                        if (convertView == null) {
                            //使用自定义布局
                            convertView = inflater.inflate(R.layout.item_secondary_review_comment, parent, false);
                            holders = new CustomReplyViewHolder(convertView);
                            //必须使用ViewHolder机制
                            convertView.setTag(holders);
                        } else {
                            holders = (CustomReplyViewHolder) convertView.getTag();
                        }
                        if (childPosition > 3) {
                            holders.userName.setVisibility(View.GONE);
                            holders.reply.setVisibility(View.GONE);
                        } else {
                            holders.userName.setText(item.getUsername());
                            holders.reply.setText(":" + item.getContent());
                        }
                        return convertView;
                    }
                })
                //下拉刷新回调
                .setOnPullRefreshCallback(new MyOnPullRefreshCallback())
                //评论、回复Item的点击回调（点击事件回调）
                .setOnItemClickCallback(new MyOnItemClickCallback())
                //回复数据加载更多回调（加载更多回复）
                .setOnReplyLoadMoreCallback(new MyOnReplyLoadMoreCallback())
                .buildCallback();
    }

    /**
     * 回复加载更多回调类
     */
    class MyOnReplyLoadMoreCallback implements OnReplyLoadMoreCallback<CommentDetailBean.CommentData.replyData> {


        @Override
        public void loading(CommentDetailBean.CommentData.replyData reply, int willLoadPage) {
            if (willLoadPage == 2) {
                Log.e("why", "loading: 》》》》》》》》评论过多");
            }
        }

        @Override
        public void complete() {

        }

        @Override
        public void failure(String msg) {

        }
    }

    class MyOnPullRefreshCallback implements OnPullRefreshCallback {

        @Override
        public void refreshing() {

        }

        @Override
        public void complete() {
            XToastUtils.success("刷新成功");
        }

        @Override
        public void failure(String msg) {
            XToastUtils.error(msg);
        }
    }

    class MyOnItemClickCallback implements OnItemClickCallback<CommentDetailBean.CommentData, CommentDetailBean.CommentData.replyData> {

        @Override
        public void commentItemOnClick(int position, CommentDetailBean.CommentData comment, View view) {
            //showReplyDialog(position, comment);
        }

        @Override
        public void replyItemOnClick(int c_position, int r_position, CommentDetailBean.CommentData.replyData reply, View view) {
        }
    }

    /**
     * by moos on 2020/04/20
     * func:弹出评论框
     */
    private void showCommentDialog() {
        dialog = new BottomSheetDialog(getContext(), R.style.BottomSheetEdit);
        View commentView = LayoutInflater.from(getContext()).inflate(R.layout.comment_dialog_layout, null);
        final EmojiconEditText commentText = commentView.findViewById(R.id.dialog_comment_et);
        final TextView bt_comment = commentView.findViewById(R.id.dialog_comment_bt);
        final ImageView emoji = commentView.findViewById(R.id.CB_emoji);
        commentText.setFocusable(true);
        commentText.setFocusableInTouchMode(true);
        commentText.requestFocus();
        dialog.setContentView(commentView);
        emojIcon = new EmojIconActions(getContext(), commentView, commentText, emoji);
        emojIcon.ShowEmojIcon();
        emojIcon.setIconsIds(R.drawable.ic_action_keyboard, R.drawable.smiley);
        /**
         * 解决bsd显示不全的情况
         */
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0, 0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());
        CommentDetailBean.CommentData detailBean = new CommentDetailBean.CommentData();

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(commentContent)) {
                    dialog.dismiss();
                    detailBean.setUsername(userBean.getData().getUsername());
                    detailBean.setImg(userBean.getData().getImg());
                    detailBean.setContent(commentContent);
                    binding.commentView.addComment(detailBean);
                    //mPresenter.AddComment(new dto(commentContent, id, 0), getUserId());
                } else {
                    XToastUtils.toast("评论内容不能为空");
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length() > 2) {
                    bt_comment.setTextColor(Color.parseColor("#fa7298"));
                } else {
                    bt_comment.setTextColor(Color.parseColor("#c0c0c0"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    @Override
    public void onGetDynamicSuccess(DynamicBean dynamicBean) {

    }

    @Override
    public void onGetDynamicFail(String e) {

    }

    @Override
    public void onGetVideoDynamicSuccess(DynamicBean dynamicBean) {

    }

    @Override
    public void onGetVideoDynamicFail(String e) {

    }

    @Override
    public void onGetAddDynamicCommentSuccess(CommentBean commentBean) {

    }

    @Override
    public void onGetAddDynamicCommentFail(String e) {

    }

    @Override
    public void onGetDynamicCommentSuccess(CommentDetailBean commentDetailBean) {
        binding.commentView.loadComplete(commentDetailBean);
    }

    @Override
    public void onGetDynamicCommentFail(String e) {
        Log.e("why", "获取评论出现错误" + e);
    }

}
