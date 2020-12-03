package com.example.dildil;

import com.example.dildil.channel_page.bean.BeInterestedBean;
import com.example.dildil.channel_page.bean.HaveViewedBean;
import com.example.dildil.dynamic_page.bean.DynamicBean;
import com.example.dildil.dynamic_page.bean.PursueBean;
import com.example.dildil.dynamic_page.bean.TopicBean;
import com.example.dildil.home_page.bean.BannerBean;
import com.example.dildil.home_page.bean.FanRecommendationBean;
import com.example.dildil.home_page.bean.MyPursuitBean;
import com.example.dildil.home_page.bean.VideoBean;
import com.example.dildil.my_page.bean.MyDataBean;
import com.example.dildil.search.bean.HotSearchBean;
import com.example.dildil.video.bean.CommentBean;
import com.example.dildil.video.bean.CommentDetailBean;
import com.example.dildil.video.bean.SwitchVideoBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Author:fuxinbo
 * 本地资源初始化+使用
 */

public class ResourcesData {
    private static List<VideoBean> beans = new ArrayList<>();
    private static List<TopicBean> topicBeans = new ArrayList<>();
    private static List<DynamicBean> dynamicBeans = new ArrayList<>();
    private static List<SwitchVideoBean> urls = new ArrayList<>();
    private static List<HotSearchBean> hotSearchBeans = new ArrayList<>();
    private static List<BannerBean> bannerImageList = new ArrayList<>();
    private static List<BeInterestedBean> beInterestedBeans = new ArrayList<>();
    private static List<HaveViewedBean> haveViewedBeans = new ArrayList<>();
    private static List<PursueBean> pursueBeans = new ArrayList<>();
    private static List<MyPursuitBean> myPursuitBeans = new ArrayList<>();
    private static List<FanRecommendationBean> fanRecommendationBeans = new ArrayList<>();
    private static List<CommentDetailBean> commentsList = new ArrayList<>();
    private CommentBean commentBean;
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

    public void initVideo() {
        SwitchVideoBean switchVideoBean = new SwitchVideoBean("480p", "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4");
        switchVideoBean.setImg("https://i0.hdslb.com/bfs/archive/c1e48e6aaf5e2eb430de9e9c635cb626103c0bef.jpg@412w_232h_1c_100q.jpg");
        SwitchVideoBean switchVideoBean2 = new SwitchVideoBean("720p", "http://vjs.zencdn.net/v/oceans.mp4");
        SwitchVideoBean switchVideoBean3 = new SwitchVideoBean("1080p", "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4");
        urls.add(switchVideoBean);
        urls.add(switchVideoBean2);
        urls.add(switchVideoBean3);
    }

    public List<SwitchVideoBean> getVideoData() {
        return urls;
    }

    public void initBanner() {
        BannerBean bannerBean = new BannerBean();
        bannerBean.setImageUrl("https://i0.hdslb.com/bfs/archive/a256e3d2614cf8be7476a376bc0dec828cd38e14.png@880w_388h_1c_95q");
        BannerBean bannerBean1 = new BannerBean();
        bannerBean1.setImageUrl("https://i0.hdslb.com/bfs/archive/02ebd0ec305f95f3f6e557fe5660fe2309d3a981.png@880w_388h_1c_95q");
        BannerBean bannerBean2 = new BannerBean();
        bannerBean2.setImageUrl("https://i0.hdslb.com/bfs/archive/545a20d626e5e57ca0745a2458b798821fc1a0df.jpg@880w_388h_1c_95q");
        bannerImageList.add(bannerBean);
        bannerImageList.add(bannerBean1);
        bannerImageList.add(bannerBean2);
    }

    public List<BannerBean> getBeannerUrl() {
        return bannerImageList;
    }

