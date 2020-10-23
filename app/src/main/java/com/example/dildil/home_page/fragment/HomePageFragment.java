package com.example.dildil.home_page.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.example.dildil.R;
import com.example.dildil.base.BaseActivity;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.databinding.FragmentHomepageBinding;
import com.example.dildil.home_page.fragment.fragment_tab.EpidemicSituationFragment;
import com.example.dildil.home_page.fragment.fragment_tab.HotFragment;
import com.example.dildil.home_page.fragment.fragment_tab.LiveBroadcastFragment;
import com.example.dildil.home_page.fragment.fragment_tab.MoviesFragment;
import com.example.dildil.home_page.fragment.fragment_tab.PursueFramgment;
import com.example.dildil.home_page.fragment.fragment_tab.RapFragment;
import com.example.dildil.home_page.fragment.fragment_tab.RecommendedFragment;
import com.example.dildil.home_page.view.HomeActivity;
import com.example.dildil.login_page.bean.LoginBean;
import com.example.dildil.search.view.SearchActivity;

import java.util.ArrayList;

public class HomePageFragment extends BaseFragment {
    FragmentHomepageBinding binding;
    private final String[] TabTitle = {"直播", "推荐", "热门", "追番", "影视", "说唱区", "抗灾区"};
    private ArrayList<Fragment> mFragments;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_homepage, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        mFragments = new ArrayList<>();
        mFragments.add(new LiveBroadcastFragment());
        mFragments.add(new RecommendedFragment());
        mFragments.add(new HotFragment());
        mFragments.add(new PursueFramgment());
        mFragments.add(new MoviesFragment());
        mFragments.add(new RapFragment());
        mFragments.add(new EpidemicSituationFragment());

        binding.userImg.setOnClickListener(this);
        binding.edText.setOnClickListener(this);
        binding.information.setOnClickListener(this);
        binding.tab.setViewPager(binding.viewPager, TabTitle, getActivity(), mFragments);
        binding.tab.setCurrentTab(1);
    }

    @Override
    protected void initData() {
        LoginBean loginBean = getUserData();
        Glide.with(getActivity()).load(loginBean.getData().getImg()).into(binding.userImg);
    }

    @Override
    protected void initLocalData() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.user_img) {
            ((HomeActivity) getActivity()).SwitchPages(4);
        } else if (id == R.id.ed_text) {
            ActivityUtils.startActivity(SearchActivity.class);
        } else if (id == R.id.information) {
            Intent intent = new Intent();
            intent.setAction(BaseActivity.signInAction);
            getContext().sendBroadcast(intent);
        }
    }

    @Override
    public void onDestroy() {
        mFragments.clear();
        mFragments = null;
        super.onDestroy();
    }
}
