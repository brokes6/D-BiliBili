package com.example.dildil.video.bean;

import java.util.List;

public class DanmuBean {
    private int code;
    private List<Datas> data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Datas> getData() {
        return data;
    }

    public void setData(List<Datas> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Datas{
        private int id;
        private int vid;
        private int showSecond;
        private int uid;
        private boolean location;
        private String color;
        private String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getVid() {
            return vid;
        }

        public void setVid(int vid) {
            this.vid = vid;
        }

        public int getShowSecond() {
            return showSecond;
        }

        public void setShowSecond(int showSecond) {
            this.showSecond = showSecond;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public boolean isLocation() {
            return location;
        }

        public void setLocation(boolean location) {
            this.location = location;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
