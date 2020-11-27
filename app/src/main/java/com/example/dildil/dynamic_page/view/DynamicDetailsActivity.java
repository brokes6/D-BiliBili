package com.example.dildil.dynamic_page.view;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.dildil.R;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.databinding.ActivityDynamicDetailsBinding;
import com.example.dildil.dynamic_page.fragment_tab.DetailsCommentFragment;
import com.example.dildil.dynamic_page.fragment_tab.DetailsForwardFragment;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

public class DynamicDetailsActivity extends BaseActivity {
    private ActivityDynamicDetailsBinding binding;
    private final String[] TabTitle = {"转发", "评论"};
    private ArrayList<Fragment> mFragments;
    private List<String> imageList = new ArrayList<>();

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dynamic_details);
        ImmersionBar.with(this)
                .statusBarColor(R.color.Pink)
                .init();
    }

    @Override
    protected void initView() {
        setLeftTitleText("详情");
        setBackBtn(getResources().getColor(R.color.While,null));
        setTitleBG(getResources().getColor(R.color.Pink,null));
        setLeftTitleTextColorWhite();

        mFragments = new ArrayList<>();
        mFragments.add(new DetailsForwardFragment());
        mFragments.add(new DetailsCommentFragment());

        imageList.add("https://i0.hdslb.com/bfs/album/69924f3977b9add651b334fc72b507c1c572b41b.png@518w_1e_1c.png");
        imageList.add("https://i0.hdslb.com/bfs/album/064d878a6070c948fec192ff7ff12215ccc0d278.png@518w_1e_1c.png");

        binding.tab.setViewPager(binding.viewPager, TabTitle, this, mFragments);
        binding.tab.setCurrentTab(1);

        setMargins(binding.main, 0, getStatusBarHeight(this), 0, 0);
    }

    @Override
    protected void initData() {
        Glide.with(this).load("https://i2.hdslb.com/bfs/face/cb620bbb9071974f37843134875d472b47532a97.jpg@50w_50h_1c.webp").into(binding.DDUserImg);
        binding.DDUserName.setText("哔哩哔哩英雄联盟赛事");
        binding.DDDate.setText("2小时前");
        binding.DDTitle.setText("【SN 1-3 DWG】\n" +
                "前期DWG补刀压制，率先控到第一条小龙和先锋！15分钟破掉上路一血塔，经济领先2000块！\n" +
                "17分钟DWG仅仅以一个辅助人头的代价，拿下火龙完成听牌！2分钟后下路团战，DWG完成对SN的伪团灭，Canyon千珏拿下7个人头！\n" +
                "22分钟DWG轻松收下火龙魂，经济领先5000块！24分钟阿Bin船长抓到机会击杀辛德拉，但无奈对手DWG经济优势过大，反手团灭SN，拿下大龙！\n" +
                "25分半DWG轻松破掉SN上路高地，1分钟后中路推进，击杀对手三人后团灭SN，DWG顺势一波拿下比赛胜利！\n" +
                "恭喜DWG夺得2020全球总决赛冠军！");
        binding.DDMulti.setLayoutParams(new LinearLayout.LayoutParams(900, ViewGroup.LayoutParams.WRAP_CONTENT));
        binding.DDMulti.setImages(imageList);

    }

    @Override
    public void onClick(View v) {

    }
}