    public void initMyData() {
        mMyDataBean = new MyDataBean();
        mMyDataBean.setUsername("Fracture6");
        mMyDataBean.setUserImg("https://i1.hdslb.com/bfs/face/acc7a0e97bf9f6c4d047777e40270a39bc7f4f7d.jpg");
        mMyDataBean.setDynamic(36);
        mMyDataBean.setFollow(33);
        mMyDataBean.setFans(0);
        mMyDataBean.setMember(true);
    }

    public MyDataBean getUserData() {
        MyDataBean mMyDataBean = new MyDataBean();
        mMyDataBean.setUsername("Fracture6");
        mMyDataBean.setUserImg("https://i1.hdslb.com/bfs/face/acc7a0e97bf9f6c4d047777e40270a39bc7f4f7d.jpg");
        mMyDataBean.setDynamic(36);
        mMyDataBean.setFollow(33);
        mMyDataBean.setFans(0);
        mMyDataBean.setMember(true);
        return mMyDataBean;
    }

    public MyDataBean getMyDataBeans() {
        return mMyDataBean;
    }

    public String[] getSearchTag() {
        String[] text = {"兰博基尼自私", "柯南", "空军一号", "华为GT2", "iphoneXr", "iphone11"};
        return text;
    }

    public void initHotSearch() {
        hotSearchBeans.clear();
        HotSearchBean bean = new HotSearchBean();
        bean.setHotSearchTitle("雾山五行");
        bean.setDegree(1);

        HotSearchBean bean1 = new HotSearchBean();
        bean1.setHotSearchTitle("小米10至尊版评测");
        bean1.setDegree(2);

        HotSearchBean bean2 = new HotSearchBean();
        bean2.setHotSearchTitle("小米透明电视");
        bean2.setDegree(2);

        HotSearchBean bean3 = new HotSearchBean();
        bean3.setHotSearchTitle("雷军直播回应赌约");
        bean3.setDegree(0);

        HotSearchBean bean4 = new HotSearchBean();
        bean4.setHotSearchTitle("芝加哥大规模抢劫");
        bean4.setDegree(0);

        HotSearchBean bean5 = new HotSearchBean();
        bean5.setHotSearchTitle("糖豆人");
        bean5.setDegree(2);

        HotSearchBean bean6 = new HotSearchBean();
        bean6.setHotSearchTitle("棘刺");
        bean6.setDegree(0);

        HotSearchBean bean7 = new HotSearchBean();
        bean7.setHotSearchTitle("元龙");
        bean7.setDegree(0);

        HotSearchBean bean8 = new HotSearchBean();
        bean8.setHotSearchTitle("以家人之名");
        bean8.setDegree(0);

        HotSearchBean bean9 = new HotSearchBean();
        bean9.setHotSearchTitle("光遇红狐狸");
        bean9.setDegree(0);

        hotSearchBeans.add(bean);
        hotSearchBeans.add(bean1);
        hotSearchBeans.add(bean2);
        hotSearchBeans.add(bean3);
        hotSearchBeans.add(bean4);
        hotSearchBeans.add(bean5);
        hotSearchBeans.add(bean6);
        hotSearchBeans.add(bean7);
        hotSearchBeans.add(bean8);
        hotSearchBeans.add(bean9);
    }

    public List<HotSearchBean> getHotSearchBeans() {
        return hotSearchBeans;
    }


