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

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.example.dildil.MyApplication;
import com.example.dildil.R;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.component.activity.ActivityModule;
import com.example.dildil.component.activity.DaggerActivityComponent;
import com.example.dildil.databinding.ActivityDynamicDetailsBinding;
import com.example.dildil.dynamic_page.bean.DynamicBean;
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

import java.io.File;
import java.util.ArrayList;
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
    private final String[] TabTitle = {"转发", "评论"};
    private ArrayList<Fragment> mFragments;
    private ArrayList<String> imageList = new ArrayList<>();
    private UserBean userBean;
    private DetailsCommentFragment detailsCommentFragment;
    private BGANinePhotoLayout mCurrentClickNpl;

    @Inject
    DynamicPresenter mPresenter;
    private int id;

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

        imageList.add("https://i0.hdslb.com/bfs/album/69924f3977b9add651b334fc72b507c1c572b41b.png@518w_1e_1c.png");
        imageList.add("https://i0.hdslb.com/bfs/album/064d878a6070c948fec192ff7ff12215ccc0d278.png@518w_1e_1c.png");

        binding.tab.setViewPager(binding.viewPager, TabTitle, this, mFragments);
        binding.tab.setCurrentTab(1);

        setMargins(binding.main, 0, getStatusBarHeight(this), 0, 0);
    }

    @Override
    protected void initData() {
        MyApplication.getDatabase(this).userDao().getAll()
                .observe(DynamicDetailsActivity.this, new Observer<UserBean>() {

                    @Override
                    public void onChanged(UserBean userBeans) {
                        userBean = userBeans;
                    }
                });

        Glide.with(this).load("https://i2.hdslb.com/bfs/face/cb620bbb9071974f37843134875d472b47532a97.jpg@50w_50h_1c.webp").into(binding.DDUserImg);
        binding.DDUserName.setText("哔哩哔哩英雄联盟赛事");
        binding.DDDate.setText("2小时前");
        binding.DDTitle.setText("【SN 1-3 DWG】\n" +
                "前期DWG补刀压制，率先控到第一条小龙和先锋！15分钟破掉上路一血塔，经济领先2000块！\n" +
                "17分钟DWG仅仅以一个辅助人头的代价，拿下火龙完成听牌！2分钟后下路团战，DWG完成对SN的伪团灭，Canyon千珏拿下7个人头！\n" +
                "22分钟DWG轻松收下火龙魂，经济领先5000块！24分钟阿Bin船长抓到机会击杀辛德拉，但无奈对手DWG经济优势过大，反手团灭SN，拿下大龙！\n" +
                "25分半DWG轻松破掉SN上路高地，1分钟后中路推进，击杀对手三人后团灭SN，DWG顺势一波拿下比赛胜利！\n" +
                "恭喜DWG夺得2020全球总决赛冠军！");
        binding.DDMulti.setData(imageList);
        binding.DDMulti.setDelegate(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.main_comment) {
            showCommentDialog();
        } else if (v.getId() == R.id.DD_user_img) {
            Intent intent = new Intent(this, PersonalActivity.class);
            intent.putExtra("uid", userBean.getData().getId());
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
    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
        mCurrentClickNpl = ninePhotoLayout;
        photoPreviewWrapper();
    }

    @Override
    public void onClickExpand(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {

    }
}