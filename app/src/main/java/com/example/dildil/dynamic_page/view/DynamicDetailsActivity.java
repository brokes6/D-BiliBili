package com.example.dildil.dynamic_page.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.ActivityDynamicDetailsBinding;
import com.example.dildil.dynamic_page.bean.AttentionDetailsBean;
import com.example.dildil.dynamic_page.bean.DynamicBean;
import com.example.dildil.dynamic_page.bean.DynamicDetailsBean;
import com.example.dildil.dynamic_page.contract.DynamicContract;
import com.example.dildil.dynamic_page.fragment_tab.DetailsCommentFragment;
import com.example.dildil.dynamic_page.fragment_tab.DetailsForwardFragment;
import com.example.dildil.dynamic_page.presenter.DynamicPresenter;
import com.example.dildil.login_page.bean.UserBean;
import com.example.dildil.my_page.view.PersonalActivity;
import com.example.dildil.util.XToastUtils;
import com.example.dildil.video.bean.CommentBean;
import com.example.dildil.video.bean.CommentDetailBean;
import com.example.dildil.video.bean.dto;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class DynamicDetailsActivity extends BaseActivity implements DynamicContract.View, BGANinePhotoLayout.Delegate {
    private ActivityDynamicDetailsBinding binding;
    private UserBean userBean;
    private DetailsCommentFragment detailsCommentFragment;
    private BGANinePhotoLayout mCurrentClickNpl;
    private int uid, id;
    private ArrayList<Fragment> mFragments;

    @Inject
    DynamicPresenter mPresenter;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dynamic_details);
        ImmersionBar.with(this)
                .statusBarColor(R.color.Pink)
                .init();

        DaggerActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build().inject(this);
        mPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        setLeftTitleText("详情");
        setBackBtn(getResources().getColor(R.color.While, null));
        setTitleBG(getResources().getColor(R.color.Pink, null));
        setLeftTitleTextColorWhite();

        mFragments = new ArrayList<>();
        mFragments.add(new DetailsForwardFragment());
        mFragments.add(detailsCommentFragment = new DetailsCommentFragment(id));
        binding.DDUserImg.setOnClickListener(this);
        binding.bottomFunction.findViewById(R.id.main_comment).setOnClickListener(this);
        binding.swipe.setRefreshFooter(new BallPulseFooter(this));
        binding.swipe.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getDynamicDetails(id, userBean.getData().getId());
            }
        });

        setMargins(binding.swipe, 0, getStatusBarHeight(this), 0, 0);
    }

    @Override
    protected void initData() {
        MyApplication.getDatabase(this).userDao().getAll()
                .observe(DynamicDetailsActivity.this, new Observer<UserBean>() {

                    @Override
                    public void onChanged(UserBean userBeans) {
                        userBean = userBeans;
                        binding.swipe.autoRefresh();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.main_comment) {
            showCommentDialog();
        } else if (v.getId() == R.id.DD_user_img) {
            Intent intent = new Intent(this, PersonalActivity.class);
            intent.putExtra("uid", uid);
            startActivity(intent);
        }
    }

    private void showCommentDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.BottomSheetEdit);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout, null);
        final EmojiconEditText commentText = commentView.findViewById(R.id.dialog_comment_et);
        final TextView bt_comment = commentView.findViewById(R.id.dialog_comment_bt);
        final ImageView emoji = commentView.findViewById(R.id.CB_emoji);
        commentText.setFocusable(true);
        commentText.setFocusableInTouchMode(true);
        commentText.requestFocus();
        dialog.setContentView(commentView);
        EmojIconActions emojIcon = new EmojIconActions(this, commentView, commentText, emoji);
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
//                    binding.commentView.addComment(detailBean);
                    mPresenter.AddDynamicComment(new dto(commentContent, id, userBean.getData().getId(), "DYNAMIC"), userBean.getData().getId());
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

    @AfterPermissionGranted(1)
    private void photoPreviewWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(mContext, perms)) {
            File downloadDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerDownload");
            BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(mContext)
                    .saveImgDir(downloadDir); // 保存图片的目录，如果传 null，则没有保存图片功能

            if (mCurrentClickNpl.getItemCount() == 1) {
                // 预览单张图片
                photoPreviewIntentBuilder.previewPhoto(mCurrentClickNpl.getCurrentClickItem());
            } else if (mCurrentClickNpl.getItemCount() > 1) {
                // 预览多张图片
                photoPreviewIntentBuilder.previewPhotos(mCurrentClickNpl.getData())
                        .currentPosition(mCurrentClickNpl.getCurrentClickItemPosition()); // 当前预览图片的索引
            }
            mContext.startActivity(photoPreviewIntentBuilder.build());
        } else {
            EasyPermissions.requestPermissions((Activity) mContext, "图片预览需要以下权限:\n\n1.访问设备上的照片", 1, perms);
        }
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
        XToastUtils.success("评论成功~");
        detailsCommentFragment.getComment();
    }

    @Override
    public void onGetAddDynamicCommentFail(String e) {
        Log.e("why", "评论出现错误" + e);
    }

    @Override
    public void onGetDynamicCommentSuccess(CommentDetailBean commentDetailBean) {

    }

    @Override
    public void onGetDynamicCommentFail(String e) {

    }

    @Override
    public void onGetDynamicDetailsSuccess(DynamicDetailsBean dynamicDetailsBean) {
        binding.main.setVisibility(View.VISIBLE);
        binding.swipe.finishRefresh(true);
        uid = dynamicDetailsBean.getData().getUid();
        binding.DDTitle.setText(dynamicDetailsBean.getData().getContent());
        binding.DDUserName.setText(dynamicDetailsBean.getData().getUpName());
        binding.DDDate.setText(dynamicDetailsBean.getData().getCreateTime().substring(5, 10));
        Glide.with(this).load(dynamicDetailsBean.getData().getUpImg()).into(binding.DDUserImg);
        binding.DDMulti.setData(new ArrayList<>(Arrays.asList(dynamicDetailsBean.getData().getImgs().split(","))));
        binding.DDMulti.setDelegate(this);

        if (!binding.DYViewPager.isInflated()) {
            View ry = binding.DYViewPager.getViewStub().inflate();
            ViewPager viewPager = ry.findViewById(R.id.viewPager);
            binding.tab.setViewPager(viewPager, new String[]{"转发", "评论" + dynamicDetailsBean.getData().getCommentNum()}, this, mFragments);
            binding.tab.setCurrentTab(1);
        }
    }

    @Override
    public void onGetDynamicDetailsFail(String e) {
        binding.main.setVisibility(View.GONE);
        binding.swipe.finishRefresh(true);
        XToastUtils.error(R.string.networkError);
    }

    @Override
    public void onGetAttentionDetailsSuccess(AttentionDetailsBean attentionDetailsBean) {

    }

    @Override
    public void onGetAttentionDetailsFail(String e) {

    }

    @Override
    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
        mCurrentClickNpl = ninePhotoLayout;
        photoPreviewWrapper();
    }

    @Override
    public void onClickExpand(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {

    }
}