    public void initBeInterestedData() {
        beInterestedBeans.clear();
        BeInterestedBean beInterestedBean = new BeInterestedBean();
        beInterestedBean.setBeInterestedImage("https://i0.hdslb.com/bfs/tag/a92c19dbc4335fd553e50e187d759b2fafee9a64.jpg@115w_115h_1c_100q.webp");
        beInterestedBean.setBeInterestedTitle("COSPLAY");

        BeInterestedBean beInterestedBean1 = new BeInterestedBean();
        beInterestedBean1.setBeInterestedImage("https://i0.hdslb.com/bfs/tag/c47710d730162f1e1c49d23e68aa0bf42b83b0e9.jpg@115w_115h_1c_100q.webp");
        beInterestedBean1.setBeInterestedTitle("游戏集锦");

        BeInterestedBean beInterestedBean2 = new BeInterestedBean();
        beInterestedBean2.setBeInterestedImage("https://i0.hdslb.com/bfs/archive/4afb90b88597f226d22fdaed28a5c4769b372fdc.png@115w_115h_1c_100q.webp");
        beInterestedBean2.setBeInterestedTitle("搞笑");

        BeInterestedBean beInterestedBean3 = new BeInterestedBean();
        beInterestedBean3.setBeInterestedImage("https://i0.hdslb.com/bfs/tag/db30d74add4aafeaf4faa1d2ddca120d1d89c52a.jpg@115w_115h_1c_100q.webp");
        beInterestedBean3.setBeInterestedTitle("风暴英雄");

        BeInterestedBean beInterestedBean4 = new BeInterestedBean();
        beInterestedBean4.setBeInterestedImage("https://i0.hdslb.com/bfs/tag/0f7b6c8c3d38382e677c1d137986a11fed8075ac.jpg@115w_115h_1c_100q.webp");
        beInterestedBean4.setBeInterestedTitle("星际争霸2");

        beInterestedBeans.add(beInterestedBean);
        beInterestedBeans.add(beInterestedBean1);
        beInterestedBeans.add(beInterestedBean2);
        beInterestedBeans.add(beInterestedBean3);
        beInterestedBeans.add(beInterestedBean4);
    }

    public List<BeInterestedBean> getBeInterestedBeans() {
        return beInterestedBeans;
    }

    public void initHaveViewedData() {
        haveViewedBeans.clear();
        HaveViewedBean haveViewedBean = new HaveViewedBean();
        haveViewedBean.setMiddle_Image("https://i0.hdslb.com/bfs/tag/c044d70d3c5f8e5920e19b8f5ef64d1bbcee625d.jpg@60w_60h_1c_100q.webp");
        haveViewedBean.setTop_Image("http://i0.hdslb.com/bfs/tag/186e0cef45b86ea1f5c8062c7f93bb7d0ecc2b71.jpg@.webp");
        haveViewedBean.setTitle("是在下输了");
        haveViewedBean.setTitle_time("07-31浏览");

        HaveViewedBean haveViewedBean1 = new HaveViewedBean();
        haveViewedBean1.setMiddle_Image("https://i0.hdslb.com/bfs/tag/a938e55152c022f381223490ca3547c1b29de438.jpg@60w_60h_1c_100q.webp");
        haveViewedBean1.setTop_Image("http://i0.hdslb.com/bfs/tag/9c3541ab7e955c3ab6fa44387be10f85e922ad1a.png@.webp");
        haveViewedBean1.setTitle("雷军");
        haveViewedBean1.setTitle_time("07-31浏览");

        HaveViewedBean haveViewedBean2 = new HaveViewedBean();
        haveViewedBean2.setMiddle_Image("https://i0.hdslb.com/bfs/tag/ea51d78fa24f69bfff2d21a2e99f9fc4680a3871.png@60w_60h_1c_100q.webp");
        haveViewedBean2.setTop_Image("http://i0.hdslb.com/bfs/tag/0189156a9a7c1ad39d840a32e69a11a881d6ba7b.png@.webp");
        haveViewedBean2.setTitle("vlog");
        haveViewedBean2.setTitle_time("07-08浏览");

        HaveViewedBean haveViewedBean3 = new HaveViewedBean();
        haveViewedBean3.setMiddle_Image("https://i0.hdslb.com/bfs/tag/caee81ee21fa1d830398442166be53d388ecb2ea.jpg@60w_60h_1c_100q.webp");
        haveViewedBean3.setTitle("乐高");
        haveViewedBean3.setTitle_time("07-07浏览");

        HaveViewedBean haveViewedBean4 = new HaveViewedBean();
        haveViewedBean4.setMiddle_Image("https://i0.hdslb.com/bfs/archive/f29372839c6265f38617c46c21da44feb36255b3.png@60w_60h_1c_100q.webp");
        haveViewedBean4.setTitle("明日方舟");
        haveViewedBean4.setTitle_time("06-30浏览");

        haveViewedBeans.add(haveViewedBean);
        haveViewedBeans.add(haveViewedBean1);
        haveViewedBeans.add(haveViewedBean2);
        haveViewedBeans.add(haveViewedBean3);
        haveViewedBeans.add(haveViewedBean4);
    }

