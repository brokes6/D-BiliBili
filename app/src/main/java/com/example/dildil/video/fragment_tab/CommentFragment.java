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
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.FragmentCommentBinding;
import com.example.dildil.login_page.bean.LoginBean;
import com.example.dildil.util.GsonUtil;
import com.example.dildil.util.SharePreferenceUtil;
import com.example.dildil.util.SharedPreferencesUtil;
import com.example.dildil.util.XToastUtils;
import com.example.dildil.video.adapter.CommentExpandAdapter;
import com.example.dildil.video.bean.CoinBean;
import com.example.dildil.video.bean.CollectionBean;
import com.example.dildil.video.bean.CommentBean;
import com.example.dildil.video.bean.CommentDetailBean;
import com.example.dildil.video.bean.DanmuBean;
import com.example.dildil.video.bean.SeadDanmuBean;
import com.example.dildil.video.bean.ThumbsUpBean;
import com.example.dildil.video.bean.VideoDetailsBean;
import com.example.dildil.video.contract.VideoDetailsContract;
import com.example.dildil.video.presenter.VideoDetailsPresenter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import javax.inject.Inject;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class CommentFragment extends BaseFragment implements VideoDetailsContract.View {
    private static final String TAG = "CommentFragment";
    FragmentCommentBinding binding;
    private CommentExpandAdapter adapter;
    private CommentBean commentBean;
    private List<CommentDetailBean.CommentData> commentsList;
    private BottomSheetDialog dialog;
    private EmojIconActions emojIcon;
    private boolean isLoad = true;
    private int id,uid;
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

        //设置 Header式
        binding.swipe.setRefreshHeader(new MaterialHeader(getContext()));
        //取消Footer
        binding.swipe.setEnableLoadMore(false);
        binding.swipe.setDisableContentWhenRefresh(true);

        binding.swipe.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Log.e(TAG, "onRefresh: 开始刷新");
                mPresenter.getVideoComment(id,1,10,uid);
            }
        });
    }

    @Override
    protected void initData() {
        showDialog();
        id = (int) SharedPreferencesUtil.getData("id", 0);
        uid = (int) SharedPreferencesUtil.getData("uid", 0);
        loginBean = GsonUtil.fromJSON(SharePreferenceUtil.getInstance(getContext()).getUserInfo(""), LoginBean.class);
        mPresenter.getVideoComment(id,1,10,uid);
        isLoad = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_page_do_comment:
                showCommentDialog();
                break;
        }
    }

    /**
     * 初始化评论和回复列表
     */
    private void initExpandableListView(final CommentDetailBean commentList) {
        binding.FCCommentList.setGroupIndicator(null);
        //初始化适配器
        adapter = new CommentExpandAdapter(getContext(), commentList);
        binding.FCCommentList.setAdapter(adapter);
        for (int i = 0; i < commentList.getData().size(); i++) {
            //遍历所有评论，都设置为展开（默认是不展开的）
            binding.FCCommentList.expandGroup(i);
        }
        hideDialog();
        //设置分组单击监听事件(就是一个是主评论的点击事件监听，一个是二级评论的点击事件监听)
        binding.FCCommentList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                Log.e(TAG, "onGroupClick: 当前的评论id>>>" + commentList.getData().get(groupPosition).getId());
                showReplyDialog(groupPosition);
                return true;
            }
        });

        binding.FCCommentList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                if (childPosition == 4){
                    XToastUtils.toast("点击了查看更多");
                }
//                XToastUtils.toast("点击了回复");
                return false;
            }
        });
        //监听展开情况
        binding.FCCommentList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //toast("展开第"+groupPosition+"个分组");

            }
        });

    }

    /**
     * by moos on 2018/04/20
     * func:弹出回复框
     */
    private void showReplyDialog(final int position) {
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
        commentText.setHint("回复 @" + commentsList.get(position).getUsername() + " 的评论:");
        commentView.measure(0, 0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(replyContent)) {
                    dialog.dismiss();
                    CommentDetailBean.CommentData.replyData replyData = new  CommentDetailBean.CommentData.replyData();
                    replyData.setContent(replyContent);
                    replyData.setImg(loginBean.getData().getImg());
                    replyData.setUsername(loginBean.getData().getUsername());
                    adapter.addTheReplyData(replyData, position);
                    binding.FCCommentList.expandGroup(position);
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

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(commentContent)) {
                    dialog.dismiss();
                    CommentDetailBean.CommentData detailBean = new CommentDetailBean.CommentData();
                    detailBean.setUsername(loginBean.getData().getUsername());
                    detailBean.setImg(loginBean.getData().getImg());
                    detailBean.setContent(commentContent);
                    adapter.addTheCommentData(detailBean);
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
        if (!isLoad){
            binding.swipe.finishRefresh(true);
        }
        initExpandableListView(commentDetailBean);
        commentsList = commentDetailBean.getData();
        hideDialog();
    }

    @Override
    public void onGetVideoCommentFail(String e) {
        hideDialog();
        Log.e(TAG, "onGetVideoCommentFail: ???????????"+e );
        binding.comments.setVisibility(View.GONE);
        XToastUtils.error("出现错误:"+e);
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
}
