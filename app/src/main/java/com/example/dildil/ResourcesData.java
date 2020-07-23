package com.example.dildil;

import com.example.dildil.dynamic_page.bean.DynamicBean;
import com.example.dildil.dynamic_page.bean.TopicBean;
import com.example.dildil.home_page.bean.VideoBean;

import java.util.ArrayList;
import java.util.List;

public class ResourcesData {
    private List<VideoBean> beans = new ArrayList<>();
    private List<TopicBean> topicBeans = new ArrayList<>();
    private List<DynamicBean> dynamicBeans = new ArrayList<>();
    private VideoBean videoBean, videoBean2, videoBean3, videoBean4,videoBean6,videoBean7;

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

    public void initTopicData(){

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

    public List<TopicBean> getTopicData(){
        return topicBeans;
    }

    public void initDynamic(){
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
        dynamicBean3.setVideo_time(13.90+"");
        dynamicBean3.setVideo_Playback_volume(5320);
        dynamicBean3.setVideo_bullet_chat(777);
        dynamicBean3.setComment_num(99);
        dynamicBean3.setThumbs_num(2386);

        dynamicBeans.add(dynamicBean);
        dynamicBeans.add(dynamicBean2);
        dynamicBeans.add(dynamicBean3);
    }

    public List<DynamicBean> getDynamicBeans(){
        return dynamicBeans;
    }
}