    public List<HaveViewedBean> getHaveViewedBeans() {
        return haveViewedBeans;
    }

    public void initRecentVisit() {

    }

    public void initPursue() {
        pursueBeans.clear();
        PursueBean pursueBean = new PursueBean();
        pursueBean.setPursueImage("https://i0.hdslb.com/bfs/archive/0fe50fefc0def68b88a32b2be67f3b63791419a3.jpg@120w_75h.webp");
        pursueBean.setPursueName("刀剑神域 爱丽丝篇 异界战争 -终章-");
        pursueBean.setToUpdate_word(18);

        PursueBean pursueBean1 = new PursueBean();
        pursueBean1.setPursueImage("https://i0.hdslb.com/bfs/archive/5894df7f7e8ccea3acbc33f124b0cbc9a24f1f22.jpg@120w_75h.webp");
        pursueBean1.setPursueName("某科学的超电磁炮T");
        pursueBean1.setToUpdate_word(19);

        PursueBean pursueBean3 = new PursueBean();
        pursueBean3.setPursueImage("https://i0.hdslb.com/bfs/archive/89c839e9be005ed074a8efb2afeb1e0fb53c7850.jpg@120w_75h.webp");
        pursueBean3.setPursueName("关于我转生变成史莱姆这档事");
        pursueBean3.setToUpdate_word(28);

        PursueBean pursueBean4 = new PursueBean();
        pursueBean4.setPursueImage("https://i0.hdslb.com/bfs/bangumi/3883837b7458fe93799591a59175d3fb26497b06.png@120w_75h.webp");
        pursueBean4.setPursueName("我的脑内选项正在全力妨碍学园恋爱喜剧");
        pursueBean4.setToUpdate_word(10);

        pursueBeans.add(pursueBean);
        pursueBeans.add(pursueBean1);
        pursueBeans.add(pursueBean3);
        pursueBeans.add(pursueBean4);
    }

    public List<PursueBean> getPursueBeans() {
        return pursueBeans;
    }

    public void initMyPursue() {
        myPursuitBeans.clear();
        MyPursuitBean pursueBean = new MyPursuitBean();
        pursueBean.setMyPursueImage("https://i0.hdslb.com/bfs/archive/0fe50fefc0def68b88a32b2be67f3b63791419a3.jpg@120w_75h.webp");
        pursueBean.setMyPursueName("刀剑神域 爱丽丝篇 异界战争 -终章-");
        pursueBean.setToUpdate_word(18);
        pursueBean.setWatch_Situation("看到第13话");

        MyPursuitBean pursueBean1 = new MyPursuitBean();
        pursueBean1.setMyPursueImage("https://i0.hdslb.com/bfs/bangumi/image/b827439e756028116cc87747d3a01569d7f1d47e.png@450w_600h.webp");
        pursueBean1.setMyPursueName("彼得·格里爾的賢者時間");
        pursueBean1.setToUpdate_word(19);
        pursueBean1.setWatch_Situation("尚未观看");

        MyPursuitBean pursueBean3 = new MyPursuitBean();
        pursueBean3.setMyPursueImage("https://i0.hdslb.com/bfs/bangumi/2c7cf455b32117c1c9e70313a564b0b189a5de90.jpg@450w_600h.webp");
        pursueBean3.setMyPursueName("RAIL WARS!-日本国有铁道公安队-");
        pursueBean3.setToUpdate_word(28);
        pursueBean3.setWatch_Situation("尚未观看");

        MyPursuitBean pursueBean4 = new MyPursuitBean();
        pursueBean4.setMyPursueImage("https://i0.hdslb.com/bfs/bangumi/image/8e0473261c426c453a0a1bc5cc0d836a4e985a0c.png@450w_600h.webp");
        pursueBean4.setMyPursueName("魔王學院的不適任者～史上最強的魔王始祖，轉生就讀子孫們的學校～");
        pursueBean4.setToUpdate_word(10);
        pursueBean4.setWatch_Situation("尚未观看");

        myPursuitBeans.add(pursueBean);
        myPursuitBeans.add(pursueBean1);
        myPursuitBeans.add(pursueBean3);
        myPursuitBeans.add(pursueBean4);
    }

