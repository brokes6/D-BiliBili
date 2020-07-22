package com.example.dildil.home_page.fragment.fragment_tab;

import android.graphics.Outline;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.dildil.R;
import com.example.dildil.base.BaseFragment;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.databinding.FragmentRecommendedBinding;
import com.example.dildil.home_page.adapter.RecommendedVideoAdapter;
import com.example.dildil.home_page.bean.VideoBean;
import com.example.dildil.home_page.other.GlideImageLoader;
import com.youth.banner.BannerConfig;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecommendedFragment extends BaseFragment {
    FragmentRecommendedBinding binding;
    List<URL> bannerImageList = new ArrayList<>();
    RecommendedVideoAdapter adapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recommended, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        try {
            URL url = new URL("https://i0.hdslb.com/bfs/archive/20a25dc84739a3852c125d55b9223d6bd70c34bb.png@880w_388h_1c_95q");
            URL url2 = new URL("https://i0.hdslb.com/bfs/sycp/creative_img/202007/82e1e1a0fd91537c6d1c30c80fe60e6c.jpg@880w_388h_1c_95q");
            bannerImageList.add(url);
            bannerImageList.add(url2);
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
        initBanner(bannerImageList);

        //垂直方向的2列
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        final int spanCount = 2;
        binding.ReRecy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int[] first = new int[spanCount];
                layoutManager.findFirstCompletelyVisibleItemPositions(first);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && (first[0] == 1 || first[1] == 1)) {
                    layoutManager.invalidateSpanAssignments();
                }
            }
        });
        adapter = new RecommendedVideoAdapter(getContext());
        binding.ReRecy.setLayoutManager(layoutManager);
        binding.ReRecy.setAdapter(adapter);
        initDates();
    }

    private void initBanner(List<?> imageUrls){
        binding.ReBanner.setImageLoader(new GlideImageLoader());
        binding.ReBanner.setImages(imageUrls);
        binding.ReBanner.setDelayTime(4000);
        binding.ReBanner.setIndicatorGravity(BannerConfig.CENTER);
        binding.ReBanner.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 15);
            }
        });
        binding.ReBanner.setClipToOutline(true);
        binding.ReBanner.start();
    }

    private void initDates(){
        VideoBean videoBean = new VideoBean();
        videoBean.setImgurl("https://i0.hdslb.com/bfs/archive/fbbfd0170433ce67fb54f7c7e76a9081705259d6.jpg@257w_145h_1c_100q.webp");
        videoBean.setBarrage_volume(24+"");
        videoBean.setPlay_volume(2+"");
        videoBean.setTitle("这是介绍介绍介绍");

        VideoBean videoBean2 = new VideoBean();
        videoBean2.setImgurl("https://i2.hdslb.com/bfs/archive/6fa9d164c643f39a7ca8b066a7d84e2205fb568f.jpg@257w_145h_1c_100q.webp");
        videoBean2.setBarrage_volume(28+"");
        videoBean2.setPlay_volume(200+"");
        videoBean2.setTitle("这是介绍介绍介绍2");

        List<VideoBean> beans = new ArrayList<>();
        beans.add(videoBean);
        beans.add(videoBean2);
        adapter.loadMore(beans);
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }
}
