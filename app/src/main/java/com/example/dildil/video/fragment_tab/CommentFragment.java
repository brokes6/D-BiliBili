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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import androidx.databinding.DataBindingUtil;

import com.example.dildil.R;
import com.example.dildil.ResourcesData;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.databinding.FragmentCommentBinding;
import com.example.dildil.util.XToastUtils;
import com.example.dildil.video.adapter.CommentExpandAdapter;
import com.example.dildil.video.bean.CommentBean;
import com.example.dildil.video.bean.CommentDetailBean;
import com.example.dildil.video.bean.ReplyDetailBean;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class CommentFragment extends BaseFragment {
    private static final String TAG = "CommentFragment";
    FragmentCommentBinding binding;
    private CommentExpandAdapter adapter;
    private CommentBean commentBean;
    private List<CommentDetailBean> commentsList;
    private BottomSheetDialog dialog;
    private ResourcesData mResourcesData;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment,container,false);
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        binding.detailPageDoComment.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        showDialog();
        mResourcesData = new ResourcesData();
        commentsList = mResourcesData.generateTestData();
        initExpandableListView(commentsList);
    }

//    @Override
//    public BasePresenter onCreatePresenter() {
//        return null;
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_page_do_comment:
                showCommentDialog();
                break;
        }
    }

    /**
     * 初始化评论和回复列表
     */
    private void initExpandableListView(final List<CommentDetailBean> commentList){
        binding.FCCommentList.setGroupIndicator(null);
        //初始化适配器
        adapter = new CommentExpandAdapter(getContext(), commentList);
        binding.FCCommentList.setAdapter(adapter);
        for(int i = 0; i<commentList.size(); i++){
            //遍历所有评论，都设置为展开（默认是不展开的）
            binding.FCCommentList.expandGroup(i);
        }
        hideDialog();
        //设置分组单击监听事件(就是一个是主评论的点击事件监听，一个是二级评论的点击事件监听)
        binding.FCCommentList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
                Log.e(TAG, "onGroupClick: 当前的评论id>>>"+commentList.get(groupPosition).getId());
//                if(isExpanded){
//                    expandableListView.collapseGroup(groupPosition);
//                }else {
//                    expandableListView.expandGroup(groupPosition, true);
//                }
                showReplyDialog(groupPosition);
                return true;
            }
        });

        binding.FCCommentList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                XToastUtils.toast("点击了回复");
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
    private void showReplyDialog(final int position){
        //本质上就是弹出一个输入框（使用了系统自带的底部弹窗）
        dialog = new BottomSheetDialog(getContext());
        View commentView = LayoutInflater.from(getContext()).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText =  commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment =  commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + commentsList.get(position).getNickName() + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(replyContent)){
                    dialog.dismiss();
                    ReplyDetailBean detailBean = new ReplyDetailBean(mResourcesData.getUserData().getUsername(),replyContent);
                    adapter.addTheReplyData(detailBean, position);
                    binding.FCCommentList.expandGroup(position);
                    XToastUtils.toast("回复成功");
                }else {
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
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
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
    private void showCommentDialog(){
        dialog = new BottomSheetDialog(getContext());
        View commentView = LayoutInflater.from(getContext()).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText =  commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment =  commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        /**
         * 解决bsd显示不全的情况
         */
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0,0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(commentContent)){
                    //commentOnWork(commentContent);
                    dialog.dismiss();
                    CommentDetailBean detailBean = new CommentDetailBean(mResourcesData.getUserData().getUserImg(),mResourcesData.getUserData().getUsername(), commentContent,"刚刚");
                    adapter.addTheCommentData(detailBean);
                    XToastUtils.toast("评论成功");

                }else {
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
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }


}