    public List<MyPursuitBean> getMyPursuitBean() {
        return myPursuitBeans;
    }

    public void initFanRecommendation() {
        FanRecommendationBean fanRecommendationBean = new FanRecommendationBean();
        fanRecommendationBean.setCover("https://i0.hdslb.com/bfs/bangumi/image/3afcc9d66735123d6168c336b579c5d9920bb9ab.jpg@320w_200h.webp");
        fanRecommendationBean.setTitle("我的青春恋爱物语果然有问题。完");
        fanRecommendationBean.setSecondaryTitle("544.4万");
        fanRecommendationBean.setWatch("尚未观看");
        fanRecommendationBean.setExclusive(true);

        FanRecommendationBean fanRecommendationBean1 = new FanRecommendationBean();
        fanRecommendationBean1.setCover("https://i0.hdslb.com/bfs/bangumi/image/0d7c67a6cc50b6541fce7f3e7c4343894d453db6.jpg@320w_200h.webp");
        fanRecommendationBean1.setTitle("Re：从零开始的异世界生活 第二季");
        fanRecommendationBean1.setSecondaryTitle("758.5万");
        fanRecommendationBean1.setWatch("尚未观看");
        fanRecommendationBean1.setExclusive(true);

        FanRecommendationBean fanRecommendationBean2 = new FanRecommendationBean();
        fanRecommendationBean2.setCover("https://i0.hdslb.com/bfs/bangumi/image/c51106d352db6751e7a64d84c324db5cac2690c5.png@320w_200h.webp");
        fanRecommendationBean2.setTitle("知晓天空之蓝的人啊");
        fanRecommendationBean2.setSecondaryTitle("50.2万");
        fanRecommendationBean2.setWatch("尚未观看");
        fanRecommendationBean2.setExclusive(false);

        FanRecommendationBean fanRecommendationBean3 = new FanRecommendationBean();
        fanRecommendationBean3.setCover("https://i0.hdslb.com/bfs/bangumi/image/16976d6ee9524f18d24aaf6b366095df51074988.gif");
        fanRecommendationBean3.setTitle("刀剑神域 爱丽丝篇 异界战争 -终章-");
        fanRecommendationBean3.setSecondaryTitle("691.4万");
        fanRecommendationBean3.setWatch("以观看到13话");
        fanRecommendationBean3.setExclusive(true);

        fanRecommendationBeans.add(fanRecommendationBean);
        fanRecommendationBeans.add(fanRecommendationBean1);
        fanRecommendationBeans.add(fanRecommendationBean2);
        fanRecommendationBeans.add(fanRecommendationBean3);
    }

    public List<FanRecommendationBean> getFanRecommendationBeans() {
        return fanRecommendationBeans;
    }

    /**
     * by moos on 2018/04/20
     * func:生成测试数据
     *
     * @return 评论数据
     */
//    public List<CommentDetailBean> generateTestData() {
//        Gson gson = new Gson();
//        commentBean = gson.fromJson(testJson, CommentBean.class);
//        List<CommentDetailBean> commentList = commentBean.getData().getList();
//        return commentList;
//    }

}
