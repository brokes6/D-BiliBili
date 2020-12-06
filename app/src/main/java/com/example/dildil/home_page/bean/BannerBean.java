package com.example.dildil.home_page.bean;

import java.util.List;

public class BannerBean {
    private int code;
    private List<BannerList> data;
    private String message;

    public static class BannerList {
        private String image;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<BannerList> getData() {
        return data;
    }

    public void setData(List<BannerList> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
