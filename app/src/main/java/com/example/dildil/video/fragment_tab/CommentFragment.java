package com.example.dildil.video.fragment_tab;

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

import com.bumptech.glide.Glide;
import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.FragmentCommentBinding;
import com.example.dildil.home_page.bean.RecommendVideoBean;
import com.example.dildil.login_page.bean.LoginBean;
import com.example.dildil.util.GsonUtil;
import com.example.dildil.util.SharePreferenceUtil;
import com.example.dildil.util.SharedPreferencesUtil;
import com.example.dildil.util.XToastUtils;
import com.example.dildil.video.bean.CoinBean;
import com.example.dildil.video.bean.CollectionBean;
import com.example.dildil.video.bean.CommentDetailBean;
import com.example.dildil.video.bean.DanmuBean;
import com.example.dildil.video.bean.SeadDanmuBean;
import com.example.dildil.video.bean.ThumbsUpBean;
import com.example.dildil.video.bean.VideoDetailsBean;
import com.example.dildil.video.contract.VideoDetailsContract;
import com.example.dildil.video.presenter.VideoDetailsPresenter;
import com.example.dildil.video.rewriting.CustomCommentViewHolder;
import com.example.dildil.video.rewriting.CustomReplyViewHolder;
import com.example.dildil.video.rewriting.CustomViewStyleConfigurator;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jidcoo.android.widget.commentview.callback.CustomCommentItemCallback;
import com.jidcoo.android.widget.commentview.callback.CustomReplyItemCallback;
import com.jidcoo.android.widget.commentview.callback.OnItemClickCallback;
import com.jidcoo.android.widget.commentview.callback.OnReplyLoadMoreCallback;

