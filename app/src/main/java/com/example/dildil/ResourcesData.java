package com.example.dildil;

import com.example.dildil.dynamic_page.bean.DynamicBean;
import com.example.dildil.dynamic_page.bean.TopicBean;
import com.example.dildil.home_page.bean.HotRankingBean;
import com.example.dildil.home_page.bean.VideoBean;
import com.example.dildil.my_page.bean.MyDataBean;
import com.example.dildil.video.bean.SwitchVideoBean;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ResourcesData {
    private List<VideoBean> beans = new ArrayList<>();
    private List<TopicBean> topicBeans = new ArrayList<>();
    private List<DynamicBean> dynamicBeans = new ArrayList<>();
    private List<HotRankingBean> hotRankingBeans = new ArrayList<>();
    private List<SwitchVideoBean> urls = new ArrayList<>();
    private List<URL> bannerImageList = new ArrayList<>();
    private VideoBean videoBean, videoBean2, videoBean3, videoBean4, videoBean6, videoBean7;
    private MyDataBean mMyDataBean;

    public ResourcesData() {
        initData();
    }

    private void initData() {
        videoBean = new VideoBean();
        videoBean.setImgurl("https://i0.hdslb.com/bfs/archive/fbbfd0170433ce67fb54f7c7e76a9081705259d6.jpg@257w_145h_1c_100q.webp");
        videoBean.setBarrage_volume(24 + "");
        videoBean.setPlay_volume(2 + "");
        videoBean.setTitle("这是介绍介绍介绍");

        videoBean2 = new VideoBean();
        videoBean2.setImgurl("https://i2.hdslb.com/bfs/archive/6fa9d164c643f39a7ca8b066a7d84e2205fb568f.jpg@257w_145h_1c_100q.webp");
        videoBean2.setBarrage_volume(28 + "");
        videoBean2.setPlay_volume(200 + "");
        videoBean2.setTitle("这是介绍介绍介绍2");

        videoBean3 = new VideoBean();
        videoBean3.setImgurl("https://i2.hdslb.com/bfs/archive/43917bb952b66f4460a7c067bec55c6a6ee5f5bf.jpg@257w_145h_1c_100q.webp");
        videoBean3.setBarrage_volume(27.0 + "万");
        videoBean3.setPlay_volume(3.1 + "万");
        videoBean3.setTitle("当 今 网 抑 云 现 状");

        videoBean4 = new VideoBean();
        videoBean4.setImgurl("https://i0.hdslb.com/bfs/archive/9c7b5059d3b45e5b17f9fbc62a33a880bcb778bd.jpg@257w_145h_1c_100q.webp");
        videoBean4.setBarrage_volume(135.1 + "万");
        videoBean4.setPlay_volume(11.1 + "万");
        videoBean4.setTitle("这才是世界的巅峰战力！！！");

        videoBean6 = new VideoBean();
        videoBean6.setImgurl("https://i0.hdslb.com/bfs/archive/08c6cf95e410f0f3315eed0334914c46890aac9f.jpg@257w_145h_1c_100q.webp");
        videoBean6.setBarrage_volume(5.7 + "万");
        videoBean6.setPlay_volume(1.1 + "万");
        videoBean6.setTitle("纱雾：再说我是你奶奶！？胸都给你拍平！！！");

        videoBean7 = new VideoBean();
        videoBean7.setImgurl("https://i0.hdslb.com/bfs/archive/ffccd8d54e5c795e1c65c7919f44088f2e1e1c65.jpg@257w_145h_1c_100q.webp");
        videoBean7.setBarrage_volume(43.3 + "万");
        videoBean7.setPlay_volume(408.8 + "万");
        videoBean7.setTitle("这样骂够吗？(200w粉纪念)");

        beans.add(videoBean);
        beans.add(videoBean2);
        beans.add(videoBean3);
        beans.add(videoBean4);
        beans.add(videoBean6);
        beans.add(videoBean7);
    }

    public List<VideoBean> getData() {
        return beans;
    }

    public void initTopicData() {

        TopicBean topicBean = new TopicBean();
        topicBean.setTopicType("话题");
        topicBean.setTopicName("手游暑假作业");

        TopicBean topicBean2 = new TopicBean();
        topicBean2.setTopicType("话题");
        topicBean2.setTopicName("和小熊一起画画");

        TopicBean topicBean3 = new TopicBean();
        topicBean3.setTopicType("订阅");
        topicBean3.setTopicName("COSPLAY");

        topicBeans.add(topicBean);
        topicBeans.add(topicBean2);
        topicBeans.add(topicBean3);
    }

    public List<TopicBean> getTopicData() {
        return topicBeans;
    }

    public void initDynamic() {
        DynamicBean dynamicBean = new DynamicBean();
        dynamicBean.setUser_img("https://i0.hdslb.com/bfs/face/d0f7a7ee34a4a45c8390eb3a07e4d7f2d70bae91.jpg_64x64.jpg");
        dynamicBean.setUser_name("极客湾Geekerwan");
        dynamicBean.setRelease_date("12分钟前");
        dynamicBean.setText("哈哈，你们会错意了，不是说b站的事");
        dynamicBean.setComment_num(256);
        dynamicBean.setThumbs_num(2174);

        DynamicBean dynamicBean2 = new DynamicBean();
        dynamicBean2.setUser_img("https://i2.hdslb.com/bfs/face/acc7a0e97bf9f6c4d047777e40270a39bc7f4f7d.jpg@.webp");
        dynamicBean2.setUser_name("Fracture6");
        dynamicBean2.setRelease_date("43分钟前");
        dynamicBean2.setText("抽奖抽奖");
        dynamicBean2.setComment_num(1);
        dynamicBean2.setThumbs_num(0);

        DynamicBean dynamicBean3 = new DynamicBean();
        dynamicBean3.setUser_img("https://i0.hdslb.com/bfs/face/c43e6cab13c9a0303cf8476cfd405cff61195726.jpg_64x64.jpg");
        dynamicBean3.setUser_name("STN工作室");
        dynamicBean3.setRelease_date("5小时前");
        dynamicBean3.setVideo_cover("https://i1.hdslb.com/bfs/archive/38214b16a2b8b0e1ed38b7467b2e498ad4542c38.jpg@257w_145h_1c_100q.webp");
        dynamicBean3.setText("竟然是真的新女主持而不是女装口口，不仅拯救了酷报也拯救了观众们的眼睛");
        dynamicBean3.setVideo_time(13.90 + "");
        dynamicBean3.setVideo_Playback_volume(5320);
        dynamicBean3.setVideo_bullet_chat(777);
        dynamicBean3.setComment_num(99);
        dynamicBean3.setThumbs_num(2386);

        dynamicBeans.add(dynamicBean);
        dynamicBeans.add(dynamicBean2);
        dynamicBeans.add(dynamicBean3);
    }

    public List<DynamicBean> getDynamicBeans() {
        return dynamicBeans;
    }

    public void initHotRanking() {
        HotRankingBean hotRankingBean = new HotRankingBean();
        hotRankingBean.setVideo_cover("https://i2.hdslb.com/bfs/archive/b1a07a8baefa0694760cd6d99f212bee45d1333d.jpg@143w_88h.webp");
        hotRankingBean.setVideo_title("敢 杀 我 的 马？！");
        hotRankingBean.setVideo_up("哦呼w");
        hotRankingBean.setVideo_play_num("152.6万");
        hotRankingBean.setVideo_time("2:25");

        HotRankingBean hotRankingBean2 = new HotRankingBean();
        hotRankingBean2.setVideo_cover("https://i0.hdslb.com/bfs/archive/c1e48e6aaf5e2eb430de9e9c635cb626103c0bef.jpg@412w_232h_1c_100q.jpg");
        hotRankingBean2.setVideo_title("有⚡茅⚡台");
        hotRankingBean2.setVideo_up("晏策去月光林地了");
        hotRankingBean2.setVideo_play_num("152.6万");
        hotRankingBean2.setVideo_time("2:25");

        HotRankingBean hotRankingBean3 = new HotRankingBean();
        hotRankingBean3.setVideo_cover("https://i2.hdslb.com/bfs/archive/8a801382150edd0ecd5d74f90dcaa4c9845360af.jpg@257w_145h_1c_100q.webp");
        hotRankingBean3.setVideo_title("希 望 新 来 的 体 育 老 师 没 事......");
        hotRankingBean3.setVideo_up("全能大鹏");
        hotRankingBean3.setVideo_play_num("46.4万");
        hotRankingBean3.setVideo_time("2:25");

        hotRankingBeans.add(hotRankingBean);
        hotRankingBeans.add(hotRankingBean2);
        hotRankingBeans.add(hotRankingBean3);
    }

    public List<HotRankingBean> getHotRanking() {
        return hotRankingBeans;
    }

    public void initVideo() {
        SwitchVideoBean switchVideoBean = new SwitchVideoBean("480p", "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4");
        SwitchVideoBean switchVideoBean2 = new SwitchVideoBean("720p", "http://vjs.zencdn.net/v/oceans.mp4");
        SwitchVideoBean switchVideoBean3 = new SwitchVideoBean("1080p", "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4");
        urls.add(switchVideoBean);
        urls.add(switchVideoBean2);
        urls.add(switchVideoBean3);
    }

    public List<SwitchVideoBean> getVideoData() {
        return urls;
    }

    public void initBanner(){
        try {
            URL url = new URL("https://i0.hdslb.com/bfs/archive/20a25dc84739a3852c125d55b9223d6bd70c34bb.png@880w_388h_1c_95q");
            URL url2 = new URL("https://i0.hdslb.com/bfs/sycp/creative_img/202007/82e1e1a0fd91537c6d1c30c80fe60e6c.jpg@880w_388h_1c_95q");
            bannerImageList.add(url);
            bannerImageList.add(url2);
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public List<URL> getBeannerUrl(){
        return bannerImageList;
    }

    public void initMyData(){
        mMyDataBean = new MyDataBean();
        mMyDataBean.setUsername("Fracture6");
        mMyDataBean.setUserImg("https://i1.hdslb.com/bfs/face/acc7a0e97bf9f6c4d047777e40270a39bc7f4f7d.jpg");
        mMyDataBean.setDynamic(36);
        mMyDataBean.setFollow(33);
        mMyDataBean.setFans(0);
        mMyDataBean.setMember(true);
    }

    public MyDataBean getMyDataBeans(){
        return mMyDataBean;
    }
}
