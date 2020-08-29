package com.example.dildil.home_page.bean;

import java.util.List;

public class RecommendVideoBean {
    private int code;
    private List<BeanData> data;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<BeanData> getData() {
        return data;
    }

    public void setData(List<BeanData> data) {
        this.data = data;
    }

    public class BeanData{
        private int id;
        private int uid;
        private int playNum;
        private int praiseNum;
        private int danmunum;
        private int length;
        private String upImg;
        private String upName;
        private String title;
        private String cover;
        private String previewUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getPlayNum() {
            return playNum;
        }

        public void setPlayNum(int playNum) {
            this.playNum = playNum;
        }

        public int getPraiseNum() {
            return praiseNum;
        }

        public void setPraiseNum(int praiseNum) {
            this.praiseNum = praiseNum;
        }

        public int getDanmunum() {
            return danmunum;
        }

        public void setDanmunum(int danmunum) {
            this.danmunum = danmunum;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public String getUpImg() {
            return upImg;
        }

        public void setUpImg(String upImg) {
            this.upImg = upImg;
        }

        public String getUpName() {
            return upName;
        }

        public void setUpName(String upName) {
            this.upName = upName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getPreviewUrl() {
            return previewUrl;
        }

        public void setPreviewUrl(String previewUrl) {
            this.previewUrl = previewUrl;
        }
    }
}