import javax.inject.Inject;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class CommentFragment extends BaseFragment implements VideoDetailsContract.View {
    private static final String TAG = "CommentFragment";
    private FragmentCommentBinding binding;
    private BottomSheetDialog dialog;
    private EmojIconActions emojIcon;
    private boolean isLoad = true;
    private int id, uid;
    private LoginBean loginBean;

    @Inject
    VideoDetailsPresenter mPresenter;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment, container, false);

        DaggerActivityComponent.builder().appComponent(MyApplication.getAppComponent())
                .activityModule(new ActivityModule(getActivity()))
                .build().inject(this);
        mPresenter.attachView(this);
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        binding.detailPageDoComment.setOnClickListener(this);
        initCommentList();
    }

    @Override
    protected void initData() {
        showDialog();
        id = (int) SharedPreferencesUtil.getData("id", 0);
        uid = (int) SharedPreferencesUtil.getData("uid", 0);
        loginBean = GsonUtil.fromJSON(SharePreferenceUtil.getInstance(getContext()).getUserInfo(""), LoginBean.class);
        mPresenter.getVideoComment(id, 1, 10, uid);
        isLoad = false;
    }

    @Override
    protected void initLocalData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.detail_page_do_comment) {
            showCommentDialog();
        }
    }

    /**
     * 初始化评论和回复列表
     */
    private void initExpandableListView(final CommentDetailBean commentList) {
        binding.commentView.loadComplete(commentList);
    }

    private void initCommentList() {
        binding.commentView.setViewStyleConfigurator(new CustomViewStyleConfigurator(getContext()));
        binding.commentView.callbackBuilder()
                //自定义评论布局(必须使用ViewHolder机制)--CustomCommentItemCallback<C> 泛型C为自定义评论数据类
                .customCommentItem(new CustomCommentItemCallback<CommentDetailBean.CommentData>() {
                    @Override
                    public View buildCommentItem(int groupPosition, CommentDetailBean.CommentData item, LayoutInflater inflater, View convertView, ViewGroup parent) {
                        //使用方法就像adapter里面的getView()方法一样
                        final CustomCommentViewHolder holder;
                        if (convertView == null) {
                            //使用自定义布局
                            convertView = inflater.inflate(R.layout.comment_item_layout, parent, false);
                            holder = new CustomCommentViewHolder(convertView);
                            //必须使用ViewHolder机制
                            convertView.setTag(holder);
                        } else {
                            holder = (CustomCommentViewHolder) convertView.getTag();
                        }
                        holder.prizes.setText("0");
                        Glide.with(getContext()).load(item.getImg()).into(holder.ico);
                        holder.userName.setText(item.getUsername());
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
                        CustomReplyViewHolder holder = null;
                        if (convertView == null) {
                            //使用自定义布局
                            convertView = inflater.inflate(R.layout.item_secondary_review_comment, parent, false);
                            holder = new CustomReplyViewHolder(convertView);
                            //必须使用ViewHolder机制
                            convertView.setTag(holder);
                        } else {
                            holder = (CustomReplyViewHolder) convertView.getTag();
                        }
                        if (childPosition>3){
                            holder.userName.setVisibility(View.GONE);
                            holder.reply.setVisibility(View.GONE);
                            holder.prizes.setVisibility(View.GONE);
                        }else{
                            holder.userName.setText(item.getUsername());
                            holder.reply.setText(item.getContent());
                            holder.prizes.setText("0");
                        }
                        return convertView;
                    }
                })
//                //下拉刷新回调
//                .setOnPullRefreshCallback(new MyOnPullRefreshCallback())
                //评论、回复Item的点击回调（点击事件回调）
                .setOnItemClickCallback(new MyOnItemClickCallback())
                //回复数据加载更多回调（加载更多回复）
                .setOnReplyLoadMoreCallback(new MyOnReplyLoadMoreCallback())
                .buildCallback();
    }

    /**
     * by moos on 2018/04/20
     * func:弹出回复框
     */
    private void showReplyDialog(final int position,CommentDetailBean.CommentData item) {
        //本质上就是弹出一个输入框（使用了系统自带的底部弹窗）
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
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentText.setHint("回复 @" + item.getUsername() + " 的评论:");
        commentView.measure(0, 0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(replyContent)) {
                    dialog.dismiss();
                    CommentDetailBean.CommentData.replyData replyData = new CommentDetailBean.CommentData.replyData();
                    replyData.setContent(replyContent);
                    replyData.setImg(loginBean.getData().getImg());
                    replyData.setUsername(loginBean.getData().getUsername());
                    binding.commentView.addReply(replyData,position);
                    XToastUtils.toast("回复成功");
                } else {
                    XToastUtils.toast("回复内容不能为空");
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            //这里就是判断输入框里是否输入了字符，如果没有则改变输入框的背景颜色
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

    /**
     * by moos on 2018/04/20
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
                    detailBean.setUsername(loginBean.getData().getUsername());
                    detailBean.setImg(loginBean.getData().getImg());
                    detailBean.setContent(commentContent);
                    binding.commentView.addComment(detailBean);
                    XToastUtils.toast("评论成功");

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

    /**
     * 回复加载更多回调类
     */
    class MyOnReplyLoadMoreCallback implements OnReplyLoadMoreCallback<CommentDetailBean.CommentData.replyData> {


        @Override
        public void loading(CommentDetailBean.CommentData.replyData reply, int willLoadPage) {
            if(willLoadPage==2){
                Log.e("why", "loading: 》》》》》》》》评论过多" );
            }
        }

        @Override
        public void complete() {

        }

        @Override
        public void failure(String msg) {

        }
    }

    class MyOnItemClickCallback implements OnItemClickCallback<CommentDetailBean.CommentData, CommentDetailBean.CommentData.replyData> {

        @Override
        public void commentItemOnClick(int position, CommentDetailBean.CommentData comment, View view) {
            showReplyDialog(position,comment);
        }

        @Override
        public void replyItemOnClick(int c_position, int r_position, CommentDetailBean.CommentData.replyData reply, View view) {

        }
    }


    @Override
    public void onGetVideoDetailsSuccess(VideoDetailsBean.BeanData videoDetailsBean) {

    }

    @Override
    public void onGetVideoDetailsFail(String e) {

    }

    @Override
    public void onGetCoinOperatedSuccess(CoinBean coinBean) {

    }

    @Override
    public void onGetCoinOperatedFail(String e) {

    }

    @Override
    public void onGetThumbsUpSuccess(ThumbsUpBean thumbsUpBean) {

    }

    @Override
    public void onGetThumbsUpFail(String e) {

    }

    @Override
    public void onGetCollectionVideoSuccess(CollectionBean collectionBean) {

    }

    @Override
    public void onGetCollectionVideoFail(String e) {

    }

    @Override
    public void onGetVideoCommentSuccess(CommentDetailBean commentDetailBean) {
        if (!isLoad) {
            //binding.swipe.finishRefresh(true);
        }
        initExpandableListView(commentDetailBean);
        hideDialog();
    }

    @Override
    public void onGetVideoCommentFail(String e) {
        hideDialog();
        binding.comments.setVisibility(View.GONE);
        XToastUtils.error(R.string.errorOccurred + e);
    }

    @Override
    public void onGetDanMuSuccess(DanmuBean danmuBean) {

    }

    @Override
    public void onGetDanMuFail(String e) {

    }

    @Override
    public void onGetSeadDanMuSuccess(SeadDanmuBean seadDanmuBean) {

    }

    @Override
    public void onGetSedaDanMuFail(String e) {

    }

    @Override
    public void onGetRelatedVideosSuccess(RecommendVideoBean recommendVideoBean) {

    }

    @Override
    public void onGetRelatedVideosFail(String e) {

    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
