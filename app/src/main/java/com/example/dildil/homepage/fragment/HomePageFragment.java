package com.example.dildil.homepage.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.dildil.R;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.databinding.FragmentHomepageBinding;
import com.example.dildil.homepage.fragment.fragment_tab.EpidemicSituationFragment;
import com.example.dildil.homepage.fragment.fragment_tab.HotFragment;
import com.example.dildil.homepage.fragment.fragment_tab.MoviesFragment;
import com.example.dildil.homepage.fragment.fragment_tab.PursueFramgment;
import com.example.dildil.homepage.fragment.fragment_tab.RapFragment;
import com.example.dildil.homepage.fragment.fragment_tab.RecommendedFragment;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends BaseFragment {
    FragmentHomepageBinding binding;
    private String[] TabTitle = {"推荐", "热门", "追番", "影视", "说唱区", "抗灾区"};
    private List<Fragment> fragmentList = new ArrayList<>();
    private ArrayList<Fragment> mFragments;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_homepage, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        mFragments = new ArrayList<>();
        mFragments.add(new RecommendedFragment());
        mFragments.add(new HotFragment());
        mFragments.add(new PursueFramgment());
        mFragments.add(new MoviesFragment());
        mFragments.add(new RapFragment());
        mFragments.add(new EpidemicSituationFragment());
    }

    @Override
    protected void initData() {
        binding.tab.setViewPager(binding.viewPager,TabTitle,getActivity(),mFragments);
    }


    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {

    }
}
